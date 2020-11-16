package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class EventTriggerEntity extends Entity{

    public EventTriggerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void update() {

    }

    @Override
    protected void initilaize() {

    }

    @Override
    public String getIcon() {
        return "え";
    }

    @Override
    public String name() {
        return "イベントトリガ";
    }

    @Override
    protected void onTrigger(Entity triggeredEntity) {
        Reference.logger.debug("接触した");
        destroy();
    }
}