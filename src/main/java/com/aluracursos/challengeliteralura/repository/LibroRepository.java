package com.aluracursos.challengeliteralura.repository;


import com.aluracursos.challengeliteralura.model.Idioma;
import com.aluracursos.challengeliteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Override
    List<Libro> findAll();

    @Query("SELECT l FROM Libro l WHERE l.autor.id = :autorId")
    List<Libro> findLibrosByAutorId(@Param("autorId") Long autorId);

    List<Libro> findByIdioma(Idioma idioma);

    @Query("SELECT l FROM Libro l ORDER BY cantidadDeDescargas DESC LIMIT 5")
    List<Libro> findTopCinco();

    @Query("SELECT l FROM Libro l WHERE titulo ILIKE %:texto%")
    Optional<Libro> findByTitulo(@Param("texto") String texto);

}
