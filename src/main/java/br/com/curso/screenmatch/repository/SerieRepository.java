package br.com.curso.screenmatch.repository;

import br.com.curso.screenmatch.model.Categoria;
import br.com.curso.screenmatch.model.Episodio;
import br.com.curso.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

//    Optional<Serie> findById(Long id);

    Optional<Serie> findByTituloContainingIgnoreCase(String serieTitulo);

    List<Serie> findByElencoContainingIgnoreCase(String nomeArtista);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

//    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer qtdTemporada, Double avaliacao);
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :qtdTemporada AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(Integer qtdTemporada, Double avaliacao);


    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE LOWER(e.titulo) LIKE LOWER(concat('%', :trechoTitulo, '%'))")
    List<Episodio> episodiosPorTrecho(String trechoTitulo);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5" )
    List<Episodio> episodiosTop5(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoEpisodio")
    List<Episodio> episodiosPorAno(Serie serie, int anoEpisodio );

    //LISTA AS 5 SERIES COM EPISODIOS COM DATA DE LANÇAMENTO MAIS RECENTES
    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> listarTop5SeriesEpisodiosRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id")
    List<Episodio> buscarTemporadasPorSerie(Long id);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :idSerie AND e.temporada = :numeroTemporada")
    List<Episodio> obterEpisodiosPorTemporada(Long idSerie, Integer numeroTemporada);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :idSerie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> buscarEpisodiosTop5DaSerie(Long idSerie);
}
