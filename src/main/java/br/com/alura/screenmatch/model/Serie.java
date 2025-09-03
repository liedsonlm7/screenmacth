package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private double avaliacao;
    private Categoria genero;
    private String sinopse;
    private String poster;
    private String atores;

    public Serie(SeriesData seriesData) {
        this.titulo = seriesData.titulo();
        this.totalTemporadas = seriesData.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(seriesData.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(seriesData.genero().split(",")[0].trim());
        this.atores = seriesData.atores();
        this.poster = seriesData.poster();
        this.sinopse = ConsultaMyMemory.obterTraducao(seriesData.sinopse()).trim();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    @Override
    public String toString() {
        return  "Gênero: " + genero + '\n' +
                "Nome: " + titulo + '\n' +
                "Temporadas: " + totalTemporadas + '\n' +
                "Avaliação: " + avaliacao + '\n' +
                "Sinopse: " + sinopse + '\n' +
                "Poster: " + poster + '\n' +
                "Atores: " + atores;
    }
}
