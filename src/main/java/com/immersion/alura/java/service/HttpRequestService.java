package com.immersion.alura.java.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpRequestService {

    private static final Logger LOGGER = Logger.getLogger(HttpRequestService.class.getName());

    public HttpRequestService() {
    }

    public String getRequestIMDB(String request) {
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
}
