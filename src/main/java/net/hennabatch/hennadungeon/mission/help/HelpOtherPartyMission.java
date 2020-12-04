package net.hennabatch.hennadungeon.mission.help;

import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.Phase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpOtherPartyMission extends Mission {

    private List<Phase> phases = new ArrayList<>(Arrays.asList(
            new FindOtherPartyPhase(),
            new KillEnemyPhase(),
            new GiveWeaponPhase()
    ));

    @Override
    protected List<Phase> getPhases() {
        return phases;
    }
}
