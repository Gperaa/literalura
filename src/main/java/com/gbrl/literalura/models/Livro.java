package com.gbrl.literalura.models;

import com.gbrl.literalura.dtos.LivroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer numeroDownloads;

    @ManyToOne
    private Autor autor;

    public Livro() {
    }

    public Livro(LivroDTO livroDTO, Autor autor) {
        this.titulo = livroDTO.titulo();
        this.idioma = Idioma.fromString(livroDTO.idiomas().get(0));
        this.numeroDownloads = livroDTO.numeroDownloads();
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format("""
                Título: %s, Autor: %s, Idioma: %s,  Numero downloads: %d
                """, titulo, autor, idioma, numeroDownloads);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Idioma getIdioma() {
        return idioma;
    }
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }
    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}