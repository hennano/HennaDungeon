package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.effect.Effect;

import java.util.Random;

public class AdditionalEffect {

    private double additionalChance;
    private Effect effect;

    public AdditionalEffect(double additionalChance, Effect effect){
        this.additionalChance = additionalChance;
        this.effect = effect;
    }

    public double getAdditionalChance() {
        return additionalChance;
    }

    public Effect getEffect() {
        return effect.cloneEffect();
    }

    public Effect chanceOfAddEffect(){
        return new Random().nextDouble() <= getAdditionalChance() ? getEffect() : null;
    }
}
