package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.entity.Entity;

public abstract class Item {

    public abstract boolean onUse(Entity entity);

    public abstract boolean canUse(Entity entity);

    public abstract String name();

    public abstract String description();
}
