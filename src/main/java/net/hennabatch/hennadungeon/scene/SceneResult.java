package net.hennabatch.hennadungeon.scene;

public class SceneResult<T>{

    private EnumResult result;
    private T data;

    public SceneResult(EnumResult result, T data){
        this.result = result;
        this.data = data;
    }

    public EnumResult result(){
        return result;
    }

    public T data(){
        return data;
    }

    public enum EnumResult{
        CONTINUE(),     //このシーンでの処理を続行
        NEXT(),         //このシーンでの処理を終了し、上位シーンの処理を行う
        EXIT();         //このシーンでの処理を終了し、上位シーンに処理を戻す

    }
}
