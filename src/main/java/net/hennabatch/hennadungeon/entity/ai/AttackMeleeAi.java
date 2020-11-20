package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.EnumDirection;

import java.util.Arrays;

public class AttackMeleeAi<T extends EnemyEntity> extends AiBase<T>{

    private WeaponItem weapon;
    private BreakableEntity target;

    public AttackMeleeAi(T entity, BreakableEntity target, WeaponItem weapon) {
        super(entity);
        this.weapon = weapon;
        this.target = target;
    }

    @Override
    protected boolean shouldExecute() {
        return Arrays.stream(EnumDirection.values()).anyMatch(x -> weapon.isInnerRange(owner, target, x));
    }

    @Override
    public void updateTask() {
        this.owner.attack(this.weapon, Arrays.stream(EnumDirection.values()).filter(x -> weapon.isInnerRange(owner, target, x)).findFirst().get());
    }
}
