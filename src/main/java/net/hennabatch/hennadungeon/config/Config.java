package net.hennabatch.hennadungeon.config;

import net.hennabatch.hennadungeon.util.PropertyFile;
import net.hennabatch.hennadungeon.util.Reference;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    private final EnumRunMode runMode = EnumRunMode.RELEASE;
    private final KeyConfig keyConfig = new KeyConfig().setDefault();
    public final boolean enabledToggleAttack = false;

    public EnumRunMode runMode(){
        return runMode;
    }

    public KeyConfig keyConfig(){
        return keyConfig;
    }

    public static Config loadConfig(Path path){
        Config config = new Config();
        PropertyFile file;
        try {
            file = PropertyFile.readPropertyFile(path);
        } catch (NoSuchFileException e) {
            Reference.logger.warn("ConfigFile was not found.");
            createDefaultConfigFile(path);
            return loadConfig(path);
        } catch (IOException e){
            Reference.logger.error(e.getMessage(), e);
            return null;
        }

        Arrays.stream(EnumKeyInput.values()).forEach(x -> {
            String s = file.getProperty(x.name());
            if(s == null){
                Reference.logger.warn(x.name() + "was not found. use default key");
            }
            config.keyConfig.replaceKey(x, (char) s.getBytes(StandardCharsets.UTF_8)[0]);
        });
        return config;
    }

    public static void createDefaultConfigFile(Path path){
        Reference.logger.info("create default config file.");
        try{
            Files.createFile(path);
        } catch (IOException e) {
            Reference.logger.error(e.getMessage(), e);
        }
        List<String> properties = new ArrayList<>();

        //keyconfig
        KeyConfig keyConfig = new KeyConfig().setDefault();
        properties.add(";Key config");
        Arrays.stream(EnumKeyInput.values()).forEach(x -> properties.add(x.name() + "=" + keyConfig.getChar(x)));

        //write
        try{
            Files.write(path, properties, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (IOException e) {
            Reference.logger.error(e.getMessage(), e);
        }

    }

}
