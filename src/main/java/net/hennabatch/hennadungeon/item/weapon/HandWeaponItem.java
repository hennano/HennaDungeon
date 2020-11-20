package net.hennabatch.hennadungeon.item.weapon;

import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandWeaponItem extends WeaponItem {
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
        return "素手";
    }

    @Override
    public String description() {
        return "アイテムを装備していないときに代わりに装備される";
    }
}
