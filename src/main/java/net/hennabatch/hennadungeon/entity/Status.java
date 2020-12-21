package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.effect.StatusEffect;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.ArrayList;
import java.util.List;

public class Status {

    private final int ATK;
    private final int DEF;
    private final int MDEF;
    private final int EVA;
    private final List<Effect> effects = new ArrayList<>();

    public Status(int atk, int def, int mdef, int eva){
        this.ATK = atk;
        this.DEF = def;
        this.MDEF = mdef;
        this.EVA = eva;
    }

    public int getStatus(EnumStatus status){
        switch (status){
            case ATK:
                return getTrueATK();
            case DEF:
                return getTrueDEF();
            case MDEF:
                return getTrueMDEF();
            case EVA:
                return getTrueEVA();
        }
        return -1;
    }

    public int getTrueATK() {
        return ATK;
    }

    public int getATK(WeaponItem weapon, ArmorItem armor){
        int baseATK = getTrueATK();
        List<Effect> equipEffects = new ArrayList<>();
        if(weapon != null) equipEffects.addAll(weapon.getEffects());
        if(armor != null) equipEffects.addAll(armor.getEffects());
        baseATK = calcStateValue(baseATK, EnumStatus.ATK, equipEffects);
        return calcStateValue(baseATK, EnumStatus.ATK);
    }

    public int getTrueDEF() {
        return DEF;
    }

    public int getDEF(WeaponItem weapon, ArmorItem armor){
        int baseDEF = getTrueDEF();
        List<Effect> equipEffects = new ArrayList<>();
        if(weapon != null) equipEffects.addAll(weapon.getEffects());
        if(armor != null) equipEffects.addAll(armor.getEffects());
        baseDEF = calcStateValue(baseDEF, EnumStatus.DEF, equipEffects);
        return calcStateValue(baseDEF, EnumStatus.DEF);
    }

    public int getTrueMDEF() {
        return MDEF;
    }

    public int getMDEF(WeaponItem weapon, ArmorItem armor){
        int baseMDEF = getTrueMDEF();
        List<Effect> equipEffects = new ArrayList<>();
        if(weapon != null) equipEffects.addAll(weapon.getEffects());
        if(armor != null) equipEffects.addAll(armor.getEffects());
        baseMDEF = calcStateValue(baseMDEF, EnumStatus.MDEF, equipEffects);
        return Math.min(Reference.MAX_MDEFEND, calcStateValue(baseMDEF, EnumStatus.MDEF));
    }

    public int getTrueEVA() {
        return EVA;
    }

    public int getEVA(WeaponItem weapon, ArmorItem armor){
        int baseEVA = getTrueEVA();
        List<Effect> equipEffects = new ArrayList<>();
        if(weapon != null) equipEffects.addAll(weapon.getEffects());
        if(armor != null) equipEffects.addAll(armor.getEffects());
        baseEVA = calcStateValue(baseEVA, EnumStatus.EVA, equipEffects);
        return Math.min(Reference.MAX_EVASION, calcStateValue(baseEVA, EnumStatus.EVA));
    }

    private int calcStateValue(int baseVal, EnumStatus eumStatus){
        return calcStateValue(baseVal, eumStatus, getEffects());
    }

    private int calcStateValue(int baseVal, EnumStatus eumStatus, List<Effect> effectList){
        final int[] val = {baseVal + effectList.stream()
                .filter(x -> x instanceof StatusEffect)
                .map(x -> (StatusEffect) x)
                .filter(x -> x.getTargetStatus().equals(eumStatus))
                .filter(x -> !x.isMagnification())
                .mapToInt(x -> (int)Math.floor(x.getVal()))
                .sum()};
        effectList.stream()
                .filter(x -> x instanceof StatusEffect)
                .map(x -> (StatusEffect)x )
                .filter(x -> x.getTargetStatus().equals(eumStatus))
                .filter(StatusEffect::isMagnification)
                .mapToDouble(StatusEffect::getVal)
                .forEach( x -> val[0] *= x);
        return val[0];
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

    public int calcDamage(int atk, WeaponItem weapon, ArmorItem armor, boolean isMagic){
        return isMagic ? (int)(atk * ((100 - getMDEF(weapon, armor)) / 100.0)) : Math.max(atk - getDEF(weapon, armor), 1);
    }

    public enum EnumStatus{
        ATK(),
        DEF(),
        MDEF(),
        EVA()
    }
}
