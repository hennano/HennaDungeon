package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.lang.ref.WeakReference;
import java.util.Random;

public abstract class EnemyEntity extends AiEntity implements IAttackable{

    public EnemyEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    //リアクション用
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

    //AI用
    public void attack(WeaponItem item, EnumDirection direction){
        Random rand = new Random();
        boolean isCompleted = getDungeon().getEntities().stream()
                .filter(x -> x instanceof BreakableEntity)
                .filter(x -> item.isInnerRange(this, x, direction))
                .map(x -> (BreakableEntity)x)
                .anyMatch(x -> x.onAttacked(this, getStatus().getATK(item, getEquipmentArmor()), getEquipmentWeapon().isMagic(), item.isMelee(), item.giveEffectsForAttacker(rand.nextDouble())));
        if(isCompleted){
            item.giveEffectsForAttacker(rand.nextDouble()).forEach(x -> getStatus().addEffect(x));
        }
    }
}
