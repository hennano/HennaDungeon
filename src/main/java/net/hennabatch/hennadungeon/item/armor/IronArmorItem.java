package net.hennabatch.hennadungeon.item.armor;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.ArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IronArmorItem extends ArmorItem {
    @Override
    public String name() {
        return "鉄の鎧";
    }

    @Override
    public String description() {
        return "鉄でできた鎧。革と比べより防御力が高いが動きづらいので評価が別れている。";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.DEF, 30, false)));
    }
}
