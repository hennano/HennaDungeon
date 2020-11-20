package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.List;

public class ApproachaTagetAi<T extends Entity> extends AiBase<T>{

    private Entity target;
    private double range;

    public ApproachaTagetAi(T owner, Entity target, double range) {
        super(owner);
        this.target = target;
        this.range = range;
    }

    @Override
    protected boolean shouldExecute() {
        return new Vec2d(this.owner).distance(target) < range;
    }

    @Override
    public void updateTask() {
        Vec2d distance = new Vec2d(this.owner).sub(target);
        List<EnumDirection> directions = new ArrayList<>();
        if(distance.abs().getX() > distance.abs().getY()){
            directions.add(distance.getX() >= 0 ? EnumDirection.NX : EnumDirection.X);
            directions.add(distance.getY() >= 0 ? EnumDirection.NY : EnumDirection.Y);
        }else{
            directions.add(distance.getY() >= 0 ? EnumDirection.NY : EnumDirection.Y);
            directions.add(distance.getX() >= 0 ? EnumDirection.NX : EnumDirection.X);
        }
        owner.move(directions.stream().filter(x -> owner.tryMove(x, 1) > 0).findFirst().get(), 1);
    }
}
