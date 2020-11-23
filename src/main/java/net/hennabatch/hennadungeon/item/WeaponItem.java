package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class WeaponItem extends EquipmentItem{

    public abstract Boolean isMagic();

    public abstract Boolean isMelee();

    public Boolean isInnerRange(IVec attacker, IVec target, EnumDirection direction){
        return range().stream().map(x -> x.rotate(direction).add(attacker)).anyMatch(x -> x.equals(new Vec2d(target)));
    }

    public abstract List<Vec2d> range();

    public List<AdditionalEffect> giveAdditionalEffectsForAttacker(){
        return new ArrayList<>();
    }

    public List<Effect> giveEffectsForAttacker(){
        return giveAdditionalEffectsForAttacker().stream().map(x -> x.chanceOfAddEffect()).filter(x -> x != null).collect(Collectors.toList());
    }

    public List<AdditionalEffect> giveAdditionalEffectsForAttacked(){
        return new ArrayList<>();
    }

    public List<Effect> giveEffectsForAttacked(){
        return giveAdditionalEffectsForAttacked().stream().map(x -> x.chanceOfAddEffect()).filter(x -> x != null).collect(Collectors.toList());
    }
}
