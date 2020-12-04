package net.hennabatch.hennadungeon.mission.help;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.floor.OtherPartyRoom;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyLeaderEntity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyMemberEntity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindOtherPartyPhase extends Phase {

    private int limit = 150;

    @Override
    public void initialize(Dungeon dungeon) {
        super.initialize(dungeon);
        //救出対象パーティの生成
        //getDungeon().spawnEntity(new HelpedPartyMemberEntity());
    }

    @Override
    public boolean shouldExecute() {
        if(limit == 0) deathOtherParty();
        if(limit-- <= 0) return false;
        return getDungeon().getInnerFloors(getDungeon().getPlayer()).stream().anyMatch(x -> x instanceof OtherPartyRoom);
    }

    @Override
    public void execute() {
        String leader = new HelpedPartyLeaderEntity(new Vec2d(0,0), null).name();
        String player = new PlayerEntity(new Vec2d(0,0),null).name();

        List<String> messages = new ArrayList<>(Arrays.asList(
                leader + ":\nすまない、助けてくれ！",
                player + ":\nわかった!"
        ));
        getDungeon().executeScene(new MessageScene(messages));
        //敵の生成
    }

    private void deathOtherParty(){
        getDungeon().getEntities().stream().filter(x -> x instanceof HelpedPartyMemberEntity).forEach(Entity::destroy);
        getDungeon().executeScene(new MessageScene(new ArrayList<>(Arrays.asList("キャーーー!!!"))));
    }
}
