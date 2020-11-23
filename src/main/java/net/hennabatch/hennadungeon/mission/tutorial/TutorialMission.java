package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.Phase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TutorialMission extends Mission {

    private List<Phase> phases = new ArrayList<>(Arrays.asList(new LeavePartyPhase(),
            new UseSkillPhase(),
            new DestroyTutorialBossPhase()));

    @Override
    protected List<Phase> getPhases() {
        return phases;
    }
}
