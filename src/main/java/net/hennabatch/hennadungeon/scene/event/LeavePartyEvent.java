package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.entity.character.RoleAttackerEntity;
import net.hennabatch.hennadungeon.entity.character.RoleDebufferEntity;
import net.hennabatch.hennadungeon.entity.character.RoleTankerEntity;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeavePartyEvent extends Event{

    private static String tanker = new RoleTankerEntity(new Vec2d(0,0), null).name();
    private static String attacker = new RoleAttackerEntity(new Vec2d(0, 0), null).name();
    private static String debuffer = new RoleDebufferEntity(new Vec2d(0, 0), null).name();
    private static String  carrier = new PlayerEntity(new Vec2d(0, 0), null).name();

    private static List<String> leaveMesssage1 = new ArrayList<>(Arrays.asList(tanker + ":\nおい…うそだろ…？",
            attacker +":\nなんでこんなろころにゴーレムがいるの！？！？",
            tanker + ":\n俺達じゃ相手できない！逃げるぞ",
            debuffer +":\n…通路塞がれてて逃げ道がない\nどうする？",
            tanker + ":\nしょうがねぇ、高価な出費だが背に腹は変えられんし部屋脱出アイテム使うぞ！",
            carrier + ":\n自分の分がないんだが",
            tanker + ":\nただの荷物持ちのくせにそんな高価なもの渡せるか！",
            tanker + ":\n俺たちが逃げ切るまでそこで時間稼いどけ！",
            attacker + ":\nそうよ！あなたの代わりなんていくらでもいるんだから！",
            debuffer + ":\n…",
            carrier + ":\nああそうかい！お前ら帰ったらただじゃ置かねえからな！！"));


    @Override
    protected void initializeScene() {
        createChildScene(new MessageScene(leaveMesssage1));
    }

    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        return new SceneResult<>(false, null);
    }
}
