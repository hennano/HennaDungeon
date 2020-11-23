package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.effect.BleedingEffect;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DestroyTutorialBossPhase extends Phase {
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntitiesByTag(new TutorialBossTag()).size() == 0;
    }

    @Override
    public void execute() {
        getDungeon().getEntities().removeIf(x -> x.getTags().stream().anyMatch(y -> y.equals(new TutorialWallTag())));
        getDungeon().getPlayer().getStatus().addEffect(new BleedingEffect(-1, getDungeon().getDifficulty()));
        //その後の行動を示す
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();
        List<String> messages = new ArrayList<>(Arrays.asList(player + ":\nめっちゃ痛いけどなんとかなったな",
                player + ":\nあいつらを早く見つけてざまあしてやる"));
        getDungeon().executeScene(new MessageScene(messages));
    }
}
