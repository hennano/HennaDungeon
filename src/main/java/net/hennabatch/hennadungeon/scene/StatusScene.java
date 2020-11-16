package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;

public class StatusScene extends Scene{
    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        return new SceneResult<>(false, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        return screen;
    }
}
