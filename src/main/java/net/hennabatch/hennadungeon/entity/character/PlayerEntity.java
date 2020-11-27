package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.BleedingEffect;
import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.entity.*;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerEntity extends BreakableEntity implements ITalkable, IHasInventory, IAttackable, IPickable {

    private int equipmentWeaponIndex = -1;
    private int equipmentArmorIndex = -1;
    private final Inventory inventory = new Inventory(50);
    private Status status = new Status(50, 20, 0, 5);
    private int useSkillTurn = -Reference.PLAYER_SKILL_COOLTIME - 1;

    public PlayerEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public int getMaxHP() {
        return 500;
    }

    @Override
    public WeaponItem getEquipmentWeapon() {
        if(equipmentWeaponIndex >= 0){
            if(inventory.getItems().get(equipmentWeaponIndex) instanceof WeaponItem){
                return (WeaponItem)inventory.getItems().get(equipmentWeaponIndex);
            }
        }
        return (WeaponItem) Items.HAND;
    }

    @Override
    public void turnAction() {

    }

    public int getEquipmentWeaponIndex(){
        return equipmentWeaponIndex;
    }

    public void setEquipmentWeapon(int index) {
        this.equipmentWeaponIndex = index;
    }

    @Override
    public ArmorItem getEquipmentArmor() {
        if(equipmentArmorIndex >= 0){
            if(inventory.getItems().get(equipmentArmorIndex) instanceof ArmorItem){
                return (ArmorItem)inventory.getItems().get(equipmentArmorIndex);
            }
        }
        return null;
    }

    public int getEquipmentArmorIndex() {
        return equipmentArmorIndex;
    }

    public void setEquipmentArmor(int index) {
        this.equipmentArmorIndex = index;
    }


    public void attack(EnumDirection direction) {
        List<BreakableEntity> targetEntities = getDungeon().getEntities().stream()
                .filter(x -> x instanceof BreakableEntity)
                .filter(x -> getEquipmentWeapon().isInnerRange(this, x, direction))
                .map(x -> (BreakableEntity)x).collect(Collectors.toList());
        boolean isCompleted = targetEntities.stream()
                .anyMatch(x -> x.onAttacked(this, getStatus().getATK(getEquipmentWeapon(), getEquipmentArmor()), getEquipmentWeapon().isMagic(), getEquipmentWeapon().isMelee(), getEquipmentWeapon().giveEffectsForAttacked()));
        if(isCompleted){
            getEquipmentWeapon().giveEffectsForAttacker().forEach(x -> getStatus().addEffect(x));
        }
    }

    @Override
    public void initilaize() {}

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

    public void useSkill(){
        if(!canUseSkill()) return;
        getStatus().addEffect(new BuffEffect(5, Status.EnumStatus.ATK, 2, true));
        getStatus().addEffect(new BuffEffect(5, Status.EnumStatus.DEF, 2, true));
        getStatus().addEffect(new BleedingEffect(10, getDungeon().getDifficulty()));
        useSkillTurn = getTurn();
    }

    public boolean canUseSkill(){
        return useSkillTurn + Reference.PLAYER_SKILL_COOLTIME < getTurn();
    }
}
