package edu.enterprise.spring.services.implementation;

import edu.enterprise.spring.models.Producto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//[69]. Crear el servicio ProductosServiceImplApiExterna para consumir la API externa.
@Slf4j //[71]. Anotación que nos permite manejar de forma abstracta varias implementaciones de  logging como Logback, Log4j, July, etc.
@Service
public class ProductoServiceImplApiExterna {

	public List<Producto> getProductos() {
		// Crea una instancia de RestTemplate. RestTemplate es una clase de Spring que proporciona métodos convenientes para consumir servicios web RESTful.
		RestTemplate restTemplate = new RestTemplate();
		log.info("Se esta construyendo un objeto de la clase ProductosServiceImplApiExterna."); //[71.A]Se agrega un mensaje de log.

		ResponseEntity<List<Producto>> response = restTemplate
				.exchange("http://localhost:8080/enterprise/api/v1/products/fake-productos", // Llama al método exchange() de RestTemplate. Este método se utiliza para consumir la API RESTful.
						HttpMethod.GET, null, new ParameterizedTypeReference<List<Producto>>() {
							// El primer parámetro es la URL de la API que se va a consumir.
							// El segundo parámetro es el método HTTP que se va a utilizar (en este caso, GET).
							// El tercer parámetro es un objeto HttpEntity que puede contener detalles de la solicitud como encabezados y cuerpo. En este caso, es null porque no se necesita para una solicitud GET.
							// El cuarto parámetro es un ParameterizedTypeReference que indica el tipo de objeto que se espera como respuesta. En este caso, se espera una lista de objetos Producto.
						});

		// Obtiene el cuerpo de la respuesta que es una lista de productos.
		List<Producto> productos = response.getBody();

		// Devuelve la lista de productos.
		return productos;
	}





}
