package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.entity.Entity;

public class CrystalItem extends Item{
    @Override
    public boolean onUse(Entity entity) {
        return false;
    }

    @Override
    public boolean canUse(Entity entity) {
        return false;
    }

    @Override
    public String name() {
        return "幾何学水晶";
    }

    @Override
    public String description() {
        return "細かな幾何学模様が書かれた水晶。これと同じものをギルドで見たことある気がする";
    }
}
