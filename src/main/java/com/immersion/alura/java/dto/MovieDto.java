package com.immersion.alura.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto {

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

    public MovieDto() {
    }
    public MovieDto setImDbRating(Double imDbRating) {
        this.imDbRating = imDbRating;
        return this;
    }
    public String getRank() {
        return rank;
    }

    public MovieDto setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBanner() {
        return banner;
    }

    public MovieDto setBanner(String banner) {
        this.banner = banner;
        return this;
    }

    @Override
    public String toString() {
        return "MovieDto{" +
                "rank='" + rank + '\'' +
                ", title='" + title + '\'' +
                ", banner='" + banner + '\'' +
                '}';
    }
}
