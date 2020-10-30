package net.hennabatch.hennadungeon.scene;

public class SceneResult<T>{

    private boolean isChildSceneContinue;
    private T data;

    public SceneResult(boolean isChildSceneContinue, T data){
        this.isChildSceneContinue = isChildSceneContinue;
        this.data = data;
    }

    public boolean isChildSceneContinue(){
        return isChildSceneContinue;
    }

    public T data(){
        return data;
    }
}
