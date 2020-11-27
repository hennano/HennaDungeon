package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.Floor;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.entity.character.PlayerEntity;
import net.hennabatch.hennadungeon.mission.Mission;
import net.hennabatch.hennadungeon.mission.Tag;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.util.Reference;
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
    private final List<Mission> missions = new ArrayList<>();

    Dungeon(GameScene scene, List<Floor> floors, EnumDifficulty difficulty){
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

    public void executeScene(Scene scene){
        this.scene.executeScene(scene);
    }

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean isInner(IVec vec){
        return getFloors().stream().anyMatch(x -> x.isInner(vec));
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public List<Entity> getEntitiesByIVec(IVec vec){
        Vec2d vec2d = new Vec2d(vec);
        return getEntities().stream().filter(vec2d::equals).collect(Collectors.toList());
    }

    public List<Entity> getEntitiesByTag(Tag tag){
        return getEntities().stream().filter(x -> x.getTags().stream().anyMatch(tag::equals)).collect(Collectors.toList());
    }

    public void spawnEntity(Entity entity){
        this.entities.add(entity);
        entity.initilaize();
        Reference.logger.debug(entity.name() + " spawn at" + new Vec2d(entity).toString());
    }

    public PlayerEntity getPlayer(){
        return this.entities.stream().filter(x -> x instanceof PlayerEntity)
                .map(x -> (PlayerEntity)x)
                .findFirst().get();
    }

    public void addMission(Mission mission){
        missions.add(mission);
        mission.initialize(this);
    }

    public List<Floor> getInnerFloors(IVec vec){
        return getFloors().stream().filter(x -> x.isInner(vec)).collect(Collectors.toList());
    }
}
