package com.immersion.alura.java.exception;

import java.io.IOException;

/**
 * Custom Exception HttpRequestService class
 */
public class HttpRequestServiceException extends RuntimeException {
    public HttpRequestServiceException() {
    }

    public HttpRequestServiceException(String message) {
        super(message);
    }

    public HttpRequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
