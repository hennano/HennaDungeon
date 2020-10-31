package net.hennabatch.hennadungeon.log;

import net.hennabatch.hennadungeon.HennaDungeon;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.util.Reference;
import org.apache.log4j.Logger;

import java.util.*;

public class SystemLogger {

    private final Logger logger = Logger.getLogger(HennaDungeon.class);
    private final List<String> logQueue = new ArrayList<String>(){
        @Override
        public boolean add(String s) {
            if(size() >= 20){
                remove(0);
            }
            return super.add(s);
        }
    };


    public void debug(String message){
        logger.debug(message);
        logQueue.add(message);
    }

    public void info(String message){
        logger.info(message);
        logQueue.add(message);
    }

    public void warn(String message){
        logger.warn(message);
        logQueue.add(message);
    }

    public void error(String message, Throwable t){
        logger.error(message, t);
        logQueue.add(message);
    }

    public Screen logScreen(){
        Screen screen = Screen.createBaseScreen(Reference.SCREEN_WIDTH * 2 + 1, Reference.SCREEN_HEIGHT);
        String splitLine = String.join("", Collections.nCopies(Reference.SCREEN_HEIGHT, "｜"));
        screen.setColumn(Reference.SCREEN_WIDTH, 0, splitLine, false, true);
        for(int i=0; i < Math.min(logQueue.size(), Reference.SCREEN_HEIGHT); i++){
            screen.setRow(Reference.SCREEN_WIDTH + 1, i, logQueue.get(i), false, false);
        }
        return screen;
    }
}
