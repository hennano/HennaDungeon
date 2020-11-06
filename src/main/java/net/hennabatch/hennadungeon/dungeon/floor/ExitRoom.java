package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.IVec;

public class ExitRoom extends Room{
    public ExitRoom(IVec upperLeft, IVec size, EnumDifficulty difficulty) {
        super(upperLeft, size, difficulty);
    }
}
