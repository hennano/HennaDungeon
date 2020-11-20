package net.hennabatch.hennadungeon.scene.menu;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.StatusEffect;
import net.hennabatch.hennadungeon.effect.TurnEffect;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.EquipmentItem;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.*;
import java.util.stream.Collectors;

public class EquipmentMenuScene extends TwoColumnMenuScene {

    private Dungeon dungeon;
    private Map<Integer, EquipmentItem> equipmentItems = new LinkedHashMap<>();

    public EquipmentMenuScene(Dungeon dungeon){
        this.dungeon = dungeon;
        for(int i = 0; i < dungeon.getPlayer().getInventory().getItems().size(); i++){
            if(dungeon.getPlayer().getInventory().getItems().get(i) instanceof EquipmentItem){
                equipmentItems.put(i, (EquipmentItem) dungeon.getPlayer().getInventory().getItems().get(i));
            }
        }
    }

    @Override
    protected String getTitle() {
        return "装備";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = equipmentItems.entrySet().stream().map(x -> x.getValue().name() + (isEquipmentItem(x.getKey()) ? "[E]" : "")).collect(Collectors.toList());
        options.add("戻る");
        return options;
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 5;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        if(pointer == getOptions().size() - 1) return new SceneResult(false, null);
        int inventoryIndex = getInventoryPointer(pointer);
        if(inventoryIndex == -1) {
            Reference.logger.error("No item found.", new Exception());
            return new SceneResult(true ,null);
        }
        if(isEquipmentItem(inventoryIndex)){
            if(dungeon.getPlayer().getInventory().getItems().get(inventoryIndex) instanceof WeaponItem){
                dungeon.getPlayer().setEquipmentWeapon(-1);
            }else if(dungeon.getPlayer().getInventory().getItems().get(inventoryIndex) instanceof ArmorItem){
                dungeon.getPlayer().setEquipmentArmor(-1);
            }
            Reference.logger.info(dungeon.getPlayer().getInventory().getItems().get(inventoryIndex).name() + "を外した");
        }else{
            if(dungeon.getPlayer().getInventory().getItems().get(inventoryIndex) instanceof WeaponItem){
                dungeon.getPlayer().setEquipmentWeapon(inventoryIndex);
            }else if(dungeon.getPlayer().getInventory().getItems().get(inventoryIndex) instanceof ArmorItem){
                dungeon.getPlayer().setEquipmentArmor(inventoryIndex);
            }
            Reference.logger.info(dungeon.getPlayer().getInventory().getItems().get(inventoryIndex).name() + "を装備した");
        }
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        if(pointer == getOptions().size() - 1) return screen;

        screen.setRow(0, 0, "装備効果", false, false);
        EquipmentItem item = (EquipmentItem) dungeon.getPlayer().getInventory().getItems().get(getInventoryPointer(pointer));
        for(int i = 0; i < item.getEffects().size(); i++){
            if (item.getEffects().get(i) instanceof StatusEffect) {
                screen.setRow(0,i + 1, ((StatusEffect) item.getEffects().get(i)).getTargetStatus().name() + ":" + (item.getEffects().get(i) instanceof BuffEffect ? "＋" : "－") + ((StatusEffect) item.getEffects().get(i)).getVal(), false, false);
            }else if(item.getEffects().get(i) instanceof TurnEffect){
                screen.setRow(0,  i + 1 , item.getEffects().get(i).name(), false, false);
            }
        }
        if(dungeon.getPlayer().getInventory().getItems().get(getInventoryPointer(pointer)) instanceof WeaponItem){
            screen = screen.overWrite(drawWeaponRange(new Screen(getDivPos(screen), screen.getHeight() / 2), (WeaponItem) item), 0, screen.getHeight() / 2);
        }
        return screen;
    }

    private Screen drawWeaponRange(Screen screen, WeaponItem item){
        screen.setRow(0,0,"攻撃範囲", false, false);
        screen.setPos(0, screen.getHeight() / 2, new PlayerEntity(new Vec2d(-1,-1), null).getIcon());
        Vec2d base = new Vec2d(0, screen.getHeight() / 2);
        item.range().stream().map(base::add).forEach(x -> screen.setPos(x.getX(), x.getY(), Reference.WEAPON_RANGE));
        return screen;
    }

    private boolean isEquipmentItem(int index){
        return dungeon.getPlayer().getEquipmentWeaponIndex() == index || dungeon.getPlayer().getEquipmentArmorIndex()== index;
    }

    private int getInventoryPointer(int pointer){
        int inventoryIndex = -1;
        int cnt = 0;
        for(Map.Entry<Integer, EquipmentItem> item : equipmentItems.entrySet()){
            if(cnt == pointer) {
                inventoryIndex = item.getKey();
                break;
            }
            cnt++;
        }
        return inventoryIndex;
    }
}
