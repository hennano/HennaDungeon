package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;

public class RegenerationEffect extends TurnEffect{

    private int val;

    public RegenerationEffect(int durationTime, int val) {
        super(durationTime);
        this.val = val;
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
        return "再生";
    }

    @Override
    public String description() {
        return "再生状態\n一定時間継続回復する";
    }

    @Override
    public RegenerationEffect cloneEffect() {
        return new RegenerationEffect(getDurationTime(), getVal());
    }
}
