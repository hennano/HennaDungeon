package net.hennabatch.hennadungeon.config;

public enum EnumRunMode {

    DEBUG("debug"),
    RELEASE("release");

    private String notation;

    private EnumRunMode(String notation){
        this.notation = notation;
    }

}
