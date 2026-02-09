package es.ciudadescolar.navidad25;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Navidad25Application implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(Navidad25Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Navidad25Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Lo que antes haciamos en el main ahora se hace aqui
		log.info("Hola Spring Boot");
	}

}
