package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class EquipmentItem extends Item{

    public List<Effect> getEffects(){
        return new ArrayList<>();
    }

    @Override
    public boolean onUse(Entity entity) {
        return false;
    }

    @Override
    public boolean canUse(Entity entity) {
        return false;
    }
}
