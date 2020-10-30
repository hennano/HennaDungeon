package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;

public class StartScene<T extends StartScene.EnumStartSceneResult> extends Scene<T>{

    private int pointer = 0;

    @Override
    SceneResult<T> run(EnumKeyInput key, SceneResult<T> childSceneResult) {
        switch (key){
            case UP:
                if(pointer > 0) pointer++;
                break;
            case DOWN:
                if(pointer < EnumStartSceneResult.values().length) pointer--;
                break;
            case ENTER:
                return new SceneResult<T>(true, (T) EnumStartSceneResult.byPointer(pointer));

        }
        return new SceneResult<>(true, null);
    }

    public enum EnumStartSceneResult{
        START(0),
        EXIT(1);

        private int pointer;

        EnumStartSceneResult(int pointer){
            this.pointer = pointer;
        }

        public static EnumStartSceneResult byPointer(int pointer){
            return Arrays.stream(EnumStartSceneResult.values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }

    @Override
    public Screen draw() {
        String title = "ダンジョンに取り残されたんだが";
        this.screen.setRow(Reference.SCREEN_WIDTH / 2 - title.length() / 2,Reference.SCREEN_HEIGHT / 4, title, false, false);
        return super.draw();
    }
}
