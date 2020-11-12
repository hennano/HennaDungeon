package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public abstract class AdditionalEffect {

    public List<Effect> giveEffectForAttacker(){
        return new ArrayList<>();
    }

    public List<Effect> giveEffectForAttacked(){
        return new ArrayList<>();
    }

}
