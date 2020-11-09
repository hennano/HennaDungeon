package net.hennabatch.hennadungeon.vec;

import java.util.Arrays;
import java.util.Random;

public enum EnumDirection {

    X(0, new Vec2d(1, 0)),
    Y(1, new Vec2d(0, 1)),
    NX(2, new Vec2d(-1, 0)),
    NY(3, new Vec2d(0, -1));

    private int num;
    private Vec2d vec;

    EnumDirection(int num, Vec2d vec) {
        this.num = num;
        this.vec = vec;
    }

    public static EnumDirection random() {
        int num = new Random().nextInt(2);
        return byNum(num);
    }

    public EnumDirection switchDirection() {
        int num = (this.num + 1) % 2;
        return byNum(num);
    }

    public EnumDirection switchOtherSide(){
        int num = (this.num + 2) % 4;
        return byNum(num);
    }

    public Vec2d vec(){
        return this.vec;
    }

    public int sin(){
        return this.vec.getY();
    }

    public int cos(){
        return this.vec.getX();
    }

    public EnumDirection normalize(){
        return byNum((4 - num) % 4);
    }

    private static EnumDirection byNum(int num) {
        return Arrays.stream(EnumDirection.values()).filter(x -> x.num == num).findFirst().orElse(EnumDirection.X);
    }
}
