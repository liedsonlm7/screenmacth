package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

//        System.out.println("\nTop 10 episódios:");
//        dadosEpisodios.stream().
//                filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A)" + e))
//                .sorted(Comparator.comparing(EpisodeData::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação" + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episode> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
                .map(d -> new Episode(t.temporada(), d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Qual episodio deseja buscar? ");
//        var trechoTitulo = sc.nextLine();
//        Optional<Episode> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()) {
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        } else {
//            System.out.println("Episódio não encontrado!");
//        }


//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream().filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca)).
//                forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episódio: " + e.getTitulo() +
//                                " Data Lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episode::getTemporada,
                        Collectors.averagingDouble(Episode::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);


    }
}
