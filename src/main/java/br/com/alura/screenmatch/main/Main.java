package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    }
}
