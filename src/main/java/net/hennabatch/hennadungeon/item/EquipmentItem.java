package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.effect.Effect;

import java.util.List;

public abstract class EquipmentItem extends Item{

    public abstract List<Effect> getEffects();
}
