package com.immersion.alura.java.conf;

import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.domain.StickerGenerator;
import com.immersion.alura.java.domain.StickerGeneratorDelegator;
import com.immersion.alura.java.exception.PropertiesConfigException;

import com.immersion.alura.java.mapper.MarvelDeserializer;
import com.immersion.alura.java.service.HttpRequestService;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class responsable to create Beans
 */
@Configuration
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
