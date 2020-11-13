package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class DropItemEntity extends Entity{

    private Item item;

    public DropItemEntity(Vec2d pos, Dungeon dungeon, Item item) {
        super(pos, dungeon);
        this.item = item;
    }

    @Override
    public void update() { }

    @Override
    protected void initilaize() { }

    @Override
    protected void onTrigger(Entity triggeredEntity) {
        if(triggeredEntity instanceof IPickable){
            if(((IPickable) triggeredEntity).pick(item)){
                Reference.logger.info(triggeredEntity.name() + "が" + item.name() + "を拾った");
                destroy();
            }
        }
    }

    @Override
    public String getIcon() {
        return "Ｉ";
    }

    @Override
    public String name() {
        return item.name();
    }
}
