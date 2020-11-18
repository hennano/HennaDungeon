package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.ArmorItem;
import net.hennabatch.hennadungeon.item.EquipmentItem;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.item.WeaponItem;
import net.hennabatch.hennadungeon.util.Reference;

import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;

public class EquipmentMenuScene extends TwoColumnMenuScene{

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
        int inventoryIndex = -1;
        int cnt = 0;
        for(Map.Entry<Integer, EquipmentItem> item : equipmentItems.entrySet()){
            if(cnt == pointer) {
                inventoryIndex = item.getKey();
                break;
            }
                cnt++;
        }
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
        return screen;
    }

    private boolean isEquipmentItem(int index){
        return dungeon.getPlayer().getEquipmentWeaponIndex() == index || dungeon.getPlayer().getEquipmentArmorIndex()== index;
    }
}
