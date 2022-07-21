package com.immersion.alura.java;

import com.immersion.alura.java.conf.PropertiesConfig;
import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.domain.StickerGenerator;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.model.Movie;
import com.immersion.alura.java.model.Movies;
import com.immersion.alura.java.model.NasaContent;
import com.immersion.alura.java.service.HttpRequestService;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final PropertiesConfig propertiesConfig = new PropertiesConfig();
    private static final HttpRequestService httpRequestService = new HttpRequestService();
    private static final ProcessData processData = new ProcessData();
    private static final JacksonParser jacksonParser = new JacksonParser();
    private static final StickerGenerator stickerGenerator = new StickerGenerator();
    private static final String URL_TOP_250_MOVIES = "api.imdb.url-top-250-movies";
    private static final String URL_MOST_POPULAR_MOVIES = "api.imdb.url-most-popular-movies";
    private static final String IMDB_KEY = "api.imdb.user.key";

    private static final String URL_MARVEL = "api.marvel.url-list-of-all.characters";
    private static final String KEY_MARVEL = "api.marvel.user.key";

    public static void main(String[] args) {
        final var top250Url = propertiesConfig.getProperties().getProperty(URL_TOP_250_MOVIES)
                .concat(propertiesConfig.getProperties().getProperty(IMDB_KEY));

        final var mostPopularMovies = propertiesConfig.getProperties().getProperty(URL_MOST_POPULAR_MOVIES)
                .concat(propertiesConfig.getProperties().getProperty(IMDB_KEY));

        LOGGER.log(Level.INFO, "Request to IMD API to get Top 250 Movies");
        final var responseTopMovies = httpRequestService.getRequestIMDB(top250Url);
        final var movies = jacksonParser.parseToJavaObject(responseTopMovies, Movies.class);
        processData.printRequest(movies);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top Most Popular Movies");
        final var responseMoviesPopular = httpRequestService.getRequestIMDB(mostPopularMovies);
        final var moviesMostPopular = jacksonParser.parseToJavaObject(responseMoviesPopular, Movies.class);
        processData.printRequest(moviesMostPopular);

        LOGGER.log(Level.INFO, "Starting process to generate Sticker");
        final Optional<Movie> movie = moviesMostPopular.getItems().stream().findAny();
        stickerGenerator.generatorSticker(movie);

/*        LOGGER.log(Level.INFO, "Request to Marvel Api to get list of all characters");
        String responseMarvelJson = httpRequestService.getMarvelRequest(propertiesConfig.getProperties().getProperty(URL_MARVEL)
                .concat(propertiesConfig.getProperties().getProperty(KEY_MARVEL)));
        System.out.println("Marvel response JSon " + responseMarvelJson);*/

        LOGGER.log(Level.INFO, "Request to Nasa Api to get the day planet photo");
        final String nasaResponse = httpRequestService.getNasaRequest("https://api.nasa.gov/planetary/apod?api_key=xgxTDgbHeohZtRXrnYCuFpKy1D7pfwKepi7cQKtD");
        jacksonParser.parseToJavaObject(nasaResponse, NasaContent.class);
    }
}
