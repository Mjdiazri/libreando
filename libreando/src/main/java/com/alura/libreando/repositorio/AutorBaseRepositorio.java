package com.alura.libreando.repositorio;

import com.alura.libreando.modelos.AutoresBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorBaseRepositorio extends JpaRepository<AutoresBase, Long> {

    AutoresBase findByNombreAutor(String nombreAutor);
    Boolean existsByNombreAutor (String nombreAutor);

    @Query("SELECT a FROM AutoresBase a WHERE (a.fechaMuerte IS NULL OR a.fechaMuerte >= :añoConsulta)AND a.fechaNacimiento <= :añoConsulta")
    List<AutoresBase> autoresVivosPorAño(@Param("añoConsulta") int añoConsulta);
}
