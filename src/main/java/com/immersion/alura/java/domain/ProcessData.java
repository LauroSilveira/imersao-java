package com.immersion.alura.java.domain;

import com.immersion.alura.java.model.Languages;
import com.immersion.alura.java.model.Movies;
import com.immersion.alura.java.model.Result;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class ProcessData {


  /**
   * Print response of JSON of IMDB
   *
   * @param movies
   */
  public void printResponseIMD(final Movies movies) {
    movies.getItems()
        .forEach(dto -> {
          System.out.println("\u001b[1m\u001b[3m\u001b[33mTitle: " + dto.getTitle() + "\u001b[0m");
          System.out.println("\u001b[1mRank: " + dto.getRank() + "\u001b[0m");
          System.out.println(dto.getBanner());
          System.out.println("IMDB Rating: " + roundAndFormatRating(dto.getImDbRating()) + " " +
              Optional.ofNullable(dto.getImDbRating()).orElse(0.0) + "/10" + "\n");
        });
  }

  /**
   * Print response of API deployed on heroku
   *
   * @param languages
   */
  public void printResponseApiLanguage(final List<Languages> languages) {
    languages.forEach(language -> {
      System.out.println("Id: " + language.getId());
      System.out.println("Name: " + language.getName());
      System.out.println("Url: " + language.getUrl());
      System.out.println("Position: " + language.getPosition());
    });

  }

  /**
   * This method round the value of imdbRating for example, if value is 5.3 it will be round to 5.5
   *
   * @param imDbRating
   * @return imdbRating value
   */
  public String roundAndFormatRating(final Double imDbRating) {
    String ratingFormatted = "";
    final BigDecimal bigDecimal = BigDecimal.valueOf(Optional.ofNullable(imDbRating).orElse(1.0))
        .setScale(0, RoundingMode.FLOOR);

    for (int i = 0; i < bigDecimal.intValueExact(); i++) {
      ratingFormatted += "\uD83C\uDF1F";
    }
    return ratingFormatted;
  }

  /**
   * Print the response of Json received from Marvel API
   *
   * @param
   */
  public void printItemsMarvelResponse(final List<Result> results) {
    results.forEach(item -> {
      System.out.println("Comic Name: " + item.name());
      System.out.println("URL: " + item.path());
    });
  }
}
