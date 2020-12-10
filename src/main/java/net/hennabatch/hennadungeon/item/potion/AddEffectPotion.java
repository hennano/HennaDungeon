package net.hennabatch.hennadungeon.item.potion;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.item.Item;

public class AddEffectPotion extends Item {

    private Effect effect;

    public AddEffectPotion(Effect effect){
        this.effect = effect;
    }

    @Override
    public boolean onUse(Entity entity) {
        if(!(entity instanceof BreakableEntity)) return false;
        ((BreakableEntity) entity).getStatus().addEffect(effect.cloneEffect());
        return true;
    }

    @Override
    public boolean canUse(Entity entity) {
        return entity instanceof BreakableEntity;
    }

    @Override
    public String name() {
        return effect.name() + "ポーション";
    }

    @Override
    public String description() {
        return name() + "を飲んで一拍置くと" + effect.name() + "がつく。ちょっとまずいけど効果は大きい。";
    }
}
