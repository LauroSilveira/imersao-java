package com.immersion.alura.java.conf;

import com.immersion.alura.java.exception.PropertiesConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig {

    public static final String PATH_TO_PROPERTIES = "src/main/resources/application.properties";

    public Properties getProperties()  {
        final Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException ex) {
            throw new PropertiesConfigException("Not found file .properties", ex.getCause());
        }
    }
}
