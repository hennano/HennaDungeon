package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.scene.event.RootEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuScene extends TwoColumnMenuScene{

    private Dungeon dungeon;

    public MainMenuScene(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected String getTitle() {
        return "メニュー";
    }

    @Override
    protected List<String> getOptions() {
        return Arrays.stream(MainMenuSceneResult.values()).map(x -> x.name).collect(Collectors.toList());
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 7;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        switch (MainMenuSceneResult.byPointer(pointer)){
            case STATUS:
            case ITEM:
            case EQUIP:
                createChildScene(new StatusScene());
                break;
            case BACK:
                return new SceneResult(false, null);
            case EXIT:
                return new SceneResult(false, RootEvent.SceneTransition.Exit);
        }
        return new SceneResult(true, null);
    }

    @Override
    protected void onCursor(int pointer) {
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        return screen;
    }

    public enum MainMenuSceneResult{

        BACK(0, "戻る"),
        STATUS(1, "ステータス"),
        ITEM(2, "アイテム"),
        EQUIP(3, "装備"),
        EXIT(4, "やめる");



        private int pointer;
        private String name;

        MainMenuSceneResult(int pointer, String name){
            this.pointer = pointer;
            this.name = name;
        }

        public static MainMenuSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
