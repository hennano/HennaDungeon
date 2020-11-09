package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.effect.StatusEffect;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.WeaponItem;

import java.util.ArrayList;
import java.util.List;

public class Status {

    private final int ATK;
    private final int DEF;
    private final int MDEF;
    private final int EVA;
    private List<Effect> effects = new ArrayList<>();

    public Status(int atk, int def, int mdef, int eva){
        this.ATK = atk;
        this.DEF = def;
        this.MDEF = mdef;
        this.EVA = eva;
    }

    public int getStatus(EnumStatus status){
        switch (status){
            case ATK:
                return getATK();
            case DEF:
                return getDEF();
            case MDEF:
                return getMDEF();
            case EVA:
                return getEVA();
        }
        return -1;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public int getMDEF() {
        return MDEF;
    }

    public int getEVA() {
        return EVA;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void addEffect(Effect effect){
        effects.add(effect);
    }

    public void removeEffect(Class<? extends Effect> clazz){
        effects.removeIf(x -> x.getClass().equals(clazz));
    }

    public void removeEffect(Effect effect){
        effects.removeIf(x -> x.equals(effect));
    }

    public void removeAllEffect(){
        effects.clear();
    }

    public int calcDamage(WeaponItem weapon, Status status, ArmorItem armor){
        final int[] atkTmp = {getATK()};
        getEffects().stream().filter(x -> x instanceof StatusEffect)
                .map(x -> (StatusEffect)x)
                .filter( x -> x.isMagnification())
                .forEach(x -> atkTmp[0] *= x.getVal());
        getEffects().stream().filter(x -> x instanceof StatusEffect)
                .map(x -> (StatusEffect)x)
                .filter( x -> !x.isMagnification())
                .forEach(x -> atkTmp[0] += x.getVal());
        int atk = atkTmp[0];
        return 0;
    }

    public enum EnumStatus{
        ATK(),
        DEF(),
        MDEF(),
        EVA();

    }
}
