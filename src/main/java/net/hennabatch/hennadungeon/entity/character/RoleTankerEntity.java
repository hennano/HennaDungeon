package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.ApproachaTagetAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class RoleTankerEntity extends EnemyEntity {

    private Status status = new Status(40, 60, 35, 0);

    public RoleTankerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachaTagetAi<>(this, getDungeon().getPlayer(), 10));
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
        return null;
    }

    @Override
    public int getMaxHP() {
        return 1200;
    }

    @Override
    public String getIcon() {
        return "Rt";
    }

    @Override
    public String name() {
        return "ウラジーミル";
    }
}
