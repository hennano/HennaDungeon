package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.*;
import net.hennabatch.hennadungeon.util.Reference;

public class RootEvent extends Event{

    @Override
    protected void initializeScene(){
        createChildScene(new StartScene());
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        if(childSceneResult.data() instanceof SceneTransition){
            SceneTransition result = (SceneTransition)(childSceneResult.data());
            if(result.isExit()){
                return new SceneResult(false, null);
            }else{
                createChildScene(result.createScene());
                return new SceneResult(true, null);
            }
        }
        return new SceneResult(true, null);
    }

    public enum SceneTransition{

        StartScene(StartScene.class),
        GameScene(GameScene.class),
        ShowRule(ShowRuleScene.class),
        Exit(null);

        private Class clazz;

        SceneTransition(Class<? extends Scene> scene){
            this.clazz = scene;
        }

        public Scene createScene(){
            try {
                return (Scene)this.clazz.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                Reference.logger.error(e.getMessage(), e);
                return null;
            }
        }

        public boolean isExit(){
            return this.clazz == null;
        }
    }

}
