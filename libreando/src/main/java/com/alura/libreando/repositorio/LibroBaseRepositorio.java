package com.alura.libreando.repositorio;

import com.alura.libreando.modelos.LibrosBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LibroBaseRepositorio extends JpaRepository<LibrosBase, Long> {

    boolean existsByTitulo(String titulo);

    @Query("SELECT l FROM LibrosBase l WHERE :idiomaUsuario MEMBER OF l.idiomas")
    List<LibrosBase> encontrarPorIdioma(@Param("idiomaUsuario") String idiomaUsuario);
}
