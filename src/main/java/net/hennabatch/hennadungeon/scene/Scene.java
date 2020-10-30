package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

public abstract class Scene<T> {

    protected Screen screen = new Screen(Reference.SCREEN_WIDTH, Reference.SCREEN_HEIGHT);
    private Scene childScene;

    protected void initializeScene(){}
    protected void finalizeScene(){}

    public SceneResult inputKey(EnumKeyInput key){
        SceneResult result = new SceneResult(false, null);
        if(getChildScene() != null){
            result = getChildScene().inputKey(key);
            if(result.isChildSceneContinue()) {
                removeChildScene();
            }
        }else{
            result = run(key, result);
        }
        return result;
    }

    //シーン内ループ用メイン
    abstract SceneResult<T> run(EnumKeyInput key, SceneResult<T> childSceneResult);

    //描画用
    public Screen draw(){
        return this.childScene != null ? this.screen.overWrite(this.childScene.draw()) : this.screen;
    }

    //子シーンの生成
    protected void createChildScene(Scene scene){
        this.childScene = scene;
        this.childScene.initializeScene();
    }

    protected Scene getChildScene(){
        return this.childScene;
    }

    protected void removeChildScene(){
        this.childScene.finalizeScene();
        this.childScene = null;
    }

}
