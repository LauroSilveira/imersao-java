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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

/**
 * This Class implements Delegate Pattern
 */
@Slf4j
public class StickerGeneratorNasa implements StickerGenerator {

  public static final String OUTPUT = "src/main/resources/output/nasa/";

  @Override
  public void stickerGenerator(String url, String name, Double ranking,
      final boolean isSubDirectory) {
    log.info("StickerGeneratorNasa creating Sticker...");
    try {
      //create directory of images
      final File directory = new File(OUTPUT);

      if (!directory.exists() && directory.mkdirs()) {
        log.warn("Directory NASA does not exist, it will be create!");
      }

      InputStream inputStream = new URL(url).openStream();
      BufferedImage originalImage = ImageIO.read(inputStream);

      int width = originalImage.getWidth();
      int height = originalImage.getHeight();
      int newHeight = height + 200;

      BufferedImage newImage = new BufferedImage(width, newHeight, TRANSLUCENT);

      Graphics2D graphics = (Graphics2D) newImage.getGraphics();
      graphics.drawImage(originalImage, 0, 0, null);

      var font = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 90);
      graphics.setFont(font);

      //Outline String
      GlyphVector glyphVector = graphics.getFont()
          .createGlyphVector(graphics.getFontRenderContext(), name);
      Shape shape = glyphVector.getOutline();

      // activate anti aliasing for text rendering
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
          RenderingHints.VALUE_RENDER_QUALITY);

      graphics.setStroke(new BasicStroke(15.0f));
      graphics.translate(280, newHeight - 30);
      graphics.setColor(Color.BLACK);
      graphics.draw(shape);
      graphics.setColor(Color.YELLOW);
      graphics.fill(shape);

      ImageIO.write(newImage, "png", new FileOutputStream(OUTPUT + name + ".png"));
      log.info("Finished NASA Sticker process!");
    } catch (IOException e) {
      throw new StickerGeneratorException("Error to read inputStream", e.getCause());
    }
  }
}
