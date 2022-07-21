package com.immersion.alura.java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NasaContent {

    @JsonProperty
    private String title;

    @JsonProperty(value = "hdurl")
    private String urlImage;

    public NasaContent() {

    }

    public NasaContent(final String title, final String urlImage) {
        this.title = title;
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
