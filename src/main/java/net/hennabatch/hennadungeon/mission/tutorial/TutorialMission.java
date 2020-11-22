package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.dungeon.floor.Passage;
import net.hennabatch.hennadungeon.dungeon.floor.Room;
import net.hennabatch.hennadungeon.dungeon.floor.StartRoom;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.GoblinEntity;
import net.hennabatch.hennadungeon.entity.object.WallEntity;
import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.event.LeavePartyEvent;
import net.hennabatch.hennadungeon.scene.event.TutorialSkillEvent;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TutorialMission extends Mission {

    private List<Phase> phases = new ArrayList<>(Arrays.asList(new AttackTutorialBossPhase(),
            new DestroyTutorialBossPhase()));

    @Override
    protected List<Phase> getPhases() {
        return phases;
    }

    private class AttackTutorialBossPhase extends Phase{

        @Override
        public boolean shouldExecute() {
            return true;
        }

        @Override
        public void execute() {
            //チュートリアルボス生成
            Entity golem = new GoblinEntity(new Vec2d(getDungeon().getPlayer()).sub(new Vec2d(0, 2)), getDungeon());
            golem.addTag(new TutorialBossTag());
            getDungeon().spawnEntity(golem);
            //出られないように壁の生成 作業中
            Room startRoom = getDungeon().getFloors().stream().filter(x -> x instanceof StartRoom).map(x -> (Room)x).findFirst().get();
            List<Vec2d> connectionPoint = startRoom.getConnectFloors().stream()
                    .filter(x -> x.getFloor() instanceof Passage)
                    .flatMap(x -> Stream.of(((Passage)x.getFloor()).getUpperLeft(), ((Passage)x.getFloor()).getLowerRight()))
                    .filter(startRoom::isInner)
                    .collect(Collectors.toList());
            connectionPoint.forEach(x ->{
                WallEntity wallEntity = new WallEntity(x, getDungeon());
                wallEntity.addTag(new TutorialWallTag());
                getDungeon().spawnEntity(wallEntity);
            });
            getDungeon().executeEvent(new LeavePartyEvent());
        }
    }

    private class DestroyTutorialBossPhase extends Phase{

        @Override
        public boolean shouldExecute() {
            return getDungeon().getPlayer().getCurrentHP() / (double)getDungeon().getPlayer().getMaxHP() <= 0.5;
        }

        @Override
        public void execute() {
            //スキルを使うように催促
            getDungeon().executeEvent(new TutorialSkillEvent());
        }
    }
}
