package net.hennabatch.hennadungeon.config;

public class Config {

    private final EnumRunMode runMode = EnumRunMode.RELEASE;
    private final KeyConfig keyConfig = new KeyConfig().setDefault();
    public final boolean enabledToggleAttack = false;

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
