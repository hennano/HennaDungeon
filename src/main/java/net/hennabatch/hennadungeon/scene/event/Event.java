package net.hennabatch.hennadungeon.scene.event;

import net.hennabatch.hennadungeon.config.EnumKeyInput;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.scene.SceneResult;
import net.hennabatch.hennadungeon.scene.Screen;
import net.hennabatch.hennadungeon.util.Reference;

public abstract class Event<T> extends Scene<T> {

    private int eventSequence = 0;

    @Override
    public SceneResult inputKey(EnumKeyInput key) {
        SceneResult result = new SceneResult(false, null);
        if(getChildScene() != null){
            result = getChildScene().inputKey(key);
            if(!result.isChildSceneContinue()) {
                removeChildScene();
                eventSequence++;
            }
        }
        if(!result.isChildSceneContinue()){
            Reference.logger.debug("runScreen:" + this.getClass().getSimpleName());
            result = run(key, result);
        }
        return result;
    }

    @Override
    protected Screen draw(Screen screen) {
        return screen;
    }

    protected int getEventSequence(){
        return eventSequence;
    }

}
