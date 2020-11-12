package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class EnemyEntity extends AiEntity{

    public EnemyEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    public void attack(EnumDirection direction) {
        getDungeon().getEntities().stream()
                .filter(x -> x instanceof BreakableEntity)
                .map(x -> (BreakableEntity)x)
                .filter(x -> getEquipmentWeapon().isInnerRange(x, direction))
                .forEach(x -> x.onAttacked(this, getStatus().getATK(getEquipmentWeapon(), getEquipmentArmor()), getEquipmentWeapon().isMagic()));
    }
}
