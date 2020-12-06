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

public class GolemEntity extends EnemyEntity {

    private Status status = new Status(50, 30, 10, 0);

    public GolemEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachaTagetAi<>(this, getDungeon().getPlayer(), 10.0));
        tasks.addTask(2, new AttackMeleeAi<>(this,  getDungeon().getPlayer(), getEquipmentWeapon()));
        tasks.addTask(3, new AddEffectAi<>(this, new BuffEffect(2, Status.EnumStatus.DEF, 2, false), 3, 5, x -> x.getCurrentHP() < x.getMaxHP() / 2));
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
        return "Gl";
    }

    @Override
    public String name() {
        return "ゴーレム";
    }

    @Override
    public DropItemTable getDropItemTable() {
        return new DropItemTable().addItem(Items.CRYSTAL, 1.0);
    }
}
