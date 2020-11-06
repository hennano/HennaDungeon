package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.IVec;

public class Room extends Floor{

    IVec upperLeft;
    IVec size;
    EnumDifficulty difficulty;

    public Room(IVec upperLeft, IVec size, EnumDifficulty difficulty){
        this.upperLeft = upperLeft;
        this.size = size;
        this.difficulty = difficulty;
    }


    @Override
    public Boolean isInner(IVec vec) {
        return false;
    }
}
