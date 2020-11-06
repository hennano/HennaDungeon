package net.hennabatch.hennadungeon.dungeon.floor;

import net.hennabatch.hennadungeon.vec.IVec;

public class Passage extends Floor{
    @Override
    public Boolean isInner(IVec vec) {
        return false;
    }
}
