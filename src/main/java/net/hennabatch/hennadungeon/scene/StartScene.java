package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.EnumCursor;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;

public class StartScene<T extends StartScene.EnumStartSceneResult> extends Scene<T>{

    private int pointer = 0;

    @Override
     protected SceneResult<T> run(EnumKeyInput key, SceneResult<T> childSceneResult) {
        System.out.println(pointer);
        switch (key){
            case UP:
                if(pointer > 0) pointer--;
                break;
            case DOWN:
                if(pointer < EnumStartSceneResult.values().length) pointer++;
                break;
            case ENTER:
                return new SceneResult<T>(false, (T) EnumStartSceneResult.byPointer(pointer));

        }
        return new SceneResult<>(true, null);
    }

    public enum EnumStartSceneResult{
        START(0),
        EXIT(1);

        private final int pointer;

        EnumStartSceneResult(int pointer){
            this.pointer = pointer;
        }

        public static EnumStartSceneResult byPointer(int pointer){
            return Arrays.stream(EnumStartSceneResult.values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }

    @Override
    protected Screen draw(Screen screen) {
        String title = "ダンジョンに取り残されたんだが";
        String start = "はじめる";
        String exit = "おわる";

        screen.setRow((Reference.SCREEN_WIDTH / 2) - (title.length() / 2),Reference.SCREEN_HEIGHT / 4, title, false, false);
        screen.setRow((Reference.SCREEN_WIDTH / 2) - 3,Reference.SCREEN_HEIGHT / 2, start, false, false);
        screen.setRow((Reference.SCREEN_WIDTH / 2) - 3,Reference.SCREEN_HEIGHT / 2 + 1, exit, false, false);
        if(this.pointer == 0) screen.setRow((Reference.SCREEN_WIDTH / 2) - 4,Reference.SCREEN_HEIGHT / 2, EnumCursor.RIGHT.getCursor(), false, false);
        if(this.pointer == 1) screen.setRow((Reference.SCREEN_WIDTH / 2) - 4,Reference.SCREEN_HEIGHT / 2 + 1, EnumCursor.RIGHT.getCursor(), false, false);
        return screen;
    }
}
