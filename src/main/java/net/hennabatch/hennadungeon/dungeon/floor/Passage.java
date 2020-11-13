package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class Passage extends Floor{

    private final Vec2d upperLeft;
    private final Vec2d lowerRight;
    private final Vec2d connectPos;

    public Passage(Vec2d upperLeft, Vec2d lowerRight, Vec2d connectPos){
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.connectPos = connectPos;
    }

    public Vec2d getUpperLeft() {
        return upperLeft;
    }

    public Vec2d getLowerRight() {
        return lowerRight;
    }

    public Vec2d getConnectPos() {
        return connectPos;
    }

    @Override
    public Boolean isInner(IVec vec) {
        //バグあり　修正予定
        if(getConnectPos().getY() == 0){
            if(vec.getX() >= getUpperLeft().getX() && vec.getX() <= getConnectPos().getX() && vec.getY() == getUpperLeft().getY()) return true;
            if(vec.getX() >= getConnectPos().getX() && vec.getX() <= getLowerRight().getX() && vec.getY() == getLowerRight().getY()) return true;
            if(getUpperLeft().getY() < getLowerRight().getY()){
                return vec.getY() >= getUpperLeft().getY() && vec.getY() <= getLowerRight().getY() && vec.getX() == getConnectPos().getX();
            }else{
                return vec.getY() >= getLowerRight().getY() && vec.getY() <= getUpperLeft().getY() && vec.getX() == getConnectPos().getX();
            }
        }else{
            if(vec.getY() >= getUpperLeft().getY() && vec.getY() <= getConnectPos().getY() && vec.getX() == getUpperLeft().getX()) return true;
            if(vec.getY() >= getConnectPos().getY() && vec.getY() <= getLowerRight().getY() && vec.getX() == getLowerRight().getX()) return true;
            if(getUpperLeft().getX() < getLowerRight().getX()){
                return vec.getX() >= getUpperLeft().getX() && vec.getX() <= getLowerRight().getX() && vec.getY() == getConnectPos().getY();
            }else{
                return vec.getX() >= getLowerRight().getX() && vec.getX() <= getUpperLeft().getX() && vec.getY() == getConnectPos().getY();
            }
        }
    }

    @Override
    public String toString() {
        return "passage uLx: " + getUpperLeft().getX() +" uLy: " + getUpperLeft().getY() +
                "\tlRx: " + getLowerRight().getX() + " lRy: " + getLowerRight().getY() +
                "\tconnectPos x: " + getConnectPos().getX() + " y: " + getConnectPos().getY();
    }
}
