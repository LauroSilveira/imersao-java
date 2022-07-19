package com.immersion.alura.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.immersion.alura.java.dto.MoviesDto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String URL_TO_250_MOVIES = "api.imd.top-250-movies-key";
    private static final String URL_MOST_POPULAR_MOVIES = "api.imd.mot-popular-movies-key";

    public static void main(String[] args) throws IOException {
        final var top250Url = getProperties().getProperty(URL_TO_250_MOVIES);
        final var mostPopularMovies = getProperties().getProperty(URL_MOST_POPULAR_MOVIES);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top 250 Movies");
        final var topMovies = getRequestIMDB(top250Url);
        final var movies = parseToMoviesDto(topMovies);
        printRequest(movies);

        LOGGER.log(Level.INFO, "Request to IMD API to get Top Most Popular Movies");
        final var responseIMDB = getRequestIMDB(mostPopularMovies);
        final var moviesMostPopular = parseToMoviesDto(responseIMDB);
        printRequest(moviesMostPopular);
    }

    private static void printRequest(MoviesDto moviesDto) {
        moviesDto.getItems()
                .forEach(dto -> {
                    System.out.println("\u001b[1m\u001b[4mTitle: " + dto.getTitle() + "\u001b[0m");
                    System.out.println("\u001b[31mRank: " + dto.getRank() + "\u001b[0m");
                    System.out.println(dto.getBanner() + "\n");
                    System.out.println("Rating: " + roundAndFormatRating(dto.getImDbRating()) + "\n");
                });
    }

    private static String roundAndFormatRating(Double imDbRating) {
        String ratingFormatted = "";
        final BigDecimal bigDecimal = BigDecimal.valueOf(Optional.ofNullable(imDbRating).orElse(1.0))
                .setScale(0, RoundingMode.FLOOR);

        for (int i = 0; i < bigDecimal.intValueExact(); i++) {
            ratingFormatted += "\uD83C\uDF1F";
        }
        return ratingFormatted;
    }

    private static MoviesDto parseToMoviesDto(String response) {
        try {
            return objectMapper.readValue(response, MoviesDto.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error to mapping URL to Java Object", ex.getCause());
        }
    }

    private static String getRequestIMDB(String request) {
        LOGGER.log(Level.INFO, "Request to IMD API");
        final HttpClient client = HttpClient.newBuilder().build();
        try {
            return client.send(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties getProperties() throws IOException {
        final Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException ex) {
            throw new IOException("Not found file .properties", ex.getCause());
        }
    }

}
