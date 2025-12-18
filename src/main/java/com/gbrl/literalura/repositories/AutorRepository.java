package com.gbrl.literalura.repositories;

import com.gbrl.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("select a from Autor a where upper(a.nome) = upper(?1)")
    Optional<Autor> findByNomeIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento >= :ano OR a.anoFalecimento IS NULL)")
    List<Autor> autoresVivosNoAno(int ano);

}
