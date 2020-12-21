package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.Status;

public class DeBuffEffect extends StatusEffect {

    public DeBuffEffect(int durationTime, Status.EnumStatus targetStatus, double val, Boolean isMagnification) {
        super(durationTime, targetStatus, val, isMagnification);
    }

    @Override
    public String name() {
        return getTargetStatus().name() +"ダウン";
    }

    @Override
    public String description() {
        return "指定された能力値が減少している状態";
    }

    @Override
    public DeBuffEffect cloneEffect() {
        return new DeBuffEffect(getDurationTime(), getTargetStatus(), getVal(), isMagnification());
    }
}
