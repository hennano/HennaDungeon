package net.hennabatch.hennadungeon.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertyFile {

    private Properties properties;

    private PropertyFile(Properties properties){
        this.properties = properties;
    }

    public static PropertyFile readPropertyFile(Path path)throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(path, StandardCharsets.UTF_8));
        return new PropertyFile(properties);
    }

    public String getProperty(String key){
        return properties.getProperty(key, null);
    }

    public String getProperty(String key, String def){
        return properties.getProperty(key, def);
    }
}
