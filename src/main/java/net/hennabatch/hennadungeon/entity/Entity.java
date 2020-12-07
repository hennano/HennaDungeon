package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.mission.Tag;
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
    private boolean isDestroy = false;
    private List<Tag> tags = new ArrayList<>();
    private int turn = 0;

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

    public Dungeon getDungeon(){
        return this.dungeon;
    }

    public void update(){
        turn++;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public int getTurn() {
        return turn;
    }

    protected void onTrigger(Entity triggeredEntity){}

    protected void onDestroy(){}

    public abstract void initialize();

    public abstract String getIcon();

    public void setDestroy(boolean bool){
        this.isDestroy = bool;
    }

    public boolean isDestroy(){
        return isDestroy;
    }

    public void destroy() {
        onDestroy();
        Reference.logger.debug(this.name() +" destroy");
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
            List<Entity> entities = dungeon.getEntitiesByIVec(checkPos);
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
        Vec2d move = new Vec2d(0, 0);
        for(Vec2d i = direction.vec(); !i.equals(direction.vec().dot(length + 1)); i = i.add(direction.vec())){
            Vec2d checkPos = i.add(this);
            if(!dungeon.isInner(checkPos)) break;
            if(dungeon.getEntitiesByIVec(checkPos).stream().anyMatch(x -> x instanceof CollidableEntity)) break;
            move = i;
        }
        return Math.abs(new Vec2d(move).add(1).area() - 1);
    }
}