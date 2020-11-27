package net.hennabatch.hennadungeon.mission.boss;

import net.hennabatch.hennadungeon.mission.Phase;

public class KillPartyPhase extends Phase {
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntitiesByTag(new PartyTag()).size() == 0;
    }

    @Override
    public void execute() {
        //会話イベントの実行
    }
}
