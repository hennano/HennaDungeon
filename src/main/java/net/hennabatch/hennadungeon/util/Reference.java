package net.hennabatch.hennadungeon.util;

import net.hennabatch.hennadungeon.config.Config;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.character.*;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final int PLAYER_SKILL_COOLTIME = 20;
    public static final int SPAWN_ENEMIES_PER_ROOM = 2;
    public static final int SPAWN_ITEMS_PER_ROOM = 1;
    public static final List<Class<? extends EnemyEntity>> SPANNABLE_ENEMIES = new ArrayList<>(Arrays.asList(
            BatEntity.class,
            GoblinEntity.class,
            SlimeEntity.class,
            WitchEntity.class,
            MimicEntity.class
    ));
    public static final List<Item> SPANNABLE_ITEMS = new ArrayList<>(Arrays.asList(
            Items.HEAL_POTION,
            Items.ATK_BUFF_POTION,
            Items.DEF_BUFF_POTION,
            Items.MDEF_BUFF_POTION,
            Items.REGENRATION_POTION
    ));
    public static final List<Item> SPANNABLE_WEAPONS = new ArrayList<>(Arrays.asList(
            Items.SWORD,
            Items.LONGSWORD,
            Items.MAGICALSWORD,
            Items.GRIMOIRE,
            Items.NECRONOMICON,
            Items.POISON_DAGGER,
            Items.BOW
    ));
    public static final List<Item> SPANNABLE_ARMORS = new ArrayList<>(Arrays.asList(
            Items.LEATHER_ARMOR,
            Items.IRON_ARMOR,
            Items.CROTHES,
            Items.ROBE
    ));
    public static final int SPAWN_TRAP_COUNT = 5;

    public static final String DUNGEON_SPACE = SCREEN_EMPTY;
    public static final String DUNGEON_WALL = "■";
    public static final String DUNGEON_EXITPATH = "・";

    public static final String WEAPON_RANGE = "※";

    //state
    public static final int MAX_MDEFEND = 80;
    public static final int MAX_EVASION = 80;
}
