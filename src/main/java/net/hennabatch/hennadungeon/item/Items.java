package net.hennabatch.hennadungeon.item;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public static final Item HEAL_POTION = new HealPotionItem();

    private static Items singleton = new Items();

    private List<Item> items = new ArrayList<>();

    private Items(){
        registerItems();
    }

    public static Items getItems(){
        return singleton;
    }

    public void registerItem(Item item){
        items.add(item);
    }

    public void registerItems(){
        registerItem(HEAL_POTION);
    }
}
