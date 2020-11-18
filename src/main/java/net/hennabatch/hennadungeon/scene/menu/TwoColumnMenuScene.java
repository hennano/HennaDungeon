package net.hennabatch.hennadungeon.scene.menu;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TwoColumnMenuScene extends Scene {

    private int pointer = 0;
    private int selectUpper = 0;
    private int selectLower = 0;

    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        switch (key){
            case UP:
                if(pointer > 0) pointer--;
                onCursor(pointer);
                break;
            case DOWN:
                if(pointer < getOptions().size() - 1) pointer++;
                onCursor(pointer);
                break;
            case ENTER:
                return onSelected(pointer);
            case CANCEL:
                return new SceneResult<>(false ,null);
        }
        return new SceneResult<>(true, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        screen = new Screen(screen.getWidth(), screen.getHeight());
        screen.setRow((Reference.SCREEN_WIDTH / 2) - (getTitle().length() / 2), 0, getTitle(), false, false);
        screen.setRow(0, 1, String.join("", Collections.nCopies(screen.getWidth(), Reference.HORIZONTAL_LINE)), false, false);
        screen.setColumn(getDivPos(screen), 2, String.join("", Collections.nCopies(screen.getWidth(), Reference.VERTICAL_LINE)), false, false);
        screen.setPos(getDivPos(screen), 1, Reference.CROSS);
        updateSelectRange(screen);
        int sum = 0;
        for(int i = selectUpper; i <= selectLower; i++){
            screen.setRow(1, sum + 3, getDivPos(screen), getOptions().get(i), true, false);
            if(i == pointer){
                screen.setPos(0, sum + 3, Reference.CURSOR_RIGHT);
            }
            sum += getOptionHeights(screen).get(i);
        }
        if(selectUpper != 0) screen.setPos(getDivPos(screen) / 2, 2, Reference.CURSOR_UP);
        if(selectLower != getOptions().size() - 1) screen.setPos(getDivPos(screen) / 2, screen.getHeight() - 1, Reference.CURSOR_DOWN);
        screen = screen.overWrite(drawRightContent(new Screen(screen.getWidth() - (getDivPos(screen) + 1), screen.getHeight() - 2), pointer), getDivPos(screen) + 1, 2);
        Reference.logger.debug("pointer:" + pointer + " up:" + selectUpper + " low:" + selectLower);
        return screen;
    }

    private void updateSelectRange(Screen screen){
        if(pointer < selectUpper) selectUpper = pointer;
        while (true){
            selectLower = calcSelectLower(screen);
            if(pointer > selectLower){
                selectUpper++;
                continue;
            }
            break;
        }
    }

    private int calcSelectLower(Screen screen){
        List<Integer> heights = getOptionHeights(screen);
        int sum = 0;
        for(int i = selectUpper; i < getOptions().size(); i++){
            sum = sum + heights.get(i);
            if(sum > screen.getHeight() - 4) return i - 1;
        }
        return getOptions().size() - 1;
    }

    private List<Integer> getOptionHeights(Screen screen){
        return getOptions().stream().map(x -> screen.calcMessageHeight(x, screen.getWidth() - (getDivPos(screen) + 1)) + 1).collect(Collectors.toList());
    }

    protected abstract String getTitle();

    protected abstract List<String> getOptions();

    protected abstract int getDivPos(Screen screen);

    protected abstract SceneResult onSelected(int pointer);

    protected void onCursor(int pointer){}

    protected abstract Screen drawRightContent(Screen screen, int pointer);
}
