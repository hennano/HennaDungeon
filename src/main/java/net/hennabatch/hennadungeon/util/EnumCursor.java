package net.hennabatch.hennadungeon.util;

public enum EnumCursor {
    UP("⯅"),
    DOWN("⯆"),
    LEFT("⯇"),
    RIGHT("⯈");


    private String cursor;

    EnumCursor(String cursor){
        this.cursor = cursor;
    }

    public String getCursor(){
        return cursor;
    }
}
