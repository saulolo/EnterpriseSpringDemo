package edu.enterprise.spring;

import edu.enterprise.spring.models.Empresa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//[5]. indica que esta clase Java oficiara de controlador y expondrá varios endpoints de
// tipo REST
@RestController
//[4].Excluye el @SpringBootApplication que es la seguridad de autenticación por el
//momento para poder imprimir el hola mundo
@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class Application {

	//[6].Para lograr que el método “saludar” sea un servicio REST, tendremos que decorarlo
	// con dicha anotación, y entre parentesis el nombre del servicio con el cual lo quiero
	// exponer. Con esta anotación indicamos que este método es un servicio REST que se
	// expondrá mediante el método HTTP GET, que es el método HTTP más frecuente.
	@GetMapping("/hello")
	public String saludar() {
		return "Hola Mundo con spring boot";
	}

	//[12].Creo un servicio para probar los métodos de la clase Empresa.
	@GetMapping("/test")
	public String test() {
		Empresa emp = new Empresa("SOLAR SAS", "Calle la geta", "313313313", "1234567");
		emp.setNombre("SOLAR LTDA");
		return ("ID: " + emp.getId() + " Nombre: " + emp.getNombre() + " NIT: " + emp.getNIT());
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
