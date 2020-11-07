package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class Passage extends Floor{

    private Vec2d upperLeft;
    private Vec2d lowerRight;
    private Vec2d connectPos;

    public Vec2d getUpperLeft() {
        return upperLeft;
    }

    public Vec2d getLowerRight() {
        return lowerRight;
    }

    public Vec2d getConnectPos() {
        return connectPos;
    }

    public Passage(Vec2d upperLeft, Vec2d lowerRight, Vec2d connectPos){
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.connectPos = connectPos;
    }

    @Override
    public Boolean isInner(IVec vec) {
        return false;
    }

    @Override
    public String toString() {
        return "passage uLx: " + getUpperLeft().getX() +" uLy: " + getUpperLeft().getY() +
                "\tlRx: " + getLowerRight().getX() + " lRy: " + getLowerRight().getY() +
                "\tconnectPos x: " + getConnectPos().getX() + " y: " + getConnectPos().getY();
    }
}
