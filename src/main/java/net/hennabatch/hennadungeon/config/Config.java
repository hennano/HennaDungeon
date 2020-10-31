package net.hennabatch.hennadungeon.config;

public class Config {

    private final EnumRunMode runMode = EnumRunMode.DEBUG;
    private final KeyConfig keyConfig = new KeyConfig().setDefault();

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
