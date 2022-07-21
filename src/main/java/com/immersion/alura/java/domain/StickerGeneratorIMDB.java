package com.immersion.alura.java.domain;

import com.immersion.alura.java.Application;
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
 * This class implements Delegate Pattern
 */
public class StickerGeneratorIMDB implements StickerGenerator {

    private static final Logger LOGGER = Logger.getLogger(StickerGeneratorIMDB.class.getName());
    public static final String OUTPUT = "src/main/resources/output/";

    @Override
    public void stickerGenerator(final String banner, final String movieTitle, final Double ratingIMBD) {
        LOGGER.log(Level.INFO, "StickerGeneratorIMDB creating Sticker...");
        try {
            //this get the real size of banner
            final String bannerRealSize = banner.substring(0, banner.indexOf("._V1"));
            InputStream inputStream = new URL(bannerRealSize).openStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int newHeight = height + 200;

            BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.drawImage(originalImage, 0, 0, null);

            var font = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 100);
            graphics.setFont(font);
            graphics.setColor(changeTextColorByIMDBRating(ratingIMBD));
            graphics.drawString(movieTitle, 250, newHeight - 60);

            ImageIO.write(newImage, "png", new FileOutputStream(OUTPUT + movieTitle + ".png"));
        } catch (IOException e) {
            throw new StickerGeneratorException("Error to read inputStream", e.getCause());
        }
    }

    private Color changeTextColorByIMDBRating(final Double imDbRating) {
        if (imDbRating > 5) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }
}
