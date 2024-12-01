package com.alura.libreando.servicios;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class <T> clase);
}
