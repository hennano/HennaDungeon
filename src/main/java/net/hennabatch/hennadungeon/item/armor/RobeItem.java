package net.hennabatch.hennadungeon.item.armor;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.ArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RobeItem extends ArmorItem {
    @Override
    public String name() {
        return "ローブ";
    }

    @Override
    public String description() {
        return "魔法が織り込まれたローブ。込められた魔力が強いため魔法を寄せ付けない。";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.MDEF, 20, false)));
    }
}
