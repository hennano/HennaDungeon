package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class CharacterEntity extends BreakableEntity{

    private ArmorItem equipmentArmor;

    public CharacterEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public ArmorItem getEquipmentArmor() {
        return equipmentArmor;
    }

    public void setEquipmentArmor(ArmorItem equipmentArmor) {
        this.equipmentArmor = equipmentArmor;
    }

}
