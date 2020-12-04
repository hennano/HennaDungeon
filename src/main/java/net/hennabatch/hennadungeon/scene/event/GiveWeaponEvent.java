package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyLeaderEntity;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.YNMessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;

public class GiveWeaponEvent extends Event{

    private Dungeon dungeon;

    public GiveWeaponEvent(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected void initializeScene() {
        String leader = new HelpedPartyLeaderEntity(new Vec2d(0, 0), null).name();
        createChildScene(new MessageScene(new ArrayList<>(Arrays.asList(leader + ":\n武器を貸してくれないか?"))));
    }

    @Override
    protected SceneResult<?> run(EnumKeyInput key, SceneResult<?> childSceneResult) {
        if(getEventSequence() == 1) {
            createChildScene(new YNMessageScene("現在装備している武器を渡しますか？"));
            return new SceneResult<>(true, null);
        }
        if(getEventSequence() == 2){
            if(childSceneResult.data() instanceof Boolean){
                if((Boolean)childSceneResult.data()){
                    //武器を渡す
                    dungeon.getPlayer().getInventory().getItems().remove(dungeon.getPlayer().getEquipmentWeaponIndex());
                    dungeon.getPlayer().setEquipmentWeapon(-1);
                    dungeon.getEntities().stream().filter(x -> x instanceof HelpedPartyLeaderEntity)
                            .map(x -> (HelpedPartyLeaderEntity)x)
                            .forEach(x -> x.setHasWeapon(true));
                }
            }
        }
        return new SceneResult<>(false, null);
    }
}
