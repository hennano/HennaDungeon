package net.hennabatch.hennadungeon.mission.help;

import net.hennabatch.hennadungeon.entity.character.HelpedPartyLeaderEntity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyMemberEntity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillEnemyPhase extends Phase {
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntitiesByTag(new EnemyTag()).size() == 0;
    }

    @Override
    public void execute() {
        String leader = new HelpedPartyLeaderEntity(new Vec2d(0,0), null).name();
        String member = new HelpedPartyMemberEntity(new Vec2d(0, 0), null).name();
        String player = new PlayerEntity(new Vec2d(0,0),null).name();

        List<String> messages = new ArrayList<>(Arrays.asList(
                leader + ":\n危ないところだった。助かる",
                member + ":\nありがとうございます",
                player + ":\nいえいえ",
                leader + ":\nこんなことを頼むのもなんなんだが",
                leader + ":\n何でもいいので武器を一つ貸しくれないか？",
                leader + ":\nさっきの襲撃で得物が使えなくなってしまったんだ",
                leader + ":\n貸せる武器があったら話しかけてくれ"
        ));
    }
}
