package br.com.curso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(
        @JsonAlias("Season")   int numedoDaTemporada,
        @JsonAlias("Episodes") List<DadosEpisodio> episodios

) {
}
