package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.function.Predicate;

public class AddEffectAi<T extends BreakableEntity> extends AdvancedAiBase<T>{

    private Effect effect;

    public AddEffectAi(T owner, Effect effect, int limit, int coolTime, Predicate<T> predicate) {
        super(owner, limit, coolTime, predicate);
        this.effect = effect;
    }

    @Override
    protected void doAny() {
        owner.getStatus().addEffect(effect.cloneEffect());
        Reference.logger.info(owner.name() + "は" + effect.name() + "が付与された");
    }
}
