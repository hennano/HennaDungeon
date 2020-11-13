package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;

public class ShowRuleScene extends Scene{
    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        return new SceneResult<>(false, RootEvent.SceneTransition.StartScene);
    }

    @Override
    protected Screen draw(Screen screen) {
        String title = "ルール";
        String[] overview = {"才能がなくてパーティの荷物持ちであるワイ、ダンジョンでチョー強い敵に出会って捨て駒にされたなう",
                            "謎の力に目覚めて大怪我を負いながらなんとか倒した",
                            "悔しいから元パーティメンバーに「ざまぁ」（倒す）したい",
                            "とりあえず脱出しないとね"};
        String[] rule = {"ダンジョンから脱出することが目標",
                            "ダンジョンの出口は画面端に表示",
                            "行動するごとにHPが一定値ずつ減少",
                            "HPが0になるとゲームオーバー",
                Reference.config.keyConfig().getChar(EnumKeyInput.UP) + "で上," +
                Reference.config.keyConfig().getChar(EnumKeyInput.LEFT) + "で下," +
                Reference.config.keyConfig().getChar(EnumKeyInput.RIGHT) +"で右," +
                Reference.config.keyConfig().getChar(EnumKeyInput.DOWN) + "で下に移動"};

        screen.setRow((Reference.SCREEN_WIDTH / 2) - (title.length() / 2), 0, title, false, false);
        for(int i = 0; i< overview.length; i++){
            screen.setRow(0, i * 2 + 2, overview[i], true, false);
        }
        for(int i = 0; i< rule.length; i++){
            screen.setRow(0, i + 4 + overview.length * 2, rule[i], false, false);
        }
        return screen;
    }
}
