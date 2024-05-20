package br.com.curso.screenmatch.repository;

import br.com.curso.screenmatch.model.Categoria;
import br.com.curso.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String serieTitulo);

    List<Serie> findByElencoContainingIgnoreCase(String nomeArtista);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);
}
