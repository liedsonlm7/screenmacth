package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(@JsonAlias("Title") String titulo,
                          @JsonAlias("Season") Integer temporada,
                          @JsonAlias("Episode") Integer episodio,
                          @JsonAlias("imdbRating") String avaliacao,
                          @JsonAlias("Released") String dataLancamento) {
}
