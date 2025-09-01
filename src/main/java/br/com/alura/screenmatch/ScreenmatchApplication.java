package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoApi = new ConsumoApi();
        var json = consumoApi.getData("http://www.omdbapi.com/?t=peaky+blinders&apikey=339000c9");
        System.out.println(json);
        ConvertData convert = new ConvertData();
        SeriesData data = convert.getData(json, SeriesData.class);
        System.out.println(data);

        json = consumoApi.getData("https://www.omdbapi.com/?t=peaky+blinders&season=5&episode=1&apikey=339000c9");
        EpisodeData episode = convert.getData(json, EpisodeData.class);
        System.out.println(episode);

        List<SeasonData> temporadas = new ArrayList<>();

        for (int i = 1; i <= data.totalTemporadas(); i++) {
            json = consumoApi.getData("https://www.omdbapi.com/?t=peaky+blinders&season=" + i + "&apikey=339000c9");
            SeasonData seasonData = convert.getData(json, SeasonData.class);
            temporadas.add(seasonData);
        }
        temporadas.forEach(System.out::println);
    }
}
