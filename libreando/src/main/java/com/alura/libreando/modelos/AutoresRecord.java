package com.alura.libreando.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutoresRecord(
        @JsonAlias("name")
        String nombreAutor,
        @JsonAlias("birth_year")
        Integer fechaNacimiento,
        @JsonAlias("death_year")
        Integer fechaMuerte){}
