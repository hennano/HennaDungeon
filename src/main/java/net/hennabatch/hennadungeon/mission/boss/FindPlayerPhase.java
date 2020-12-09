package net.hennabatch.hennadungeon.mission.boss;

import net.hennabatch.hennadungeon.dungeon.floor.ExitRoom;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.entity.character.RoleAttackerEntity;
import net.hennabatch.hennadungeon.entity.character.RoleDebufferEntity;
import net.hennabatch.hennadungeon.entity.character.RoleTankerEntity;
import net.hennabatch.hennadungeon.entity.object.ExitEntity;
import net.hennabatch.hennadungeon.mission.Phase;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindPlayerPhase extends Phase {

    @Override
    public boolean shouldExecute() {
        if(getDungeon().getPlayer().isHidden()) return false;
        return getDungeon().getInnerFloors(getDungeon().getPlayer()).stream().anyMatch(x -> x instanceof ExitRoom);
    }

    @Override
    public void execute() {
        String tanker = new RoleTankerEntity(new Vec2d(0, 0), null).name();
        String attacker = new RoleAttackerEntity(new Vec2d(0, 0), null).name();
        String debuffer = new RoleDebufferEntity(new Vec2d(0, 0), null).name();
        String carrier = new PlayerEntity(new Vec2d(0, 0), null).name();

        List<String> messsage = new ArrayList<>(Arrays.asList(
                tanker + ":\nよし、脱出まですぐだ",
                attacker + ":\n散々だったわ",
                debuffer + ":\n…脱出したら" + carrier + "の捜索願い出さないと",
                tanker + ":\nばっかお前、それしたらバレるだろーが",
                attacker + ":\nそうよ！しかもわざわざあいつのためにする必要なんてないわ",
                debuffer + ":\n…",
                debuffer + ":\n…あ、あそこ…",
                tanker + ":\nん？",
                attacker + ":\nなんで" + carrier + "がここにいるのよ！？",
                carrier + ":\nまた会ったな",
                tanker + ":\nゴーレムはどうしたんだよ",
                carrier + ":\nクソ雑魚なめくじでしたわ",
                tanker + ":\n…このままギルドに報告されるとまずいな",
                tanker + ":\nよし、やるぞ!",
                attacker + ":\nゴーレムにやられていればよかったのよ！",
                debuffer + ":\n…"
        ));
        //出口の削除
        getDungeon().removeIfEntity(x -> x instanceof ExitEntity);
        getDungeon().executeScene(new MessageScene(messsage));
    }
}
