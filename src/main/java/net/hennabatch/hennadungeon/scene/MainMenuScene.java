package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.scene.event.RootEvent;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuScene extends TwoColumnMenuScene{

    private Dungeon dungeon;

    public MainMenuScene(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected String getTitle() {
        return "メニュー";
    }

    @Override
    protected List<String> getOptions() {
        return Arrays.stream(MainMenuSceneResult.values()).map(x -> x.name).collect(Collectors.toList());
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 7;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        switch (MainMenuSceneResult.byPointer(pointer)){
            case STATUS:
                createChildScene(new StatusScene());
            case ITEM:
                createChildScene(new ItemMenuScene(dungeon));
                break;
            case EQUIP:
                createChildScene(new EquipmentMenuScene(dungeon));
                break;
            case BACK:
                return new SceneResult(false, null);
            case EXIT:
                createChildScene(new YNMessageScene("ゲームを終了しますか？"));
                break;
        }
        return new SceneResult(true, null);
    }

    @Override
    protected SceneResult<?> onExitChildScene(SceneResult<?> result) {
        if(result.data() instanceof Boolean){
            if((Boolean) result.data()){
                return new SceneResult<>(false, RootEvent.SceneTransition.Exit);
            }
        }
        return super.onExitChildScene(result);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        screen.setRow(0,1, "名前　" + dungeon.getPlayer().name(), false, false);
        screen.setRow(0,2, "HP", false , false);
        screen.drawGauge(1, 2, screen.getWidth() - 3, dungeon.getPlayer().getCurrentHP(), dungeon.getPlayer().getMaxHP(), "■", Reference.SCREEN_EMPTY, true);
        screen.setRow(screen.getWidth() / 2, 3, dungeon.getPlayer().getCurrentHP() + "/" + dungeon.getPlayer().getMaxHP(), false, false);
        screen.setRow(1, 4, "ATK:" + dungeon.getPlayer().getStatus().getTrueATK(), false, false);
        screen.setRow(1, 5, "DEF:" + dungeon.getPlayer().getStatus().getTrueDEF(), false, false);
        screen.setRow(1, 6, "MDEF:" + dungeon.getPlayer().getStatus().getTrueMDEF(), false, false);
        screen.setRow(1, 7, "EVA:" + dungeon.getPlayer().getStatus().getTrueEVA(), false, false);
        return screen;
    }

    public enum MainMenuSceneResult{

        BACK(0, "戻る"),
        STATUS(1, "ステータス"),
        ITEM(2, "アイテム"),
        EQUIP(3, "装備"),
        EXIT(4, "やめる");

        private int pointer;
        private String name;

        MainMenuSceneResult(int pointer, String name){
            this.pointer = pointer;
            this.name = name;
        }

        public static MainMenuSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
