package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;

public class RootEvent extends EventScene{

    @Override
    SceneResult eventSequence(int eventNumber, EnumKeyInput key, SceneResult childSceneResult) {
        if(childSceneResult.data() instanceof StartScene.EnumStartSceneResult){
            StartScene.EnumStartSceneResult result = (StartScene.EnumStartSceneResult)(childSceneResult.data());
            switch (result){
                case EXIT:
                    return new SceneResult(SceneResult.EnumResult.NEXT, null);
                case START:
                    createChildScene(new GameScene());
                    return new SceneResult(SceneResult.EnumResult.CONTINUE, null);

            }
        }
        return new SceneResult(SceneResult.EnumResult.CONTINUE, null);
    }
}
