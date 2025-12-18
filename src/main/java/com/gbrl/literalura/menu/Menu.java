package com.gbrl.literalura.menu;

import com.gbrl.literalura.dtos.AutorDTO;
import com.gbrl.literalura.dtos.LivroDTO;
import com.gbrl.literalura.dtos.ResultadoDTO;
import com.gbrl.literalura.models.Autor;
import com.gbrl.literalura.models.Idioma;
import com.gbrl.literalura.models.Livro;
import com.gbrl.literalura.repositories.AutorRepository;
import com.gbrl.literalura.repositories.LivroRepository;
import com.gbrl.literalura.servicos.ConsumoAPI;
import com.gbrl.literalura.servicos.ConversorDados;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConversorDados conversor = new ConversorDados();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Menu(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                                  ESCOLHA UMA OPÇÃO:
                    1 -> BUSCAR LIVRO PELO TÍTULO
                    2 -> LISTAR LIVROS REGISTRADOS
                    3 -> LISTAR AUTORES REGISTRADOS
                    4 -> LISTAR AUTORES VIVOS EM UM DETERMINADO ANO
                    5 -> LISTAR LIVROS EM UM DETERMINADO IDIOMA
                    0 -> SAIR
                    -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                    """);

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Aplicação encerrada");
                    break;
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                default:
                    System.out.println("Insira uma opção válida.");
                    break;
            }
        }

    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                SELECIONE UM IDIOMA PARA BUSCAR LIVROS:
                es - Espanhol
                en - Inglês
                fr - Francês
                pt - Português
                """);
        var idiomaBusca = scanner.nextLine();

        try {
            // CORREÇÃO AQUI: Use fromString em vez de valueOf
            Idioma idioma = Idioma.fromString(idiomaBusca);

            List<Livro> livros = livroRepository.findByIdioma(idioma);
            if (livros.isEmpty()) {
                System.out.println("Não existem livros nesse idioma no banco de dados.");
            } else {
                livros.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma inválido! Por favor, digite uma das siglas acima.");
        }
    }

    private void listarAutoresVivos() {
        System.out.print("Digite o ano para buscar o autor: ");
        int anoBusca = scanner.nextInt();
        scanner.nextLine();

        List<Autor> autores = autorRepository.autoresVivosNoAno(anoBusca);
        if (autores.isEmpty()) {
            System.out.println("NENHUM AUTOR REGISTRADO FOI ENCONTRADO");
        } else  {
            for (Autor autor : autores) {
                System.out.println(autor.getNome());
            }
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.print("TÍTULO DE LIVRO PARA A BUSCA: ");
        String livroBuscado = scanner.nextLine();

        String json = consumo.getJson(
                URL_BASE + livroBuscado.trim().toLowerCase().replaceAll(" ", "%20")
        );

         ResultadoDTO resultadoBusca = conversor.converteDados(json, ResultadoDTO.class);

        if (resultadoBusca != null && !resultadoBusca.livros().isEmpty()) {
            LivroDTO livroDTO = resultadoBusca.livros().get(0);
            AutorDTO autorDTO = livroDTO.autores().get(0);

            Autor autor = autorRepository.findByNomeIgnoreCase(autorDTO.nome())
                    .orElseGet(() -> autorRepository.save(new Autor(autorDTO)));

            Livro livro = new Livro(livroDTO, autor);
            try {
                livroRepository.save(livro);
                System.out.println(livro);
            } catch (Exception e) {
                System.out.println("ERRO AO CADASTRAR LIVRO");
            }
        } else {
            System.out.println("LIVRO NÃO ENCONTRADO");
        }

    }

}
























