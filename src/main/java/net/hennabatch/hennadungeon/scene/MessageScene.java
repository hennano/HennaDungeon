package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MessageScene extends Scene{

    private final int windowWidth;
    private final int defaultWindowHeight = 3;
    protected final Deque<String> messages;
    private final boolean canStretch;

    public MessageScene(List<String> messages){
        this(Reference.SCREEN_WIDTH, messages, false);
    }

    public MessageScene(int width, List<String> messages, boolean canStretch){
        this.windowWidth = width;
        this.messages = new ArrayDeque<>(messages);
        this.canStretch = canStretch;
    }

    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        return new SceneResult(messages.size() > 0, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        String mes = messages.pollFirst();
        Reference.logger.info(mes);
        drawMessage(screen, mes);
        return screen;
    }

    protected void drawMessage(Screen screen, String mes){
        if(canStretch){
            screen.fillRect(0, screen.getHeight() -  Math.max(defaultWindowHeight, screen.calcMessageHeight(mes, 2)) - 2, windowWidth - 1, screen.getHeight() - 1, Reference.SCREEN_EMPTY, true);
            screen.setRow(1, screen.getHeight() -  Math.max(defaultWindowHeight, screen.calcMessageHeight(mes, 2)) - 1, windowWidth - 1, mes, true, false);
        }else{
            screen.fillRect(0, screen.getHeight() -  defaultWindowHeight - 2, windowWidth - 1, screen.getHeight() - 1, Reference.SCREEN_EMPTY, true);
            screen.setRow(1, screen.getHeight() -  defaultWindowHeight - 1, windowWidth - 1, mes, true, false);
        }
    }
}
