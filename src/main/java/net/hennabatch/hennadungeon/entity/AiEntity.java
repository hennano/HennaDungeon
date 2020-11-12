package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.ai.Task;
import net.hennabatch.hennadungeon.vec.Vec2d;

public abstract class AiEntity extends BreakableEntity {

    protected Task tasks = new Task();

    public AiEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    public abstract void initilaizeAi();

    @Override
    protected void initilaize() {
        initilaizeAi();
    }

    @Override
    public void update() {
        tasks.run();
    }
}
