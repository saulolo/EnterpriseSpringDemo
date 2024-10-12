package edu.enterprise.spring.services.implementation;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.enterprise.spring.models.Producto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*[61]. Creo esta clase con la lógica para leer dicho Json*/
/* Otro tipo de mecanismo para diferenciar dos o más beans que implementan la misma interfaz es mediante la anotación
@Qualifier. Esta anotación permitirá a Spring resolver la inyección de acuerdo al nombre del bean. Primero debemos asignarle
un nombre, un alias, a nuestros dos servicios.Ej: @Service("JSON"), @Qualifier tiene prioridad sobre @Primary. Tambien se
puede establecer coondicionales de prioridad como la anotación @ConditionalOnProperty y configurarlo en el application.properties.
Este mecanismo es bastante eficaz para aquellos escenarios donde nuestro sistema implementa distintas estrategias para una
misma API.*/
@Primary //Esta anotación la pongo en caso de que tengamos dos servicios con el mismo nombre y a la hora de exponerlo Spring no sepa caul de los dos tomar primero.
@Service
public class ProductoServicesJsonImpl {


	/*Este código es el que carga el archivo JSON de productos desde el classpath del  proyecto y lo deserializa a una
	lista de productos. De este trabajo se encarga la librería Jackson que es el conversor JSON-JAVA y viceversa que
	utiliza por definición Spring. En muchos casos lo hace de forma interna y transparente, como por ejemplo cuando resuelve
	 las peticiones REST.*/
	public List<Producto> getProductos() {
		try {
			// Obtiene un InputStream del archivo productos.json en el classpath de la aplicación
			InputStream is = this.getClass().getResourceAsStream("/productos.json");
			/*Utiliza la biblioteca Jackson para deserializar el contenido del archivo JSON a una lista de objetos Producto.
			ObjectMapper es la clase principal de la biblioteca Jackson y proporciona funcionalidad para leer y escribir JSON,
			ya sea a o desde objetos Java básicos (o POJOs), o a o desde un árbol JSON genérico (JsonNode), o a o desde
			un flujo de bytes o documento JSON.readValue() es un método de ObjectMapper que deserializa el contenido JSON
			del InputStream dado en una lista de objetos Producto. new TypeReference<List<Producto>>(){} se utiliza para
			mantener la información de tipo de la lista de objetos Producto durante la deserialización.*/
			return new ObjectMapper().readValue(is, new TypeReference<List<Producto>>(){});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	//Ahora procedo a crear su respectivo controlador ==> [62].

}
