package edu.enterprise.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//[4].Excluye el @SpringBootApplication que es la seguridad de autenticación por el
//momento para poder imprimir el hola mundo
@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class Application {

	@GetMapping("/hello")
	public String saludar() {
		return "Hola Mundo con spring boot";
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
