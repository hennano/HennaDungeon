package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.entity.PlayerEntity;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        

        return null;
    }

    private int calcHeigth(String message){
        return drawingMessageLines(message).size();
    }


    private List<String> drawingMessageLines(String message){
        List<String> messageLines = spritCRLF(message);
        return messageLines.stream()
                .flatMap(x -> Stream.of(Pattern.compile("[\\s\\S]{1," + (windowWidth - 2) + "}").matcher(x).group()))
                .collect(Collectors.toList());
    }

    private List<String> spritCRLF(String message){
        return new ArrayList<>(Arrays.asList(message.split("\n")));
    }
}
