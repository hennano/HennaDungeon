package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainMenuScene extends Scene{

    private int pointer = 0;

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        switch (key){
            case LEFT:
                if(pointer > 0) pointer--;
                break;
            case RIGHT:
                if(pointer < MainMenuScene.EnumMainMenuSceneResult.values().length - 1) pointer++;
                break;
            case ENTER:
                createChildScene(new MessageScene(new ArrayList<>(Arrays.asList("そんなことよりおうどん食べたい", "ながたにえんでも可"))));
                return new SceneResult<>(true, MainMenuScene.EnumMainMenuSceneResult.byPointer(pointer));

        }
        return new SceneResult<>(true, null);
    }

    public enum EnumMainMenuSceneResult{
        BACK(0),
        SHOWSTATUS(1),
        ITEM(2),
        EQUIP(3),
        EXIT(4);

        private final int pointer;

        EnumMainMenuSceneResult(int pointer){
            this.pointer = pointer;
        }

        public static MainMenuScene.EnumMainMenuSceneResult byPointer(int pointer){
            return Arrays.stream(MainMenuScene.EnumMainMenuSceneResult.values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }

    }

    @Override
    protected Screen draw(Screen screen) {

        String title = "メニュー";
        String item1 = "戻る";
        String item2 = "ステータス";
        String item3 = "アイテム";
        String item4 = "装備";
        String item5 = "やめる";

        screen = new Screen(screen.getWidth(), screen.getHeight());
        screen.setRow((Reference.SCREEN_WIDTH / 2) - (title.length() / 2), 0, title, false, false);
        screen.setRow(0, 1, String.join("", Collections.nCopies(screen.getWidth(), "―")), false, false);
        screen.setColumn(screen.getWidth() /2, 2, String.join("", Collections.nCopies(screen.getWidth(), "―")), false, false);
        return screen;
    }
}
