package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.DungeonBuilder;
import net.hennabatch.hennadungeon.dungeon.floor.ExitRoom;
import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.effect.IUnmovable;
import net.hennabatch.hennadungeon.entity.CollidableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.scene.menu.MainMenuScene;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

public class GameScene extends Scene {

    private Dungeon dungeon;
    private boolean readyToAttack = false;
    private boolean seeExitPath = false;
    private Deque<Scene> eventQueue = new ArrayDeque<>();

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
        dungeon.getEntities().stream()
                .sorted(Comparator.comparing(x -> new Vec2d(dungeon.getPlayer()).distance(x)))
                .filter(x -> new Vec2d(dungeon.getPlayer()).distance(x) < 10)
                .forEach(Entity::update);
        dungeon.removeIfEntity(Entity::isDestroy);
        return new SceneResult(true, null);
    }

    private boolean playerAction(EnumKeyInput key){
        //あたま悪いコード過ぎて差し替えたい
        switch (key){
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                if(dungeon.getPlayer().getStatus().getEffects().stream().anyMatch(x -> x instanceof IUnmovable)){
                    Reference.logger.info(dungeon.getPlayer().name() + "は体がしびれて動けない");
                    return true;
                }
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
                    if(dungeon.getPlayer().getStatus().getEffects().stream().anyMatch(x -> x instanceof IUnmovable)){
                        Reference.logger.info(dungeon.getPlayer().name() + "は体がしびれて動けない");
                        return true;
                    }
                    dungeon.getPlayer().useSkill();
                    Reference.logger.info("スキルを使用した！");
                    return true;
                }else{
                    Reference.logger.info("スキルは今は使えない");
                }
                break;
            case SEEPATH:
                Reference.logger.info("出口への道を思い出した");
                seeExitPath = true;
        }
        return false;
    }

    private void updateMissions(){
        dungeon.getMissions().forEach(Mission::update);
    }

    @Override
    protected SceneResult onExitChildScene(SceneResult result){
        if(result.data() instanceof RootEvent.SceneTransition){
            if(((RootEvent.SceneTransition) result.data()).isExit()){
                return new SceneResult(false, RootEvent.SceneTransition.StartScene);
            }else{
                return new SceneResult(false, result.data());
            }
        }else if(eventQueue.size() > 0) {
            createChildScene(eventQueue.pollFirst());
        }
        return new SceneResult(true, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        drawMap(screen);
        drawEntity(screen);
        if(seeExitPath){
            drawExitPath(screen);
            seeExitPath = false;
        }
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
                if(entities.stream().anyMatch(x -> !x.isHidden())){
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

    private void drawExitPath(Screen screen){
        Vec2d playerPos = new Vec2d(dungeon.getPlayer());
        Floor currentFloor = dungeon.getInnerFloors(playerPos).stream().min(Comparator.comparing(Floor::exitRoomDistance)).get();
        if(currentFloor instanceof ExitRoom) return;
        Floor exitFloor = currentFloor.getPathToExit().getFloor();
        for(int sx = 1; sx < screen.getWidth() - 1; sx++){
            for (int sy = 2; sy < screen.getHeight() - 2; sy++){
                if(exitFloor.isInner(new Vec2d(sx - ((screen.getWidth() - 2) / 2), sy - ((screen.getHeight() - 4) / 2)).add(playerPos))){
                    screen.setPos(sx, sy, Reference.DUNGEON_EXITPATH);
                }
            }
        }
    }

    public void executeScene(Scene scene){
        if(getChildScene() != null) {
            eventQueue.offerLast(scene);
        }else{
            createChildScene(scene);
        }
    }

    private void toggleReadyToAttack(){
        readyToAttack = !readyToAttack;
    }
}
