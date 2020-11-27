package net.hennabatch.hennadungeon.item.potion;

import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.item.Item;

public class HealPotionItem extends Item {

    private final int heal = 250;
    @Override
    public boolean onUse(Entity entity) {
        if(!(entity instanceof BreakableEntity)) return false;
        ((BreakableEntity) entity).addHP(heal);
        return true;
    }

    @Override
    public boolean canUse(Entity entity) {
        if(!(entity instanceof BreakableEntity)) return false;
        return ((BreakableEntity) entity).getCurrentHP() < ((BreakableEntity) entity).getMaxHP();
    }

    @Override
    public String name() {
        return "即時回復ポーション";
    }

    @Override
    public String description() {
        return "HPを即座に" +  heal + "回復する";
    }
}
