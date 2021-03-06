package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.effect.IUnmovable;
import net.hennabatch.hennadungeon.entity.object.DropItemEntity;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Item;
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

    public ArmorItem getEquipmentArmor(){
        return null;
    }

    public abstract WeaponItem getEquipmentWeapon();

    public boolean onAttacked(Entity attackedEntity, int atk, boolean isMagic, boolean isMelee, List<Effect> additionalEffects){
        Random rand = new Random();
        if (rand.nextDouble() * 100 < getStatus().getEVA(getEquipmentWeapon(), getEquipmentArmor())){
            Reference.logger.info(this.name() + "は回避した");
            return false;
        }
        subHP(this.getStatus().calcDamage(atk, getEquipmentWeapon(), getEquipmentArmor(), isMagic));
        additionalEffects.forEach(x -> {
            getStatus().addEffect(x);
            Reference.logger.info(this.name() + "は" + x.name() + "になった");
        });
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
        if(!isDestroy()){
            if(getStatus().getEffects().stream().noneMatch(x -> x instanceof IUnmovable)){
                turnAction();
            }else{
                Reference.logger.info(this.name() + "は体がしびれて動けない");
            }
            this.getStatus().getEffects().forEach(x -> x.update(this));
            this.getStatus().getEffects().removeIf(Effect::isDestroy);
        }
        super.update();
    }

    public void turnAction(){}

    public abstract int getMaxHP();

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void addHP(int hp){
        setCurrentHP(Math.min(getCurrentHP() + hp, getMaxHP()));
    }

    public void subHP(int hp){
        setCurrentHP(Math.max(getCurrentHP() - hp, 0));
        Reference.logger.info(this.name() + "は" + hp + "ダメージ受けた");
        if(getCurrentHP() <= 0) this.setDestroy(true);
    }

    @Override
    protected void onDestroy() {
        if(getDropItemTable() != null){
            Item item = getDropItemTable().getDropItem();
            if(item != null){
                getDungeon().spawnEntity(new DropItemEntity(new Vec2d(this), getDungeon(), item));
                Reference.logger.info(item.name() + "を落とした");
            }
        }
    }

    public DropItemTable getDropItemTable(){
        return null;
    }
}
