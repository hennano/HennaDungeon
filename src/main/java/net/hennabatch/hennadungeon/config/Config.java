package net.hennabatch.hennadungeon.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.hennabatch.hennadungeon.util.Reference;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {

    private EnumRunMode runMode = EnumRunMode.DEBUG;
    private KeyConfig keyConfig = new KeyConfig().setDefault();

    public static Config loadConfig(String jsonPath){
        /*
        Config config = new Config();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new LinkedHashMap<>();
        try {
            map = mapper.readValue(jsonPath, new TypeReference<LinkedHashMap<String,Object>>(){});

        } catch (Exception e) {
            return null;
        }
         */
        return null;
    }




    public EnumRunMode runMode(){
        return runMode;
    }

    public KeyConfig keyConfig(){
        return keyConfig;
    }

}
