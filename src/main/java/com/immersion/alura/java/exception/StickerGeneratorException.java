package com.immersion.alura.java.exception;

/**
 * Custom StickerGeneratorException class
 * when occurs an error generating sticker
 */
public class StickerGeneratorException extends RuntimeException {
    public StickerGeneratorException() {
    }

    public StickerGeneratorException(String message) {
        super(message);
    }

    public StickerGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
