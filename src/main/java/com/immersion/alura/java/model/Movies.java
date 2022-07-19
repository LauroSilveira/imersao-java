package com.immersion.alura.java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies {

    @JsonProperty(value = "items")
    private List<Movie> items;

    public Movies() {
    }

    public Movies(List<Movie> items) {
        this.items = items;
    }

    public List<Movie> getItems() {
        return items;
    }

    public Movies setItems(List<Movie> items) {
        this.items = items;
        return this;
    }
}
