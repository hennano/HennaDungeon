package net.hennabatch.hennadungeon.scene.menu.help;

import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.menu.TwoColumnMenuScene;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatusHelpScene extends TwoColumnMenuScene {
    @Override
    protected String getTitle() {
        return "ステータス";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = Arrays.stream(StatusHelpScene.StatusHelpSceneResult.values()).map(x -> x.name).collect(Collectors.toList());
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
            screen.setRow(0, 0, StatusHelpScene.StatusHelpSceneResult.byPointer(pointer).description, true, false);
        }
        return screen;
    }

    public enum StatusHelpSceneResult{

        ATK(0, Status.EnumStatus.ATK.name(), "攻撃力\n数値が高いほどより多くのダメージを与えられる"),
        DEF(1, Status.EnumStatus.DEF.name(), "防御力\n数値が高いほど受ける物理ダメージが減る"),
        MDEF(2, Status.EnumStatus.MDEF.name(), "魔法防御力\n数値が高いほど受ける魔法ダメージが割合で減る\n最大" + Reference.MAX_MDEFEND + "まで"),
        EVA(3, Status.EnumStatus.EVA.name(), "回避率\n数値が高いほど敵の攻撃を回避できる確立が増える\n最大" + Reference.MAX_EVASION + "まで"),
        T(4, "Tつきステータス", "頭にTがつくステータスは素のステータス値\nない場合は装備やエフェクトによって変動した値");

        private int pointer;
        private String name;
        private String description;

        StatusHelpSceneResult(int pointer, String name, String description){
            this.pointer = pointer;
            this.name = name;
            this.description = description;
        }

        public static StatusHelpScene.StatusHelpSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
