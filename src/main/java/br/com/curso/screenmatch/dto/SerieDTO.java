package br.com.curso.screenmatch.dto;

import br.com.curso.screenmatch.model.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String elenco,
        String poster,
        String sinopse)
{
}
