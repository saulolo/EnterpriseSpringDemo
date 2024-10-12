package edu.enterprise.spring.controllers;

import edu.enterprise.spring.enums.ProductoCategoriaEnum;
import edu.enterprise.spring.models.Producto;
import edu.enterprise.spring.services.interfaces.IProductoService;
import jakarta.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//[62][76].RestControllers de Productos
@RestController
@RequestMapping("/products")
public class ProductoRestController {


	private final IProductoService iProductoService;

	public ProductoRestController(IProductoService iProductoService) {
		this.iProductoService = iProductoService;
	}

	/* VER PRODUCTOS JSON */
	@GetMapping(value = "/productos-json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> getProductosJson() {
		Resource resource = new ClassPathResource("/productos.json");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(resource);
	}

	/*[68]. Nuestros proyectos Spring que desarrollaremos no solo ofrecerán unas API’s de servicios, sino que también,
	en muchas ocasiones deberemos obtener información de sistemas internos a la organización, e incluso, sistemas externos.
	Spring Boot ofrece un mecanismo sencillo para consumir estas API’s permitiendo digerirlas fácilmente en nuestros
	objetos java “POJO” (nuestras clases de dominio). “RestTemplate” es la clase más utilizada para consumir servicios
	externos en Spring Boot, pero antes vamos a conformar el contexto. Antes necesitamos “inventar” esa plataforma externa
	que vamos a consumir. Para nuestro caso, haremos una API REST al modo “fake” o “dummy” para poder llevar a cabo nuestra
	tarea. Voy a generar un nuevo endpoint simulando que es una API de un sistema externo para luego consumirla. Entonces,
	situado en la clase “ProductoControllerRest” creo el nuevo método que simulará ser una api externa.*/
	@GetMapping(value = "/fake-productos")
	public ResponseEntity<?> fakeProductosAPI() {
		List<Producto> productos = new ArrayList<>(Arrays.asList(
				new Producto(1L, "Camiseta Juventus", "Camiseta de futbol del Juventus" , 1100.22, 4, null, ProductoCategoriaEnum.MODA),
				new Producto(2L, "Camiseta River Plate", "Camiseta de futbol del River Plate", 1000.4, 8,null, ProductoCategoriaEnum.HOGAR),
				new Producto(3L, "Camiseta Boca Juniors", "Camiseta de futbol del Boca Junior", 900.13, 1, null, ProductoCategoriaEnum.TECNOLOGIA)
		)
		);
		return ResponseEntity.ok(productos);
	}

	/* Ahora creare el servicio ProductosServiceImplApiExterna que implemente la interface “ProductoServiceJson” con el
	nombre “ProductosServiceImplApiExterna” y codificaremos el método “getProductos” que consuma esta API Fake para
	simular el consumo de una API externa. ==> [69]. */


	/**
	 * Crear un producto.
	 * @return Producto creado.
	 * @throws Exception Excepción en caso de error al crear el producto.
	 */
	@PostMapping("/crear")
	public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
		try {
			return new ResponseEntity<>(iProductoService.crearProducto(producto), HttpStatus.CREATED);
			//ResponeEntity es una clase de Spring que representa toda la respuesta HTTP: código de estado, encabezados y cuerpo.
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	/**
	 * Ver todos los productos.
	 * @return Lista de productos.
	 * @throws Exception Excepción en caso de error al obtener los productos.
	 */
	@GetMapping("/ver")
	public ResponseEntity<List<Producto>> verProductos() {
		try {
			return new ResponseEntity<>(iProductoService.verProductos(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

