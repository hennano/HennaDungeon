package net.hennabatch.hennadungeon.config;

public enum EnumRunMode {

    DEBUG("debug"),
    RELEASE("release");

    private final String notation;

    EnumRunMode(String notation){
        this.notation = notation;
    }

}
