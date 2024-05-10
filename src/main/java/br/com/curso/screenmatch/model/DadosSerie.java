package br.com.curso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Anotação pra ignorar campos do Json não definidos na classe Record
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
        @JsonAlias ("Title")        String titulo,
        @JsonAlias ("totalSeasons") int totalTemporadas,
        @JsonAlias ("imdbRating")   double avaliacao
) {}
