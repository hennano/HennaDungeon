package net.hennabatch.hennadungeon.mission.help;

import net.hennabatch.hennadungeon.entity.character.HelpedPartyLeaderEntity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyMemberEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveWeaponPhase extends Phase{
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntities().stream().filter(x -> x instanceof HelpedPartyLeaderEntity)
                .map(x -> (HelpedPartyLeaderEntity)x)
                .anyMatch(x -> x.hasWeapon());
    }

    @Override
    public void execute() {
        String leader = new HelpedPartyLeaderEntity(new Vec2d(0, 0), null).name();

        List<String> messages = new ArrayList<>(Arrays.asList(
                leader + ":\nありがとう！これで安全に脱出できるよ"
        ));
        getDungeon().executeScene(new MessageScene(messages));
        //パーティの削除
        getDungeon().getEntities().removeIf(x -> x instanceof HelpedPartyMemberEntity);
    }
}
