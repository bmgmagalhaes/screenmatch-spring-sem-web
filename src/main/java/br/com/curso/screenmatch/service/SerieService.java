package br.com.curso.screenmatch.service;

import br.com.curso.screenmatch.dto.EpisodioDTO;
import br.com.curso.screenmatch.dto.SerieDTO;
import br.com.curso.screenmatch.model.Episodio;
import br.com.curso.screenmatch.model.Serie;
import br.com.curso.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> listarSeries(){
        return converteListaSerieParaDto(serieRepository.findAll());

    }

    public List<SerieDTO> listarSeriesTop5() {
        return converteListaSerieParaDto(serieRepository.findTop5ByOrderByAvaliacaoDesc());

    }

    public List<SerieDTO> listarSeriesEpisodiosNovos() {

        return converteListaSerieParaDto(serieRepository.listarTop5SeriesEpisodiosRecentes());

    }

    public SerieDTO buscarSeriePorId(Long id) {

        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(),
                    s.getGenero(), s.getElenco(), s.getPoster(), s.getSinopse());
        } else {
            return null;
        }

    }

    //LISTA TODAS TEMPORADAS/EPISÓDIOS E EPISÓDIOS DE UMA SÉRIE
    public List<EpisodioDTO> buscarEpisodiosDaSerie(Long id) {
        return converteListaEpisodioDto(serieRepository.buscarTemporadasPorSerie(id));

    }

    //LISTA TODOS OS EPISÓDIOS DE UMA TEMPORADA DE UMA SÉRIE
    public List<EpisodioDTO> buscarEpisodiosPorTemporada(Long idSerie, Integer numeroTemporada) {

        return converteListaEpisodioDto(serieRepository.obterEpisodiosPorTemporada(idSerie, numeroTemporada));
    }

    //LISTA TODOS OS EPISÓDIOS MAIS BEM AVALIADOS UMA TEMPORADA DE UMA SÉRIE
    public List<EpisodioDTO> buscarEpisodiosTop5DaSerie(Long idSerie) {

        return converteListaEpisodioDto(serieRepository.buscarEpisodiosTop5DaSerie(idSerie));
    }



    //CONVERTE UMA LISTA DE SÉRIES PARA O FORMATO SERIE DTO
    private List<SerieDTO> converteListaSerieParaDto(List<Serie> series) {

        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getElenco(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    //CONVERTE UMA LISTA DE EPISÓDIOS PARA O FORMATO SERIE DTO
    private List<EpisodioDTO> converteListaEpisodioDto(List<Episodio> episodios){

        return episodios.stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

}

