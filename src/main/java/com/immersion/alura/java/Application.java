package com.immersion.alura.java;

import com.immersion.alura.java.domain.*;
import com.immersion.alura.java.enums.EndpointsEnum;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.mapper.MarvelDeserializer;
import com.immersion.alura.java.model.*;
import com.immersion.alura.java.service.imdb.HttpRequestServiceIMDB;
import com.immersion.alura.java.service.marvel.HttpRequestServiceMarvel;
import com.immersion.alura.java.service.nasa.HttpRequestServiceNasa;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;

import static com.immersion.alura.java.enums.EndpointsEnum.*;

@Slf4j
public class Application {


    private static final ProcessData processData = new ProcessData();
    private static final MarvelDeserializer marvelDeserializer = new MarvelDeserializer();

    //Patter Java Delegate
    private static final StickerGeneratorDelegator stickerGeneratorIMDB = new StickerGeneratorDelegator(
            new StickerGeneratorIMDB());
    private static final StickerGeneratorDelegator stickerGeneratorNasa = new StickerGeneratorDelegator(
            new StickerGeneratorNasa());
    private static final StickerGeneratorDelegator stickerGeneratorMarvel = new StickerGeneratorDelegator(
            new StickerGeneratorMarvel());

    private static final StickerGeneratorDelegator stickerGeneratorLanguages = new StickerGeneratorDelegator(new StickerGeneratorLanguages());

    private static final JacksonParser jacksonParser = new JacksonParser();

    public static void main(String[] args) {
        final var httpClient = HttpClient.newHttpClient();
        log.info("Request to IMDB API to get Top 250 Movies");
        final var responseTopMovies = HttpRequestServiceIMDB.getInstance().getRequestIMDB(httpClient, URL_TOP_250_MOVIES.getEndpoint());
        final var top250Movies = jacksonParser.parseToJavaObject(responseTopMovies, Movies.class);
        processData.printResponseIMD(top250Movies);

        log.info("Request to IMDB API to get Top Most Popular Movies");
        final var responseMoviesPopular = HttpRequestServiceIMDB.getInstance().getRequestIMDB(httpClient, URL_MOST_POPULAR_MOVIES.getEndpoint());
        final var moviesMostPopular = jacksonParser.parseToJavaObject(responseMoviesPopular,
                Movies.class);
        processData.printResponseIMD(moviesMostPopular);

        log.info("Starting process to generate Sticker");
        final List<Movie> movies = moviesMostPopular.getItems().stream().limit(5L).toList();
        movies.forEach(
                m -> stickerGeneratorIMDB.stickerGenerator(m.getBanner(), m.getTitle(), m.getImDbRating(),
                        false));

        log.info("Request to Nasa Api to get Astronomic Day Photo...");
        final String nasaResponse = HttpRequestServiceNasa.getInstance().getNasaRequest(httpClient, URL_ASTRONOMIC_DAY_NASA.getEndpoint());
        NasaContent nasaContent = jacksonParser.parseToJavaObject(nasaResponse, NasaContent.class);
        stickerGeneratorNasa.stickerGenerator(nasaContent.urlImage(), nasaContent.title(), null, false);

        log.info("Request to Marvel API to get list of all characters");
        final String responseMarvelJson = HttpRequestServiceMarvel.getInstance().getMarvelRequest(httpClient,
                URL_MARVEL_CHARACTERS.getUrl());
        final List<Result> results = marvelDeserializer.deserialize(responseMarvelJson);
        results.forEach(r -> stickerGeneratorMarvel.stickerGenerator(r.path(), r.name(), null, false));

        log.info("Request to Marvel Api to get list of Comics");
        final String comicsResponse = HttpRequestServiceMarvel.getInstance().getMarvelRequest(httpClient, URL_MARVEL_COMICS.getUrl());
        final List<Result> comics = marvelDeserializer.deserialize(comicsResponse);
        comics.forEach(r -> stickerGeneratorMarvel.stickerGenerator(r.path(), r.name(), null, true));

        log.info("Request to API Rest Programming language...");
        String languagesJson = HttpRequestServiceIMDB.getInstance().getRequestIMDB(HttpClient.newHttpClient(), URL_PROGRAMMING_LANGUAGE_API.getUrl());
        List<Languages> languages = Arrays.asList(jacksonParser.parseToJavaObject(languagesJson, Languages[].class));
        processData.printResponseApiLanguage(languages);
        languages.forEach(l -> stickerGeneratorLanguages.stickerGenerator(l.getUrl(), l.getName(), Double.parseDouble(l.getPosition()), false));
    }
}
