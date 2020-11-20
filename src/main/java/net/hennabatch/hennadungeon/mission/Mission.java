package net.hennabatch.hennadungeon.mission;

public abstract class Mission {

    private int phase = 0;

    protected int getPhase(){
        return this.phase;
    }

    protected void setPhase(int phase){
        this.phase = phase;
    }

    protected void next(){
        this.phase++;
    }

    public abstract boolean isComplete();

    public abstract void onFlagUpdated(Flag flag);


}
