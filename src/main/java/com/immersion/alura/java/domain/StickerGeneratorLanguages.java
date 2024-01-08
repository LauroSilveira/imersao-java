package com.immersion.alura.java.domain;

import static java.awt.Transparency.TRANSLUCENT;

import com.immersion.alura.java.exception.StickerGeneratorException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StickerGeneratorLanguages implements StickerGenerator {

    public static final String OUTPUT = "src/main/resources/output/language-api/";

    @Override
    public void stickerGenerator(final String url, final String name, final Double ranking,
                                 final boolean isSubDirectory) {

        log.info("StickerGeneratorLanguages creating Sticker...");

        //create directory of images
        final File directory = new File(OUTPUT);
        if (!directory.exists() && directory.mkdir()) {
            log.warn("Directory API Languages does not exist, it will be create!");
        }
        try {

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
            GlyphVector glyphVector = graphics.getFont()
                    .createGlyphVector(graphics.getFontRenderContext(), name);
            Shape shape = glyphVector.getOutline();

            // activate antialiasing for text rendering
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

            ImageIO.write(newImage, "png", new FileOutputStream(directory.getPath() + "/" + name + ".png"));
            log.info("Finished API Programming languages Sticker process!");
        } catch (IOException e) {
            throw new StickerGeneratorException("Error to read inputStream", e.getCause());
        } catch (NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        }

    }
}
