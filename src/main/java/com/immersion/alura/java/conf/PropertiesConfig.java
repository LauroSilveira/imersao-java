package com.immersion.alura.java.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig {

    public Properties getProperties()  {
        final Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(inputStream);
            return properties;
        } catch (IOException ex) {
            throw new RuntimeException("Not found file .properties", ex.getCause());
        }
    }
}
