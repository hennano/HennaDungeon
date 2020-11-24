package net.hennabatch.hennadungeon.item.armor;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.effect.InvisibleEffect;
import net.hennabatch.hennadungeon.item.ArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StealthRobeItem extends ArmorItem {
    @Override
    public String name() {
        return "透過ローブ";
    }

    @Override
    public String description() {
        return "白と灰色の市松模様が描かれているローブ。これをかぶっている間は周りから姿が見えなくなる";
    }

    @Override
    public List<Effect> getEffects() {
        return new ArrayList<>(Arrays.asList(new InvisibleEffect(-1)));
    }
}
