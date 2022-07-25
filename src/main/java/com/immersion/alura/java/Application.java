package com.immersion.alura.java;

import com.immersion.alura.java.domain.*;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.model.Languages;
import com.immersion.alura.java.model.Movie;
import com.immersion.alura.java.model.Movies;
import com.immersion.alura.java.model.NasaContent;
import com.immersion.alura.java.service.HttpRequestService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.immersion.alura.java.enums.EndpointsEnum.*;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final HttpRequestService httpRequestService = new HttpRequestService();
    private static final ProcessData processData = new ProcessData();
    private static final JacksonParser jacksonParser = new JacksonParser();

    //Patter Java Delegate
    private static final StickerGeneratorDelegator stickerGeneratorIMDB = new StickerGeneratorDelegator(new StickerGeneratorIMDB());
    private static final StickerGeneratorDelegator stickerGeneratorNasa = new StickerGeneratorDelegator(new StickerGeneratorNasa());
    private static final StickerGeneratorDelegator stickerGeneratorLanguages = new StickerGeneratorDelegator(new StickerGeneratorLanguages());

    public static void main(String[] args) {

        LOGGER.log(Level.INFO, "Request to IMD API to get Top 250 Movies");
        final var responseTopMovies = httpRequestService.getRequestIMDB(URL_TOP_250_MOVIES.getEndpoint());
        final var movies = jacksonParser.parseToJavaObject(responseTopMovies, Movies.class);
        processData.printRequest(movies);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top Most Popular Movies");
        final var responseMoviesPopular = httpRequestService.getRequestIMDB(URL_MOST_POPULAR_MOVIES.getEndpoint());
        final var moviesMostPopular = jacksonParser.parseToJavaObject(responseMoviesPopular, Movies.class);
        processData.printRequest(moviesMostPopular);

        LOGGER.log(Level.INFO, "Starting process to generate Sticker");
        final List<Movie> movie = moviesMostPopular.getItems().stream().limit(5L).toList();
        stickerGeneratorIMDB.stickerGenerator(movie.stream().findFirst().get().getBanner(), movie.stream().findFirst().get().getTitle(),
                movie.stream().findFirst().get().getImDbRating());

        LOGGER.log(Level.INFO, "Request to Nasa Api to get Astronomic Day Photo...");
        final String nasaResponse = httpRequestService.getNasaRequest(URL_ASTRONOMIC_DAY_NASA.getEndpoint());
        NasaContent nasaContent = jacksonParser.parseToJavaObject(nasaResponse, NasaContent.class);
        stickerGeneratorNasa.stickerGenerator(nasaContent.urlImage(), nasaContent.title(), null);

        //TODO: Implement Marven endpoint
/*
        LOGGER.log(Level.INFO, "Request to Marvel Api to get list of all characters");
        String responseMarvelJson = httpRequestService.getMarvelRequest(propertiesConfig.getProperties().getProperty(URL_MARVEL.getUrl())
                .concat(propertiesConfig.getProperties().getProperty(EndpointsEnum.K)));
        System.out.println("Marvel response JSon " + responseMarvelJson);
*/
        LOGGER.log(Level.INFO, "Request to API Rest programming language...");
        String languagesJson = httpRequestService.getRequestIMDB(URL_LANGUAGE_API.getUrl());
        List<Languages> languages = Arrays.asList(jacksonParser.parseToJavaObject(languagesJson, Languages[].class));
        //TODO: refactor to another class
        languages.forEach(language -> {
            System.out.println("Id: " + language.getId());
            System.out.println("Name: " + language.getName());
            System.out.println("Url: " + language.getUrl());
            System.out.println("Position: " + language.getPosition());
        });

        languages.forEach(l -> stickerGeneratorLanguages.stickerGenerator(l.getUrl(), l.getName(),
                Double.parseDouble(l.getPosition())));
    }
}
