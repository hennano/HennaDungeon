package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.DungeonBuilder;
import net.hennabatch.hennadungeon.scene.event.Event;

public class GameScene extends Scene{

    @Override
    protected void initializeScene() {
        DungeonBuilder builder = new DungeonBuilder();
        builder.build(this);
    }

    @Override
     protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        return null;
    }

    @Override
    protected Screen draw(Screen screen) {
        return screen;
    }

    public void executeEvent(Event event){
        createChildScene(event);
    }
}
