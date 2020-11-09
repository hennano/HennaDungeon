package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class Entity implements IVec {

    protected Vec2d pos;
    private Dungeon dungeon;
    private boolean isDestroy = false;

    public Entity(Vec2d pos, Dungeon dungeon){
        this.pos = pos;
        this.dungeon = dungeon;
    }

    @Override
    public int getX() {
        return pos.getX();
    }

    @Override
    public int getY() {
        return pos.getY();
    }

    protected Dungeon getDungeon(){
        return this.dungeon;
    }

    public abstract void update();

    protected abstract void onTrigger(Entity triggeredEntity);

    protected abstract void initilaize();

    public abstract String getIcon();

    public Boolean isDestroy(){
        return this.isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public abstract String name();
}
