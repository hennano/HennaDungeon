package net.hennabatch.hennadungeon;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class HennaDungeon {

    public static void main(String[] args){

        Reference.logger.debug("initializing game");

        //ゲーム初期化
        RootEvent root = new RootEvent();
        Reference.logger.logScreen().overWrite(root.drawScreen(new Screen(Reference.SCREEN_WIDTH, Reference.SCREEN_HEIGHT))).println();


        try(Terminal terminal = TerminalBuilder.terminal()){
            while (root.inputKey(getKeyInput(terminal)).isChildSceneContinue()){
                Reference.logger.logScreen().overWrite(root.drawScreen(new Screen(Reference.SCREEN_WIDTH, Reference.SCREEN_HEIGHT))).println();
            }
        } catch (IOException e) {
            Reference.logger.error(e.getMessage(), e);
        }
    }

    private static EnumKeyInput getKeyInput(Terminal terminal)throws IOException{
        int ch;
        EnumKeyInput input = null;
        while (input == null) {
            ch = terminal.reader().read();
            char c = (char) ch;
            input = Reference.config.keyConfig().getByChar(c);
        }
        Reference.logger.debug("inputKey: " + input.name());
        return input;
    }
}
