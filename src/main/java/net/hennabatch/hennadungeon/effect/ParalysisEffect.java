package net.hennabatch.hennadungeon.effect;

public class ParalysisEffect extends TurnEffect implements IUnmovable{

    public ParalysisEffect(int durationTime) {
        super(durationTime);
    }

    @Override
    public String name() {
        return "麻痺";
    }

    @Override
    public ParalysisEffect cloneEffect() {
        return new ParalysisEffect(this.getDurationTime());
    }
}
