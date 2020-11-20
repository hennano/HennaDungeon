package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class StayAi<T extends Entity> extends AiBase<T>{

    public StayAi(T entity) {
        super(entity);
    }

    @Override
    protected boolean shouldExecute() {
        return true;
    }

    @Override
    public void updateTask() {
        Reference.logger.debug(owner.name() + ":Staying at " + new Vec2d(owner).toString());
    }
}
