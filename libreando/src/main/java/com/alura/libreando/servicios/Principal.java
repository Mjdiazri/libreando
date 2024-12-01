package com.alura.libreando.servicios;


import com.alura.libreando.modelos.*;
import com.alura.libreando.repositorio.AutorBaseRepositorio;
import com.alura.libreando.repositorio.LibroBaseRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import static java.util.stream.Collectors.toList;


@Service
public class Principal{
    private Scanner tecladoUsuario = new Scanner(System.in);
    private String tituloBuscado;
    private String url = "https://gutendex.com/books";
    @Autowired
    private ConsumoApi consumirApi;
    @Autowired
    private ConvertirDatos convertirDatos;
    @Autowired
    private LibroBaseRepositorio libroBaseRepositorio;
    @Autowired
    private  AutorBaseRepositorio autorBaseRepositorio;


    public void mostrarMenu(){
        boolean inicioMenu = false;
        while (inicioMenu != true){

            System.out.println("""
                    ------------------------------------------
                    Seleccione una de las siguientes opciones:
                    1- Consultar libro
                    2- Listar libros Guardados
                    3- Listar autores Guardados
                    4- Listar autores vivos en un año
                    5- Listar libros por idioma
                    0- Salir
                    ------------------------------------------
                    """);

            var eleccionUsuario = tecladoUsuario.nextInt();
            tecladoUsuario.nextLine();


            switch (eleccionUsuario){
                case 1:
                    consultarLibroEnAPi();
                    break;

                case 2:
                    mostrarLibrosGuardados();
                    break;

                case 3:
                    mostrarAutoresGuardados();
                    break;

                case 4:
                    mostrarAutoresVivos();
                    break;

                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Saliendo de la aplicacion");
                    inicioMenu = true;

                default:
                    System.out.println("Opcion No valida");
                    return;
            }

        }
    }



    public void guardarLibroEnBD(ResultadosRecord resultados){
        if(resultados.resultados() != null && !resultados.resultados().isEmpty()){
            LibrosRecord libro = resultados.resultados().get(0);

            List<AutoresBase> autores = libro.autores().stream()
                    .map(autoresRecord -> {
                        AutoresBase autorExistente = autorBaseRepositorio.findByNombreAutor(autoresRecord.nombreAutor());
                        if(autorExistente != null) {
                            System.out.println("Autor Existente: " + autorExistente);
                            return autorExistente;
                        } else {
                            AutoresBase nuevoAutor = new AutoresBase(
                                    autoresRecord.nombreAutor(),
                                    autoresRecord.fechaNacimiento(),
                                    autoresRecord.fechaMuerte()
                            );
                            System.out.println("Guardando autor en repositorio");
                            return autorBaseRepositorio.save(nuevoAutor);
                        }
                    })
                    .toList();

            AutoresBase autor1 = autores.size() > 0 ? autores.get(0) : null;
            AutoresBase autor2 = autores.size() > 1 ? autores.get(1) : null;

            LibrosBase nuevoLibro = new LibrosBase(
                    libro.titulo(),
                    libro.idioma(),
                    libro.numeroDeDescargas(),
                    autor1,
                    autor2
            );

            if(!libroBaseRepositorio.existsByTitulo(nuevoLibro.getTitulo())){
                System.out.println("Guardando en repositorio");
                libroBaseRepositorio.save(nuevoLibro);
            } else {
                System.out.println("Libro ya existe en base de datos");
            }
        } else {
            System.out.println("No hay resultados para guardar en la base de datos");
        }

    }

    //Metodos opciones del Menu
    private void consultarLibroEnAPi(){
        System.out.println("Escribe el nombre del libro que quieres buscar");
        tituloBuscado = tecladoUsuario.nextLine();

        try {
            var json = consumirApi.obtenerDatos(url + "?search=" + tituloBuscado.replace(" ", "+"));
            System.out.println("JSON API: " + json);
            ResultadosRecord resultados = convertirDatos.obtenerDatos(json, ResultadosRecord.class);
            System.out.println("Deserelizando Json a Resultados: " + resultados.resultados());

            if (resultados.resultados() != null && !resultados.resultados().isEmpty()) {
                LibrosRecord libro = resultados.resultados().get(0);

                System.out.println("De ResultadosRecord a LibrosRecord: ");
                System.out.println("Titulo Libro: " + libro.titulo());
                System.out.println("Idioma: " + libro.idioma());
                System.out.println("Total descargas: " + libro.numeroDeDescargas());

                String nombreAutores = libro.autores().stream()
                        .map(AutoresRecord::nombreAutor)
                        .reduce((a,b) -> a + "-" + b)
                        .orElse("No se encontraron autores");
                System.out.println("Autores: " + nombreAutores);

                guardarLibroEnBD(resultados);



            } else {
                System.out.println("No se encontraron resultados para el libro buscado");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
        }



    }





    private void mostrarLibrosGuardados() {
        List<LibrosBase> librosGuardados = libroBaseRepositorio.findAll();

        if (librosGuardados.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }

        librosGuardados.forEach(l -> {
            System.out.println("-----------------------------");
            System.out.println("Libro: " + l.getTitulo());
            System.out.println("Idioma: " + l.getIdiomas());
            System.out.println("Numero de descargas: " + l.getNumeroDeDescargas());

            String autores = l.getAutor1().getNombreAutor();

            if(l.getAutor2() != null ){
                autores += "/" + l.getAutor2().getNombreAutor();
            }
            System.out.println("Autores: " + autores);
            System.out.println("-----------------------------");
            });
        }


    @Transactional
    private void mostrarAutoresGuardados(){
        List<AutoresBase> autoresGuardados = autorBaseRepositorio.findAll();

        if(autoresGuardados.isEmpty()){
            System.out.println("No hay autores guardados en la base de datos");
            return;
        }

        autoresGuardados.forEach(a -> {
            System.out.println("--------------------------");
            System.out.println("Nombre autor: " + a.getNombreAutor());
            System.out.println("Fecha de nacimiento: " + a.getFechaNacimiento());
            if(a.getFechaMuerte() != null){
                System.out.println("Fecha de muerte: " + a.getFechaMuerte());
            } else {
                System.out.println("Sin fecha de muerte");
            }


            if(!a.getLibrosAutor1().isEmpty()){
                System.out.println("Libros: ");
                a.getLibrosAutor1().forEach(l -> {
                    System.out.println("- " + l.getTitulo());
                });
            }

            if(!a.getLibrosAutor2().isEmpty()){
                System.out.println("Libros: ");
                a.getLibrosAutor2().forEach(l-> {
                    System.out.println("- " + l.getTitulo());
                });
            }

            System.out.println("--------------------------");
        });
    }

    private void mostrarAutoresVivos(){
        System.out.println("Indique el año que quiere consultar");
        int añoConsulta = tecladoUsuario.nextInt();
        System.out.println(añoConsulta);
        tecladoUsuario.nextLine();

        List<AutoresBase> autoresVivos = autorBaseRepositorio.autoresVivosPorAño(añoConsulta);
         if(autoresVivos.isEmpty()){
             System.out.println("No se encontraron autores vivos en la fecha ingresada");
             return;
         }

         autoresVivos.forEach(a -> {
             System.out.println("--------------------------");
             System.out.println("Nombre autor: " + a.getNombreAutor());
             System.out.println("Fecha nacimiento: " + a.getFechaNacimiento());
             System.out.println("Fecha de muerte: " + a.getFechaMuerte());
             if(!a.getLibrosAutor1().isEmpty()){
                 System.out.println("Libros: ");
                 a.getLibrosAutor1().forEach(l -> {
                     System.out.println("- " + l.getTitulo());
                 });
             }

             if(!a.getLibrosAutor2().isEmpty()){
                 System.out.println("Libros: ");
                 a.getLibrosAutor2().forEach(l-> {
                     System.out.println("- " + l.getTitulo());
                 });
             }

             System.out.println("--------------------------");

         });
    }


    private void mostrarLibrosPorIdioma() {
        System.out.println("""
                Seleccione el idioma que quiere consultar:
                en- English
                zh- Chino
                """);
        String idiomaUsuario = tecladoUsuario.nextLine();

        List<LibrosBase> libroporIdioma = libroBaseRepositorio.encontrarPorIdioma(idiomaUsuario);

        if(libroporIdioma.isEmpty()){
            System.out.println("Idioma no encontrado");
            return;
        }

        libroporIdioma.forEach(i -> {
            System.out.println("--------------------");
            System.out.println("Titulo: " + i.getTitulo());
            System.out.println("Descargas: " + i.getNumeroDeDescargas());

            String autores = i.getAutor1().getNombreAutor();

            if(i.getAutor2() != null ){
                autores += "/" + i.getAutor2().getNombreAutor();
            }
            System.out.println("Autores: " + autores);
            System.out.println("-----------------------------");
        });
    }
}