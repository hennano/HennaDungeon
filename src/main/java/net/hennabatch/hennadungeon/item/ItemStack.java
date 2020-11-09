package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.entity.Entity;

public class ItemStack {

    private Item item;
    int stack;

    public ItemStack(Item item, int stack){
        this.item = item;
        this.stack = stack;
    }

    public Item getItem() {
        return item;
    }

    public int getStack() {
        return stack;
    }

    public int add(int val){
        stack += val;
        int over = 0;
        if(stack > getItem().maxStack()){
            over = stack - getItem().maxStack();
            stack -= over;
        }
        return over;
    }

    public boolean canSub(int sub){
        return (stack - sub) >= 0;
    }

    public void onUse(Entity entity){
        boolean doUse = getItem().onUse(entity, this);
        if(doUse){

        }
    }
}
