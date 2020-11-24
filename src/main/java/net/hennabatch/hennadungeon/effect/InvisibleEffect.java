package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;

public class InvisibleEffect extends TurnEffect{
    //装備への適応禁止　装備解除後に戻らなくなる

    public InvisibleEffect(int durationTime) {
        super(durationTime);
    }

    @Override
    public String name() {
        return "透明";
    }

    @Override
    public InvisibleEffect cloneEffect() {
        return new InvisibleEffect(getDurationTime());
    }

    @Override
    public void updateEffect(BreakableEntity entity) {
        if(!entity.isHidden()){
            entity.setHidden(true);
        }
        if(isDestroy()){
            entity.setHidden(false);
        }
        super.updateEffect(entity);
    }
}
