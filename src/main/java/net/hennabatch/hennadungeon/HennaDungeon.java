package net.hennabatch.hennadungeon;

import net.hennabatch.hennadungeon.config.Config;
import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.Console;
import java.io.IOException;

public class HennaDungeon {

    public static void main(String[] srgs){

        //ゲーム初期化
        RootEvent root = new RootEvent();


        try(Terminal terminal = TerminalBuilder.terminal()){
            while (root.inputKey(getkeyInput(terminal)).isChildSceneContinue()){
                root.draw();
            }
        } catch (IOException e) {
            Reference.logger.error(e.getMessage(), e);
            return;
        }

    }

    private static EnumKeyInput getkeyInput(Terminal terminal)throws IOException{
        int ch = 0;
        EnumKeyInput input = null;
        while (input == null) {
            ch = terminal.reader().read();
            char c = (char) ch;
            input = Reference.config.keyConfig().getByChar(c);
        }
        return input;
    }
}
