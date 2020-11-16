package net.hennabatch.hennadungeon.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenuScene extends TwoColumnMenuScene{
    @Override
    protected String getTitle() {
        return "メニュー";
    }

    @Override
    protected List<String> getOptions() {
        return new ArrayList<>(Arrays.asList("戻る",
                "ステータス",
                "アイテム",
                "そうび",
                "やめる"));
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 7;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        return new SceneResult(false, null);
    }

    @Override
    protected void onCursor(int pointer) {
    }

    @Override
    protected Screen drawRightContent(Screen screen) {
        return screen;
    }
}
