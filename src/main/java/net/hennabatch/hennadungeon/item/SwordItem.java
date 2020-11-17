package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwordItem extends WeaponItem{
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
        return new ArrayList<>(Arrays.asList(new Vec2d(1 ,0)));
    }

    @Override
    public String name() {
        return "剣";
    }

    @Override
    public String description() {
        return "初心者に親しまれている剣。使いやすくて手頃な値段なのが魅力。";
    }
}
