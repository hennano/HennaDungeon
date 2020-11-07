package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class Passage extends Floor{

    private Vec2d upperLeft;
    private Vec2d lowerRight;
    private Vec2d connectPos;

    public Passage(Vec2d upperLeft, Vec2d lowerRight, Vec2d connectPos){
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.connectPos = connectPos;
    }

    @Override
    public Boolean isInner(IVec vec) {
        return false;
    }
}
