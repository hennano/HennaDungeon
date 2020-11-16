package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class ShowRuleScene extends Scene{
    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        return new SceneResult<>(false, RootEvent.SceneTransition.StartScene);
    }

    @Override
    protected Screen draw(Screen screen) {
        String title = "ルール";
        String overview = "才能がなくてパーティの荷物持ちであるワイ、ダンジョンでチョー強い敵に出会って捨て駒にされたなう\n" +
                            "謎の力に目覚めて大怪我を負いながらなんとか倒した\n" +
                            "悔しいから元パーティメンバーに「ざまぁ」（倒す）したい\n" +
                            "とりあえず脱出しないとね";
        String rule = "ダンジョンから脱出することが目標\n" +
                            "ダンジョンの出口は画面端に表示\n"+
                            "行動するごとにHPが一定値ずつ減少\n" +
                            "HPが0になるとゲームオーバー\n" +
                Reference.config.keyConfig().getChar(EnumKeyInput.UP) + "で上," +
                Reference.config.keyConfig().getChar(EnumKeyInput.LEFT) + "で下," +
                Reference.config.keyConfig().getChar(EnumKeyInput.RIGHT) +"で右," +
                Reference.config.keyConfig().getChar(EnumKeyInput.DOWN) + "で下に移動";

        screen.setRow((Reference.SCREEN_WIDTH / 2) - (title.length() / 2), 0, title, false, false);
        Vec2d overViewEnd =  screen.setRow(0, 2, overview, true, false);
        screen.setRow(0, overViewEnd.getY() + 2, rule, true, false);
        return screen;
    }
}
