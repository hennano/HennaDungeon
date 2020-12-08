package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.dungeon.floor.ExitRoom;
import net.hennabatch.hennadungeon.effect.BleedingEffect;
import net.hennabatch.hennadungeon.entity.character.*;
import net.hennabatch.hennadungeon.entity.object.ExitEntity;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.mission.boss.PartyTag;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillTutorialBossPhase extends Phase {
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntitiesByTag(new TutorialBossTag()).size() == 0;
    }

    @Override
    public void execute() {
        getDungeon().removeIfEntity(x -> x.getTags().stream().anyMatch(y -> y.equals(new TutorialWallTag())));
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
        //出口にボスの設置
        ExitRoom exitRoom = (ExitRoom) getDungeon().getFloors().stream().filter(x -> x instanceof ExitRoom).findFirst().get();
        getDungeon().spawnEntity(new ExitEntity(exitRoom.getUpperLeft().add(exitRoom.size().div(2)), getDungeon()));
        RoleAttackerEntity attackerEntity = new RoleAttackerEntity(exitRoom.getUpperLeft().add(exitRoom.size().div(2).add(new Vec2d(1, 0))), getDungeon());
        attackerEntity.addTag(new PartyTag());
        RoleDebufferEntity debufferEntity = new RoleDebufferEntity(exitRoom.getUpperLeft().add(exitRoom.size().div(2).add(new Vec2d(1, 1))), getDungeon());
        debufferEntity.addTag(new PartyTag());
        RoleTankerEntity tankerEntity = new RoleTankerEntity(exitRoom.getUpperLeft().add(exitRoom.size().div(2).add(new Vec2d(0, -1))), getDungeon());
        tankerEntity.addTag(new PartyTag());
        getDungeon().spawnEntity(attackerEntity);
        getDungeon().spawnEntity(debufferEntity);
        getDungeon().spawnEntity(tankerEntity);
        getDungeon().executeScene(new MessageScene(messages));
    }
}
