package br.com.curso.screenmatch;

import br.com.curso.screenmatch.model.DadosEpisodio;
import br.com.curso.screenmatch.model.DadosSerie;
import br.com.curso.screenmatch.model.DadosTemporada;
import br.com.curso.screenmatch.service.ConsumoAPI;
import br.com.curso.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoApi = new ConsumoAPI();
		ConverteDados conversor = new ConverteDados();

		// OBTENDO DADOS GERAIS DA SÉRIE
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=vikings&apikey=bf0e95ab");
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		//OBTENDO DADOS DE UM EPISÓDIO
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=vikings&Season=1&Episode=1&apikey=bf0e95ab");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		//OBTENDO DADOS DE UMA TEMPORADA
		List<DadosTemporada> listaDeEpisodios = new ArrayList<>();

		for (int i = 1; i<=dadosSerie.totalTemporadas();i++){
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=vikings&Season="+i+"&apikey=bf0e95ab");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			listaDeEpisodios.add(dadosTemporada);
		}

		for (DadosTemporada temporada: listaDeEpisodios){
			System.out.println(temporada.numedoDaTemporada());
			for (DadosEpisodio episodio: temporada.episodios()){
				System.out.println(episodio);
			}
		}

	}
}
