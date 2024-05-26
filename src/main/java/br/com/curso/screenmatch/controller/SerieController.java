package br.com.curso.screenmatch.controller;

import br.com.curso.screenmatch.dto.EpisodioDTO;
import br.com.curso.screenmatch.dto.SerieDTO;
import br.com.curso.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> listarTodasSeries(){
        return serieService.listarSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> listarSeriesTop5(){
        return serieService.listarSeriesTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> listarLancamentos(){
        return serieService.listarSeriesEpisodiosNovos();
    }

    @GetMapping("/{id}")
    public SerieDTO buscarSeriePorId(@PathVariable Long id){
        return serieService.buscarSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> buscarEpisodiosDaSerie(@PathVariable Long id){
        return serieService.buscarEpisodiosDaSerie(id);
    }

    //1/temporadas/1
    @GetMapping("/{id1}/temporadas/{id2}")
    public List<EpisodioDTO> buscarEpisodiosPorTemporada(
            @PathVariable("id1") Long idSerie,
            @PathVariable("id2") Integer numeroTemporada){

        return serieService.buscarEpisodiosPorTemporada(idSerie, numeroTemporada);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> buscarEpisodiosPorTemporada(
            @PathVariable("id") Long idSerie){

        return serieService.buscarEpisodiosTop5DaSerie(idSerie);
    }
}
