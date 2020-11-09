package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.BleedingEffect;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class PlayerEntity extends CharacterEntity implements IAttackable, IInventory{

    private WeaponItem equipmentWeapon;

    public PlayerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public int getMaxHP() {
        return 1000;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return equipmentWeapon;
    }

    @Override
    public void setEquipmentWeapon(WeaponItem weapon) {
        this.equipmentWeapon = weapon;
    }


    @Override
    public void attack(EnumDirection direction) {
        getDungeon().getEntities().stream()
                .filter(x -> x instanceof BreakableEntity)
                .filter(x -> getEquipmentWeapon().isInnerRange(x, direction))
                .map(x -> (BreakableEntity)x)
                .forEach(x -> x.onAttacked(this));
    }

    @Override
    public void update() { }

    @Override
    protected void initilaize() {
        getStatus().addEffect(new BleedingEffect(-1, getDungeon().getDifficulty()));
    }

    @Override
    public String getIcon() {
        return "＠";
    }

    @Override
    public String name() {
        return "荷物持ち";
    }
}
