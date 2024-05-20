package br.com.curso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numero,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String dataLancamento) {

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", avaliacao='" + avaliacao + '\'' +
                ", dataLancamento='" + dataLancamento;
    }
}