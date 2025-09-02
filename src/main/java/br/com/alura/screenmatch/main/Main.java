package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
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

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = sc.nextLine();
        consumo = new ConsumoApi();
        var json = consumo.getData(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        SeriesData data = convert.getData(json, SeriesData.class);
        System.out.println(data);

        List<SeasonData> temporadas = new ArrayList<>();

        for (int i = 1; i <= data.totalTemporadas(); i++) {
            json = consumo.getData(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = convert.getData(json, SeasonData.class);
            temporadas.add(seasonData);
        }
        temporadas.forEach(System.out::println);

        for (int i = 0; i < data.totalTemporadas(); i++) {
            List<EpisodeData> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<EpisodeData> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        System.out.println("\nTop 5 episódios:");
        dadosEpisodios.stream().
                filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
                .map(d -> new Episode(t.temporada(), d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);


    }
}
