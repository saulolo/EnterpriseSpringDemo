package edu.enterprise.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//[4].Excluye el @SpringBootApplication que es la seguridad de autenticación por el
//momento para poder imprimir el hola mundo de mi controlador y los otros controladores.
@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
