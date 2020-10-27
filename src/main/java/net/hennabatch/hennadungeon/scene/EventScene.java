package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;

abstract class EventScene<T> extends Scene<T>{

    private int sceneNumber = 0;

    @Override
    SceneResult<T> run(EnumKeyInput key, SceneResult<T> childSceneResult) {
        SceneResult<T> result =  eventSequence(this.sceneNumber, key, childSceneResult);
        sceneNumber++;
        return result;
    }

    abstract SceneResult<T> eventSequence(int eventNumber, EnumKeyInput key, SceneResult<T> childSceneResult);
}
