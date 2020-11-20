package net.hennabatch.hennadungeon.mission;

import net.hennabatch.hennadungeon.dungeon.Dungeon;

public abstract class Phase {

    private Dungeon dungeon;

    public void initialize(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    public abstract boolean doNext();

    public void finalizePhase(){
    }
}
