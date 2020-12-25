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
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.UP) + ":上");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.DOWN) + ":下");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.LEFT) + ":左");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.RIGHT) + ":右");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.SUBMIT) + ":決定");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.CANCEL) + ":キャンセル");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.MENU) + ":メニューを開く");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.SKILL) + ":スキルを使用する");
        Reference.logger.info(Reference.config.keyConfig().getChar(EnumKeyInput.SEEPATH) + ":帰り道を確認する");
        Reference.logger.info("詳細はゲーム内メニューのヘルプにて");
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
