package com.aluracursos.challengeliteralura.model;

public enum Idioma {
    ESPANOL("es", "Español"),
    INGLES("en", "Inglés"),
    FRANCES("fr", "Francés"),
    JAPONES("ja", "Japonés"),
    ITALIANO("it", "Italiano"),
    RUSO("ru", "Ruso"),
    PORTUGUESBR("br", "Portugués");

    private String idiomaGut;
    private String idiomaEspanol;
    Idioma (String idiomaGut, String idiomaEspanol){
        this.idiomaGut = idiomaGut;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaGut.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    @Override
    public String toString() {
        return idiomaEspanol;
    }
}
