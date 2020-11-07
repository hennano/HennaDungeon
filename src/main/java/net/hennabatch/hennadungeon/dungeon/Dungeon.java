package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.EnumDirection;
import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Dungeon {

    private List<Floor> floors = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private HashSet<Vec2d> dungeonMap = new HashSet<>();
    private GameScene scene;
    private EnumDifficulty difficulty;

    Dungeon(DungeonBuilder builder){

    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void executeEvent(Event event){
        scene.executeEvent(event);
    }

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }
}
