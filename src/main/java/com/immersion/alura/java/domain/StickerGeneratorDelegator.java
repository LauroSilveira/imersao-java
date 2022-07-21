package com.immersion.alura.java.domain;

/**
 * This class implements Delegate Pattern
 */
public class StickerGeneratorDelegator implements StickerGenerator {
    private final StickerGenerator stickerGenerator;

    public StickerGeneratorDelegator(StickerGenerator stickerGenerator) {
        this.stickerGenerator = stickerGenerator;
    }
    @Override
    public void stickerGenerator(String banner, String movieTitle, Double ratingIMBD) {
        stickerGenerator.stickerGenerator(banner, movieTitle, ratingIMBD);
    }
}
