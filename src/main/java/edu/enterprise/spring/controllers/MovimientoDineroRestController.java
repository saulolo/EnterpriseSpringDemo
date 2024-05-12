package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.MovimientoDinero;
import edu.enterprise.spring.services.MovimientoDineroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


//[50].RestControllers de  movimientoDinero
@RestController
@RequestMapping("/movements")
public class MovimientoDineroRestController {


	@Autowired
	MovimientoDineroService movimientoDineroService;



	/* [52]. Continuo trabajando este punto que se enfocará en los códigos de respuesta HTTP que deberán de entrear los
	serviciose REST para el manejo de las convenciones y buenas prácticas. Para implementarlo en Spring Boot, debemos
	apoyarnos en una clase de tipo envoltorio llamada “ResponseEntity” (no es una anotación, es una clase). Esta clase
	deberá estar definida en la sección de retorno situada en la asignatura del método.*/

	/* [53]. Códigos de error HTTP: son códigos de respuesta deberán entregar los servicios REST para seguir  las
	convenciones y buenas prácticas en caso de que se produzcan excepciones en la ejecución.*/

	/* Ver MovimientoDinero */
	@GetMapping
	public ResponseEntity<?> verMovimientos() {
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


	/* Guardar MovimientoDinero */
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

	/* //Guardar MovimientoDinero (version 2, sin ResponseEntity ni el link de retorno)
	 	@PostMapping
		public MovimientoDinero guardarMovimiento(@RequestBody MovimientoDinero movimiento) {
		return movimientoDineroService.saveOrUpdateMovimiento(movimiento);
	}
	*/

	/* Ver MovimientoDinero por ID */
	@GetMapping("/{id}")
	public ResponseEntity<?> verMovimientoById(@PathVariable Integer id) {
		return ResponseEntity.ok(movimientoDineroService.getMoviminetoById(id));
	}

	///* Ver MovimientoDinero por ID (version 2)*/
	/*
		@GetMapping("/{id}")
	public Optional<MovimientoDinero> verMovimientoById(@PathVariable Integer id) {
		return movimientoDineroService.getMoviminetoById(id);
	}
	*/


	/* Editar MovimientoDinero por Id  */
	@PatchMapping("/{id}") //Actualizo este controlador (Aplica igual para los verbos PUT) para que devuelvas respuestas HTTPS de ResponseEntity generica
	public ResponseEntity<MovimientoDinero> actulizaciónParcialMovimiento(@PathVariable Integer id, @RequestBody MovimientoDinero movimiento) {
		MovimientoDinero mov = movimientoDineroService.getMoviminetoById(id).get();
		if (mov != null) {
			mov.setConcepto(movimiento.getConcepto());
			mov.setMonto(movimiento.getMonto());
			mov.setUsuario(movimiento.getUsuario());
			return ResponseEntity.ok(movimientoDineroService.saveOrUpdateMovimiento(mov));
		}
		return null;
	}

	/*  Editar MovimientoDinero por Id (Version 2) sin ResponseEntity
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


	/* Eliminar MovimientoDinero por Id  */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> EliminarMovimiento(@PathVariable Integer id) {
		boolean respuesta = movimientoDineroService.deleteMovimiento(id);
		if (respuesta) {
			return ResponseEntity.ok("Se eliminó correctamente el movimiento de dinero con Id: " + id);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO se eliminó el movimiento de dinero con Id: " + id);
		//Devuelve un 404 (No encontrado) con un mensaje personalizado en el cuerpo en caso de false.
	}


	/* Eliminar MovimientoDinero por Id (Version 2 Sin ResponseEntity)
		@DeleteMapping("/{id}")
		public String EliminarMovimiento(@PathVariable Integer id) {
		boolean respuesta = movimientoDineroService.deleteMovimiento(id);
		if (respuesta) {
			return "Se eliminó correctamente el movimiento de dinero con Id: " + id;
		}
		return "NO se eliminó el movimiento de dinero con Id: " + id;
	}
	*/


	/* Ver MovimientoDinero filtrado por Empleado  */
	@GetMapping("/{id}/employees")
	public ResponseEntity<?> verMovimientosPorEmpleado(@PathVariable Integer id) {
		return ResponseEntity.ok(movimientoDineroService.obtenerPorEmpleado(id));
	}


	/* Ver MovimientoDinero filtrado por Empresa */
	@GetMapping("/{id}/enterprises")
	public ResponseEntity<?> verMovimientosPorEmpresa(@PathVariable Integer id) {
		return ResponseEntity.ok(movimientoDineroService.obtenerPorEmpresa(id));
	}

	//Voy para La clase 17 y para el punto [51]. aqui me paso a la rama feature/develop, para continuar trabajando con Controller
	//Luego voy a trabajar los códigos de respuestas de los controladores REST de esta misma clase ==> [52].
	//Despues de agregar los ResponseEntity a los métodos planos, procedere a agregar los códigos de error. ==> [53].

}
