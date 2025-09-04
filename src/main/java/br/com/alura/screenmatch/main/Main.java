package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConvertData convert = new ConvertData();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=339000c9";
    private List<SeriesData> seriesData = new ArrayList<>();

    private SerieRepository repository;

    private List<Serie> series = new ArrayList<>();

    public Main(SerieRepository repository) {
        this.repository = repository;
    }


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries buscadas
                4 - Buscar série por titulo
                5 - Buscar séries por ator
                
                0 - Sair
                """;

            System.out.println(menu);
            System.out.println("Digite a opção desejada: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarSeriesWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void buscarSeriesWeb() {
        SeriesData data = getSeriesData();
        Serie serie = new Serie(data);
        // seriesData.add(data);
        repository.save(serie);
        System.out.println(data);
    }

    private SeriesData getSeriesData() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = sc.nextLine();

        var json = consumo.getData(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        SeriesData data = convert.getData(json, SeriesData.class);
        System.out.println("\nSeriés encontradas: ");
        return data;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = sc.nextLine();
        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<SeasonData> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.getData(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData seasonData = convert.getData(json, SeasonData.class);
                temporadas.add(seasonData);
            }
            temporadas.forEach(System.out::println);

            List<Episode> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream().map(e -> new Episode(d.temporada(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada");
        }


    }

    private void listarSeriesBuscadas() {
        series = repository.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = sc.nextLine();

        Optional<Serie> serieBuscada = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Dados da série: " + serieBuscada.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Qual o nome para busca? ");
        var nomeAtor = sc.nextLine();
        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = sc.nextDouble();
        List<Serie> seriesEncontradas = repository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " - Avaliação: " + s.getAvaliacao()));
    }
}

