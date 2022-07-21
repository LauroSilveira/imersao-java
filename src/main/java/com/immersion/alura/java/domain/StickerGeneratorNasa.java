package com.immersion.alura.java.domain;

import com.immersion.alura.java.exception.StickerGeneratorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Class implements Delegate Pattern
 */
public class StickerGeneratorNasa implements StickerGenerator {

    public static final String OUTPUT = "src/main/resources/output/";
    private static final Logger LOGGER = Logger.getLogger(StickerGeneratorNasa.class.getName());

    @Override
    public void stickerGenerator(String banner, String movieTitle, Double ratingIMBD) {
        LOGGER.log(Level.INFO, "StickerGeneratorNasa creating Sticker...");
        try {
            InputStream inputStream = new URL(banner).openStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int newHeight = height + 200;

            BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.drawImage(originalImage, 0, 0, null);

            var font = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 100);
            graphics.setFont(font);
            graphics.drawString(movieTitle, 250, newHeight - 60);

            ImageIO.write(newImage, "png", new FileOutputStream(OUTPUT + movieTitle + ".png"));
        } catch (IOException e) {
            throw new StickerGeneratorException("Error to read inputStream", e.getCause());
        }
    }
}
