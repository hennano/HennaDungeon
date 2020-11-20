package net.hennabatch.hennadungeon.mission;

import net.hennabatch.hennadungeon.dungeon.Dungeon;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.List;

public abstract class Mission {

    private Dungeon dungeon;

    private int currentPhase = 0;

    public void initialize(Dungeon dungeon){
        Reference.logger.debug(this.getClass().getSimpleName() + "is initialize");
        this.dungeon = dungeon;
        getCurrentPhase().initialize(dungeon);
    }

    protected Dungeon getDungeon(){
        return dungeon;
    }

    protected abstract List<Phase> getPhases();

    protected Phase getCurrentPhase(){
        try{
            return getPhases().get(currentPhase);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void update(){
        if(getCurrentPhase().doNext()){
            getCurrentPhase().finalizePhase();
            currentPhase++;
            getCurrentPhase().initialize(dungeon);
        }
    }

    public boolean isComplete(){
        return getCurrentPhase() != null;
    }
}
