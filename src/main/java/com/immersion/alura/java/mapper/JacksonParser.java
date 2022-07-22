package com.immersion.alura.java.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.immersion.alura.java.exception.ParseJacksonException;

import java.io.IOException;

public class JacksonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T parseToJavaObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException ex) {
            throw  new ParseJacksonException("Error to deserialize to Java Object", ex.getCause());
        }
    }
}
