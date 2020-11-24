package net.hennabatch.hennadungeon.item.weapon;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NecronomiconItem extends WeaponItem {
    @Override
    public Boolean isMagic() {
        return true;
    }

    @Override
    public Boolean isMelee() {
        return false;
    }

    @Override
    public List<Vec2d> range() {
        return new ArrayList<>(Arrays.asList(new Vec2d(1, 0), new Vec2d(2,  0), new Vec2d(3, 0)));
    }

    @Override
    public String name() {
        return "ネクロノミコン";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new BuffEffect(-1, Status.EnumStatus.ATK, 20, false)));
    }
}
