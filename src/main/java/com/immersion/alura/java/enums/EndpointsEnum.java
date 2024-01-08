package com.immersion.alura.java.enums;

import com.immersion.alura.java.conf.PropertiesConfig;

import java.util.Objects;

public enum EndpointsEnum {
    URL_MOST_POPULAR_MOVIES("api.imdb.url-most-popular-movies", "API_KEY_IMDB", ""),
    URL_TOP_250_MOVIES("api.imdb.url-top-250-movies", "API_KEY_IMDB", ""),
    URL_ASTRONOMIC_DAY_NASA("api.nasa.url.photo-astronomic-of-the-day", "API_KEY_NASA", ""),
    URL_MARVEL_CHARACTERS("api.marvel.url-characteres", "", ""),
    URL_MARVEL_COMICS("api.marvel.url-comics", "", ""),

    URL_PROGRAMMING_LANGUAGE_API("api.programming-language-api", "", "");
    private final String url;
    private final String apiKey;
    private final String endpoint;
    private final String publicKey;

    EndpointsEnum(final String url, final String apiKey, final String publicKey) {
        this.url = new PropertiesConfig().getProperties().getProperty(url);
        this.apiKey = System.getenv(apiKey);
        this.endpoint = getUrl() + getApiKey();
        this.publicKey = Objects.equals(publicKey, "") ? apiKey : "";
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
