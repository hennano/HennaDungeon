package net.hennabatch.hennadungeon.mission.help;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.floor.OtherPartyRoom;
import net.hennabatch.hennadungeon.entity.EnemyEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.GoblinEntity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyLeaderEntity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyMemberEntity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FindOtherPartyPhase extends Phase {

    private int limit = 150;

    @Override
    public void initialize(Dungeon dungeon) {
        super.initialize(dungeon);
        //救出対象パーティの生成
        OtherPartyRoom room = getDungeon().getFloors().stream().filter(x -> x instanceof OtherPartyRoom)
                .map(x -> (OtherPartyRoom)x).findFirst().get();
        EnumDirection direction = room.size().getX() < room.size().getY() ? EnumDirection.Y : EnumDirection.X;

        Vec2d center = room.getUpperLeft().add(room.size().div(2));
        getDungeon().spawnEntity(new HelpedPartyLeaderEntity(center, getDungeon()));
        getDungeon().spawnEntity(new HelpedPartyMemberEntity(center.add(new Vec2d(0, 1).rotate(direction)), getDungeon()));
        getDungeon().spawnEntity(new HelpedPartyMemberEntity(center.add(new Vec2d(0, -1).rotate(direction)), getDungeon()));
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
        OtherPartyRoom room = getDungeon().getFloors().stream().filter(x -> x instanceof OtherPartyRoom)
                .map(x -> (OtherPartyRoom)x).findFirst().get();
        EnumDirection direction = room.size().getX() < room.size().getY() ? EnumDirection.Y : EnumDirection.X;

        Vec2d center = room.getUpperLeft().add(room.size().div(2));
        EnemyEntity enemy = new GoblinEntity(center.add(new Vec2d(2, 0).rotate(direction)),getDungeon());
        enemy.addTag(new EnemyTag());
        getDungeon().spawnEntity(enemy);
    }

    private void deathOtherParty(){
        List<Entity> party = getDungeon().getEntities().stream().filter(x -> x instanceof HelpedPartyMemberEntity).collect(Collectors.toList());
        party.forEach(Entity::destroy);
        getDungeon().executeScene(new MessageScene(new ArrayList<>(Arrays.asList("キャーーー!!!"))));
    }
}
