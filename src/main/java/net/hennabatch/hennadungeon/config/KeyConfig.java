package net.hennabatch.hennadungeon.config;

import java.util.HashMap;
import java.util.Map;

public class KeyConfig {

    private final Map<EnumKeyInput, Character> keys = new HashMap<>();

    public KeyConfig setDefault(){
        this.keys.put(EnumKeyInput.UP, 'w');
        this.keys.put(EnumKeyInput.DOWN, 's');
        this.keys.put(EnumKeyInput.LEFT, 'a');
        this.keys.put(EnumKeyInput.RIGHT, 'd');
        this.keys.put(EnumKeyInput.SUBMIT, 'l');
        this.keys.put(EnumKeyInput.CANCEL, ';');
        this.keys.put(EnumKeyInput.MENU, 'q');
        this.keys.put(EnumKeyInput.SKILL, 'e');
        this.keys.put(EnumKeyInput.SEEPATH, 'f');
        return this;
    }

    public EnumKeyInput getByChar(char ch){
        return this.keys.entrySet().stream().filter(x -> x.getValue() == ch).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public Character getChar(EnumKeyInput key){
        try {
            return this.keys.get(key);
        }catch(Exception e){
            return null;
        }
    }

    public void replaceKey(EnumKeyInput keyInput, char c){
        keys.remove(keyInput);
        keys.put(keyInput, c);
    }
}
