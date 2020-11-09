package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.EnumDirection;

public interface IAttackable {

    Status getStatus();
    WeaponItem getEquipmentWeapon();
    void setEquipmentWeapon(WeaponItem weapon);

    void attack(EnumDirection direction);
}
