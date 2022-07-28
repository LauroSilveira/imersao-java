package com.immersion.alura.java.service;

import com.immersion.alura.java.exception.HttpRequestServiceException;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRequestService {

    private static final Logger LOGGER = Logger.getLogger(HttpRequestService.class.getName());
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public String getRequestIMDB(String request) {
        LOGGER.log(Level.INFO, "Request to IMD API");

        try {
            //Asynchronous HTTP request
            return httpClient.sendAsync(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .whenComplete(((success, throwable) -> success.body()))
                    .thenApply(HttpResponse::body)
                    .get();
        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to IMD API", e.getCause());
        }
    }

    public String getMarvelRequest(String request) {
        LOGGER.log(Level.INFO, "Request to Marvel API...");
        LocalDateTime now = LocalDateTime.now();
        final var publicKey = "27a05f818a4f8f75077c8bba7c6ab1fb";
        final var privateKey = "36f297235f6d79bf449d5cf0998b6753b777f044";

        var hashSHA256 = generateHashMD5(now.toString(), privateKey, publicKey);
        String uri = "%s?ts=%s&apikey=%s&hash=%s&limit=30".formatted(request, now, publicKey, hashSHA256);

        try {
            //Http 2.0
           return httpClient.sendAsync(HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build(), BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to Data API", e.getCause());
        }
    }

    private String generateHashMD5(String uuid, final String privateKey, final String publicKey) {
        try {
            String apiKey = uuid + privateKey + publicKey;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(apiKey.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(hash).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating Hash", e.getCause());
        }
    }


    public String getNasaRequest(String request) {
        LOGGER.log(Level.INFO, "Request to Data API...");
        try {
            return httpClient.send(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to Nasa API", e.getCause());
        }

    }
}
