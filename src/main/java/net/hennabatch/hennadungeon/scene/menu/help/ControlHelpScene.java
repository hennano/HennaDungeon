package net.hennabatch.hennadungeon.scene.menu.help;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.menu.TwoColumnMenuScene;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControlHelpScene extends TwoColumnMenuScene {

    @Override
    protected String getTitle() {
        return "操作方法";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = Arrays.stream(ControlHelpScene.ControlHelpSceneResult.values()).map(x -> x.key.name()).collect(Collectors.toList());
        options.add("戻る");
        return options;
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 6;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        if(pointer == getOptions().size() - 1) return new SceneResult(false, null);
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        if(pointer != getOptions().size() - 1) {
            screen.setRow(0, 0, "対応キー:\'" + Reference.config.keyConfig().getChar(ControlHelpSceneResult.byPointer(pointer).key) + "\'", true, false);
            screen.setRow(0, 2, ControlHelpSceneResult.byPointer(pointer).description, true, false);
        }
        return screen;
    }

    public enum ControlHelpSceneResult{

        UP(0, EnumKeyInput.UP, "上方向へのキャラクターまたはカーソルの移動が行えます"),
        DOWN(1, EnumKeyInput.DOWN, "下方向へのキャラクターまたはカーソルの移動が行えます"),
        LEFT(2, EnumKeyInput.LEFT, "左方向へのキャラクターまたはカーソルの移動が行えます"),
        RIGHT(3, EnumKeyInput.RIGHT, "右方向へのキャラクターまたはカーソルの移動が行えます"),
        ENTER(4, EnumKeyInput.ENTER, "選択の決定、メッセージ送りが行えます\nまた、ダンジョン内で入力すると攻撃準備状態になります"),
        CANCEL(5, EnumKeyInput.CANCEL, "選択の取り消し、メッセージのスキップが行えます\nまた、メニュー内で入力すると一つ前の画面に戻ります"),
        MENU(6, EnumKeyInput.MENU, "ダンジョン内で入力するとメニューの表示が行えます"),
        SKILL(7, EnumKeyInput.SKILL, "ダンジョン内で入力するとスキルを使用できます"),
        SEEPATH(8, EnumKeyInput.SEEPATH, "ダンジョン内で入力すると出口に一番近い部屋がマークされます");

        private int pointer;
        private EnumKeyInput key;
        private String description;

        ControlHelpSceneResult(int pointer, EnumKeyInput key, String description){
            this.pointer = pointer;
            this.key = key;
            this.description = description;
        }

        public static ControlHelpScene.ControlHelpSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
