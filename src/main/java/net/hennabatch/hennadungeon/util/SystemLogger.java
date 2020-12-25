package net.hennabatch.hennadungeon.util;

import net.hennabatch.hennadungeon.HennaDungeon;
import net.hennabatch.hennadungeon.config.EnumRunMode;
import net.hennabatch.hennadungeon.scene.Screen;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if(Reference.config == null) return;
        if(Reference.config.runMode().equals(EnumRunMode.DEBUG)){
            logQueue.add(message);
        }
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
        Screen logScreen = new Screen(Reference.SCREEN_WIDTH, Reference.SCREEN_HEIGHT);
        for(int i=0; i < Math.min(logQueue.size(), Reference.SCREEN_HEIGHT); i++){
            screen.setRow(Reference.SCREEN_WIDTH + 1, i, logQueue.get(i).replace("\n", ""), false, false);
        }
        return screen;
    }
}
