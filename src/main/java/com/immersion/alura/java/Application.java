package com.immersion.alura.java;

import com.immersion.alura.java.conf.PropertiesConfig;
import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.model.Movie;
import com.immersion.alura.java.service.HttpRequestService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final PropertiesConfig propertiesConfig = new PropertiesConfig();
    private static final HttpRequestService httpRequestService = new HttpRequestService();
    private static final ProcessData processData = new ProcessData();
    private static final JacksonParser mapper = new JacksonParser();
    private static final String URL_TOP_250_MOVIES = "api.imdb.url-top-250-movies";
    private static final String URL_MOST_POPULAR_MOVIES = "api.imdb.url-most-popular-movies";
    private static final String KEY = "api.imdb.user.key";

    public static void main(String[] args) {
        final var top250Url = propertiesConfig.getProperties().getProperty(URL_TOP_250_MOVIES)
                .concat(propertiesConfig.getProperties().getProperty(KEY));

        final var mostPopularMovies = propertiesConfig.getProperties().getProperty(URL_MOST_POPULAR_MOVIES)
                .concat(propertiesConfig.getProperties().getProperty(KEY));

        LOGGER.log(Level.INFO, "Request to IMD API to get Top 250 Movies");
        final var topMovies = httpRequestService.getRequestIMDB(top250Url);
        final var movies = mapper.parseToMoviesDto(topMovies);
        processData.printRequest(movies);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top Most Popular Movies");
        final var responseIMDB = httpRequestService.getRequestIMDB(mostPopularMovies);
        final var moviesMostPopular = mapper.parseToMoviesDto(responseIMDB);
        processData.printRequest(moviesMostPopular);

        LOGGER.log(Level.INFO, "Starting process to generate Sticker");
        final Optional<Movie> movie = moviesMostPopular.getItems().stream().findAny();
        processData.generatorSticker(movie);
    }
}
