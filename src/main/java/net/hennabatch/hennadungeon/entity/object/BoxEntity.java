package net.hennabatch.hennadungeon.entity.object;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.CollidableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class BoxEntity extends CollidableEntity {

    private Item item;

    public BoxEntity(Vec2d pos, Dungeon dungeon, Item item) {
        super(pos, dungeon);
        this.item = item;
    }

    @Override
    public void onCollision(Entity collidedEntity) {

    }

    @Override
    public void initilaize() {

    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}
