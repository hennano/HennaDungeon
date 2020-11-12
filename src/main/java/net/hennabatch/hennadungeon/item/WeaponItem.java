package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.IVec;

import java.util.List;

public abstract class WeaponItem extends EquipmentItem{

    public abstract Boolean isMagic();

    public abstract Boolean isInnerRange(IVec vec, EnumDirection direction);

    public abstract List<AdditionalEffect> getAdditionalEffects();

}
