package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.entity.Entity;

public class KeyItem extends Item{
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
        return "鍵";
    }

    @Override
    public String description() {
        return "鍵のかかった宝箱を開けるための鍵。中身はいいものかもしれない。";
    }
}
