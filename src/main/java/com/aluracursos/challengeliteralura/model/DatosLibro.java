package com.aluracursos.challengeliteralura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Double cantidadDeDescargas
) {

    @Override
    public String toString() {
        return  "\n------- Libro -------" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autores.get(0).nombre() +
                "\nIdioma: " + Idioma.fromString(idioma.get(0)) +
                "\nCantidad de descargas: " + cantidadDeDescargas +
                "\n-------------------";
    }
}
