package com.immersion.alura.java.exception;

/**
 * Custom Exception HttpRequestService class
 */
public class HttpRequestServiceException extends RuntimeException {

    public HttpRequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
