package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.ApproachTargetAi;
import net.hennabatch.hennadungeon.entity.ai.AttackMeleeAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class OakEntity extends EnemyEntity {

    private Status status = new Status(50, 20, 15, 5);

    public OakEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachTargetAi<>(this, getDungeon().getPlayer(), 5.0));
        tasks.addTask(2, new AttackMeleeAi<>(this, getDungeon().getPlayer(), getEquipmentWeapon()));
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
        return (WeaponItem) Items.LONGSWORD;
    }

    @Override
    public int getMaxHP() {
        return 400;
    }

    @Override
    public String getIcon() {
        return "Ok";
    }

    @Override
    public String name() {
        return "オーク";
    }
}
