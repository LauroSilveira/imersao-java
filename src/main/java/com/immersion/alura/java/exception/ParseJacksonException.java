package com.immersion.alura.java.exception;

/**
 * Custom exception for Jackson Parser
 * When Object mapper cannot read a file
 */
public class ParseJacksonException extends RuntimeException {


    public ParseJacksonException() {
    }

    public ParseJacksonException(String message) {
        super(message);
    }

    public ParseJacksonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseJacksonException(Throwable cause) {
        super(cause);
    }
}
