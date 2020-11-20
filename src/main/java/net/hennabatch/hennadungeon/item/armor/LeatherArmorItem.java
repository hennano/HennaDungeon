package net.hennabatch.hennadungeon.item.armor;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.ArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeatherArmorItem extends ArmorItem {
    @Override
    public String name() {
        return "革の鎧";
    }

    @Override
    public String description() {
        return "革でできた鎧。動きやすく好評。";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.DEF, 10, false)));
    }
}
