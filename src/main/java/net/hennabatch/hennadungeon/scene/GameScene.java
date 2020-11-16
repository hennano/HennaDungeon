package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.DungeonBuilder;
import net.hennabatch.hennadungeon.entity.CollidableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.List;

public class GameScene extends Scene{

    private Dungeon dungeon;

    @Override
    protected void initializeScene() {
        DungeonBuilder builder = new DungeonBuilder();
        dungeon = builder.build(this);
    }

    @Override
     protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        switch (key){
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                dungeon.getPlayer().move(EnumDirection.byKey(key), 1);
                break;
            case MENU:
                createChildScene(new MainMenuScene(dungeon));
        }
        return new SceneResult(true, null);
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

    public void drawEntity(Screen screen){
        Vec2d playerPos = new Vec2d(dungeon.getPlayer());
        for(int sx = 1; sx < screen.getWidth() - 1; sx++){
            for (int sy = 2; sy < screen.getHeight() - 2; sy++){
                List<Entity> entities = dungeon.getEntityByIVec(new Vec2d(sx - ((screen.getWidth() - 2) / 2), sy - ((screen.getHeight() - 4) / 2)).add(playerPos));
                if(entities.size() > 0){
                    Entity entity;
                    if(entities.stream().anyMatch(x -> x instanceof CollidableEntity)){
                        entity = entities.stream().filter(x -> x instanceof CollidableEntity)
                                .findFirst().get();
                    }else{
                        entity = entities.stream().findFirst().get();
                    }
                    screen.setPos(sx, sy, entity.getIcon());
                }
            }
        }
        screen.setPos(screen.getWidth() / 2 - 1, screen.getHeight() / 2 - 2, dungeon.getPlayer().getIcon());
    }

    public void executeEvent(Event event){
        createChildScene(event);
    }
}
