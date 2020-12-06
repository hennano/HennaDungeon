package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;

import java.util.function.Predicate;

public abstract class AdvancedAiBase<T extends Entity> extends AiBase<T>{

    private int remaining;
    private int coolTime;
    private int useTurn = 0;
    private Predicate<T> predicate;

    public AdvancedAiBase(T owner, int limit, int coolTime, Predicate<T> predicate) {
        super(owner);
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
        doAny();
        useTurn = owner.getTurn();
        remaining--;
    }

    abstract protected void doAny();
}
