package com.immersion.alura.java.model.enums;

public enum GetUrlEnum {
    URL_MOST_POPULAR_MOVIES("api.imdb.url-most-popular-movies", "api.imdb.user.key"),
    URL_TOP_250_MOVIES("api.imdb.url-top-250-movie", "api.imdb.user.key"),
    URL_ASTRONOMIC_DAY_NASA("api.nasa.url.photo-astronomic-of-the-day", "api.nasa.user.key"),
    URL_MARVEL("api.marvel.url-list-of-all.characters", "api.marvel.user.key");

    private String url;
    private String apiKey;

    GetUrlEnum(final String url, final String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }
}
