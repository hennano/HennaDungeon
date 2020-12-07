package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.RegenerationEffect;
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

public class RoleAttackerEntity extends EnemyEntity {
    public RoleAttackerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachaTagetAi<>(this, getDungeon().getPlayer(), 10.0));
        tasks.addTask(2, new AttackMeleeAi<>(this, getDungeon().getPlayer(), getEquipmentWeapon()));
        tasks.addTask(5, new AddEffectAi<>(this, new RegenerationEffect(5, 50), 1, 0, x -> x.getMaxHP() / getCurrentHP() > 4));
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public ArmorItem getEquipmentArmor() {
        return (ArmorItem) Items.CROTHES;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return (WeaponItem) Items.GRIMOIRE;
    }

    @Override
    public int getMaxHP() {
        return 700;
    }

    @Override
    public String getIcon() {
        return "Ra";
    }

    @Override
    public String name() {
        return "アリエテ";
    }
}
