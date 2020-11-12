package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import javax.management.InvalidAttributeValueException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Dungeon {

    private final List<Floor> floors;
    private final List<Entity> entities;
    private final HashSet<Vec2d> dungeonMap = new HashSet<>();
    private final GameScene scene;
    private final EnumDifficulty difficulty;

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

    public void executeEvent(Event event){
        scene.executeEvent(event);
    }

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean isInner(IVec vec){
        return getFloors().stream().anyMatch(x -> x.isInner(vec));
    }

    public List<Entity> getEntityByIVec(IVec vec){
        Vec2d vec2d = Vec2d.byIVec(vec);
        return getEntities().stream().filter(vec2d::equals).collect(Collectors.toList());
    }
}
