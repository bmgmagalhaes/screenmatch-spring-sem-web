package br.com.curso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(

        @JsonAlias("Title")      String titulo,
        @JsonAlias("imdbRating") String avaliacao,
        @JsonAlias("Released")   String dataDeLancamento
) {
}
