package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.floor.ExitRoom;
import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.effect.RegenerationEffect;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.AddEffectAi;
import net.hennabatch.hennadungeon.entity.ai.ApproachTargetInaFloorAi;
import net.hennabatch.hennadungeon.entity.ai.AttackMeleeAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class RoleTankerEntity extends EnemyEntity {

    private Status status = new Status(40, 60, 35, 0);

    public RoleTankerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        Floor floor = getDungeon().getFloors().stream().filter(x -> x instanceof ExitRoom).findFirst().get();
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachTargetInaFloorAi<>(this, getDungeon().getPlayer(), floor));
        tasks.addTask(2, new AttackMeleeAi<>(this, getDungeon().getPlayer(), getEquipmentWeapon()));
        tasks.addTask(5, new AddEffectAi<>(this, new RegenerationEffect(5, 50), 1, 0, x -> x.getMaxHP() / getCurrentHP() > 4));
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
        return (WeaponItem) Items.SWORD;
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

    @Override
    public String description() {
        return "パーティでタンクを担当していた人\n戦闘センスは眼を見張るものがあるが他人を見下す癖がある";
    }
}
