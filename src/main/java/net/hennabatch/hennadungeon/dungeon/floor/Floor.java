package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;

import java.util.ArrayList;
import java.util.List;

public abstract class Floor {

    private final List<ConnectFloor> connectFloors = new ArrayList<>();
    private ConnectFloor pathToExit;

    public abstract Boolean isInner(IVec vec);

    public ConnectFloor getPathToExit(){
        return this.pathToExit;
    }

    public void setPathToExit(ConnectFloor floor){
        this.pathToExit = floor;
    }

    public void addConnectFloor(ConnectFloor floor){
        if(connectFloors.stream().noneMatch(x -> x.getFloor().equals(floor.getFloor()))){
            this.connectFloors.add(floor);
            floor.getFloor().addConnectFloor(new ConnectFloor(this, floor.getDirection().switchOtherSide()));
        }
    }

    public void removeConnectFloor(Floor floor){
        if(connectFloors.removeIf(x -> x.getFloor().equals(floor))){
            floor.removeConnectFloor(this);
        }
    }

    public List<ConnectFloor> getConnectFloors() {
        return connectFloors;
    }

    public int exitRoomDistance(){
        Floor current = this;
        int cnt = 0;
        while (current.getClass() != ExitRoom.class){
            current = current.getPathToExit().getFloor();
            cnt++;
        }
        return cnt;
    }

}
