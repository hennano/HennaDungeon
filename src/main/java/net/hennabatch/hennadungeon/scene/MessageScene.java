package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.*;

public class MessageScene extends Scene{

    private int windowWidth;
    private int defaultWindowHeight = 3;
    private Deque<String> messages;
    private boolean canStretch;

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
        return new SceneResult(messages.size() <= 0, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        String mes = messages.peekFirst();
        if(canStretch){
            screen.fillRect(0, screen.getHeight() -  Math.max(defaultWindowHeight, calcHeigth(mes)) - 2, windowWidth - 1, screen.getHeight() - 1, "　", true);
            screen.setRow(1, screen.getHeight() -  Math.max(defaultWindowHeight, calcHeigth(mes)) - 1, windowWidth - 1, mes, true, false);
        }else{
            screen.fillRect(0, screen.getHeight() -  defaultWindowHeight - 2, windowWidth - 1, screen.getHeight() - 1, "　", true);
            screen.setRow(1, screen.getHeight() -  defaultWindowHeight - 1, windowWidth - 1, mes, true, false);
        }
        return screen;
    }

    private int calcHeigth(String message){
        List<String> mesList =  new ArrayList<>(Arrays.asList(message.split("\n")));
        return mesList.stream()
                .mapToInt(x -> x.length() / (windowWidth - 2))
                .sum();
    }
}
