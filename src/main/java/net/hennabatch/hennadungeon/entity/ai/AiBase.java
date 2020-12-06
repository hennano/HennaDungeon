package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;

public abstract class AiBase <T extends Entity>{

    protected T owner;

    public AiBase(T owner){
        this.owner = owner;
    }

    protected abstract boolean shouldExecute();
    protected boolean shouldContinueExecute(){
        return shouldExecute();
    }

    public void startExecuting(){}

    public void resetTask(){}

    public abstract void updateTask();



}
