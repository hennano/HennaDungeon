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
    public <T extends Effect> T cloneEffect() {
        return null;
    }
}
