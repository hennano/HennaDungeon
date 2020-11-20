package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.dungeon.floor.Room;
import net.hennabatch.hennadungeon.entity.DropItemEntity;
import net.hennabatch.hennadungeon.entity.GoblinEntity;
import net.hennabatch.hennadungeon.entity.PlayerEntity;
import net.hennabatch.hennadungeon.item.Items;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.Arrays;

public class MonoRoomDungeonBuilder extends DungeonBuilder {

    @Override
    public Dungeon build(GameScene scene) {
        Section section = new Section(new Vec2d(0, 0), new Vec2d(9, 9));
        section.generateRoom(Room.class, section.upperLeft.add(1), section.lowerRight.sub(1));
        Dungeon dungeon = new Dungeon(scene, new ArrayList<>(Arrays.asList(new Floor[]{section.room})), EnumDifficulty.NORMAL);
        dungeon.spawnEntity(new PlayerEntity(section.room.size().div(2).add(section.room.getUpperLeft()), dungeon));
        dungeon.spawnEntity(new DropItemEntity(section.room.size().div(2).add(section.room.getUpperLeft()).sub(1), dungeon, Items.HEAL_POTION));
        dungeon.spawnEntity(new DropItemEntity(section.room.size().div(2).add(section.room.getUpperLeft()).sub(-1), dungeon, Items.SWORD));
        dungeon.spawnEntity(new GoblinEntity(section.room.size().div(2).add(section.room.getUpperLeft()).sub(-2), dungeon));
        return dungeon;
    }
}
