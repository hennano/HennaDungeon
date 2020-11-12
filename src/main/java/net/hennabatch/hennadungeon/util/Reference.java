package net.hennabatch.hennadungeon.util;

import net.hennabatch.hennadungeon.config.Config;

public class Reference {

    public static final int SCREEN_WIDTH = 20;
    public static final int SCREEN_HEIGHT = 20;

    public static final SystemLogger logger = new SystemLogger();
    public static final Config config = new Config();


    //dungeon
    public static final int DUNGEON_WIDTH = 100;
    public static final int DUNGEON_HEIGHT = 100;
    public static final int DUNGEON_MAXROOMS = 25;
    public static final int DUNGEON_MIN_ROOMWIDTH = 3;
    public static final int DUNGEON_MIN_ROOMHEIGTH = 3;
    public static final double DUNGEON_CONNECT_CHANCE = 0.5;


    //state
    public static final int MAX_MDEFEND = 80;
    public static final int MAX_EVASION = 80;
}
