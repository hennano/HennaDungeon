package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerDeadEvent extends Event{

    @Override
    protected void initializeScene() {
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();
        List<String> messages = new ArrayList<>(Arrays.asList(
                player +":\nやべぇ、もう体力が持たない…",
                player +":\nこれはだめなやつだ…"
        ));
        createChildScene(new MessageScene(messages));
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        if(getEventSequence() == 1) return new SceneResult(false, RootEvent.SceneTransition.GameOverScene);
        return new SceneResult(true, null);
    }
}
