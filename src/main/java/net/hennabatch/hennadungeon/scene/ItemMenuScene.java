package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMenuScene extends TwoColumnMenuScene{

    private Dungeon dungeon;

    public ItemMenuScene(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected String getTitle() {
        return "アイテム";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = dungeon.getPlayer().getInventory().getItems().stream().map(x -> x.name()).collect(Collectors.toList());
        options.add("戻る");
        return options;
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 10;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        if(pointer == getOptions().size() - 1) return new SceneResult(false, null);
        Item item = dungeon.getPlayer().getInventory().getItems().get(pointer);
        if(item.canUse(dungeon.getPlayer())){
            item.onUse(dungeon.getPlayer());
            dungeon.getPlayer().getInventory().getItems().remove(pointer);
            createChildScene(new MessageScene(new ArrayList<>(Arrays.asList(item.name() + "を使用した"))));
        }else{
            createChildScene(new MessageScene(new ArrayList<>(Arrays.asList(item.name() + "は今は使えないようだ"))));
        }
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        if(pointer != getOptions().size() - 1){
            screen.setRow(0, 0, dungeon.getPlayer().getInventory().getItems().get(pointer).description(), true, false);
        }
        return screen;
    }
}
