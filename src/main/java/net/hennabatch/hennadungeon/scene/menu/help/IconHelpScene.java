package net.hennabatch.hennadungeon.scene.menu.help;

import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.*;
import net.hennabatch.hennadungeon.entity.object.*;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.menu.TwoColumnMenuScene;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IconHelpScene extends TwoColumnMenuScene {
    @Override
    protected String getTitle() {
        return "マップアイコン";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = Arrays.stream(IconHelpScene.IconHelpSceneResult.values()).map(x -> x.name).collect(Collectors.toList());
        options.add("戻る");
        return options;
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 8;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        if(pointer == getOptions().size() - 1) return new SceneResult(false, null);
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        if(pointer != getOptions().size() - 1) {
            screen.setRow(1, 0, "アイコン\""+ IconHelpSceneResult.byPointer(pointer).icon + "\"", true, false);
            screen.setRow(0, 2, IconHelpSceneResult.byPointer(pointer).description, true, false);
        }
        return screen;
    }

    private enum IconHelpSceneResult{

        WALL(0, "壁", Reference.DUNGEON_WALL, "ダンジョンを構成する壁\n移動不可"),
        STEPS(1, new ExitEntity(new Vec2d(0, 0), null)),
        BOX(2, new BoxEntity(new Vec2d(0, 0), null, null, false)),
        DROP(3, "ドロップアイテム", new DropItemEntity(new Vec2d(0, 0), null, null).getIcon(), new DropItemEntity(new Vec2d(0, 0), null, null).description()),
        TRAP(4, new TrapEntity(new Vec2d(0, 0), null, null)),
        SEAL(5, new WallEntity(new Vec2d(0, 0), null)),
        PLAYER(6, new PlayerEntity(new Vec2d(0, 0), null)),
        MEMBER(7, new HelpedPartyMemberEntity(new Vec2d(0, 0), null)),
        LEADER(8, new HelpedPartyLeaderEntity(new Vec2d(0, 0), null)),
        GOBLIN(9, new GoblinEntity(new Vec2d(0, 0), null)),
        SLIME(10, new SlimeEntity(new Vec2d(0, 0), null)),
        BAT(11, new BatEntity(new Vec2d(0, 0), null)),
        OAK(12, new OakEntity(new Vec2d(0, 0), null)),
        WITCH(13, new WitchEntity(new Vec2d(0, 0), null)),
        MIMIC(14, new MimicEntity(new Vec2d(0, 0), null).name(), new MimicEntity(new Vec2d(0, 0), null).getTrueIcon(), new MimicEntity(new Vec2d(0, 0), null).description()),
        GOLEM(15, new GolemEntity(new Vec2d(0, 0), null)),
        ATTACKER(16, new RoleAttackerEntity(new Vec2d(0, 0), null)),
        DEBUFFER(17, new RoleDebufferEntity(new Vec2d(0, 0), null)),
        TANKER(18, new RoleTankerEntity(new Vec2d(0, 0), null));

        private int pointer;
        private String name;
        private String icon;
        private String description;

        IconHelpSceneResult(int pointer, Entity entity){
            this(pointer, entity.name(), entity.getIcon(), entity.description());
        }

        IconHelpSceneResult(int pointer, String name, String icon, String description){
            this.pointer = pointer;
            this.name = name;
            this.icon = icon;
            this.description = description;
        }

        public static IconHelpScene.IconHelpSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
