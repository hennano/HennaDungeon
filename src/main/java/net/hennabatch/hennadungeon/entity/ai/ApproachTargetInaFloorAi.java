package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.entity.Entity;

public class ApproachTargetInaFloorAi<T extends Entity> extends ApproachTargetAi<T> {

    private Floor floor;

    public ApproachTargetInaFloorAi(T owner, Entity target, Floor floor) {
        super(owner, target, 0.0);
        this.floor = floor;
    }

    @Override
    protected boolean shouldExecute() {
        return !target.isHidden() && target.getDungeon().getInnerFloors(target).stream().anyMatch(floor::equals);
    }
}
