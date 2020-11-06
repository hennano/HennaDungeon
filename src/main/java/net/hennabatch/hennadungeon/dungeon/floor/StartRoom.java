package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.vec.IVec;

public class StartRoom extends Room{
    public StartRoom(IVec upperLeft, IVec size, EnumDifficulty difficulty) {
        super(upperLeft, size, difficulty);
    }
}
