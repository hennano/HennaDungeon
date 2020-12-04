package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.dungeon.floor.ConnectFloor;
import net.hennabatch.hennadungeon.dungeon.floor.Passage;
import net.hennabatch.hennadungeon.dungeon.floor.Room;
import net.hennabatch.hennadungeon.dungeon.floor.StartRoom;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.*;
import net.hennabatch.hennadungeon.entity.object.WallEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LeavePartyPhase extends Phase {

    @Override
    public boolean shouldExecute() {
        return true;
    }

    @Override
    public void execute() {
        //チュートリアルボス生成
        Entity golem = new GolemEntity(new Vec2d(getDungeon().getPlayer()).sub(new Vec2d(0, 2)), getDungeon());
        golem.addTag(new TutorialBossTag());
        getDungeon().spawnEntity(golem);
        //出られないように壁の生成
        Room startRoom = getDungeon().getFloors().stream().filter(x -> x instanceof StartRoom).map(x -> (Room) x).findFirst().get();
        List<Vec2d> connectionPoint = new ArrayList<>();
        for(ConnectFloor connect : startRoom.getConnectFloors()){
            if(!(connect.getFloor() instanceof Passage)) continue;

            if(startRoom.isInner(((Passage) connect.getFloor()).getUpperLeft())){
                connectionPoint.add(new Vec2d(((Passage) connect.getFloor()).getUpperLeft()).add(connect.getDirection().vec()));
            }
            if(startRoom.isInner(((Passage) connect.getFloor()).getLowerRight())){
                connectionPoint.add(new Vec2d(((Passage) connect.getFloor()).getLowerRight()).add(connect.getDirection().vec()));
            }
        }
        connectionPoint.forEach(x -> {
            WallEntity wallEntity = new WallEntity(x, getDungeon());
            wallEntity.addTag(new TutorialWallTag());
            getDungeon().spawnEntity(wallEntity);
        });
        String tanker = new RoleTankerEntity(new Vec2d(0, 0), null).name();
        String attacker = new RoleAttackerEntity(new Vec2d(0, 0), null).name();
        String debuffer = new RoleDebufferEntity(new Vec2d(0, 0), null).name();
        String carrier = new PlayerEntity(new Vec2d(0, 0), null).name();

        List<String> messsage = new ArrayList<>(Arrays.asList(tanker + ":\nおい…うそだろ…？",
                attacker + ":\nなんでこんなところにゴーレムがいるの！？！？",
                tanker + ":\n俺達じゃ相手できない！逃げるぞ",
                debuffer + ":\n…通路塞がれてて逃げ道がない\nどうする？",
                tanker + ":\nしょうがねぇ、高価な出費だが背に腹は変えられんし部屋脱出アイテム使うぞ！",
                carrier + ":\n自分の分がないんだが",
                tanker + ":\nただの荷物持ちのくせにそんな高価なもの渡せるか！",
                tanker + ":\n俺たちが逃げ切るまでそこで時間稼いどけ！",
                attacker + ":\nそうよ！あなたの代わりなんていくらでもいるんだから！",
                debuffer + ":\n…",
                carrier + ":\nああそうかい！お前ら帰ったらただじゃ置かねえからな！！"));
        getDungeon().executeScene(new MessageScene(messsage));
    }
}
