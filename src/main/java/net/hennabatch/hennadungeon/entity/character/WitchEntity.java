package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.DropItemTable;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.ApproachTargetAi;
import net.hennabatch.hennadungeon.entity.ai.AttackMeleeAi;
import net.hennabatch.hennadungeon.entity.ai.BlinkAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class WitchEntity extends EnemyEntity {

    private Status status = new Status(50, 10, 30, 5);

    public WitchEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachTargetAi<>(this, getDungeon().getPlayer(), 5.0));
        tasks.addTask(2, new AttackMeleeAi<>(this, getDungeon().getPlayer(), getEquipmentWeapon()));
        tasks.addTask(3, new BlinkAi<>(this, 1, 3, 5, 5, x -> true));
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return (WeaponItem) Items.GRIMOIRE;
    }

    @Override
    public int getMaxHP() {
        return 150;
    }

    @Override
    public String getIcon() {
        return "Wt";
    }

    @Override
    public String name() {
        return "魔女";
    }

    @Override
    public String description() {
        return "魔法を操る魔女\n近づかれる前に倒すのをモットーとしているため火力に比重をおいている";
    }

    @Override
    public DropItemTable getDropItemTable() {
        return new DropItemTable().addItem(Items.MDEF_BUFF_POTION, 0.2);
    }
}
