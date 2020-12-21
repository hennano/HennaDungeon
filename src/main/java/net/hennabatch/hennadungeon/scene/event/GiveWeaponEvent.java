package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyLeaderEntity;
import net.hennabatch.hennadungeon.entity.character.HelpedPartyMemberEntity;
import net.hennabatch.hennadungeon.entity.object.DropItemEntity;
import net.hennabatch.hennadungeon.item.Item;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.scene.MessageScene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.YNMessageScene;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.*;

public class GiveWeaponEvent extends Event{

    private Dungeon dungeon;

    public GiveWeaponEvent(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    protected void initializeScene() {
        String leader = new HelpedPartyLeaderEntity(new Vec2d(0, 0), null).name();
        createChildScene(new MessageScene(new ArrayList<>(Collections.singletonList(leader + ":\n武器を貸してくれないか?"))));
    }

    @Override
    protected SceneResult run(EnumKeyInput key, SceneResult childSceneResult) {
        if(getEventSequence() == 1) {
            createChildScene(new YNMessageScene("現在装備している武器を渡しますか？"));
            return new SceneResult(true, null);
        }
        if(getEventSequence() == 2){
            if(childSceneResult.data() instanceof Boolean){
                if((Boolean)childSceneResult.data()){
                    if(dungeon.getPlayer().getEquipmentWeapon() != Items.HAND){
                        //武器を渡す
                        dungeon.getPlayer().removeInventoryItem(dungeon.getPlayer().getEquipmentWeaponIndex());
                        dungeon.getPlayer().setEquipmentWeapon(-1);
                        dungeon.getEntities().stream().filter(x -> x instanceof HelpedPartyLeaderEntity)
                                .map(x -> (HelpedPartyLeaderEntity)x)
                                .forEach(x -> x.setHasWeapon(true));
                        String leader = new HelpedPartyLeaderEntity(new Vec2d(0, 0), null).name();

                        List<String> messages = new ArrayList<>(Arrays.asList(
                                leader + ":\nありがとう！これで安全に脱出できるよ",
                                leader + ":\nお礼と言ってはなんだが手持ちのポーションを渡すよ",
                                leader + ":\nうまく役立ててほしい"
                        ));
                        dungeon.executeScene(new MessageScene(messages));
                        //報酬のドロップ
                        dungeon.spawnEntity(new DropItemEntity(new Vec2d(dungeon.getEntities().stream().filter(x -> x instanceof HelpedPartyLeaderEntity).findFirst().get()), dungeon, giveRandomPotion()));
                        //パーティの削除
                        dungeon.getEntities().removeIf(x -> x instanceof HelpedPartyMemberEntity);
                    }else{
                        createChildScene(new MessageScene(new ArrayList<>(Arrays.asList("武器を装備していないので渡せない"))));
                        return new SceneResult(true, null);
                    }
                }
            }
        }
        return new SceneResult(false, null);
    }

    private Item giveRandomPotion(){
        switch (new Random().nextInt(3)){
            case 0:
                return Items.MULTIPLE_ATK_BUFF_POTION;
            case 1:
                return Items.MULTIPLE_DEF_BUFF_POTION;
            case 2:
                return Items.MULTIPLE_MDEF_BUFF_POTION;
        }
        return Items.HEAL_POTION;
    }
}
