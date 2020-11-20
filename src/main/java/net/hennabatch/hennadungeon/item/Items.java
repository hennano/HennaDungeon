package net.hennabatch.hennadungeon.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Items {

    public static final Item HEAL_POTION = new HealPotionItem();

    public static final Item HAND = new HandWeaponItem();
    public static final Item SWORD = new SwordItem();

    private static final Items singleton = new Items();

    private final List<Item> items = new ArrayList<>();

    private Items(){
        registerItems();
    }

    public static Items getInstance(){
        return singleton;
    }

    public void registerItem(Item item){
        items.add(item);
    }

    public void registerItems(){
        registerItem(HEAL_POTION);
        registerItem(SWORD);
        registerItem(HAND);
    }
}
