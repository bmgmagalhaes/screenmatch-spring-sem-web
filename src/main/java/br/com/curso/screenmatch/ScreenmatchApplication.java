package br.com.curso.screenmatch;

import br.com.curso.screenmatch.model.DadosEpisodio;
import br.com.curso.screenmatch.model.DadosSerie;
import br.com.curso.screenmatch.service.ConsumoAPI;
import br.com.curso.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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


	}
}
