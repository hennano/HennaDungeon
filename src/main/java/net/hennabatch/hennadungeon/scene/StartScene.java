package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.EnumCursor;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;

public class StartScene extends Scene{

    private int pointer = 0;

    @Override
     protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        switch (key){
            case UP:
                if(pointer > 0) pointer--;
                break;
            case DOWN:
                if(pointer < EnumStartSceneResult.values().length) pointer++;
                break;
            case ENTER:
                return new SceneResult<>(false, EnumStartSceneResult.byPointer(pointer).getNext());

        }
        return new SceneResult<>(true, null);
    }

    public enum EnumStartSceneResult{
        START(0, RootEvent.SceneTransition.GameScene),
        SHOWRULE(1, RootEvent.SceneTransition.ShowRule),
        EXIT(2, RootEvent.SceneTransition.Exit);

        private final int pointer;
        private final RootEvent.SceneTransition next;

        EnumStartSceneResult(int pointer, RootEvent.SceneTransition next){
            this.pointer = pointer;
            this.next = next;
        }

        public static EnumStartSceneResult byPointer(int pointer){
            return Arrays.stream(EnumStartSceneResult.values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }

        public RootEvent.SceneTransition getNext() {
            return next;
        }
    }

    @Override
    protected Screen draw(Screen screen) {
        String title = "ダンジョンに取り残されたんだが";
        String start = "はじめる";
        String rule = "ルール";
        String exit = "おわる";

        screen.setRow((Reference.SCREEN_WIDTH / 2) - (title.length() / 2),Reference.SCREEN_HEIGHT / 4, title, false, false);
        screen.setRow((Reference.SCREEN_WIDTH / 2) - 3,Reference.SCREEN_HEIGHT / 2, start, false, false);
        screen.setRow((Reference.SCREEN_WIDTH / 2) - 3,Reference.SCREEN_HEIGHT / 2 + 1, rule, false, false);
        screen.setRow((Reference.SCREEN_WIDTH / 2) - 3,Reference.SCREEN_HEIGHT / 2 + 2, exit, false, false);
        if(this.pointer == 0) screen.setPos((Reference.SCREEN_WIDTH / 2) - 4,Reference.SCREEN_HEIGHT / 2, EnumCursor.RIGHT.getCursor());
        if(this.pointer == 1) screen.setPos((Reference.SCREEN_WIDTH / 2) - 4,Reference.SCREEN_HEIGHT / 2 + 1, EnumCursor.RIGHT.getCursor());
        if(this.pointer == 2) screen.setPos((Reference.SCREEN_WIDTH / 2) - 4,Reference.SCREEN_HEIGHT / 2 + 2, EnumCursor.RIGHT.getCursor());
        return screen;
    }
}
