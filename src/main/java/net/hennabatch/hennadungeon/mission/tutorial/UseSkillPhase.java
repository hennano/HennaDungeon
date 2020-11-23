package net.hennabatch.hennadungeon.mission.tutorial;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UseSkillPhase extends Phase {

    @Override
    public boolean shouldExecute() {
        return getDungeon().getPlayer().getCurrentHP() / (double) getDungeon().getPlayer().getMaxHP() <= 0.5;
    }

    @Override
    public void execute() {
        //スキルを使うように催促
        String player = new PlayerEntity(new Vec2d(0, 0), null).name();
        List<String> messages = new ArrayList<>(Arrays.asList(player + ":\nいってえし硬いなぁ",
                player + ":\nさすがの自分もここまでかもしれんな",
                "…お前は戦闘で使えないから荷物持ちな！…",
                "…よっわ\n今まで何してたのよ？…",
                player + ":\nここで死んでたまるかぁぁ！",
                player + ":\n絶対見返してやるわ！！",
                Reference.config.keyConfig().getChar(EnumKeyInput.SKILL) + "でスキルを使用する"));
        getDungeon().executeScene(new MessageScene(messages));
    }
}
