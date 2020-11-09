package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.CharacterEntity;

public abstract class Effect {

    private int durationTime;
    private boolean isDestory = false;

    public Effect(int durationTime){
        this.durationTime = durationTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public boolean isDestory() {
        return isDestory;
    }

    public void setDestory(boolean destory) {
        isDestory = destory;
    }

    public void update(CharacterEntity entity){
        updateEffect(entity);
        durationTime--;
        if(durationTime == 0) setDestory(true);
    }

    public void updateEffect(CharacterEntity entity){}

    public abstract String name();

}
