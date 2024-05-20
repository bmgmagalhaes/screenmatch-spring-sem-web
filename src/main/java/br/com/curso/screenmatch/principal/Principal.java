package br.com.curso.screenmatch.principal;

import br.com.curso.screenmatch.model.DadosSerie;
import br.com.curso.screenmatch.model.DadosTemporada;
import br.com.curso.screenmatch.model.Episodio;
import br.com.curso.screenmatch.model.Serie;
import br.com.curso.screenmatch.repository.SerieRepository;
import br.com.curso.screenmatch.service.ConsumoAPI;
import br.com.curso.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=bf0e95ab";

    private Scanner leitor = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
//    private List<DadosSerie> listaDeSeries = new ArrayList<>();
    private SerieRepository serieRepository;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository serieRepositorypository) {
        this.serieRepository = serieRepositorypository;
    }


    public void exibeMenu(){

        int opcao = -1;
        var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries selecionadas
                    0 - Sair
                    """;
        while (opcao!=0) {
            System.out.println(menu);

            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesSelecionadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }


        //LISTANDO OS EPISÓDIOS DA SÉRIE COM O NÚMERO DA TEMPORADA
//        List<Episodio> episodios = listaTemporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(e -> new Episodio(t.numero(), e))
//                ).collect(Collectors.toList());

//        episodios.forEach(System.out::println);




        // LISTAR EPISÓDIOS A PARTIR DE UM ANO
//        System.out.print("Quer listar episódios a partir de que ano? ");
//        var ano = leitor.nextInt();
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(episodio -> System.out.println(
//                        "Temporada: "+episodio.getTemporada()+
//                            ", Episódio: "+episodio.getTitulo()+
//                            ", Data: "+episodio.getDataLancamento().format(formatador)+
//                            ", Nota: "+episodio.getAvaliacao()));




//        BUSCA EPISÓDIO POR TÍTULO
//        System.out.print("Informe o título do episódio: ");
//        var tituloBuscado = leitor.nextLine();
//
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(episodio -> episodio.getTitulo().toUpperCase().contains(tituloBuscado.toUpperCase()))
//                .findFirst();
//
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episódio localizado: ");
//            System.out.println("Temporada "+episodioBuscado.get().getTemporada());
//            System.out.println("Lançado em "+episodioBuscado.get().getDataLancamento());
//        } else {
//            System.out.println("Episódio não encontrado");
//        }


//        //AVALIACAO MÉDIA POR TEMPORADA
//        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
//                .filter(episodio -> episodio.getAvaliacao() > 0)
//                .collect(Collectors.groupingBy(
//                        Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)
//                ));
//        System.out.println(avaliacaoPorTemporada);
//
//
//
//        //ANALISE ESTATÍSTICA DA SERIE
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(episodio -> episodio.getAvaliacao()>0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//
//        System.out.println("Média: " + est.getAverage());
//
//        var piorNota = est.getMin();
//        var melhorNota = est.getMax();
//
//        Optional<Episodio> melhorAvaliacao = episodios.stream()
//                .filter(e -> e.getAvaliacao()== melhorNota)
//                .collect(Collectors.toSet()).stream()
//                .findAny();
//        System.out.println("Melhor episódio: " +melhorAvaliacao.get().getTitulo() +
//                " (Nota: " + melhorNota+") - [TEMPORADA: "+ melhorAvaliacao.get().getTemporada()  +"]");
//
//
//        Optional<Episodio> piorAvaliacao = episodios.stream()
//                .filter(e -> e.getAvaliacao()== piorNota)
//                .collect(Collectors.toSet()).stream()
//                .findAny();
//
//        System.out.println("Pior episódio: " + piorAvaliacao.get().getTitulo() +
//                " (Nota: " + piorNota+") - [TEMPORADA: "+ piorAvaliacao.get().getTemporada()  +"]");
//
//        System.out.println("Quantidade: " + est.getCount());
    }

    private void listarSeriesSelecionadas() {

        series = serieRepository.findAll();
        series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);

    }


    private void buscarEpisodioPorSerie() {

        listarSeriesSelecionadas();

        System.out.print("Informe o nome da série: ");
        var seriePesquisada = leitor.nextLine().toLowerCase();

        Optional<Serie> serieBuscada = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(seriePesquisada))
                .findFirst();

        if (serieBuscada.isPresent()) {

            var serieEncontrada = serieBuscada.get();
            var serieTitulo = serieEncontrada.getTitulo().toLowerCase().replace(" ","+");
            List<DadosTemporada> temporadas = new ArrayList<>();

            //OBTENDO DADOS DOS EPISÓDIOS DE CADA TEMPORADA
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoAPI.obterDados(ENDERECO + serieTitulo + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            List<Episodio> listaDeEpisodios = temporadas.stream()
                                                    .flatMap(d->d.episodios().stream()
                                                            .map(e-> new Episodio(d.numero(),e)))
                                                            .collect(Collectors.toList());
            serieEncontrada.setEpisodios(listaDeEpisodios);
            serieRepository.save(serieEncontrada);

            System.out.println(temporadas);



        } else {
            System.out.println("Nenhuma série salva com esse nome");
        }


    }

    private void buscarSerieWeb() {
        DadosSerie dadosSerie = getDadosSerie();
        Serie serie = new Serie(dadosSerie);
        serieRepository.save(serie);
        System.out.println(dadosSerie);
    }

    private DadosSerie getDadosSerie() {
        System.out.print("Informe a série: ");
        var serie = leitor.nextLine().replace(" ", "+");


        // OBTENDO DADOS GERAIS DA SÉRIE
        var json = consumoAPI.obterDados(ENDERECO+serie+API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        return dadosSerie;

    }

}
