package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.config.EnumKeyInput;

import javax.swing.tree.ExpandVetoException;

abstract class EventScene<T> extends Scene<T>{

    private int eventSequence = 0;

    @Override
    public SceneResult inputKey(EnumKeyInput key) {
        SceneResult result = new SceneResult(false, null);
        if(getChildScene() != null){
            result = getChildScene().inputKey(key);
            if(result.isChildSceneContinue()) {
                removeChildScene();
                eventSequence++;
            }
        }
        if(!result.isChildSceneContinue()){
            result = run(key, result);
        }
        return result;
    }

    protected int getEventSequence(){
        return eventSequence;
    }

}
