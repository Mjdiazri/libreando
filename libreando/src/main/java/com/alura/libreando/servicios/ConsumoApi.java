package com.alura.libreando.servicios;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoApi {

    private final HttpClient client;

    public ConsumoApi(){
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    public String obtenerDatos(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200 ){
            throw new RuntimeException("Error en la solicitud: Codigo de estado"+response.statusCode());
        }

        return response.body();

    }


}
