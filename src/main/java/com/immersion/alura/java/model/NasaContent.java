package com.immersion.alura.java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public record NasaContent(@JsonProperty String title,
                          @JsonProperty(value = "hdurl") String urlImage) {

}
