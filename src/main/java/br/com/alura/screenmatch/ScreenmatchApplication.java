package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    }
}
