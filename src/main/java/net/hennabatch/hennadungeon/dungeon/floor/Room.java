package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class Room extends Floor{

    Vec2d upperLeft;
    Vec2d lowerRight;

    public Room(Vec2d upperLeft, Vec2d lowerRight){
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }

    public Vec2d getUpperLeft(){
        return upperLeft.clone();
    }

    public Vec2d getLowerRight(){
        return lowerRight.clone();
    }

    public Vec2d size(){
        return this.lowerRight.sub(this.upperLeft).add(new Vec2d(1, 1));
    }

    @Override
    public Boolean isInner(IVec vec) {
        return vec.getX() >= this.getUpperLeft().getX() && vec.getX() <= this.getLowerRight().getX() && vec.getY() >= this.getUpperLeft().getY() && vec.getY() <= this.getLowerRight().getY();
    }

    @Override
    public String toString() {
        return "room uLx: " + getUpperLeft().getX() +" uLy: " + getUpperLeft().getY() +
                "\tlRx: " + getLowerRight().getX() + " lRy: " + getLowerRight().getY();
    }
}
