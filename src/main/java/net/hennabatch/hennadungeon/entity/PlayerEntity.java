package net.hennabatch.hennadungeon.entity;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.BleedingEffect;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class PlayerEntity extends BreakableEntity implements ITalkable, IHasInventory, IPickable{

    private WeaponItem equipmentWeapon;
    private ArmorItem equipmentArmor;
    private final Inventory inventory = new Inventory(50);

    public PlayerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public Status getStatus() {
        return new Status(50, 10, 0, 5);
    }

    @Override
    public int getMaxHP() {
        return 1000;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        return equipmentWeapon;
    }

    public void setEquipmentWeapon(WeaponItem weapon) {
        this.equipmentWeapon = weapon;
    }

    @Override
    public ArmorItem getEquipmentArmor() {
        return equipmentArmor;
    }

    public void setEquipmentArmor(ArmorItem equipmentArmor) {
        this.equipmentArmor = equipmentArmor;
    }


    public void attack(EnumDirection direction) {
        getDungeon().getEntities().stream()
                .filter(x -> x instanceof BreakableEntity)
                .filter(x -> getEquipmentWeapon().isInnerRange(x, direction))
                .map(x -> (BreakableEntity)x)
                .forEach(x -> x.onAttacked(this, getStatus().getATK(getEquipmentWeapon(), getEquipmentArmor()), getEquipmentWeapon().isMagic()));
    }

    @Override
    public void update() { }

    @Override
    protected void initilaize() {
        getStatus().addEffect(new BleedingEffect(-1, getDungeon().getDifficulty()));
    }

    @Override
    public String getIcon() {
        return "＠";
    }

    @Override
    public String name() {
        return "荷物持ち";
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean pick(Item item) {
        return inventory.addItem(item);
    }
}
