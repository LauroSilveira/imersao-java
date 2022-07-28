package com.immersion.alura.java.model;

import java.util.List;
import lombok.Builder;

@Builder
public record Comics(String available, String collectionURI, List<Items> items) {

}
