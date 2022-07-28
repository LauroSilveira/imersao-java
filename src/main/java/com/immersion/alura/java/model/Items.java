package com.immersion.alura.java.model;

import lombok.Builder;

@Builder
public record Items(String resourceURI, String name) {

}
