package net.hennabatch.hennadungeon.mission;

import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.entity.Entity;

import java.util.List;

public abstract class Flag {

    protected abstract Mission getOwnerMission();

    protected void updateFlag(){
        getOwnerMission().onFlagUpdated(this);
    }

    public void onDestroy(){}

    public void onTrigger(Entity target){}

    public void update(){}

    public void onCollision(Entity collidedEntity){}

    public void onAttacked(Entity attackedEntity, int atk, boolean isMagic, boolean isMelee, List<Effect> additionalEffects){};
}
