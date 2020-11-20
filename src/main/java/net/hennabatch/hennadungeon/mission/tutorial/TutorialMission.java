package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.dungeon.floor.Room;
import net.hennabatch.hennadungeon.dungeon.floor.StartRoom;
import net.hennabatch.hennadungeon.entity.character.GolemEntity;
import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.Phase;

import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TutorialMission extends Mission {
    @Override
    protected List<Phase> getPhases() {
        return new ArrayList<>(Arrays.asList(new TutorialBossDeadPhase()));
    }

    private class TutorialBossDeadPhase extends Phase{

        @Override
        public void initialize(Dungeon dungeon) {
            super.initialize(dungeon);
            //チュートリアルボス生成
            dungeon.spawnEntity(new GolemEntity(new Vec2d(getDungeon().getPlayer()).sub(new Vec2d(0, 2)), dungeon));
            //出られないように壁の生成 作業中
            Room startRoom = getDungeon().getFloors().stream().filter(x -> x instanceof StartRoom).map(x -> (Room)x).findFirst().get();
            //会話イベントなど
        }

        @Override
        public boolean doNext() {
            return getDungeon().getEntitiesByTag(new TutorialBossTag()).size() == 0;
        }

        @Override
        public void finalizePhase() {
            //壁の削除
            getDungeon().getEntities().removeIf(x -> x.getTags().stream().anyMatch(y -> y.equals(new TutorialWallTag())));
            //会話イベントなどの実行
        }
    }
}
