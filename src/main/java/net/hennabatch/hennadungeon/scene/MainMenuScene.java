package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.EnumCursor;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;

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
                return new SceneResult<>(false, MainMenuScene.EnumMainMenuSceneResult.byPointer(pointer));

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
        screen.setRow(1, 1, item1, false, false);
        screen.setRow(item1.length() + 3, 1, item2, false, false);
        screen.setRow(item1.length() + item2.length() + 5, 1, item3, false, false);
        screen.setRow(1, 2, item4, false, false);
        screen.setRow(item4.length() + 3, 2, item5, false, false);
        switch (pointer){
            case 0:
                screen.setPos(0, 1, EnumCursor.RIGHT.getCursor());
                break;
            case 1:
                screen.setPos(item1.length() + 2, 1, EnumCursor.RIGHT.getCursor());
                break;
            case 2:
                screen.setPos(item1.length() + item2.length() + 4, 1, EnumCursor.RIGHT.getCursor());
                break;
            case 3:
                screen.setPos(0, 2, EnumCursor.RIGHT.getCursor());
                break;
            case 4:
                screen.setPos(item4.length() + 2, 2, EnumCursor.RIGHT.getCursor());
        }

        return screen;
    }
}
