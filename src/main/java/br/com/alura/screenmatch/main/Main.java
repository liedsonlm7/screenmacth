package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConvertData convert = new ConvertData();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=339000c9";
    private List<SeriesData> seriesData = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries buscadas
                
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
        seriesData.add(data);
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
        SeriesData seriesData = getSeriesData();
        List<SeasonData> temporadas = new ArrayList<>();

        for (int i = 1; i <= seriesData.totalTemporadas(); i++) {
            var json = consumo.getData(ENDERECO + seriesData.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = convert.getData(json, SeasonData.class);
            temporadas.add(seasonData);
        }
        temporadas.forEach(System.out::println);

    }

    private void listarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = seriesData.stream().map(d -> new Serie(d)).collect(Collectors.toList());
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }
}

