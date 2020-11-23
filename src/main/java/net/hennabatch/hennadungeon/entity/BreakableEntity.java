package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class BreakableEntity extends CollidableEntity{

    private int currentHP;

    public BreakableEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
        currentHP = getMaxHP();
    }

    public abstract Status getStatus();

    public abstract ArmorItem getEquipmentArmor();

    public abstract WeaponItem getEquipmentWeapon();

    public boolean onAttacked(Entity attackedEntity, int atk, boolean isMagic, boolean isMelee, List<Effect> additionalEffects){
        Random rand = new Random();
        //if (rand.nextDouble() * 100 < getStatus().getEVA(getEquipmentWeapon(), getEquipmentArmor())) return false;
        subHP(this.getStatus().calcDamage(atk, getEquipmentWeapon(), getEquipmentArmor(), isMagic));
        additionalEffects.stream().forEach(x -> getStatus().addEffect(x));
        return true;
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        if(collidedEntity instanceof IAttackable) {
            EnumDirection direction = Arrays.stream(EnumDirection.values()).filter( x -> x.vec().add(this).equals(collidedEntity)).findFirst().get().switchOtherSide();
            ((IAttackable) collidedEntity).attack(direction);
        }
    }

    @Override
    public void update() {
        super.update();
        this.getStatus().getEffects().forEach(x -> x.update(this));
        this.getStatus().getEffects().removeIf(Effect::isDestroy);
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
        setCurrentHP(Math.max(getCurrentHP() - hp, 0));
        Reference.logger.info(this.name() + "は" + hp + "ダメージ受けた");
        if(getCurrentHP() <= 0) this.destroy();
    }
}
