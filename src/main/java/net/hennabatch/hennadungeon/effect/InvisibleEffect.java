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
        entity.setHidden(true);
        super.updateEffect(entity);
    }

    @Override
    public void onDestroy(BreakableEntity entity) {
        entity.setHidden(false);
    }
}
