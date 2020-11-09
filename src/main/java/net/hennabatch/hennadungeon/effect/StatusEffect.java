package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.Status;

public abstract class StatusEffect extends Effect{

    private Status.EnumStatus targetStatus;
    private int val;
    private boolean isMagnification;

    public StatusEffect(int durationTime, Status.EnumStatus targetStatus, int val, Boolean isMagnification){
        super(durationTime);
        this.targetStatus = targetStatus;
        this.val = val;
        this.isMagnification = isMagnification;
    }

    public Status.EnumStatus getTargetStatus() {
        return targetStatus;
    }

    public int getVal() {
        return val;
    }

    public boolean isMagnification(){
        return this.isMagnification;
    }
}
