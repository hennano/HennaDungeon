package net.hennabatch.hennadungeon.scene.menu.help;

import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.menu.TwoColumnMenuScene;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActionHelpScene extends TwoColumnMenuScene {
    @Override
    protected String getTitle() {
        return "行動";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = Arrays.stream(ActionHelpScene.ActionMenuSceneResult.values()).map(x -> x.name).collect(Collectors.toList());
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
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        if(pointer != getOptions().size() - 1) {
            screen.setRow(0, 0, ActionHelpScene.ActionMenuSceneResult.byPointer(pointer).description, true, false);
        }
        return screen;
    }

    public enum ActionMenuSceneResult{

        MOVE(0 , "移動", "移動キーを入力することでその方向に移動できます\n移動先に障害物がある場合は移動できません\nターンを消費します"),
        ATTACK(1, "攻撃", "敵がいる方向へ移動キーを入力、または攻撃準備状態で移動キーを入力を入力すると攻撃することができます。\nターンを消費します"),
        SKILL(2, "スキル", "スキルキーを入力するとスキルを発動できます\nスキルにはクールタイムが存在します\nスキル発動時にターンを消費します"),
        MENU(3, "メニュー", "メニューキーを入力するとメニューを開けます\nターンを消費しません"),
        ITEM(4, "アイテム", "メニューよりアイテムを使用できます\nターンを消費しません"),
        EQUIP(5, "装備", "メニューより武器・防具を装備できます\nターンを消費しません");

        private int pointer;
        private String name;
        private String description;

        ActionMenuSceneResult(int pointer, String name, String description){
            this.pointer = pointer;
            this.name = name;
            this.description = description;
        }

        public static ActionHelpScene.ActionMenuSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }

}
