package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;

public class RootEvent extends EventScene{

    @Override
    protected void initializeScene(){
        createChildScene(new StartScene());
    }

    @Override
    SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        if(childSceneResult.data() instanceof StartScene.EnumStartSceneResult){
            StartScene.EnumStartSceneResult result = (StartScene.EnumStartSceneResult)(childSceneResult.data());
            switch (result){
                case EXIT:
                    return new SceneResult(false, null);
                case START:
                    createChildScene(new GameScene());
                    return new SceneResult(true, null);

            }
        }
        return new SceneResult(true, null);
    }
}
