package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.AiEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.ITalkable;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpedPartyMemberEntity extends AiEntity {

    private Status status = new Status(80, 30, 10, 5);

    public HelpedPartyMemberEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public ArmorItem getEquipmentArmor() {
        return null;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return (WeaponItem) Items.HAND;
    }

    @Override
    public int getMaxHP() {
        return 500;
    }

    @Override
    public String getIcon() {
        return "◇";
    }

    @Override
    public String name() {
        return "メンバー";
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        if(!(collidedEntity instanceof ITalkable)){
            super.onCollision(collidedEntity);
            return;
        }
        getDungeon().executeScene(new MessageScene(new ArrayList<>(Arrays.asList(name() + ":\nいてて…"))));
    }
}
