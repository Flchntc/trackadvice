package br.com.alura.trackadvice;

import br.com.alura.trackadvice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.trackadvice.principal.Principal;

@SpringBootApplication
public class TrackadviceApplication implements CommandLineRunner {

	@Autowired
	private TrackRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(TrackadviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
}
