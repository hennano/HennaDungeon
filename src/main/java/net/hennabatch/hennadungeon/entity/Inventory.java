package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final List<Item> items = new ArrayList<>();
    private final int capacity;

    public Inventory(int capacity){
        this.capacity = capacity;
    }

    public boolean addItem(Item item){
        if(this.items.size() > capacity) return false;
        this.items.add(item);
        return true;
    }

    public <T extends Entity & IHasInventory> boolean use(T entity, int index){
        if(!this.items.get(index).canUse(entity)) return false;
        if(this.items.get(index).onUse(entity)){
            this.items.remove(index);
        }
        return true;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getItemIndex(Item item){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).equals(item)) return i;
        }
        return -1;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCountByItem(Item item){
        return Math.toIntExact(this.items.stream().filter(x -> x.equals(item)).count());
    }

    public int getCount(){
        return this.items.size();
    }
}
