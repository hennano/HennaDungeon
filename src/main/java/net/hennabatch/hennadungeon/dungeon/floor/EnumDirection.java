package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.dungeon.DungeonBuilder;

import java.util.Arrays;
import java.util.Random;

public enum EnumDirection {

    X(0),
    Y(1),
    NX(2),
    NY(3);

    private int num;

    EnumDirection(int num) {
        this.num = num;
    }

    public static EnumDirection random() {
        int num = new Random().nextInt(2);
        return byNum(num);
    }

    public EnumDirection switchDirection() {
        int num = (this.num + 1) % 2;
        return byNum(num);
    }

    private static EnumDirection byNum(int num) {
        return Arrays.stream(EnumDirection.values()).filter(x -> x.num == num).findFirst().orElse(EnumDirection.X);
    }
}
