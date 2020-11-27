package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;

public class RegenerationEffect extends TurnEffect{

    private int val;

    public RegenerationEffect(int durationTime, int val) {
        super(durationTime);
    }

    public int getVal() {
        return val;
    }

    @Override
    public void updateEffect(BreakableEntity entity) {
        entity.addHP(getVal());
    }

    @Override
    public String name() {
        return "回復";
    }

    @Override
    public RegenerationEffect cloneEffect() {
        return new RegenerationEffect(getDurationTime(), getVal());
    }
}
