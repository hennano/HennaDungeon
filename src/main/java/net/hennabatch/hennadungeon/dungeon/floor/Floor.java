package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;

import java.util.ArrayList;
import java.util.List;

public abstract class Floor {

    private List<Floor> connectFloors = new ArrayList<>();
    private Floor pathToExit;
    private EnumDirection exitDirection;

    public abstract Boolean isInner(IVec vec);

    public Floor getPathToExit(){
        return this.pathToExit;
    }

    public void setPathToExit(Floor floor){
        this.pathToExit = floor;
    }

    public EnumDirection getExitDirection(){
        return this.exitDirection;
    }

    public void  setExitDirection(EnumDirection direction){
        this.exitDirection = exitDirection;
    }

    public void addConnectFloor(Floor floor){
        if(connectFloors.stream().anyMatch(x -> x.equals(floor))){
            this.connectFloors.add(floor);
            floor.addConnectFloor(this);
        }
    }

    public void removeConnectFloor(Floor floor){
        if(connectFloors.removeIf(x -> x.equals(floor))){
            floor.removeConnectFloor(this);
        }
    }

    public List<Floor> getConnectFloors() {
        return connectFloors;
    }
}
