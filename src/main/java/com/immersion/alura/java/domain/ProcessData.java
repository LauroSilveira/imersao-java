package com.immersion.alura.java.domain;

import com.immersion.alura.java.model.Movies;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


}
