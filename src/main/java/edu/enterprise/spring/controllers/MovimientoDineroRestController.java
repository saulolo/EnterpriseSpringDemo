package edu.enterprise.spring.controllers;

import edu.enterprise.spring.configurations.ConfigurationsParameters;
import edu.enterprise.spring.exceptions.ResourceNotFoundException;
import edu.enterprise.spring.models.MovimientoDinero;
import edu.enterprise.spring.services.impl.MovimientoDineroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


//[50].RestControllers de  movimientoDinero
@RestController
@RequestMapping("/movements")
public class MovimientoDineroRestController {


	@Autowired
	@Lazy //[63]. Ejemplo de lazy: Este mecanismo, en oposición con el prematuro, inicializa el bean (crea una instancia de la clase “MovimientoDineroService”) sólo bajo demanda Es decir, solo cuando se ejecute el endpoint REST que hace uso del servicio
	private MovimientoDineroServiceImpl movimientoDineroService;

	@Autowired //[66]. Inyecto la clase de ConfigurationsParameters donde se setean los valores de los atributos de la clase con los valores indicados en el archivo “application.properties”.
	private ConfigurationsParameters configurationsParameters;



	/* [52]. Continuo trabajando este punto que se enfocará en los códigos de respuesta HTTP que deberán de entrear los
	serviciose REST para el manejo de las convenciones y buenas prácticas. Para implementarlo en Spring Boot, debemos
	apoyarnos en una clase de tipo envoltorio llamada “ResponseEntity” (no es una anotación, es una clase). Esta clase
	deberá estar definida en la sección de retorno situada en la asignatura del método.*/


	/* VER MOVIMIENTO DE DINERO */
	@GetMapping
	public ResponseEntity<?> verMovimientos() {
		//[67]. Ahora agrego una pequeña traza en el método verMovimientos  dentro de este mismo controlador, por el momento lo hare con un println, despues implementaré la traza con un motor log.
		//cuando ejecute el endpoint desde POSTMAN se exhiben por consola los valores de esas claves recuperadas desde el archivo “application.properties”.
		System.out.println("params" + configurationsParameters.toString()) ;
		return ResponseEntity.ok(movimientoDineroService.getAllMovimientoDinero());
	}
	/*
	public ResponseEntity<List<MovimientoDinero>> verMovimientos() {
		return ResponseEntity.ok(movimientoDineroService.getAllMovimientoDinero());
	}
	En primer lugar, este método, ya no retorna una lista de Movimientos de Dinero, sino que en este caso, devolverá
	un objeto del tipo “ResponseEntity” que tendrá información de respuesta, pero además, contendrá la lista de MovimientoDinero.
	Gracias a este objeto, podremos no solo devolver el contenido producido por este servicio sino también, información
	adicional como el código de respuesta HTTP en este caso el método ok. Es por eso que se trata de una clase de tipo “Wrapper” (envoltorio) ya
	que embebe un objeto dentro de ella. Este tipo de dato declarado dentro del segmento genérico puede modificarse para
	ser aún más flexible. Vamos entonces a cambiar el tipo de dato retornado por el “wildcard” <?>. Este “wildcard” permite
	mayor flexibilidad ante un cambio en el cuerpo del método, o seá ya no debemos preocuparnos por definir que tipo de dato
	se retorna por si en un futuro quedemos modificar la firma de nuestro método con un Map, Set, etc.*/


	/* GUARDAR MOVIMIENTO DE DINERO */
	/*Siguiendo las recomendaciones de la tesis REST, los servicios POST de creación deberían retornar el nuevo recurso
	creado (201 CREATED(, pero además se propone también, retornar la URL para acceder a ese nuevo recurso. Es un link
	que apunta al servicio GET “MovimientoDinero by movements”.*/
	@PostMapping
	public ResponseEntity<MovimientoDinero> guardarMovimiento(@RequestBody MovimientoDinero movimiento) {
		MovimientoDinero movimientoGuardado = movimientoDineroService.saveOrUpdateMovimiento(movimiento);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(movimientoGuardado.getId())
				.toUri();
		return ResponseEntity.created(location).body(movimientoGuardado);
	}

	/* Se ha declarado como retorno un “ResponseEntity” que nos permitirá devolver el código 201 (“CREATED”) como se especifica
	 en la última línea. También, con el método “body”, se retorna el recurso creado entero como respuesta de la petición.
	 Fragmento de código al estilo DSL: recupera el path actual de este servicio. Como formalidad, REST propone que cada
	 vez que se crea un recurso, se retorne una respuesta en el HEADER con su ubicación para una futura obtención.
	 Entonces el método “ServletUriComponentsBuilder.fromCurrentRequest()” retorna la locación actual del servicio corriente
	 que es http://localhost:8080/movements. Los métodos subsiguientes “path” y “buildAndExpand” agregan a dicha URL el
	 sufijo con el path variable “id”. Conformando la URL final http://localhost:8080/clientes/{id} y útil para quien
	 consuma el servicio.*/

	/* //GUARDAR MOVIMIENTO DE DINERO (version 2, sin ResponseEntity ni el link de retorno)
	 	@PostMapping
		public MovimientoDinero guardarMovimiento(@RequestBody MovimientoDinero movimiento) {
		return movimientoDineroService.saveOrUpdateMovimiento(movimiento);
	}
	*/


	/* [53]. Códigos de error HTTP: son códigos de respuesta deberán entregar los servicios REST para seguir  las
	convenciones y buenas prácticas en caso de que se produzcan excepciones en la ejecución.
	Modifique el codico anterior (version 3) para generar validaciones con códigos de respuestas HTTP, en el
	caso de que se encuentre alguna ocurrencia, el código entrará en el “if” y responderá con el código de éxito 200 OK.
	De lo contrario la ejecución seguirá y finalmente responderá con el código NOT FOUND 404.*/


	// VER MOVIMIENTO DE DINERO POR ID
	@GetMapping("/{id}")
	public ResponseEntity<?> verMovimientoById(@PathVariable Integer id) {
		Optional<MovimientoDinero> movimientoEncontrado = movimientoDineroService.getMoviminetoById(id);
		if (movimientoEncontrado.isPresent()) {
			return ResponseEntity.ok(movimientoEncontrado.get());
		}
		throw new ResourceNotFoundException("Id no encontrado");
		/* En caso de no encontrar el Id, Lanzo la excepcion personalizada de manera explicita en lugar de construir
		el código NOT FOUND con “ResponseEntity”.*/
	}

	/* Pero para manejar las excepiones de una manera mas profesional creamos el package exceptions para alli manejar
	todas las excepciones personalizadas, para que Spring se encargue de generar el “ResponseEntity.notFound().build()”
	cuando detecte en tiempo de ejecución que se ha lanzado la excepcion “ResourceNotFoundException”. Este comportamiento,
	se logra gracias al decorador @ResponseStatus situado en la parte superior de la clase “ResourceNotFoundException”.==> [54].*/

	/* Ahora voy a personalizar otro tipo de excepción en caso de utilizarla y es uno que tiene que ver con la forma del uso y
	consumo de la petición. Supongamos que determinamos arbitrariamente que el “userName” siempre será válido cuando
	contenga exactamente tres caracteres. Por ejemplo, “arm”, “ald” o “col”. De acuerdo a esta validación, implementaremos
	una solución que lanza una excepcion cuando este usuario está mal formado. Este tipo de excepción está contemplado en
	los códigos HTTP como error 400, que significa “BAD REQUEST”, es decir que la petición está mal formada por el cliente
	que consume el servicio. Vamos a crear entonces la excepción con el nombre “BadRequestException”, por supuesto, dentro
	del paquete “exceptions” ==> [56].*/


	/* En primer se busca el id con el método getMoviminetoById, Luego; el método map(ResponseEntity::ok) transforma el
	MovimientoDinero contenido en el Optional en un ResponseEntity con un estado HTTP 200 (OK). Si el Optional está vacío,
	este método no hace nada. Finalmente, el método orElseThrow(()-> new ResourceNotFoundException("Id no encontrado."))
	se llama en el Optional. Este método lanza una excepción ResourceNotFoundException si el Optional está vacío. Si el
	Optional contiene un valor, este método no hace nada.
	*/
	/*	//MÉTODO IMPLEMENTADO DE MANERA MAS PROFESIONAL CON EL USO DE LAMDAS
	 *//* VER MOVIMIENTO DE DINERO POR ID (version 4 con lamdas) *//*
	@GetMapping("/{id}")
	public ResponseEntity<?> verMovimientoById(@PathVariable Integer id) {
		return movimientoDineroService.getMoviminetoById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResourceNotFoundException("Id no encontrado."));
	}*/



	/*
	//VER MOVIMIENTO DE DINERO POR ID (version 3 sin la verificación de si encuenta o no el Id)
	@GetMapping("/{id}")
	public ResponseEntity<?> verMovimientoById(@PathVariable Integer id) {
		return ResponseEntity.ok(movimientoDineroService.getMoviminetoById(id));
	}
	 */


		//* VER MOVIMIENTO DE DINERO POR ID(version 2 método plano)*/
	/*
		@GetMapping("/{id}")
	public Optional<MovimientoDinero> verMovimientoById(@PathVariable Integer id) {
		return movimientoDineroService.getMoviminetoById(id);
	}
	*/


		/* EDITAR MOVIMIENTO DE DINERO POR ID  */
		@PatchMapping("/{id}") //Actualizo este controlador (Aplica igual para los verbos PUT) para que devuelvas respuestas HTTPS de ResponseEntity generica
		public ResponseEntity<MovimientoDinero> actulizaciónParcialMovimiento (@PathVariable Integer
		id, @RequestBody MovimientoDinero movimiento){
			MovimientoDinero mov = movimientoDineroService.getMoviminetoById(id).get();
			if (mov != null) {
				mov.setConcepto(movimiento.getConcepto());
				mov.setMonto(movimiento.getMonto());
				mov.setUsuario(movimiento.getUsuario());
				return ResponseEntity.ok(movimientoDineroService.saveOrUpdateMovimiento(mov));
			}
			return null;
		}

	/*  EDITAR MOVIMIENTO DE DINERO POR ID (Version 2) sin ResponseEntity
		@PatchMapping("/{id}")
		public MovimientoDinero actulizaciónParcialMovimiento(@PathVariable Integer id, @RequestBody MovimientoDinero movimiento) {
		MovimientoDinero mov = movimientoDineroService.getMoviminetoById(id).get();
		if (mov != null) {
			mov.setConcepto(movimiento.getConcepto());
			mov.setMonto(movimiento.getMonto());
			mov.setUsuario(movimiento.getUsuario());
			return movimientoDineroService.saveOrUpdateMovimiento(mov);
		}
		return null;
	}
	  */


		/* ELIMINAR MOVIMIENTO DE DINERO POR ID  */
		@DeleteMapping("/{id}")
		public ResponseEntity<String> EliminarMovimiento (@PathVariable Integer id){
			boolean respuesta = movimientoDineroService.deleteMovimiento(id);
			if (respuesta) {
				return ResponseEntity.ok("Se eliminó correctamente el movimiento de dinero con Id: " + id);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO se eliminó el movimiento de dinero con Id: " + id);
			//Devuelve un 404 (No encontrado) con un mensaje personalizado en el cuerpo en caso de false.
		}


	/* ELIMINAR MOVIMIENTO DE DINERO POR ID (Version 2 Sin ResponseEntity)
		@DeleteMapping("/{id}")
		public String EliminarMovimiento(@PathVariable Integer id) {
		boolean respuesta = movimientoDineroService.deleteMovimiento(id);
		if (respuesta) {
			return "Se eliminó correctamente el movimiento de dinero con Id: " + id;
		}
		return "NO se eliminó el movimiento de dinero con Id: " + id;
	}
	*/


		/* VER MOVIMIENTO DE DINERO FILTRADO POR EMPELADO  */
		@GetMapping("/{id}/employees")
		public ResponseEntity<?> verMovimientosPorEmpleado (@PathVariable Integer id){
			return ResponseEntity.ok(movimientoDineroService.obtenerPorEmpleado(id));
		}


		/* VER MOVIMIENTO DE DINERO FILTRADO POR EMPRESA */
		@GetMapping("/{id}/enterprises")
		public ResponseEntity<?> verMovimientosPorEmpresa (@PathVariable Integer id){
			return ResponseEntity.ok(movimientoDineroService.obtenerPorEmpresa(id));
		}

		//Voy para La clase 17 y para el punto [51]. aqui me paso a la rama feature/develop, para continuar trabajando con Controller
		//Luego voy a trabajar los códigos de respuestas de los controladores REST de esta misma clase ==> [52].
		//Despues de agregar los ResponseEntity a los métodos planos, procederé a agregar los códigos de error. ==> [53].

	/* Spring propone la creación de una clase Java con el propósito exclusivo de manejar el comportamiento de la aplicación
	para cada una de las excepciones que sean lanzadas durante el consumo de los servicios. La crearé en el paquete de
	controllers, RestResponseEntityExceptionHandler  ==> [57]. */

	/*[58]. Si bien esta app está enfocado en la elaboración de una API REST donde el tipo “media” de dato más transportado
	es el estándar JSON, vamos a ver resumidamente como ejemplo otros tipos de media soportados por Spring que pueden ser
	útiles para algunos casos, ejemplos de estos formaos puden ser PDF, imagenes en PNG, Markdown, etc. En este  ejemplo
	veré: ==> [58.A]. Respuesta HTML y [58.B] Respuesta XML */

	/* VER MOVIMIENTO DE DINERO EN HTML(controlador de ejemplo para ver respuestas en HTML. */
	//58.A]. Método de ejemplo que muestra una Respuesta en formato HTML.(Con datos quemados).
	//El guión para sepapar palabras en la URL se conoce como Hyphen: "/movements-html".
	@GetMapping(value = "/movements-html", produces = MediaType.TEXT_HTML_VALUE) //Anotación que indica que vamos a devolver la respuesta en formato HTML.
	public String getMovimientoDineroHTML() {
		//Se crea un “String” que genera una serie mínima de etiquetas HTML para representar un movimiento.
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append(" <div><h1>Movimiento</h1>");
		sb.append("	 <ul>");
		sb.append("	  <li>Concepto: Papeleria</li>");
		sb.append("	  <li>Monto: 30.000</li>");
		sb.append("	 </ul>");
		sb.append(" </div>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}


	/* VER MOVIMIENTO DE DINERO EN XML(controlador de ejemplo para ver respuestas en XML. */
	//58.B]. Método de ejemplo que muestra una Respuesta en formato XML.(Con datos quemados).
	@GetMapping(value = "/movements-xml", produces = MediaType.APPLICATION_XML_VALUE) //Anotación que indica que vamos a devolver la respuesta en formato XML.
	public String getMovimientoDineroXML() {
		//Se crea un “String” que genera una serie mínima de etiquetas XML para representar un movimiento.
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append(" <Movimiento>");
		sb.append("	  <Concepto>Concepto: Papeleria</Concepto>");
		sb.append("	  <monto>Monto: 30.000</monto>");
		sb.append(" </Movimiento>");
		sb.append("</xml>");

		return sb.toString();
	}

	//De este apartado pasaré a configurar la URL base de la API REST en el aplication.properties ==> [59].

}

