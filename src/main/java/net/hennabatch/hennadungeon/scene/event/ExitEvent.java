package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.boss.BossMission;
import net.hennabatch.hennadungeon.mission.help.HelpOtherPartyMission;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.SceneResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExitEvent extends Event{

    private List<Mission> missions;

    public ExitEvent(List<Mission> missions){
        this.missions = missions;
    }

    @Override
    protected void initializeScene() {
        createChildScene(new MessageScene(new ArrayList<>(Collections.singletonList("ダンジョンから脱出した!"))));
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        if(missions.stream().filter(x -> x instanceof BossMission).anyMatch(Mission::isComplete)) return new SceneResult(false, RootEvent.SceneTransition.PartyKillEndingScene);
        if(missions.stream().filter(x -> x instanceof HelpOtherPartyMission).anyMatch(Mission::isComplete)) return new SceneResult(false, RootEvent.SceneTransition.HelpOtherPartyEndingScene);
        return new SceneResult(false, RootEvent.SceneTransition.SociallyDeadEndingScene);
    }


}
