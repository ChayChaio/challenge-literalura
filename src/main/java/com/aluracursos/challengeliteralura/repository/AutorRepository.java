package com.aluracursos.challengeliteralura.repository;

import com.aluracursos.challengeliteralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Override
    List<Autor> findAll();

    @Query("SELECT a FROM Autor a WHERE nombre ILIKE %:texto%")
    Optional<Autor> findByNombre(String texto);

    @Query("SELECT a FROM Autor a WHERE nacimiento < :anho AND defuncion > :anho")
    List<Autor> listarAutoresPorAnho(Integer anho);

    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :maximo AND (a.defuncion IS NULL OR a.defuncion >= :minimo)")
    List<Autor> listarAutoresVivosEntreAnhos(Integer minimo, Integer maximo);


}
