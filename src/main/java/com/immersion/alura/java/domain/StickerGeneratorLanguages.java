package com.immersion.alura.java.domain;

import com.immersion.alura.java.exception.StickerGeneratorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.Transparency.TRANSLUCENT;

public class StickerGeneratorLanguages implements StickerGenerator {

    public static final String OUTPUT = "src/main/resources/output";
    private static final Logger LOGGER = Logger.getLogger(StickerGeneratorLanguages.class.getName());

    @Override
    public void stickerGenerator(String url, String name, Double ranking) {

        LOGGER.log(Level.INFO, "StickerGeneratorLanguages creating Sticker...");
        try {
            //create directory of images
            final File directory = new File(OUTPUT);


            InputStream inputStream = new URL(url).openStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int newHeight = height + 200;

            BufferedImage newImage = new BufferedImage(width, newHeight, TRANSLUCENT);

            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.drawImage(originalImage, 0, 0, null);

            var font = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 60);
            graphics.setFont(font);

            //Outline String
            GlyphVector glyphVector = graphics.getFont().createGlyphVector(graphics.getFontRenderContext(), name);
            Shape shape = glyphVector.getOutline();

            // activate anti aliasing for text rendering
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            graphics.setStroke(new BasicStroke(11.0f));
            graphics.translate(70, newHeight - 130);
            graphics.setColor(Color.BLACK);
            graphics.draw(shape);
            graphics.setColor(Color.YELLOW);
            graphics.fill(shape);

            if (!directory.exists()) {
                directory.mkdir();
                LOGGER.log(Level.WARNING, "Directory directory does not exist, it will be create!");
            }
            ImageIO.write(newImage, "png", new FileOutputStream(directory.getPath() + "/" + name + ".png"));
            LOGGER.log(Level.INFO, "Finished Sticker process!");
        } catch (IOException e) {
            throw new StickerGeneratorException("Error to read inputStream", e.getCause());
        }

    }
}
