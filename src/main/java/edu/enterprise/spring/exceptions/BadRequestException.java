package edu.enterprise.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*[56] Clase ResourceNotFoundException de excepcion personalizada */
@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST) //indicará que al lanzarse esta excepcion, se reportara con un código de respuesta 400.
public class BadRequestException extends RuntimeException{

	public BadRequestException(String message) {
		super(message);
	}


	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
