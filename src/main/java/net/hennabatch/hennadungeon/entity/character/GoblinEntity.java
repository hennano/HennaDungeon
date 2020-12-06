package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.ApproachaTagetAi;
import net.hennabatch.hennadungeon.entity.ai.AttackMeleeAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class GoblinEntity extends EnemyEntity {

    private Status status = new Status(50, 10, 30, 0);

    public GoblinEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachaTagetAi<>(this, getDungeon().getPlayer(), 5.0));
        tasks.addTask(2, new AttackMeleeAi<>(this,  getDungeon().getPlayer(), getEquipmentWeapon()));
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
        return 250;
    }

    @Override
    public String getIcon() {
        return "Gb";
    }

    @Override
    public String name() {
        return "ゴブリン";
    }
}
