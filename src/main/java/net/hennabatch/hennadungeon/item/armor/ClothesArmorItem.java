package net.hennabatch.hennadungeon.item.armor;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.ArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClothesArmorItem extends ArmorItem {
    @Override
    public String name() {
        return "魔法服";
    }

    @Override
    public String description() {
        return "魔法が織り込まれた服。魔法耐性があるので主に後衛が来ている。";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.MDEF, 10, false)));
    }
}
