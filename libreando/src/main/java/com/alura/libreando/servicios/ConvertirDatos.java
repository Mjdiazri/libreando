package com.alura.libreando.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvertirDatos implements IConvertirDatos{

    private ObjectMapper objetoMapeado = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objetoMapeado.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir los datos " + e.getMessage());
        }
    }
}
