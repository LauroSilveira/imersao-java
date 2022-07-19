package com.immersion.alura.java.domain;

import com.immersion.alura.java.model.Movie;
import com.immersion.alura.java.model.Movies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Optional;

public class ProcessData {

    public void printRequest(Movies movies) {
        movies.getItems()
                .forEach(dto -> {
                    System.out.println("\u001b[1m\u001b[3m\u001b[33mTitle: " + dto.getTitle() + "\u001b[0m");
                    System.out.println("\u001b[1mRank: " + dto.getRank() + "\u001b[0m");
                    System.out.println(dto.getBanner());
                    System.out.println("IMDB Rating: " + roundAndFormatRating(dto.getImDbRating()) + " " +
                            Optional.ofNullable(dto.getImDbRating()).orElse(0.0) + "/10" + "\n");
                });
    }

    public String roundAndFormatRating(Double imDbRating) {
        String ratingFormatted = "";
        final BigDecimal bigDecimal = BigDecimal.valueOf(Optional.ofNullable(imDbRating).orElse(1.0))
                .setScale(0, RoundingMode.FLOOR);

        for (int i = 0; i < bigDecimal.intValueExact(); i++) {
            ratingFormatted += "\uD83C\uDF1F";
        }
        return ratingFormatted;
    }

    public void generatorSticker(final Optional<Movie> movie) {
        if (movie.isPresent()) {
            try {
                //this get the real size of banner
                final String bannerRealSize = movie.get().getBanner().substring(0, movie.get().getBanner().indexOf("._V1"));
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
                graphics.setColor(changeTextColorByIMDBRating(movie.get().getImDbRating()));
                graphics.drawString(movie.get().getTitle(), 250, newHeight - 60);

                ImageIO.write(newImage, "png", new FileOutputStream("src/main/resources/output/"
                        + movie.get().getTitle() + ".png"));
            } catch (IOException e) {
                throw new RuntimeException("Error to read inputStream", e.getCause());
            }

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
