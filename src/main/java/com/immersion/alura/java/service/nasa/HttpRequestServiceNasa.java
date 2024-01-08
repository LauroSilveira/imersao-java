package com.immersion.alura.java.service.nasa;

import com.immersion.alura.java.exception.HttpRequestServiceException;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class HttpRequestServiceNasa {

    public static HttpRequestServiceNasa getInstance() {
        return new HttpRequestServiceNasa();
    }
    public String getNasaRequest(HttpClient client, String request) {
        log.info("Request to NASA API...");
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
