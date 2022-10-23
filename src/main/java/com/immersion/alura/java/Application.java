package com.immersion.alura.java;

import static com.immersion.alura.java.enums.EndpointsEnum.URL_ASTRONOMIC_DAY_NASA;
import static com.immersion.alura.java.enums.EndpointsEnum.URL_MARVEL_CHARACTERS;
import static com.immersion.alura.java.enums.EndpointsEnum.URL_MARVEL_COMICS;
import static com.immersion.alura.java.enums.EndpointsEnum.URL_MOST_POPULAR_MOVIES;
import static com.immersion.alura.java.enums.EndpointsEnum.URL_TOP_250_MOVIES;

import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.domain.StickerGeneratorDelegator;
import com.immersion.alura.java.domain.StickerGeneratorIMDB;
import com.immersion.alura.java.domain.StickerGeneratorMarvel;
import com.immersion.alura.java.domain.StickerGeneratorNasa;
import com.immersion.alura.java.mapper.JacksonParser;
import com.immersion.alura.java.mapper.MarvelDeserializer;
import com.immersion.alura.java.model.Movie;
import com.immersion.alura.java.model.Movies;
import com.immersion.alura.java.model.NasaContent;
import com.immersion.alura.java.model.Result;
import com.immersion.alura.java.service.HttpRequestService;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

  private static final HttpRequestService httpRequestService = new HttpRequestService();
  private static final ProcessData processData = new ProcessData();
  private static final MarvelDeserializer marverDeserializer = new MarvelDeserializer();

  //Patter Java Delegate
  private static final StickerGeneratorDelegator stickerGeneratorIMDB = new StickerGeneratorDelegator(
      new StickerGeneratorIMDB());
  private static final StickerGeneratorDelegator stickerGeneratorNasa = new StickerGeneratorDelegator(
      new StickerGeneratorNasa());
  private static final StickerGeneratorDelegator stickerGeneratorMarvel = new StickerGeneratorDelegator(
      new StickerGeneratorMarvel());


  private static final JacksonParser jacksonParser = new JacksonParser();


  //TODO:API rest was in Heroku and now it´s paid, search another cloud to upload
  //private static final StickerGeneratorDelegator stickerGeneratorLanguages = new StickerGeneratorDelegator(new StickerGeneratorLanguages());
  public static void main(String[] args) throws URISyntaxException {

    log.info("Request to IMD API to get Top 250 Movies");
    final var responseTopMovies = httpRequestService.getRequestIMDB(
        URL_TOP_250_MOVIES.getEndpoint());
    final var top250Movies = jacksonParser.parseToJavaObject(responseTopMovies, Movies.class);
    processData.printResponseIMD(top250Movies);

    log.info("Request to IMD API to get Top Most Popular Movies");
    final var responseMoviesPopular = httpRequestService.getRequestIMDB(
        URL_MOST_POPULAR_MOVIES.getEndpoint());
    final var moviesMostPopular = jacksonParser.parseToJavaObject(responseMoviesPopular,
        Movies.class);
    processData.printResponseIMD(moviesMostPopular);

    log.info("Starting process to generate Sticker");
    final List<Movie> movies = moviesMostPopular.getItems().stream().limit(5L).toList();
    movies.forEach(
        m -> stickerGeneratorIMDB.stickerGenerator(m.getBanner(), m.getTitle(), m.getImDbRating(),
            false));

    log.info("Request to Nasa Api to get Astronomic Day Photo...");
    final String nasaResponse = httpRequestService.getNasaRequest(
        URL_ASTRONOMIC_DAY_NASA.getEndpoint());
    NasaContent nasaContent = jacksonParser.parseToJavaObject(nasaResponse, NasaContent.class);
    stickerGeneratorNasa.stickerGenerator(nasaContent.urlImage(), nasaContent.title(), null, false);

    log.info("Request to Marvel API to get list of all characters");
    final String responseMarvelJson = httpRequestService.getMarvelRequest(
        URL_MARVEL_CHARACTERS.getUrl());
    final List<Result> results = marverDeserializer.deserialize(responseMarvelJson);
    results.forEach(r -> stickerGeneratorMarvel.stickerGenerator(r.path(), r.name(), null, false));

    log.info("Request to Marve Api to get list of Comics");
    final String comicsResponse = httpRequestService.getMarvelRequest(URL_MARVEL_COMICS.getUrl());
    final List<Result> comics = marverDeserializer.deserialize(comicsResponse);
    comics.forEach(r -> stickerGeneratorMarvel.stickerGenerator(r.path(), r.name(), null, true));

//TODO: Upload he API rest programming languague in another cloud and uncomment this pard
/*        LOGGER.log(Level.INFO, "Request to API Rest Programming language...");
        String languagesJson = httpRequestService.getRequestIMDB("https://alura-programming-lang-api.herokuapp.com/languages");
        List<Languages> languages = Arrays.asList(jacksonParser.parseToJavaObject(languagesJson, Languages[].class));
        processData.printResponseApiLanguage(languages);
      languages.forEach(l -> stickerGeneratorLanguages.stickerGenerator(l.getUrl(), l.getName(),
                Double.parseDouble(l.getPosition())));*/
  }
}
