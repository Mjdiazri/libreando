package com.alura.libreando.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores_base")
public class AutoresBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombreAutor;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @OneToMany(mappedBy = "autor1", fetch = FetchType.EAGER)
    private List<LibrosBase> librosAutor1 = new ArrayList<>();

    @OneToMany(mappedBy = "autor2", fetch = FetchType.EAGER)
    private List<LibrosBase> librosAutor2 = new ArrayList<>();



    public AutoresBase() {
    }

    public AutoresBase(String nombreAutor, Integer fechaNacimiento, Integer fechaMuerte) {
        this.nombreAutor = nombreAutor;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaMuerte = fechaMuerte;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<LibrosBase> getLibrosAutor1() {
        return librosAutor1;
    }

    public void setLibrosAutor1(List<LibrosBase> librosAutor1) {
        this.librosAutor1 = librosAutor1;
    }

    public List<LibrosBase> getLibrosAutor2() {
        return librosAutor2;
    }

    public void setLibrosAutor2(List<LibrosBase> librosAutor2) {
        this.librosAutor2 = librosAutor2;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        AutoresBase that = (AutoresBase) obj;
        return nombreAutor != null ? nombreAutor.equals(that.nombreAutor) : that.nombreAutor == null;
    }

    @Override
    public String toString() {
        return "Nombre autor: " + nombreAutor +
                ", Año nacimiento: " + fechaNacimiento +
                ", Año fallecimiento: " + fechaMuerte;
    }
}