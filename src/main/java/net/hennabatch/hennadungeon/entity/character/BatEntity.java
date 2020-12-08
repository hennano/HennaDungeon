package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.entity.DropItemTable;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.AddEffectAi;
import net.hennabatch.hennadungeon.entity.ai.ApproachaTagetAi;
import net.hennabatch.hennadungeon.entity.ai.AttackMeleeAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class BatEntity extends EnemyEntity {

    private Status status = new Status(20, 10, 10, 10);

    public BatEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachaTagetAi<>(this, getDungeon().getPlayer(), 5.0));
        tasks.addTask(2, new AttackMeleeAi<>(this,  getDungeon().getPlayer(), getEquipmentWeapon()));
        tasks.addTask(3, new AddEffectAi<>(this, new BuffEffect(3, Status.EnumStatus.EVA, 25, false), 3, 3, x -> true));
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
        return 100;
    }

    @Override
    public String getIcon() {
        return "Bd";
    }

    @Override
    public String name() {
        return "コウモリ";
    }

    @Override
    public DropItemTable getDropItemTable() {
        return new DropItemTable().addItem(Items.HEAL_POTION, 0.5)
                .addItem(Items.REGENRATION_POTION, 0.5);
    }
}
