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

public class SociallyDeadEndingEvent extends Event{
    @Override
    protected void initializeScene() {
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();

        List<String> endingMessage = new ArrayList<>(Arrays.asList(
                player + ":\nダンジョンを出たあと自分は必死で街へ戻った。",
                player + ":\n街で自分の所属しているギルドにダンジョンでのことを報告した。",
                player + ":\nそのときにゴーレムから入手した謎の水晶も渡した。",
                player + ":\nその謎の水晶には情報が記録されているらしい。",
                player + ":\nギルドにある解析装置で解析したところ一つの動画が見つかった。",
                player + ":\nその動画には自分がゴーレムに立ち向かう前のいざこざが映っていた。",
                player + ":\nこのことが決定打となり前パーティは仲間を見捨てるやべーやつ認定された",
                player + ":\nもう自分は関係ないが",
                player + ":\nギルドの紹介で新しいパーティを組んだ。",
                player + ":\nこのパーティでは割とうまくやっていけてる。",
                player + ":\n来週、因縁のダンジョンへ遠征することが決まった。",
                player + ":\n同時期にダンジョンにいたギルド1のパーティがまだ帰ってきていないらしい。",
                player + ":\n今回の遠征はそのパーティの遺品を回収することが目的。",
                player + ":\n前回の情報があるぶんうまく立ち回れると思う。",
                "ToDo:助けを求めている人を助ける"));
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
            return "Normal Ending";
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
