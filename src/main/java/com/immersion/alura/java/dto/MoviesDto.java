package com.immersion.alura.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviesDto {

    @JsonProperty(value = "items")
    private List<MovieDto> items;

    public MoviesDto() {
    }

    public MoviesDto(List<MovieDto> items) {
        this.items = items;
    }

    public List<MovieDto> getItems() {
        return items;
    }

    public MoviesDto setItems(List<MovieDto> items) {
        this.items = items;
        return this;
    }
}
