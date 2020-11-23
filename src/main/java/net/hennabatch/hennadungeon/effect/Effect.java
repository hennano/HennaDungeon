package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;

public abstract class Effect{

    private int durationTime;
    private boolean isDestroy = false;

    public Effect(int durationTime){
        this.durationTime = durationTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public void update(BreakableEntity entity){
        updateEffect(entity);
        durationTime--;
        if(durationTime == 0) setDestroy(true);
    }

    public void updateEffect(BreakableEntity entity){}

    public abstract String name();

    public abstract <T extends Effect> T cloneEffect();
}
