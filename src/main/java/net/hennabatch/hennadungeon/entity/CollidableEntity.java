package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class CollidableEntity extends Entity{

    public CollidableEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    protected void onTrigger(Entity triggeredEntity) {
        return;
    }

    public abstract void onCollision(Entity collidedEntity);
}
