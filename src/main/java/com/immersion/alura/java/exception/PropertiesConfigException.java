package com.immersion.alura.java.exception;

/**
 * Custom PropertiesConfig Exception Class
 */
public class PropertiesConfigException extends RuntimeException {

    public PropertiesConfigException() {

    }

    public PropertiesConfigException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
