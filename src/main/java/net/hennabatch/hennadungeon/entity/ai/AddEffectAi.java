package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.function.Predicate;

public class AddEffectAi<T extends BreakableEntity> extends AiBase<T>{

    private int remaining;
    private int coolTime;
    private int useTurn = 0;
    private Effect effect;
    private Predicate<T> predicate;

    public AddEffectAi(T owner, Effect effect, int limit, int coolTime, Predicate<T> predicate) {
        super(owner);
        this.effect = effect;
        this.predicate = predicate;
        this.remaining = limit;
        this.coolTime = coolTime;
    }

    @Override
    protected boolean shouldExecute() {
        if(remaining == 0) return false;
        if(useTurn + coolTime > owner.getTurn()) return false;
        return predicate.test(owner);
    }

    @Override
    public void updateTask() {
        owner.getStatus().addEffect(effect.cloneEffect());
        Reference.logger.info(owner.name() + "は" + effect.name() + "が付与された");
        useTurn = owner.getTurn();
        remaining--;
    }
}
