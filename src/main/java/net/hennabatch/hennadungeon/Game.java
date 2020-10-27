package net.hennabatch.hennadungeon;

import net.hennabatch.hennadungeon.config.Config;
import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.log.SystemLogger;
import net.hennabatch.hennadungeon.scene.RootEvent;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.util.Reference;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Game {

    private final Config config;

    public Game(){
        this.config = new Config();
        Reference.logger = new SystemLogger(this.config);
    }

    public void run(){
        RootEvent root = new RootEvent();
        SceneResult result = new SceneResult(SceneResult.EnumResult.CONTINUE, null);
        while (result.result().equals(SceneResult.EnumResult.CONTINUE)) {
            System.out.println("ddd");
            root.inputKey(getKeyInput());
            root.draw();
        }
    }

    private EnumKeyInput getKeyInput(){
        EnumKeyInput input = null;
        try(Terminal terminal = TerminalBuilder.terminal()) {
            int ch = 0;
            while (input == null){
                input = this.config.keyConfig().getByChar((char)terminal.reader().read());
            }
        } catch(IOException e) {
            Reference.logger.error(e.getMessage(), e);
        }
        return input;
    }
}
