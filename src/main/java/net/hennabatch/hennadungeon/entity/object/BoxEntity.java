package net.hennabatch.hennadungeon.entity.object;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.CollidableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class BoxEntity extends CollidableEntity {

    private Item item;
    private boolean isRequireKey;

    public BoxEntity(Vec2d pos, Dungeon dungeon, Item item, boolean isRequireKey) {
        super(pos, dungeon);
        this.item = item;
        this.isRequireKey = false;
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        if(!(collidedEntity instanceof PlayerEntity)) return;

        if(isRequireKey){
            if(((PlayerEntity) collidedEntity).getInventory().getItems().stream().noneMatch(x -> x.equals(Items.KEY))) {
                Reference.logger.info("宝箱には鍵がかかっている");
                return;
            }else{
                ((PlayerEntity) collidedEntity).getInventory().getItems().remove(((PlayerEntity) collidedEntity).getInventory().getItemIndex(Items.KEY));
                Reference.logger.info("鍵を使用した");
            }
        }
        setDestroy(true);
        getDungeon().spawnEntity(new DropItemEntity(new Vec2d(this), getDungeon(), item));
        Reference.logger.info("宝箱を開けた");
    }

    @Override
    public void initialize() {

    }

    @Override
    public String getIcon() {
        return "☆";
    }

    @Override
    public String name() {
        return "宝箱";
    }
}
