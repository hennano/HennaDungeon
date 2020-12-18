package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.util.EnumDifficulty;

public class PoisonEffect extends TurnEffect{

    private final int val;

    public PoisonEffect(int durationTime, EnumDifficulty difficulty) {
        super(durationTime);
        this.val = setValByDifficulty(difficulty);
    }

    public PoisonEffect(int durationTime, int val) {
        super(durationTime);
        this.val = val;
    }

    @Override
    public void updateEffect(BreakableEntity entity) {
        entity.subHP(val);
    }

    @Override
    public String name() {
        return "毒";
    }

    @Override
    public String description() {
        return "毒状態\n一定時間継続ダメージを受ける";
    }

    @Override
    public PoisonEffect cloneEffect() {
        return new PoisonEffect(getDurationTime(), val);
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
