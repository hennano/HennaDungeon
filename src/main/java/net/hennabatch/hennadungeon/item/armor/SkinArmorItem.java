package net.hennabatch.hennadungeon.item.armor;

import net.hennabatch.hennadungeon.item.ArmorItem;

public class SkinArmorItem extends ArmorItem {
    @Override
    public String name() {
        return "生身の体がアーマーだ！";
    }

    @Override
    public String description() {
        return name();
    }
}
