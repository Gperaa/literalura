package com.gbrl.literalura.models;

public enum Idioma {
    PORTUGUES("pt"),
    INGLES("en"),
    ESPANHOL("es"),
    FRANCES("fr");

    private String sigla;

    Idioma(String sigla) {
        this.sigla = sigla;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.sigla.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para: " + text);
    }
}