package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.effect.BleedingEffect;
import net.hennabatch.hennadungeon.entity.character.GolemEntity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.item.Items;
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
        String boss = new GolemEntity(new Vec2d(0, 0), null).name();
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();
        List<String> messages = new ArrayList<>(Arrays.asList(
                boss + ":\nグゴゴゴゴギゴゴ",
                player + ":\n出血がひどいけどなんとかなったな",
                player + ":\nバッグの中にあった" + Items.HEAL_POTION.name() + "で応急処置しておくか",
                player + ":\nしっかし、あいつら自分をおいてほんとに逃げやがった",
                player + ":\n早く見つけてざまあしてやらんと気がすまねぇ"));
        getDungeon().getPlayer().addHP(getDungeon().getPlayer().getMaxHP());
        getDungeon().executeScene(new MessageScene(messages));
    }
}
