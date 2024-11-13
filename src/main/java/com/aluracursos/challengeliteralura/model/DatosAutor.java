package com.aluracursos.challengeliteralura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer nacimiento,
        @JsonAlias("death_year") Integer defuncion
) {
    @Override
    public String toString() {
        return  "------- Autor -------" +
                "\nAutor: " + nombre +
                "\nAño de nacimiento: " + nacimiento +
                "\nAño de defunción: " + defuncion;
    }
}
