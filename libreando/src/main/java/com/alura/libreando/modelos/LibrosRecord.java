package com.alura.libreando.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibrosRecord(
        @JsonAlias("title")
        String titulo,
        @JsonAlias("authors")
        List<AutoresRecord> autores,
        @JsonAlias("languages")
        List<String> idioma,
        @JsonAlias("download_count")
        Integer numeroDeDescargas) {}
