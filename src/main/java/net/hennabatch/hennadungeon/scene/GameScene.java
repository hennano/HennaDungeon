package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.DungeonBuilder;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.sql.Ref;

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
        Vec2d playerPos = Vec2d.byIVec(dungeon.getPlayer());
        for(int sx = 1; sx < screen.getWidth() - 1; sx++){
            for (int sy = 2; sy < screen.getHeight() - 2; sy++){
                screen.setPos(sx, sy, dungeon.isInner(new Vec2d(sx - ((screen.getWidth() - 2) / 2), sy - ((screen.getHeight() - 4) / 2)).add(playerPos)) ? Reference.DUNGEON_SPACE : Reference.DUNGEON_WALL);
            }
        }
    }

    public void drawEntity(Screen screen){
        screen.setPos(screen.getWidth() / 2, screen.getHeight() / 2, dungeon.getPlayer().getIcon());
    }

    public void executeEvent(Event event){
        createChildScene(event);
    }
}
