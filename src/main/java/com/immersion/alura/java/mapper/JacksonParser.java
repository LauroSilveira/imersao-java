package com.immersion.alura.java.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.immersion.alura.java.model.Movies;

public class JacksonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Movies parseToMoviesDto(String response) {
        try {
            return objectMapper.readValue(response, Movies.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error to mapping URL to Java Object", ex.getCause());
        }
    }
}
