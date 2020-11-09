package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;

public abstract class AiBase <T extends Entity>{

    protected T entity;

    public AiBase(T entity){
        this.entity = entity;
    }

    protected abstract boolean shouldExecute();
    protected boolean shouldContinueExecute(){
        return shouldExecute();
    }

    public void startExecuteing(){}

    public void resetTask(){}

    public abstract void updateTask();



}
