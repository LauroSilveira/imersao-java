package com.immersion.alura.java.service.imdb;

import com.immersion.alura.java.exception.HttpRequestServiceException;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class HttpRequestServiceIMDB {

    public static HttpRequestServiceIMDB getInstance() {
        return new HttpRequestServiceIMDB();
    }

    public String getRequestIMDB(HttpClient client, String request) {
        log.info("Request to IMDB API");

        try {
            //Asynchronous HTTP request
            return client.sendAsync(HttpRequest.newBuilder()
                            .uri(URI.create(request))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .whenComplete(((success, throwable) -> success.body()))
                    .thenApply(HttpResponse::body)
                    .get();
        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to IMDB API", e.getCause());
        }
    }


}
