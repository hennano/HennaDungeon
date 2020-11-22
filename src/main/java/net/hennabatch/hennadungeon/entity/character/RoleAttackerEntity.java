package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class RoleAttackerEntity extends EnemyEntity {
    public RoleAttackerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initilaizeAi() {

    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public ArmorItem getEquipmentArmor() {
        return null;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return null;
    }

    @Override
    public int getMaxHP() {
        return 0;
    }

    @Override
    public String getIcon() {
        return "Ra";
    }

    @Override
    public String name() {
        return "アリエテ";
    }
}
