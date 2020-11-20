package net.hennabatch.hennadungeon.mission;

import net.hennabatch.hennadungeon.entity.ai.Task;

public class Tag {


    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass();
    }
}
