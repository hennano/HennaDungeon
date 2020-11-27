package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.scene.menu.MenuScene;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StartScene extends MenuScene {

    @Override
    protected String getTitle() {
        return "ダンジョンに取り残されたんだが";
    }

    @Override
    protected List<String> getOptions() {
        return Arrays.stream(EnumStartSceneResult.values()).map(x -> x.optionName).collect(Collectors.toList());
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        return new SceneResult<>(false, EnumStartSceneResult.byPointer(pointer).getNext());
    }

    public enum EnumStartSceneResult{
        START(0, RootEvent.SceneTransition.GameScene, "はじめる"),
        SHOWRULE(1, RootEvent.SceneTransition.ShowRule, "ルール"),
        EXIT(2, RootEvent.SceneTransition.Exit, "おわる");

        private final int pointer;
        private final RootEvent.SceneTransition next;
        private final String optionName;

        EnumStartSceneResult(int pointer, RootEvent.SceneTransition next, String optionName){
            this.pointer = pointer;
            this.next = next;
            this.optionName = optionName;
        }

        public static EnumStartSceneResult byPointer(int pointer){
            return Arrays.stream(EnumStartSceneResult.values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }

        public RootEvent.SceneTransition getNext() {
            return next;
        }
    }
}
