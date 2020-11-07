package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class Entity implements IVec {

    protected Vec2d pos;
    protected Dungeon dungeon;

    @Override
    public int getX() {
        return pos.getX();
    }

    @Override
    public int getY() {
        return pos.getY();
    }
}
