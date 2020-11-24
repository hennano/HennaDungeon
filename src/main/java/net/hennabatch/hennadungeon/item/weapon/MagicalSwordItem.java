package net.hennabatch.hennadungeon.item.weapon;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagicalSwordItem extends SwordItem {
    @Override
    public Boolean isMagic() {
        return true;
    }

    @Override
    public Boolean isMelee() {
        return true;
    }

    @Override
    public String name() {
        return "魔法剣";
    }

    @Override
    public String description() {
        return "魔法により構成された剣。輝いているので眩しい。";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.ATK, 15, false)));
    }
}
