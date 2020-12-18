package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.util.Reference;

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
        if(durationTime == 0) {
            setDestroy(true);
            Reference.logger.info(name() + "の効果が切れた");
            onDestroy(entity);
        }
    }

    public void onDestroy(BreakableEntity entity){}

    public void updateEffect(BreakableEntity entity){}

    public abstract String name();

    public abstract String description();

    public abstract Effect cloneEffect();
}
