package net.hennabatch.hennadungeon.mission.boss;

import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.Phase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BossMission extends Mission {

    private List<Phase> phases = new ArrayList<>(Arrays.asList(new FindPlayerPhase(), new KillPartyPhase()));

    @Override
    protected List<Phase> getPhases() {
        return phases;
    }
}
