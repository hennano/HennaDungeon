package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.IMimicable;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;

import java.util.Arrays;

public class MimicUntilRangeAi<T extends Entity & IMimicable> extends AiBase<T> {

    private Entity target;
    private WeaponItem weapon;

    public MimicUntilRangeAi(T owner, Entity target, WeaponItem weapon) {
        super(owner);
        this.target = target;
        this.weapon = weapon;
    }

    @Override
    protected boolean shouldExecute() {
        return owner.isMimicking();
    }

    @Override
    public void updateTask() {
        if(Arrays.stream(EnumDirection.values()).anyMatch(x -> weapon.isInnerRange(owner, target, x)) && !target.isHidden()){
            owner.setMimicking(false);
            Reference.logger.info(owner.name() + "が正体を表した");
        }
    }
}
