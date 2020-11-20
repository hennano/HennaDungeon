package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements IVec {

    private int x;
    private int y;
    private final Dungeon dungeon;
    private boolean isHidden = false;

    public Entity(Vec2d pos, Dungeon dungeon){
        setPos(pos);
        this.dungeon = dungeon;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(IVec pos){
        setX(pos.getX());
        setY(pos.getY());
    }

    protected Dungeon getDungeon(){
        return this.dungeon;
    }

    public abstract void update();

    protected void onTrigger(Entity triggeredEntity){}

    protected void onDestroy(){}

    public abstract void initilaize();

    public abstract String getIcon();

    public void destroy() {
        onDestroy();
        getDungeon().getEntities().removeIf(x -> x.equals(this));
    }

    public abstract String name();

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public void move(EnumDirection direction, int length){
        IVec currentPos = this;
        List<Entity> triggeredEntities = new ArrayList<>();
        CollidableEntity collidableEntity = null;
        for(Vec2d i = direction.vec(); !i.equals(direction.vec().dot(length + 1)); i = i.add(direction.vec())){
            Vec2d checkPos = i.add(this);
            Reference.logger.debug(checkPos.toString() +": " + dungeon.isInner(checkPos));
            if(!dungeon.isInner(checkPos)) break;
            List<Entity> entities = dungeon.getEntityByIVec(checkPos);
            if(entities.stream().anyMatch(x -> x instanceof CollidableEntity)){
                collidableEntity = (CollidableEntity) entities.stream().filter(x -> x instanceof CollidableEntity).findFirst().get();
                break;
            }else if(entities.size() > 0) triggeredEntities.addAll(entities);
            currentPos = checkPos;
        }
        setPos(currentPos);
        Reference.logger.debug(this.name() + " moveTo x: " + getX() + " y: "+ getY());
        triggeredEntities.forEach(x -> x.onTrigger(this));
        if(collidableEntity != null) collidableEntity.onCollision(this);
    }

    public int tryMove(EnumDirection direction, int length){
        IVec currentPos = this;
        for(Vec2d i = direction.vec(); !direction.vec().equals(direction.vec().dot(length)); i = i.add(direction.vec())){
            Vec2d checkPos = i.add(this);
            if(!dungeon.isInner(checkPos)) break;
            if(dungeon.getEntityByIVec(checkPos).stream().anyMatch(x -> x instanceof CollidableEntity)) break;
            currentPos = i;
        }
        return Math.abs(new Vec2d(currentPos).add(1).area() - 1);
    }
}