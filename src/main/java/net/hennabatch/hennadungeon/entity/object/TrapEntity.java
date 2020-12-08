package net.hennabatch.hennadungeon.entity.object;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.effect.Effect;
import net.hennabatch.hennadungeon.effect.ParalysisEffect;
import net.hennabatch.hennadungeon.effect.PoisonEffect;
import net.hennabatch.hennadungeon.entity.BreakableEntity;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TrapEntity extends Entity {

    private Effect effect;

    public TrapEntity(Vec2d pos, Dungeon dungeon) {
        super(pos, dungeon);
        List<Effect> effects = new ArrayList<>(Arrays.asList(
           new PoisonEffect(5, dungeon.getDifficulty()),
           new ParalysisEffect(3)
        ));
        this.effect = effects.get(new Random().nextInt(effects.size()));
    }

    public TrapEntity(Vec2d pos, Dungeon dungeon, Effect effect) {
        super(pos, dungeon);
        this.effect = effect;
    }

    @Override
    public void initialize() {
        this.setHidden(true);
    }

    @Override
    public String getIcon() {
        return "Tr";
    }

    @Override
    public String name() {
        return "トラップ";
    }

    @Override
    protected void onTrigger(Entity triggeredEntity) {
        if(!(triggeredEntity instanceof BreakableEntity)) return;
        setHidden(false);
        ((BreakableEntity) triggeredEntity).getStatus().addEffect(effect.cloneEffect());
        Reference.logger.info(triggeredEntity.name() + "は" + effect.name() + "を付与された");
    }
}
