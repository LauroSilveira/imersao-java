package com.immersion.alura.java.domain;

import static java.awt.Transparency.TRANSLUCENT;

import com.immersion.alura.java.exception.StickerGeneratorException;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StickerGeneratorMarvel implements StickerGenerator {

  public static final String OUTPUT_CHARACTERS = "src/main/resources/output/marvel/characters";
  public static final String OUTPUT_COMICS = "src/main/resources/output/marvel/comics";

  @Override
  public void stickerGenerator(final String url, final String name, final Double ranking,
      final boolean isSubDirectory) {
    log.info("StickerGeneratorMarvel creating Sticker...");
    try {
      //create directory of images
      File directory = null;

      if (isSubDirectory) {
        directory = new File(OUTPUT_COMICS);
      } else {
        directory = new File(OUTPUT_CHARACTERS);
      }

      if (!directory.exists() && directory.mkdirs()) {
        log.warn("Directory Characters does not exist, it will be create!");
      }

      InputStream inputStream = new URL(url).openStream();
      BufferedImage originalImage = ImageIO.read(inputStream);

      final int width = originalImage.getWidth();
      final int height = originalImage.getHeight();
      final int newHeight = height + 200;

      final BufferedImage newImage = new BufferedImage(width, newHeight, TRANSLUCENT);

      Graphics2D graphics = (Graphics2D) newImage.getGraphics();
      graphics.drawImage(originalImage, 0, 0, null);

      var font = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 60);
      graphics.setFont(font);

      ImageIO.write(newImage, "png", new FileOutputStream(directory.getPath().concat("/") + name + ".png"));
      log.info("Sticker process Finished!");
    } catch (IOException e) {
      throw new StickerGeneratorException("Error to read inputStream", e.getCause());
    }

  }
}
