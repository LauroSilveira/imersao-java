package com.immersion.alura.java;

import static com.immersion.alura.java.enums.EndpointsEnum.URL_MARVEL;

import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.domain.StickerGeneratorDelegator;
import com.immersion.alura.java.domain.StickerGeneratorIMDB;
import com.immersion.alura.java.domain.StickerGeneratorMarvel;
import com.immersion.alura.java.domain.StickerGeneratorNasa;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.mapper.MarvelDeserializer;
import com.immersion.alura.java.model.Result;
import com.immersion.alura.java.service.HttpRequestService;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final HttpRequestService httpRequestService = new HttpRequestService();
    private static final ProcessData processData = new ProcessData();
    private static final JacksonParser jacksonParser = new JacksonParser();
    private static final MarvelDeserializer marverDeserializer = new MarvelDeserializer();

    //Patter Java Delegate
    private static final StickerGeneratorDelegator stickerGeneratorIMDB = new StickerGeneratorDelegator(new StickerGeneratorIMDB());
    private static final StickerGeneratorDelegator stickerGeneratorNasa = new StickerGeneratorDelegator(new StickerGeneratorNasa());
    private static final StickerGeneratorDelegator stickerGeneratorMarvel = new StickerGeneratorDelegator(new StickerGeneratorMarvel());
    //private static final StickerGeneratorDelegator stickerGeneratorLanguages = new StickerGeneratorDelegator(new StickerGeneratorLanguages());
    public static void main(String[] args) throws URISyntaxException {

/*        LOGGER.log(Level.INFO, "Request to IMD API to get Top 250 Movies");
        final var responseTopMovies = httpRequestService.getRequestIMDB(URL_TOP_250_MOVIES.getEndpoint());
        final var movies = jacksonParser.parseToJavaObject(responseTopMovies, Movies.class);
        processData.printResponseIMD(movies);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top Most Popular Movies");
        final var responseMoviesPopular = httpRequestService.getRequestIMDB(URL_MOST_POPULAR_MOVIES.getEndpoint());
        final var moviesMostPopular = jacksonParser.parseToJavaObject(responseMoviesPopular, Movies.class);
        processData.printResponseIMD(moviesMostPopular);

        LOGGER.log(Level.INFO, "Starting process to generate Sticker");
        final List<Movie> movie = moviesMostPopular.getItems().stream().limit(5L).toList();
        stickerGeneratorIMDB.stickerGenerator(movie.stream().findFirst().get().getBanner(), movie.stream().findFirst().get().getTitle(),
                movie.stream().findFirst().get().getImDbRating());

        LOGGER.log(Level.INFO, "Request to Nasa Api to get Astronomic Day Photo...");
        final String nasaResponse = httpRequestService.getNasaRequest(URL_ASTRONOMIC_DAY_NASA.getEndpoint());
        NasaContent nasaContent = jacksonParser.parseToJavaObject(nasaResponse, NasaContent.class);
        stickerGeneratorNasa.stickerGenerator(nasaContent.urlImage(), nasaContent.title(), null);*/


        LOGGER.log(Level.INFO, "Request to Data Api to get list of all characters");
        final String responseMarvelJson = httpRequestService.getMarvelRequest(URL_MARVEL.getUrl());
        final List<Result> results = marverDeserializer.deserialize(responseMarvelJson);

        results.forEach(r -> stickerGeneratorMarvel.stickerGenerator(r.path(), r.name(), null));

/*        LOGGER.log(Level.INFO, "Request to API Rest Programming language...");
        String languagesJson = httpRequestService.getRequestIMDB("https://alura-programming-lang-api.herokuapp.com/languages");
        List<Languages> languages = Arrays.asList(jacksonParser.parseToJavaObject(languagesJson, Languages[].class));
        processData.printResponseApiLanguage(languages);*/


/*        languages.forEach(l -> stickerGeneratorLanguages.stickerGenerator(l.getUrl(), l.getName(),
                Double.parseDouble(l.getPosition())));*/
    }
}
