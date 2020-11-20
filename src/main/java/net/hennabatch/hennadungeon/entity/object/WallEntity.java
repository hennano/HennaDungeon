package net.hennabatch.hennadungeon.entity.object;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.CollidableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class WallEntity extends CollidableEntity {
    public WallEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        return;
    }

    @Override
    public void update() { }

    @Override
    public void initilaize() { }

    @Override
    public String getIcon() {
        return "⛝";
    }

    @Override
    public String name() {
        return "壁";
    }
}
