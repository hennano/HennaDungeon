package net.hennabatch.hennadungeon.scene.menu;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.List;

public abstract class MenuScene extends Scene {

    private int pointer = 0;

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        switch (key){
            case UP:
                if(pointer > 0) pointer--;
                onCursor(pointer);
                break;
            case DOWN:
                if(pointer < getOptions().size() - 1) pointer++;
                onCursor(pointer);
                break;
            case SUBMIT:
                return onSelected(pointer);
        }
        return new SceneResult(true, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        screen = new Screen(screen.getWidth(), screen.getHeight());
        screen.setRow((Reference.SCREEN_WIDTH / 2) - (getTitle().length() / 2),Reference.SCREEN_HEIGHT / 4, getTitle(), false, true);
        int startPos = getOptions().stream()
                .mapToInt(x -> (Reference.SCREEN_WIDTH / 2) - (x.length() / 2))
                .min().getAsInt();
        for(int i = 0; i < getOptions().size(); i++){
            if(pointer == i) screen.setPos(startPos - 1,Reference.SCREEN_HEIGHT / 2 + i, Reference.CURSOR_RIGHT);
            screen.setRow(startPos,Reference.SCREEN_HEIGHT / 2 + i, getOptions().get(i), false, false);
        }
        return screen;
    }

    protected abstract String getTitle();

    protected abstract List<String> getOptions();

    protected abstract SceneResult onSelected(int pointer);

    protected void onCursor(int pointer){}
}
