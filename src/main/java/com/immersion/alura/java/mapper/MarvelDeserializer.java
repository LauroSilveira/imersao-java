package com.immersion.alura.java.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.immersion.alura.java.domain.ProcessData;
import com.immersion.alura.java.exception.HttpRequestServiceException;
import com.immersion.alura.java.model.Result;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class MarvelDeserializer {

  private static final ProcessData processData = new ProcessData();
  private static final ObjectMapper mapper = new ObjectMapper();

  public List<Result> deserialize(String jsonParser) {
    try {

      final List<Result> results = StreamSupport
          .stream(mapper.readTree(jsonParser)
              .get("data")
              .get("results")
              .spliterator(), false)
          .map(node -> Result.builder()
              .name(Optional.ofNullable(node.get("name")).isPresent() ? node.get("name").asText() :
                  node.get("title").asText())
              .path(
                  String.format("%s.%s",
                      node.get("thumbnail").get("path").asText(),
                      node.get("thumbnail").get("extension").asText()))
              .build())
          .toList();

      processData.printItemsMarvelResponse(results);

      return results;
    } catch (IOException e) {
      throw new HttpRequestServiceException("MarvelDeserializer error to deserialize Commics object", e.getCause());
    }

  }
}
