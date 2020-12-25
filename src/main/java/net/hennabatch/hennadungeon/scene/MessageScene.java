package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.ArrayList;
import java.util.List;

public class MessageScene extends Scene{

    private final int windowWidth;
    private final int defaultWindowHeight = 3;
    protected final List<String> messages;
    private int messagesIndex = 0;
    private final boolean canStretch;

    public MessageScene(List<String> messages){
        this(Reference.SCREEN_WIDTH, messages, false);
    }

    public MessageScene(int width, List<String> messages, boolean canStretch){
        this.windowWidth = width;
        this.messages = new ArrayList<>(messages);
        this.canStretch = canStretch;
        Reference.logger.info(messages.get(messagesIndex));
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        switch (key){
            case SUBMIT:
                return new SceneResult(showNextMessage(), null);
            case CANCEL:
                return new SceneResult(skipMessages(), null);
            default:
                return new SceneResult(true, null);
        }
    }

    private boolean showNextMessage(){
        messagesIndex++;
        if(messages.size() > messagesIndex) {
            Reference.logger.info(messages.get(messagesIndex));
            return true;
        }else{
            return false;
        }
    }

    private boolean skipMessages(){
        if(messages.size() < messagesIndex + 1) return false;
        messages.subList(messagesIndex + 1, messages.size()).forEach(Reference.logger::info);
        return false;
    }

    @Override
    protected Screen draw(Screen screen) {
        drawMessage(screen, messages.get(messagesIndex));
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
