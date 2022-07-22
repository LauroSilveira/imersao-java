package com.immersion.alura.java;

import com.immersion.alura.java.conf.PropertiesConfig;
import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.domain.StickerGeneratorDelegator;
import com.immersion.alura.java.domain.StickerGeneratorIMDB;
import com.immersion.alura.java.domain.StickerGeneratorNasa;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.model.Movie;
import com.immersion.alura.java.model.Movies;
import com.immersion.alura.java.model.NasaContent;
import com.immersion.alura.java.model.enums.GetUrlEnum;
import com.immersion.alura.java.service.HttpRequestService;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.immersion.alura.java.model.enums.GetUrlEnum.URL_MARVEL;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final PropertiesConfig propertiesConfig = new PropertiesConfig();
    private static final HttpRequestService httpRequestService = new HttpRequestService();
    private static final ProcessData processData = new ProcessData();
    private static final JacksonParser jacksonParser = new JacksonParser();

    //Patter Java Delegate
    private static final StickerGeneratorDelegator stickerGeneratorIMDB = new StickerGeneratorDelegator(new StickerGeneratorIMDB());
    private static final StickerGeneratorDelegator stickerGeneratorNasa = new StickerGeneratorDelegator(new StickerGeneratorNasa());
    public static void main(String[] args) {
        final var top250Url = propertiesConfig.getProperties().getProperty(GetUrlEnum.URL_TOP_250_MOVIES.getUrl())
                + propertiesConfig.getProperties().get(GetUrlEnum.URL_TOP_250_MOVIES.getApiKey());


        final var mostPopularMoviesUrl = propertiesConfig.getProperties()
                .getProperty(GetUrlEnum.URL_MOST_POPULAR_MOVIES.getUrl())
                + propertiesConfig.getProperties().getProperty(GetUrlEnum.URL_MOST_POPULAR_MOVIES.getApiKey());

        final var nasaUrl = propertiesConfig.getProperties().getProperty(GetUrlEnum.URL_ASTRONOMIC_DAY_NASA.getUrl())
                + propertiesConfig.getProperties().getProperty(GetUrlEnum.URL_ASTRONOMIC_DAY_NASA.getApiKey());

        LOGGER.log(Level.INFO, "Request to IMD API to get Top 250 Movies");
        final var responseTopMovies = httpRequestService.getRequestIMDB(top250Url);
        final var movies = jacksonParser.parseToJavaObject(responseTopMovies, Movies.class);
        processData.printRequest(movies);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top Most Popular Movies");
        final var responseMoviesPopular = httpRequestService.getRequestIMDB(mostPopularMoviesUrl);
        final var moviesMostPopular = jacksonParser.parseToJavaObject(responseMoviesPopular, Movies.class);
        processData.printRequest(moviesMostPopular);

        LOGGER.log(Level.INFO, "Starting process to generate Sticker");
        final Optional<Movie> movie = moviesMostPopular.getItems().stream().findAny();
        stickerGeneratorIMDB.stickerGenerator(movie.get().getBanner(), movie.get().getTitle(), movie.get().getImDbRating());

        //TODO: Implement Marven endpoint
/*
        LOGGER.log(Level.INFO, "Request to Marvel Api to get list of all characters");
        String responseMarvelJson = httpRequestService.getMarvelRequest(propertiesConfig.getProperties().getProperty(URL_MARVEL.getUrl())
                .concat(propertiesConfig.getProperties().getProperty(GetUrlEnum.K)));
        System.out.println("Marvel response JSon " + responseMarvelJson);
*/

        LOGGER.log(Level.INFO, "Request to Nasa Api to get Astronomic Day Photo...");
        final String nasaResponse = httpRequestService.getNasaRequest(nasaUrl);
        NasaContent nasaContent = jacksonParser.parseToJavaObject(nasaResponse, NasaContent.class);
        stickerGeneratorNasa.stickerGenerator(nasaContent.urlImage(), nasaContent.title(), null);

    }
}
