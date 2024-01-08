package com.immersion.alura.java.service.marvel;

import com.immersion.alura.java.exception.HttpRequestServiceException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
public class HttpRequestServiceMarvel {

    public static final String URL = "%s?ts=%s&apikey=%s&hash=%s&limit=15";

    public static HttpRequestServiceMarvel getInstance() {
        return new HttpRequestServiceMarvel();
    }

    public String getMarvelRequest(HttpClient client, String request) {
        log.info("Request to Marvel API...");
        Timestamp timestamp = Timestamp.from(Instant.now());
        final var publicKey = System.getenv("PUBLIC_KEY_MARVEL");
        final var privateKey = System.getenv("PRIVATE_KEY_MARVEL");

        var hashSHA256 = generateHashMD5(timestamp, privateKey, publicKey);
        String uri = URL.formatted(request,
                URLEncoder.encode(timestamp.toString(), StandardCharsets.UTF_8), publicKey, hashSHA256);

        try {
            //Http 2.0
            return client.sendAsync(HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .GET()
                            .build(), HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .get();

        } catch (Exception e) {
            throw new HttpRequestServiceException("Cannot do request to Data API", e.getCause());
        }
    }

    private String generateHashMD5(Timestamp timestamp, final String privateKey,
                                   final String publicKey) {
        try {
            String apiKey = timestamp + privateKey + publicKey;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(apiKey.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, hash).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating Hash", e.getCause());
        }
    }
}
