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

public class HelpOtherPartyEndingEvent extends Event{
    @Override
    protected void initializeScene() {
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();

        List<String> endingMessage = new ArrayList<>(Arrays.asList(
                player + ":\nダンジョンを出たあと自分は必死で街へ戻った。",
                player + ":\n街で自分の所属しているギルドにダンジョンでのことを報告した。",
                player + ":\nギルドでの報告中、ダンジョン内で助けたあのパーティにまた出会った。",
                player + ":\nそのパーティはギルド内でも名高い実力があるパーティだった。",
                player + ":\nダンジョンでは斥候が不在であったため、奇襲を受けてしまったらしい。",
                player + ":\nそのパーティから一緒に行かないかと誘われた。もちろんその誘いを受けた。",
                player + ":\n今現在パーティで新しいダンジョンへ向かっている。",
                player + ":\nもちろん困難が待ち受けていると思う。",
                player + ":\nまあこのパーティがならどうにでもなるだろう。",
                "Congratulations!",
                "シナリオ:C0118237\nゲームデザイン:C0118237\nキャラクターデザイン:C01182137",
                "マップデザイン:C0118237\nプログラム:C0118237\nデバック:C01182137",
                "制作:そんなことよりながたにえんプロジェクト(1名)"
        ));
        createChildScene(new MessageScene(endingMessage));
    }

    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        if(getEventSequence() == 1) {
            createChildScene(new EndMenuScene());
            return new SceneResult(true, null);
        }
        return childSceneResult;
    }

    private class EndMenuScene extends MenuScene {
        @Override
        protected String getTitle() {
            return "True Ending";
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
