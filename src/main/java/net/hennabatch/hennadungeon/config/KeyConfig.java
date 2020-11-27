package net.hennabatch.hennadungeon.config;

import java.util.HashMap;
import java.util.Map;

public class KeyConfig {

    private final Map<Character, EnumKeyInput> keys = new HashMap<>();

    public KeyConfig setDefault(){
        this.keys.put('w', EnumKeyInput.UP);
        this.keys.put('s', EnumKeyInput.DOWN);
        this.keys.put('a', EnumKeyInput.LEFT);
        this.keys.put('d', EnumKeyInput.RIGHT);
        this.keys.put(';', EnumKeyInput.ENTER);
        this.keys.put('\'', EnumKeyInput.CANCEL);
        this.keys.put('q', EnumKeyInput.MENU);
        this.keys.put('e', EnumKeyInput.SKILL);
        this.keys.put('f', EnumKeyInput.SEEPATH);
        return this;
    }

    public EnumKeyInput getByChar(char ch){
        try {
            return this.keys.get(ch);
        }catch(Exception e){
            return null;
        }
    }

    public char getChar(EnumKeyInput key){
        return this.keys.entrySet().stream().filter(x -> x.getValue() == key).map(Map.Entry::getKey).findFirst().orElse(null);
    }
}
