package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.ArrayList;
import java.util.Arrays;

public class YNMessageScene extends MessageScene{

    private int pointer = 0;

    public YNMessageScene(String message) {
        super(new ArrayList<>(Arrays.asList(message)));
    }

    public YNMessageScene(int width, String message, boolean canStretch) {
        super(width, new ArrayList<>(Arrays.asList(message)), canStretch);
    }

    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        switch (key){
            case LEFT:
                pointer = 0;
                break;
            case RIGHT:
                pointer = 1;
                break;
            case ENTER:
                return new SceneResult<>(false, pointer == 0);
        }
        return new SceneResult<>(true, null);
    }

    @Override
    protected Screen draw(Screen screen) {
         drawMessage(screen, messages.peekFirst());

         String yes = "はい";
         String no = "いいえ";

         screen.setRow(screen.getWidth() - (yes.length() + 2 + no.length() + 1), screen.getHeight() - 2, yes, false, false);
         screen.setRow(screen.getWidth() - (no.length() + 1), screen.getHeight() - 2, no, false, false);
         screen.setPos(pointer == 0 ? screen.getWidth() - (yes.length() + 2 + no.length() + 2): screen.getWidth() - (no.length() + 2), screen.getHeight() - 2, Reference.CURSOR_RIGHT);
         return screen;
    }
}