package net.hennabatch.hennadungeon.entity.character;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.ITalkable;
import net.hennabatch.hennadungeon.mission.help.HelpOtherPartyMission;
import net.hennabatch.hennadungeon.mission.help.KillEnemyPhase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.event.GiveWeaponEvent;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpedPartyLeaderEntity extends HelpedPartyMemberEntity{

    private boolean hasWeapon = false;

    public HelpedPartyLeaderEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public String getIcon() {
        return "◆";
    }

    @Override
    public String name() {
        return "リーダー";
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        if(!(collidedEntity instanceof ITalkable)){
            super.onCollision(collidedEntity);
            return;
        }
        if((getDungeon().getMissions().stream().filter(x -> x instanceof HelpOtherPartyMission).findFirst().get().getCurrentPhase() instanceof KillEnemyPhase)){
            getDungeon().executeScene(new MessageScene(new ArrayList<>(Arrays.asList(name() + ":\nすまない、助けてくれ…"))));
            return;
        }
        getDungeon().executeScene(new GiveWeaponEvent(getDungeon()));
    }

    public boolean hasWeapon() {
        return hasWeapon;
    }

    public void setHasWeapon(boolean hasWeapon) {
        this.hasWeapon = hasWeapon;
    }
}
