package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.HashSet;
import java.util.List;

public class Dungeon {

    private List<Floor> floors;
    private List<Entity> entities;
    private HashSet<Vec2d> dungeonMap = new HashSet<>();
    private GameScene scene;
    private EnumDifficulty difficulty;

    Dungeon(GameScene scene, IVec size, List<Floor> floors, List<Entity> entities, EnumDifficulty difficulty){
        this.floors = floors;
        this.entities = entities;
        this.scene = scene;
        this.difficulty = difficulty;
        generateDungeonMap(size);
    }

    private void generateDungeonMap(IVec vec){
        for(int x = 0; x < vec.getX(); x++){
            for(int y = 0; y < vec.getY(); y++){
                int finalX = x;
                int finalY = y;
                if(floors.stream().anyMatch(i -> i.isInner(new Vec2d(finalX, finalY)))){
                    dungeonMap.add(new Vec2d(finalX, finalY));
                }
            }
        }
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public boolean tryAddEntity(Entity entity){
        if(!dungeonMap.stream().anyMatch(x -> x.equals(entity))) return false;
        addEntity(entity);
        return true;
    }

    public void executeEvent(Event event){
        scene.executeEvent(event);
    }

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }
}
