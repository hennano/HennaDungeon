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

public class RoleDebufferEntity extends EnemyEntity {

    private Status status = new Status(40, 15, 15, 20);

    public RoleDebufferEntity(Vec2d pos, Dungeon dungeon) {
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
        return (ArmorItem) Items.LEATHER_ARMOR;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return (WeaponItem) Items.POISON_DAGGER;
    }

    @Override
    public int getMaxHP() {
        return 500;
    }

    @Override
    public String getIcon() {
        return "Rd";
    }

    @Override
    public String name() {
        return "ドラコーン";
    }

    @Override
    public String description() {
        return "パーティで斥候を担当していた人\n無口だが"+ new PlayerEntity(new Vec2d(0,0), null).name() +"に対してはよく話すようになった";
    }
}
