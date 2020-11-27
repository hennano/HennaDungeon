package net.hennabatch.hennadungeon.mission.boss;

import net.hennabatch.hennadungeon.dungeon.floor.ExitRoom;
import net.hennabatch.hennadungeon.mission.Phase;

public class FindPlayerPhase extends Phase {

    @Override
    public boolean shouldExecute() {
        if(getDungeon().getPlayer().isHidden()) return false;
        return getDungeon().getInnerFloors(getDungeon().getPlayer()).stream().anyMatch(x -> x instanceof ExitRoom);
    }

    @Override
    public void execute() {
        //会話イベントの実行
    }
}
