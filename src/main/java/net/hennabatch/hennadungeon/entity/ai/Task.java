package net.hennabatch.hennadungeon.entity.ai;

import net.hennabatch.hennadungeon.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task {

    private final List<AiTask> tasks = new ArrayList<>();
    private AiTask runningTask;

    public List<AiTask> getTasks(){
        return tasks;
    }

    public void addTask(int priority, AiBase<? extends Entity> ai){
        this.tasks.add(new AiTask(priority, ai));
    }

    public void removeTask(AiBase<? extends Entity> ai){
        this.tasks.removeIf( x -> x.getAi().equals(ai));
    }

    public void run(){
        final Boolean[] alreadyExecuted = {false};
        tasks.stream().sorted(Comparator.comparing(AiTask::getPriority).reversed()).forEach( x -> {
            if(x.getAi().shouldExecute() && !alreadyExecuted[0]){
                alreadyExecuted[0] = true;
                if(runningTask != null) runningTask.getAi().resetTask();
                runningTask = x;
                runningTask.getAi().startExecuteing();
                runningTask.getAi().updateTask();
            }else if(x.equals(runningTask) && x.getAi().shouldContinueExecute()){
                runningTask.getAi().updateTask();
            }
        });
    }


    static class AiTask{
        private final int priority;
        private final AiBase ai;

        AiTask(int priority, AiBase ai){
            this.priority = priority;
            this.ai = ai;
        }

        public int getPriority() {
            return priority;
        }

        public AiBase getAi() {
            return ai;
        }
    }
}
