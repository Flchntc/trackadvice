package br.com.alura.trackadvice.principal;

import br.com.alura.trackadvice.model.Artista;
import br.com.alura.trackadvice.model.Musica;
import br.com.alura.trackadvice.model.TipoArtista;
import br.com.alura.trackadvice.repository.TrackRepository;
import br.com.alura.trackadvice.service.ConsultaChatGPT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private List<Artista> dadosArtista = new ArrayList<>();
    private final TrackRepository repositorio;
    private Optional<Artista> buscaArtista;
    public Principal(TrackRepository repositorio) {
        this.repositorio = repositorio;
    }
    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    *** Track Advice Músicas ***
                                        
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                        
                    9- Sair
                    """;

            System.out.println(menu);

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private void cadastrarArtistas() {
        var sair = "S";
        while(sair.equalsIgnoreCase("s")){
            System.out.println("Informe o nome do artista: ");
            var nomeArtista = leitura.nextLine();
            System.out.println("Informe o tipo de artista(solo,dupla ou banda): ");
            var tipoArtista = TipoArtista.valueOf(leitura.nextLine().toUpperCase());

            Artista artista = new Artista(nomeArtista, tipoArtista);
            repositorio.save(artista);

            System.out.println("Deseja cadastrar um novo artista (S/N) ?");
            sair = leitura.nextLine();
        }
    }
    private void cadastrarMusicas() {
        System.out.println("Cadastrar música de que artista? ");
        var nome = leitura.nextLine();

        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);

        if(artista.isPresent()){
            System.out.println("Informe o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else{
            System.out.println("Artista não encontrado");
        }
    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar músicas de que artista? ");
        var nome = leitura.nextLine();
        List<Musica> musicas = repositorio.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void pesquisarDadosDoArtista(){
        System.out.println("Digite um artista para buscar mais informações: ");
        var nome = leitura.nextLine();
        var resposta = ConsultaChatGPT.ConsultaChatGPT(nome);
        System.out.println(resposta.trim());
    }

}
