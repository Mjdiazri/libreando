package com.alura.libreando;

import com.alura.libreando.servicios.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibreandoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreandoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ApplicationContext context) {
		return args -> {
			Principal principal = context.getBean(Principal.class);
			principal.mostrarMenu();
		};
	}


}

