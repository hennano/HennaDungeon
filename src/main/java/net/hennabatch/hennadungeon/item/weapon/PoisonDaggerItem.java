package net.hennabatch.hennadungeon.item.weapon;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.effect.PoisonEffect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.AdditionalEffect;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PoisonDaggerItem extends WeaponItem {
    @Override
    public Boolean isMagic() {
        return false;
    }

    @Override
    public Boolean isMelee() {
        return true;
    }

    @Override
    public List<Vec2d> range() {
        return new ArrayList<>(Arrays.asList(new Vec2d(1, 0)));
    }

    @Override
    public String name() {
        return "ポイズンダガー";
    }

    @Override
    public String description() {
        return "刃に毒が塗られたダガー。切りつけられた相手はその毒に蝕まれる";
    }

    @Override
    public List<AdditionalEffect> giveAdditionalEffectsForAttacked() {
        return Collections.singletonList(new AdditionalEffect(0.3, new PoisonEffect(3, EnumDifficulty.NORMAL)));
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.ATK, 5, false)));
    }
}
