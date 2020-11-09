package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class EnemyEntity extends AiEntity implements IAttackable{

    public EnemyEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }
}
