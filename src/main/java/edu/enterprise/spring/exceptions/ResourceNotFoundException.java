package edu.enterprise.spring.exceptions; //[54]. Paquete de manejo de excepciones personlizadas.

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*[55] Clase ResourceNotFoundException de excepcion personalizada */
@ResponseBody //indica que esta excepción, al lanzarse desde algún controlador, formará parte del cuerpo de respuesta de la petición.
@ResponseStatus(HttpStatus.NOT_FOUND) //indicará que al lanzarse esta excepcion, se reportara con un código de respuesta 404. Esto significa que Spring creará una “ResponseEntity” por nosotros al detectar el lanzamiento de esta excepción.
public class ResourceNotFoundException extends RuntimeException{

	//Constructor que acpeta un mensaje de tipo String para proporcionar un mensaje personalizado que describe el error.
	//Este metodo es util para cuando busque un objeto en la BD por su id y no exista.
	public ResourceNotFoundException(String message) {
		super(message);
	}

	/* Método sobreescrito de la clase RuntimeException ys e utiliza para obtener el mensaje de error de la excepción.*/
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
