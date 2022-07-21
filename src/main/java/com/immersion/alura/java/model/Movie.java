package com.immersion.alura.java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @JsonProperty
    private String rank;
    @JsonProperty
    private String title;
    @JsonProperty(value = "image")
    private String banner;
    @JsonProperty
    private Double imDbRating;

    public Double getImDbRating() {
        return imDbRating;
    }

    //Constructor necessary to object mapper Jackson
    public Movie() {

    }
    public Movie setImDbRating(Double imDbRating) {
        this.imDbRating = imDbRating;
        return this;
    }
    public String getRank() {
        return rank;
    }

    public Movie setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBanner() {
        return banner;
    }

    public Movie setBanner(String banner) {
        this.banner = banner;
        return this;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "rank='" + rank + '\'' +
                ", title='" + title + '\'' +
                ", banner='" + banner + '\'' +
                '}';
    }
}
