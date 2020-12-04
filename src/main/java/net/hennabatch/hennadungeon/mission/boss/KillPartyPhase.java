package net.hennabatch.hennadungeon.mission.boss;

import net.hennabatch.hennadungeon.dungeon.floor.ExitRoom;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.entity.object.ExitEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillPartyPhase extends Phase {
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntitiesByTag(new PartyTag()).size() == 0;
    }

    @Override
    public void execute() {
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();

        List<String> messsage = new ArrayList<>(Arrays.asList(
                player + ":\nはぁ…はぁ…",
                player + ":\nあいつらは最後の最後まで迷惑をかけ続けるなぁ",
                player + ":\n事情が事情だけども人殺ししちゃったなぁ",
                player + ":\nスッキリはしたけども",
                player + ":\n…とりあえずギルドに戻るか"
        ));
        //出口の再生成
        ExitRoom exitRoom = (ExitRoom) getDungeon().getFloors().stream().filter(x -> x instanceof ExitRoom).findFirst().get();
        getDungeon().spawnEntity(new ExitEntity(exitRoom.getUpperLeft().add(exitRoom.size().div(2)), getDungeon()));
        getDungeon().executeScene(new MessageScene(messsage));
    }
}
