package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.PlayerEntity;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.scene.event.Event;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dungeon {

    private final List<Floor> floors;
    private final List<Entity> entities = new ArrayList<>();
    private final GameScene scene;
    private final EnumDifficulty difficulty;

    Dungeon(GameScene scene, IVec size, List<Floor> floors, EnumDifficulty difficulty){
        this.floors = floors;
        this.scene = scene;
        this.difficulty = difficulty;
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
        Vec2d vec2d = new Vec2d(vec);
        return getEntities().stream().filter(vec2d::equals).collect(Collectors.toList());
    }

    public void spawnEntity(Entity entity){
        this.entities.add(entity);
    }

    public PlayerEntity getPlayer(){
        return this.entities.stream().filter(x -> x instanceof PlayerEntity)
                .map(x -> (PlayerEntity)x)
                .findFirst().get();
    }
}
