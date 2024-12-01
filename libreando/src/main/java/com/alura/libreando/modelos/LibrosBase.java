package com.alura.libreando.modelos;

import jakarta.persistence.*;
import java.util.List;

    @Entity
@Table(name = "libros_base")
public class LibrosBase {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @Column(unique = true)
        private String titulo;


        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "libros_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
        @Column(name = "idioma")
        private List<String> idiomas;
        private Integer numeroDeDescargas;

        @ManyToOne( fetch = FetchType.EAGER)
        @JoinColumn(name = "autor_1_id", referencedColumnName = "id")
        private AutoresBase autor1;

        @ManyToOne
        @JoinColumn(name = "autor_2_id", referencedColumnName = "id")
        private AutoresBase autor2;

        public LibrosBase() {
        }

        public LibrosBase(String titulo, List<String> idiomas, Integer numeroDeDescargas, AutoresBase autor1, AutoresBase autor2) {
            this.titulo = titulo;
            this.idiomas = idiomas;
            this.numeroDeDescargas = numeroDeDescargas;
            this.autor1 = autor1;
            this.autor2 = autor2;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public List<String> getIdiomas() {
            return idiomas;
        }

        public void setIdiomas(List<String> idiomas) {
            this.idiomas = idiomas;
        }

        public Integer getNumeroDeDescargas() {
            return numeroDeDescargas;
        }

        public void setNumeroDeDescargas(Integer numeroDeDescargas) {
            this.numeroDeDescargas = numeroDeDescargas;
        }

        public AutoresBase getAutor1() {
            return autor1;
        }

        public void setAutor1(AutoresBase autor1) {
            this.autor1 = autor1;
        }

        public AutoresBase getAutor2() {
            return autor2;
        }

        public void setAutor2(AutoresBase autor2) {
            this.autor2 = autor2;
        }
    }