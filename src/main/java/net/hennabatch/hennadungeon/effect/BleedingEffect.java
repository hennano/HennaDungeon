package net.hennabatch.hennadungeon.effect;

import net.hennabatch.hennadungeon.entity.CharacterEntity;
import net.hennabatch.hennadungeon.util.EnumDifficulty;

public class BleedingEffect extends TurnEffect{

    private int val;
    public BleedingEffect(int durationTime, EnumDifficulty difficulty) {
        super(durationTime);
        this.val = setValByDifficulty(difficulty);
    }

    public BleedingEffect(int durationTime, int val) {
        super(durationTime);
        this.val = val;
    }

    @Override
    public void updateEffect(CharacterEntity entity) {
        entity.subHP(val);
    }

    @Override
    public String name() {
        return "出血";
    }

    public int setValByDifficulty(EnumDifficulty difficulty){
        switch (difficulty) {
            case EASY:
                return 2;
            case HARD:
                return 10;
        }
        return 5;
    }

}
