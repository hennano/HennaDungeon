package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.item.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DropItemTable {

    private List<DropItem> dropItems = new ArrayList<>();

    public DropItemTable addItem(Item item, double chance){
        this.dropItems.add(new DropItem(item, chance));
        return this;
    }

    public DropItemTable removeItem(int index){
        this.dropItems.remove(index);
        return this;
    }

    public DropItemTable removeItem(Item item){
        this.dropItems.removeIf(item::equals);
        return this;
    }

    public Item getDropItem(){
        List<DropItem> dropItemList = this.dropItems.stream().sorted(Comparator.comparing(DropItem::getChance).reversed()).collect(Collectors.toList());
        double currentChance = 0;
        double num = new Random().nextDouble();
        for(DropItem item : dropItemList){
            currentChance += item.getChance();
            if(num < currentChance) return item.getItem();
        }
        return null;
    }


    protected class DropItem{

        private final Item item;
        private final double chance;

        DropItem(Item item, double chance){
            this.item = item;
            this.chance = chance;
        }

        Item getItem() {
            return item;
        }

        double getChance() {
            return chance;
        }
    }
}
