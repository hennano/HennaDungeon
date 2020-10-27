package net.hennabatch.hennadungeon.log;

import net.hennabatch.hennadungeon.HennaDungeon;
import net.hennabatch.hennadungeon.config.Config;
import net.hennabatch.hennadungeon.config.EnumRunMode;
import org.apache.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;

public class SystemLogger {

    private final Config config;
    private final Logger logger = Logger.getLogger(HennaDungeon.class);
    private final Deque<String> logQueue = new ArrayDeque<>();

    public SystemLogger(Config configIn){
        config = configIn;
    }

    public void debug(String message){
        logger.debug(message);
        if(config.runMode().equals(EnumRunMode.DEBUG)) logQueue.push(message);
    }

    public void info(String message){
        logger.info(message);
        logQueue.push(message);
    }

    public void warn(String message){
        logger.warn(message);
        logQueue.push(message);
    }

    public void error(String message, Throwable t){
        logger.error(message, t);
        logQueue.push(message);
    }
}
