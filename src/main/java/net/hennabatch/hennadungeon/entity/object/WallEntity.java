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
    public void onCollision(Entity collidedEntity) {}

    @Override
    public void update() { }

    @Override
    public void initialize() { }

    @Override
    public String getIcon() {
        return "⛝";
    }

    @Override
    public String name() {
        return "封鎖壁";
    }

    @Override
    public String description() {
        return "通路を塞いでいる壁\n一定条件を満たすといつの間にか消えている";
    }
}
