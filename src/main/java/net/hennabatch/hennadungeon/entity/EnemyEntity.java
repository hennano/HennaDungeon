package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.Random;

public abstract class EnemyEntity extends AiEntity implements IAttackable{

    public EnemyEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    public void attack(EnumDirection direction) {
        Random rand = new Random();
        boolean isCompleted = getDungeon().getEntities().stream()
                .filter(x -> x instanceof BreakableEntity)
                .filter(x -> getEquipmentWeapon().isInnerRange(this, x, direction))
                .map(x -> (BreakableEntity)x)
                .anyMatch(x -> x.onAttacked(this, getStatus().getATK(getEquipmentWeapon(), getEquipmentArmor()), getEquipmentWeapon().isMagic(), getEquipmentWeapon().isMelee(), getEquipmentWeapon().giveEffectsForAttacker(rand.nextDouble())));
        if(isCompleted){
            getEquipmentWeapon().giveEffectsForAttacker(rand.nextDouble()).forEach(x -> getStatus().addEffect(x));
        }
    }
}
