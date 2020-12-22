package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.DropItemTable;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.IMimicable;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.entity.ai.ApproachTargetAi;
import net.hennabatch.hennadungeon.entity.ai.AttackMeleeAi;
import net.hennabatch.hennadungeon.entity.ai.MimicUntilRangeAi;
import net.hennabatch.hennadungeon.entity.ai.StayAi;
import net.hennabatch.hennadungeon.entity.object.BoxEntity;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class MimicEntity extends EnemyEntity implements IMimicable {

    private Status status = new Status(50, 40, 30, 15);
    private boolean isMimicking = true;
    private String mimickingIcon;

    @Override
    public boolean isMimicking() {
        return isMimicking;
    }

    @Override
    public void setMimicking(boolean mimicking) {
        isMimicking = mimicking;
    }

    public MimicEntity(Vec2d pos, Dungeon dungeon) {
        this(pos, dungeon, new BoxEntity(pos, null, null, false).getIcon());
    }

    public MimicEntity(Vec2d pos, Dungeon dungeon, String mimickingIcon) {
        super(pos, dungeon);
        this.mimickingIcon = mimickingIcon;
    }

    @Override
    public void initializeAi() {
        tasks.addTask(0, new StayAi<>(this));
        tasks.addTask(1, new ApproachTargetAi<>(this, getDungeon().getPlayer(), 5.0));
        tasks.addTask(2, new AttackMeleeAi<>(this, getDungeon().getPlayer(), getEquipmentWeapon()));
        tasks.addTask(3, new MimicUntilRangeAi<>(this, getDungeon().getPlayer(), getEquipmentWeapon()));
    }

    @Override
    public Status getStatus() {
        return status;
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
        return isMimicking ? mimickingIcon : getTrueIcon();
    }

    public String getTrueIcon(){
        return "Mm";
    }

    @Override
    public String name() {
        return "ミミック";
    }

    @Override
    public String description() {
        return "何かしらに偽装して獲物が通りかかるのを待つ\n獲物が気づいたときにはもう手遅れだ\n宝箱に偽装することが多い";
    }

    @Override
    public DropItemTable getDropItemTable() {
        return new DropItemTable().addItem(Items.MULTIPLE_ATK_BUFF_POTION, 0.3)
                .addItem(Items.MULTIPLE_DEF_BUFF_POTION, 0.3)
                .addItem(Items.MULTIPLE_MDEF_BUFF_POTION, 0.3)
                .addItem(Items.HEAL_POTION, 1);
    }
}
