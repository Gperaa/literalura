package com.gbrl.literalura.repositories;

import com.gbrl.literalura.models.Idioma;
import com.gbrl.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("select l from Livro l where upper(l.titulo) = upper(?1)")
    Optional<Livro> findByTituloIgnoreCase(String titulo);

    @Query("select l from Livro l where l.idioma = ?1")
    List<Livro> findByIdioma(Idioma idioma);

}
