package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class BlinkAi<T extends Entity> extends AdvancedAiBase<T>{

    private final int maxRange;
    private final int minRange;

    public BlinkAi(T owner, int range, int limit, int coolTime, Predicate<T> predicate) {
        this(owner, 0, range, limit, coolTime, predicate);
    }

    public BlinkAi(T owner, int min, int max, int limit, int coolTime, Predicate<T> predicate) {
        super(owner, limit, coolTime, predicate);
        this.minRange = min;
        this.maxRange = max;
    }

    @Override
    protected void doAny() {
        Reference.logger.debug("dddddd");
        Random rand = new Random();
        int range = rand.nextInt(maxRange - minRange) + minRange;
        List<Vec2d> vecs = new Vec2d(owner).rangeMax(range);
        Vec2d targetPos = vecs.parallelStream().filter(x -> owner.getDungeon().isInner(x)).findAny().get();
        owner.setPos(targetPos);
        Reference.logger.info(owner.name() + "はワープした");
    }
}
