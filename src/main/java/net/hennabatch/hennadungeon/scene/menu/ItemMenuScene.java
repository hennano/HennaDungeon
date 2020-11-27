package net.hennabatch.hennadungeon.scene.menu;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.object.DropItemEntity;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMenuScene extends TwoColumnMenuScene {

    private Dungeon dungeon;
    private int selectedPointer = 0;

    public ItemMenuScene(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected String getTitle() {
        return "アイテム";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = dungeon.getPlayer().getInventory().getItems().stream().map(Item::name).collect(Collectors.toList());
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
        selectedPointer = pointer;
        createChildScene(new SelectedItemMenu(pointer + 3));
        return new SceneResult(true, null);
    }

    @Override
    protected SceneResult<?> onExitChildScene(SceneResult<?> result) {
        if(result.data() instanceof EnumSelect){
            switch ((EnumSelect)result.data()){
                case USE:
                    useItem(selectedPointer);
                    selectedPointer = 0;
                    break;
                case DROP:
                    dropItem(selectedPointer);
                    selectedPointer = 0;
                    break;
            }
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

    private void useItem(int pointer){
        Item item = dungeon.getPlayer().getInventory().getItems().get(pointer);
        if(item.canUse(dungeon.getPlayer())){
            item.onUse(dungeon.getPlayer());
            dungeon.getPlayer().getInventory().getItems().remove(pointer);
            createChildScene(new MessageScene(new ArrayList<>(Arrays.asList(item.name() + "を使用した"))));
        }else{
            createChildScene(new MessageScene(new ArrayList<>(Arrays.asList(item.name() + "は今は使えないようだ"))));
        }
    }

    private void dropItem(int pointer){
        Item item = dungeon.getPlayer().getInventory().getItems().get(pointer);
        dungeon.getPlayer().getInventory().getItems().remove(pointer);
        dungeon.spawnEntity(new DropItemEntity(new Vec2d(dungeon.getPlayer()), dungeon, item));
        createChildScene(new MessageScene(new ArrayList<>(Arrays.asList(item.name() + "を捨てた"))));
    }

    private static class SelectedItemMenu extends Scene{

        private int selectItemHeight;
        private int pointer = 0;

        SelectedItemMenu(int selectItemHeight){
            this.selectItemHeight = selectItemHeight;
        }

        @Override
        protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
            switch (key){
                case UP:
                    pointer = Math.max(0, pointer - 1);
                    break;
                case DOWN:
                    pointer = Math.min(EnumSelect.values().length -1, pointer + 1);
                    break;
                case ENTER:
                    return new SceneResult<>(false, EnumSelect.byPointer(pointer));
                case CANCEL:
                    return new SceneResult<>(false, EnumSelect.CANCEL);
            }
            return new SceneResult<>(true, null);
        }

        @Override
        protected Screen draw(Screen screen) {
            screen.fillRect(3, Math.min(selectItemHeight - 1, screen.getHeight() - 4), 9, Math.min(selectItemHeight - 1, screen.getHeight() - 4) + EnumSelect.values().length + 1, Reference.SCREEN_EMPTY, true);
            screen.setPos(4, Math.min(selectItemHeight, screen.getHeight() - 4) + pointer, Reference.CURSOR_RIGHT);
            Arrays.stream(EnumSelect.values()).forEach(x -> screen.setRow(5, Math.min(selectItemHeight, screen.getHeight() - 4) + x.pointer, x.name, false, false));
            return screen;
        }
    }

    private enum EnumSelect{
        USE(0, "使う"),
        DROP(1, "捨てる"),
        CANCEL(2, "戻る");

        private String name;
        private int pointer;

        EnumSelect(int pointer, String name){
            this.pointer = pointer;
            this.name = name;
        }

        String getName() {
            return name;
        }

        static EnumSelect byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
