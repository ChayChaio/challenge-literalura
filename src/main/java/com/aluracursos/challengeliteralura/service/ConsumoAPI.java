package com.aluracursos.challengeliteralura.service;

import com.aluracursos.challengeliteralura.model.Autor;
import com.aluracursos.challengeliteralura.model.Libro;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    private static final String URL_BASE = "https://gutendex.com/books?search=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + url))
                .build();
        HttpResponse<String> response;

        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = objectMapper.readTree(response.body());

            JsonNode results = root.get("results");

            if (results.size() > 0) {
                JsonNode book = results.get(0);
                return book.toPrettyString();
            }
            return null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
