package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.Status;

public class BuffEffect extends StatusEffect{

    public BuffEffect(int durationTime, Status.EnumStatus targetStatus, int val, Boolean isMagnification) {
        super(durationTime, targetStatus, val, isMagnification);
    }

    @Override
    public String name() {
        return getTargetStatus().name() +"アップ";
    }
}
