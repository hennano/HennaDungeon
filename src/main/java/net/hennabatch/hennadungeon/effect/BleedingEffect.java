package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.util.EnumDifficulty;

public class BleedingEffect extends TurnEffect{

    private final int val;
    public BleedingEffect(int durationTime, EnumDifficulty difficulty) {
        super(durationTime);
        this.val = setValByDifficulty(difficulty);
    }

    public BleedingEffect(int durationTime, int val) {
        super(durationTime);
        this.val = val;
    }

    @Override
    public void updateEffect(BreakableEntity entity) {
        entity.subHP(val);
    }

    @Override
    public String name() {
        return "出血";
    }

    @Override
    public String description() {
        return "怪我をして出血している状態\n継続ダメージを受ける";
    }

    @Override
    public BleedingEffect cloneEffect() {
        return new BleedingEffect(getDurationTime(), val);
    }

    public int setValByDifficulty(EnumDifficulty difficulty){
        switch (difficulty) {
            case EASY:
                return 1;
            case HARD:
                return 5;
        }
        return 2;
    }

}
