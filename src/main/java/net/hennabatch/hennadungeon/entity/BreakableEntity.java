package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class BreakableEntity extends CollidableEntity{

    private int currentHP;

    public BreakableEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
        currentHP = getMaxHP();
    }

    public abstract Status getStatus();

    public abstract ArmorItem getEquipmentArmor();

    public abstract WeaponItem getEquipmentWeapon();

    public void onAttacked(Entity attackedEntity, int atk, boolean isMagic){
        subHP(this.getStatus().calcDamage(atk, getEquipmentWeapon(), getEquipmentArmor(), isMagic));
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        if(collidedEntity instanceof PlayerEntity){
            this.onAttacked(collidedEntity, ((PlayerEntity)collidedEntity).getStatus().getATK(((PlayerEntity)collidedEntity).getEquipmentWeapon(), ((PlayerEntity)collidedEntity).getEquipmentArmor()), ((PlayerEntity)collidedEntity).getEquipmentWeapon().isMagic());
        }else if(collidedEntity instanceof EnemyEntity){
            this.onAttacked(collidedEntity, ((EnemyEntity)collidedEntity).getStatus().getATK(((EnemyEntity)collidedEntity).getEquipmentWeapon(), ((EnemyEntity)collidedEntity).getEquipmentArmor()), ((EnemyEntity)collidedEntity).getEquipmentWeapon().isMagic());
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
        if(getCurrentHP() == 0) this.destroy();
    }
}
