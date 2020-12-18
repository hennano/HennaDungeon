package net.hennabatch.hennadungeon.scene.menu.help;

import net.hennabatch.hennadungeon.effect.*;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.scene.menu.TwoColumnMenuScene;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EffectHelpScene extends TwoColumnMenuScene {
    @Override
    protected String getTitle() {
        return "エフェクト";
    }

    @Override
    protected List<String> getOptions() {
        List<String> options = Arrays.stream(EffectHelpScene.EffectMenuSceneResult.values()).map(x -> x.effect.name()).collect(Collectors.toList());
        options.add("戻る");
        return options;
    }

    @Override
    protected int getDivPos(Screen screen) {
        return 7;
    }

    @Override
    protected SceneResult onSelected(int pointer) {
        if(pointer == getOptions().size() - 1) return new SceneResult(false, null);
        return new SceneResult(true, null);
    }

    @Override
    protected Screen drawRightContent(Screen screen, int pointer) {
        if(pointer != getOptions().size() - 1) {
            screen.setRow(0, 0, EffectMenuSceneResult.byPointer(pointer).effect.description(), true, false);
        }
        return screen;
    }

    public enum EffectMenuSceneResult{

        ATKBUFF(0, new BuffEffect(-1, Status.EnumStatus.ATK, 0, false)),
        DEFBUFF(1, new BuffEffect(-1, Status.EnumStatus.DEF, 0, false)),
        MDEFBUFF(2, new BuffEffect(-1, Status.EnumStatus.MDEF, 0, false)),
        EVABUFF(3, new BuffEffect(-1, Status.EnumStatus.EVA, 0, false)),
        ATKDEBUFF(4, new DeBuffEffect(-1, Status.EnumStatus.ATK, 0, false)),
        DEFDEBUFF(5, new DeBuffEffect(-1, Status.EnumStatus.DEF, 0, false)),
        MDEFDEBUFF(6, new DeBuffEffect(-1, Status.EnumStatus.MDEF, 0, false)),
        EVADEBUFF(7, new DeBuffEffect(-1, Status.EnumStatus.EVA, 0, false)),
        REGENERATION(8, new RegenerationEffect(-1, 0)),
        POISON(9, new PoisonEffect(-1, 0)),
        PARALYSIS(10, new ParalysisEffect(-1)),
        BLEEDING(11, new BleedingEffect(-1, 0)),
        INVISIBLE(12, new InvisibleEffect(-1));


        private int pointer;
        private Effect effect;

        EffectMenuSceneResult(int pointer, Effect effect){
            this.pointer = pointer;
            this.effect = effect;
        }

        public static EffectHelpScene.EffectMenuSceneResult byPointer(int pointer){
            return Arrays.stream(values()).filter(x -> x.pointer == pointer).findFirst().orElse(null);
        }
    }
}
