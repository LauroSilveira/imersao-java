package com.immersion.alura.java.service;

import com.immersion.alura.java.exception.HttpRequestServiceException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpRequestService {

    private static final Logger LOGGER = Logger.getLogger(HttpRequestService.class.getName());
    //TODO: change this method to Asynchronous Java request
    public String getRequestIMDB(String request) {
        LOGGER.log(Level.INFO, "Request to IMD API");
        final HttpClient client = HttpClient.newBuilder().build();
        try {
            return client.send(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to IMD API", e.getCause());
        }
    }

    public String getMarvelRequest(String request) {
        LOGGER.log(Level.INFO, "Request to Marvel API...");
        final HttpClient client = HttpClient.newBuilder().build();
        try {
            return client.send(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to IMD API", e.getCause());
        }

    }

    public String getNasaRequest(String request) {
        LOGGER.log(Level.INFO, "Request to Marvel API...");
        final HttpClient client = HttpClient.newBuilder().build();
        try {
            return client.send(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to Nasa API", e.getCause());
        }

    }
}
