package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.DungeonBuilder;
import net.hennabatch.hennadungeon.entity.CollidableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.scene.menu.MainMenuScene;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.Comparator;
import java.util.List;

public class GameScene extends net.hennabatch.hennadungeon.scene.Scene {

    private Dungeon dungeon;
    private boolean readyToAttack = false;

    @Override
    protected void initializeScene() {
        DungeonBuilder builder = new DungeonBuilder();
        dungeon = builder.build(this);
        updateMissions();
    }

    @Override
     protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        boolean isNext = playerAction(key);
        updateMissions();
        if(!isNext) return new SceneResult(true, null);
        //近い順に敵の行動処理
        dungeon.getEntities().stream().sorted(Comparator.comparing(x -> new Vec2d(dungeon.getPlayer()).distance(x))).forEach(x -> x.update());
        return new SceneResult(true, null);
    }

    private boolean playerAction(EnumKeyInput key){
        switch (key){
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                if(readyToAttack){
                    dungeon.getPlayer().attack(EnumDirection.byKey(key));
                    if(!Reference.config.enabledToggleAttack){
                        toggleReadyToAttack();
                    }
                }else{
                    dungeon.getPlayer().move(EnumDirection.byKey(key), 1);
                }
                return true;
            case MENU:
                createChildScene(new MainMenuScene(dungeon));
                break;
            case ENTER:
                toggleReadyToAttack();
                break;
            case SKILL:
                if(dungeon.getPlayer().canUseSkill()){
                    dungeon.getPlayer().useSkill();
                    Reference.logger.info("スキルを使用した！");
                    return true;
                }else{
                    Reference.logger.info("スキルは今は使えない");
                }
        }
        return false;
    }

    private void updateMissions(){
        dungeon.getMissions().forEach(x -> x.update());
    }

    @Override
    protected SceneResult onExitChildScene(SceneResult result) {
        if(result.data() instanceof RootEvent.SceneTransition){
            if(((RootEvent.SceneTransition) result.data()).isExit()) return new SceneResult(false, RootEvent.SceneTransition.StartScene);
        }
        return new SceneResult(true, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        drawMap(screen);
        drawEntity(screen);
        drawUI(screen);
        return screen;
    }

    private void drawMap(Screen screen){
        Vec2d playerPos = new Vec2d(dungeon.getPlayer());
        for(int sx = 1; sx < screen.getWidth() - 1; sx++){
            for (int sy = 2; sy < screen.getHeight() - 2; sy++){
                screen.setPos(sx, sy, dungeon.isInner(new Vec2d(sx - ((screen.getWidth() - 2) / 2), sy - ((screen.getHeight() - 4) / 2)).add(playerPos)) ? Reference.DUNGEON_SPACE : Reference.DUNGEON_WALL);
            }
        }
    }

    private void drawEntity(Screen screen){
        for(int sx = 1; sx < screen.getWidth() - 1; sx++){
            for (int sy = 2; sy < screen.getHeight() - 2; sy++){
                List<Entity> entities = dungeon.getEntitiesByIVec(new Vec2d(sx - ((screen.getWidth() - 2) / 2), sy - ((screen.getHeight() - 4) / 2)).add(dungeon.getPlayer()));
                if(entities.size() > 0){
                    Entity entity;
                    if(entities.stream().filter(x -> !x.isHidden()).anyMatch(x -> x instanceof CollidableEntity)){
                        entity = entities.stream().filter(x -> !x.isHidden()).filter(x -> x instanceof CollidableEntity)
                                .findFirst().get();
                    }else{
                        entity = entities.stream().filter(x -> !x.isHidden()).findFirst().get();
                    }
                    screen.setPos(sx, sy, entity.getIcon());
                }
            }
        }
    }

    private void drawUI(Screen screen){
        screen.setRow(1,0, "HP", false, false);
        screen.drawGauge(2, 0, screen.getWidth() - 4, dungeon.getPlayer().getCurrentHP(), dungeon.getPlayer().getMaxHP(), "※", Reference.SCREEN_EMPTY, true);
    if(readyToAttack) screen.setRow(1, screen.getHeight() - 1, "攻撃準備状態", false, false);
    }

    public void executeScene(Scene scene){
        createChildScene(scene);
    }

    private void toggleReadyToAttack(){
        readyToAttack = !readyToAttack;
    }
}
