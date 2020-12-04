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

    public Phase getCurrentPhase(){
        try{
            return getPhases().get(currentPhase);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }


    public void update(){
        if(isComplete()) return;

        if(getCurrentPhase().shouldExecute()) {
            Reference.logger.debug(this.getClass().getSimpleName() +" is phase" + currentPhase);
            getCurrentPhase().execute();
            currentPhase++;
            if(!isComplete()) getCurrentPhase().initialize(dungeon);
            if(isComplete()) Reference.logger.debug(this.getClass().getSimpleName() + " is completed");
        }
    }

    public boolean isComplete(){
        return getCurrentPhase() == null;
    }
}
