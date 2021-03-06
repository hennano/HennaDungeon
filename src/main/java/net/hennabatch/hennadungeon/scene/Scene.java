package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

public abstract class Scene {

    public Scene(){
        Reference.logger.debug("create scene: " + this.getClass().getSimpleName());
        this.initializeScene();
    }

    private Scene childScene;

    protected void initializeScene(){}
    protected void finalizeScene(){}

    public SceneResult inputKey(EnumKeyInput key){
        SceneResult result = new SceneResult(false, null);
        if(getChildScene() != null){
            result = getChildScene().inputKey(key);
            if(!result.isChildSceneContinue()) {
                removeChildScene();
                result = onExitChildScene(result);
            }
        }else{
            Reference.logger.debug("runScreen:" + this.getClass().getSimpleName());
            result = run(key, result);
        }
        return result;
    }

    //シーン内ループ用メイン
    protected abstract SceneResult run(EnumKeyInput key, SceneResult childSceneResult);

    public Screen drawScreen(Screen screen){
        return this.childScene != null ? this.childScene.drawScreen(draw(screen)): draw(screen);
    }

    //描画用
    protected abstract Screen draw(Screen screen);

    //子シーンの生成
    protected void createChildScene(Scene scene){
        this.childScene = scene;
    }

    protected Scene getChildScene(){
        return this.childScene;
    }

    protected void removeChildScene(){
        this.childScene.finalizeScene();
        Reference.logger.debug("remove child scene: " + childScene.getClass().getSimpleName());
        this.childScene = null;
    }

    //子シーンの処理結果を反映させる
    protected SceneResult onExitChildScene(SceneResult result){
        return new SceneResult(true, false);
    }

}
