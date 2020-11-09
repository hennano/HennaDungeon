package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.EnumDirection;

public class ConnectFloor {
    private Floor floor;
    private EnumDirection direction;

    public ConnectFloor(Floor floor, EnumDirection direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public Floor getFloor() {
        return floor;
    }

    public EnumDirection getDirection() {
        return direction;
    }
}
