package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class BreakableEntity extends CollidableEntity{

    private int currentHP;

    public BreakableEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    public abstract Status getStatus();

    public abstract ArmorItem getEquipmentArmor();

    public void onAttacked(IAttackable attackedEntity){
        subHP(attackedEntity.getStatus().calcDamage(attackedEntity.getEquipmentWeapon(), this.getStatus(), this.getEquipmentArmor()));
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        if(collidedEntity instanceof IAttackable){
            onAttacked((IAttackable)collidedEntity);
        }
    }

    public abstract int getMaxHP();

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void addHP(int hp){
        setCurrentHP(Math.max(getCurrentHP() + hp, getMaxHP()));
    }

    public void subHP(int hp){
        setCurrentHP(Math.min(getCurrentHP() - hp, 0));
        Reference.logger.info(this.name() + "は" + hp + "ダメージ受けた");
        if(getCurrentHP() == 0) this.setDestroy(true);
    }
}
