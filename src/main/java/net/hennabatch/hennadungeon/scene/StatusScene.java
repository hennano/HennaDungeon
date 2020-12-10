package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Collections;

public class StatusScene extends Scene{

    private PlayerEntity player;

    public StatusScene(PlayerEntity player){
        this.player = player;
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        return new SceneResult(false, null);
    }

    @Override
    protected Screen draw(Screen screen) {
        String title = "ステータス";

        screen = new Screen(screen.getWidth(), screen.getHeight());
        screen.setRow((Reference.SCREEN_WIDTH / 2) - (title.length() / 2), 0, title, false, false);
        screen.setRow(0, 1, String.join("", Collections.nCopies(screen.getWidth(), Reference.HORIZONTAL_LINE)), false, false);

        screen.setRow(0,2, "名前:" + player.name(), false, false);
        screen.setRow(0,3, "HP", false , false);
        screen.drawGauge(1, 3, screen.getWidth() - 3, player.getCurrentHP(), player.getMaxHP(), "■", Reference.SCREEN_EMPTY, true);
        screen.setRow(screen.getWidth() / 2, 4, player.getCurrentHP() + "/" + player.getMaxHP(), false, false);
        screen.setRow(0, 5, "能力値", false, false);
        screen.setRow(1, 6, "TATK:" + player.getStatus().getTrueATK(),false, false);
        screen.setRow(1 + (screen.getWidth() / 2), 6,  "ATK:" + player.getStatus().getATK(player.getEquipmentWeapon(), player.getEquipmentArmor()), false, false);
        screen.setRow(1, 7, "TDEF:" + player.getStatus().getTrueDEF(),false, false);
        screen.setRow(1 + (screen.getWidth() / 2), 7,  "DEF:" + player.getStatus().getDEF(player.getEquipmentWeapon(), player.getEquipmentArmor()), false, false);
        screen.setRow(1, 8, "TMDEF:" + player.getStatus().getTrueMDEF(),false, false);
        screen.setRow(1 + (screen.getWidth() / 2), 8,   "MDEF:" + player.getStatus().getMDEF(player.getEquipmentWeapon(), player.getEquipmentArmor()), false, false);
        screen.setRow(1, 9, "TEVA:" + player.getStatus().getTrueEVA(),false, false);
        screen.setRow(1 + (screen.getWidth() / 2), 9,   "EVA:" + player.getStatus().getEVA(player.getEquipmentWeapon(), player.getEquipmentArmor()), false, false);
        screen.setRow(0, 10, "状態", false, false);
        if(player.getStatus().getEffects().size() != 0){
            for(int i = 0; i < player.getStatus().getEffects().size(); i++){
                screen.setRow( 1 + ((i % 2) * (screen.getWidth() / 2)), 11 + (i / 2), player.getStatus().getEffects().get(i).name(), false, false);
            }
        }else{
        screen.setRow(1, 11, "問題なし", false, false);
        }
        return screen;
    }
}
