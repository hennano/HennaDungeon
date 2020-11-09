package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.entity.Entity;

public abstract class Item {

    public abstract int maxStack();

    public abstract boolean onUse(Entity entity, ItemStack stack);
}
