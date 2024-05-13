package edu.enterprise.spring.controllers;

import edu.enterprise.spring.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

//[57]. Clase para manejar el comportamiento de las excepciones lanzadas.
@ControllerAdvice //Indica que esta clase estará destinada a manejar las respuestas de los servicios REST para las excepciones requeridas.
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/* El método “handleBadRequest” manejará la respuesta de los servicios REST cada vez que se lance la excepción
    “BadRequestException”. Esto se logra anotando el método con @ExceptionHandler(BadRequestException).*/
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestap", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("Error", HttpStatus.BAD_REQUEST.toString());

		return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
	}
}

/* En resumen, este método captura una RuntimeException, construye un cuerpo de respuesta personalizado con detalles
sobre la excepción y luego devuelve una respuesta HTTP con el estado BAD_REQUEST.
esto me imprimira un json cuando se lance por ejemplo:
{
	"type": "about:blank",
	"title": "Bad Request",
	"status": 400,
	"detail": "Failed to convert 'id' with value: 'asf'",
	"instance": "/movements/asf"
}
*/
