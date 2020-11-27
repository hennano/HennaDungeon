package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.scene.menu.MenuScene;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameOverScene extends MenuScene {

    @Override
    protected String getTitle() {
        return "ゲームオーバー";
    }

    @Override
    protected List<String> getOptions() {
        return Arrays.stream(GameOverScene.EnumGameOverSceneResult.values()).map(x -> x.optionName).collect(Collectors.toList());
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        return new SceneResult<>(false, EnumGameOverSceneResult.byPointer(pointer).getNext());
    }

    private enum EnumGameOverSceneResult{
        Retry(0, RootEvent.SceneTransition.GameScene, "もういちど挑戦する"),
        Back(1, RootEvent.SceneTransition.StartScene, "スタートメニューへ戻る");

        private final int pointer;
        private final RootEvent.SceneTransition next;
        private final String optionName;

        EnumGameOverSceneResult(int pointer, RootEvent.SceneTransition next, String optionName){
            this.pointer = pointer;
            this.next = next;
            this.optionName = optionName;
        }

        public static GameOverScene.EnumGameOverSceneResult byPointer(int pointer){
            return Arrays.stream(GameOverScene.EnumGameOverSceneResult.values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }

        public RootEvent.SceneTransition getNext() {
            return next;
        }
    }
}
