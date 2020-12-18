package net.hennabatch.hennadungeon.scene.menu.help;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.menu.TwoColumnMenuScene;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HelpMenuScene extends TwoColumnMenuScene {

    private Dungeon dungeon;

    public HelpMenuScene(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected String getTitle() {
        return "ヘルプ";
    }

    @Override
    protected List<String> getOptions() {
        return Arrays.stream(HelpMenuScene.HelpMenuSceneResult.values()).map(x -> x.name).collect(Collectors.toList());
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 10;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        switch (HelpMenuSceneResult.byPointer(pointer)){
            case CONTROL:
                createChildScene(new ControlHelpScene());
                break;
            case ACTION:
                createChildScene(new ActionHelpScene());
                break;
            case BACK:
                return new SceneResult(false, null);
        }
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        screen.setRow(0, 0, HelpMenuSceneResult.byPointer(pointer).description, true, false);
        return screen;
    }

    public enum HelpMenuSceneResult{

        CONTROL(0, "操作方法", "操作方法についての説明"),
        ACTION(1, "行動", "行動についての説明"),
        STATUS(2, "ステータス", "ステータスについての説明"),
        EFFECT(3, "エフェクト", "エフェクトにていての説明"),
        ICON(4, "マップアイコン", "マップアイコンについての説明"),
        BACK(5, "戻る", "");

        private int pointer;
        private String name;
        private String description;

        HelpMenuSceneResult(int pointer, String name, String description){
            this.pointer = pointer;
            this.name = name;
            this.description = description;
        }

        public static HelpMenuScene.HelpMenuSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
