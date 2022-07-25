package com.immersion.alura.java.enums;

import com.immersion.alura.java.conf.PropertiesConfig;

public enum EndpointsEnum {
    URL_MOST_POPULAR_MOVIES("api.imdb.url-most-popular-movies", "API_KEY_IMDB"),
    URL_TOP_250_MOVIES("api.imdb.url-top-250-movies", "API_KEY_IMDB"),
    URL_ASTRONOMIC_DAY_NASA("api.nasa.url.photo-astronomic-of-the-day", "API_KEY_NASA"),
    URL_MARVEL("api.marvel.url-list-of-all.characters", "API_KEY_MARVEL"),
    URL_LANGUAGE_API("https://alura-programming-lang-api.herokuapp.com/languages", null);

    private final String url;
    private final String apiKey;
    private final String endpoint;

    EndpointsEnum(final String url, final String apiKey) {
        this.url = new PropertiesConfig().getProperties().getProperty(url);
        this.apiKey = System.getenv(apiKey);
        this.endpoint = getUrl() + getApiKey();
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
}
