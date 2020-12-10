package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.menu.MenuScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartyKillEndingEvent extends Event{

    @Override
    protected void initializeScene() {
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();

        List<String> endingMessage = new ArrayList<>(Arrays.asList(
                player + ":\nダンジョンを出たあと自分は必死で街へ戻った",
                player + ":\n街で自分の所属しているギルドにダンジョンでのことを報告した",
                player + ":\nただ初めて人殺しをして興奮していたからうまく伝わったかはわからない",
                player + ":\n自分はしばらくの間休暇を取ることにした",
                player + ":\n休暇中、ダンジョンでの出来事が頭から離れない",
                player + ":\n少し外に出てみようかと思ったが足が震える",
                player + ":\n自分はまた冒険者として活動できるのであろうか",
                "ToDo:元パーティメンバーを倒さないで逃げる方法を見つける"
        ));
        createChildScene(new MessageScene(endingMessage));
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        if(getEventSequence() == 1) {
            createChildScene(new EndMenuScene());
            return new SceneResult(true, null);
        }
        return childSceneResult;
    }

    private static class EndMenuScene extends MenuScene{
        @Override
        protected String getTitle() {
            return "Good Ending";
        }

        @Override
        protected List<String> getOptions() {
            return new ArrayList<>(Arrays.asList("タイトルへ戻る"));
        }

        @Override
        protected SceneResult onSelected(int pointer) {
            return new SceneResult(false, RootEvent.SceneTransition.StartScene);
        }
    }
}
