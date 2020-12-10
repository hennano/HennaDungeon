package net.hennabatch.hennadungeon.scene;

public class SceneResult {

    private final boolean isChildSceneContinue;
    private final Object data;

    public SceneResult(boolean isChildSceneContinue, Object data){
        this.isChildSceneContinue = isChildSceneContinue;
        this.data = data;
    }

    public boolean isChildSceneContinue(){
        return isChildSceneContinue;
    }

    public Object data(){
        return data;
    }
}
