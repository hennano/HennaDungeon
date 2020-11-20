package net.hennabatch.hennadungeon.util;

import net.hennabatch.hennadungeon.config.Config;

public class Reference {

    public static final SystemLogger logger = new SystemLogger();
    public static final Config config = new Config();

    //screen
    public static final int SCREEN_WIDTH = 20;
    public static final int SCREEN_HEIGHT = 20;
    public static final String SCREEN_EMPTY = "　";
    public static final String CURSOR_UP = "⯅ ";
    public static final String CURSOR_DOWN = "⯆ ";
    public static final String CURSOR_LEFT = "⯇ ";
    public static final String CURSOR_RIGHT = " ⯈";
    public static final String HORIZONTAL_LINE = "―";
    public static final String VERTICAL_LINE = "｜";
    public static final String CROSS = "＋";

    //dungeon
    public static final int DUNGEON_WIDTH = 100;
    public static final int DUNGEON_HEIGHT = 100;
    public static final int DUNGEON_MAXROOMS = 25;
    public static final int DUNGEON_MIN_ROOMWIDTH = 5;
    public static final int DUNGEON_MIN_ROOMHEIGTH = 5;
    public static final double DUNGEON_CONNECT_CHANCE = 0.3;

    public static final String DUNGEON_SPACE = SCREEN_EMPTY;
    public static final String DUNGEON_WALL = "■";

    public static final String WEAPON_RANGE = "※";

    //state
    public static final int MAX_MDEFEND = 80;
    public static final int MAX_EVASION = 80;
}
