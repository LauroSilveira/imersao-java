package com.immersion.alura.java.domain;

import com.immersion.alura.java.exception.StickerGeneratorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.Transparency.TRANSLUCENT;

/**
 * This class implements Delegate Pattern
 */
public class StickerGeneratorIMDB implements StickerGenerator {

    private static final Logger LOGGER = Logger.getLogger(StickerGeneratorIMDB.class.getName());
    public static final String OUTPUT = "src/main/resources/output";

    @Override
    public void stickerGenerator(final String url, final String name, final Double ranking) {
        LOGGER.log(Level.INFO, "StickerGeneratorIMDB creating Sticker...");
        try {
            //create directory of images
            final File directory = new File(OUTPUT);

            //this get the real size of banner
            final String bannerRealSize = url.substring(0, url.indexOf("._V1"));
            InputStream inputStream = new URL(bannerRealSize).openStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int newHeight = height + 200;

            BufferedImage newImage = new BufferedImage(width, newHeight, TRANSLUCENT);

            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.drawImage(originalImage, 0, 0, null);

            var font = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 100);
            graphics.setFont(font);
            graphics.setColor(changeTextColorByIMDBRating(ranking));

            //Outline String
            GlyphVector glyphVector = graphics.getFont().createGlyphVector(graphics.getFontRenderContext(), name);
            Shape shape = glyphVector.getOutline();

            // activate anti aliasing for text rendering
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            graphics.setStroke(new BasicStroke(15.0f));
            graphics.translate(308, newHeight - 30);
            graphics.setColor(Color.BLACK);
            graphics.draw(shape);
            graphics.setColor(Color.CYAN);
            graphics.fill(shape);

            if (!directory.exists()) {
                directory.mkdir();
                LOGGER.log(Level.WARNING, "Directory directory does not exist, it will be create!");
            }
            ImageIO.write(newImage, "png", new File(directory.getPath() + "/" + name + ".png"));
            LOGGER.log(Level.INFO, "Finished Sticker process!");
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
