package net.hennabatch.hennadungeon.entity.object;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.scene.event.ExitEvent;
import net.hennabatch.hennadungeon.vec.Vec2d;

public class ExitEntity extends Entity {
    public ExitEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
    }

    @Override
    public void initialize() {

    }

    @Override
    protected void onTrigger(Entity triggeredEntity) {
        if(triggeredEntity instanceof PlayerEntity) {
            getDungeon().executeScene(new ExitEvent(getDungeon().getMissions()));
        }
    }

    @Override
    public String getIcon() {
        return "＃";
    }

    @Override
    public String name() {
        return "出口";
    }

    @Override
    public String description() {
        return "ダンジョンの出口\n外から光が差している";
    }
}
