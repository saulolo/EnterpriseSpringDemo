package edu.enterprise.spring.services.interfaces;

import edu.enterprise.spring.models.Producto;

import java.util.List;

/*[74]. Creo una capa de Impl dentro de la capa de servicios donde se implementa la interfaz IProductoService.
Esto con el fin de orquestar los componentes de la Entidad y el repositorio. Es una muy buena practica el tener una capa
de servicios solo donde implmenetamos las interfaces de los metodos a desarrollar, lo hace más ordenada, estable y
desacoplada. Es una buena práctica de programación. Aqui solo declaro el método, no lo implemento.*/
/**
 * Interfaz que define los métodos que permiten realizar las operaciones CRUD de la entidad Producto.
 */
public interface IProductoService {


	/**
	 * Método que permite crear un producto.
	 * @param producto
	 * @return
	 */
	Producto crearProducto(Producto producto);


	/**
	 * Método que permite ver todos los productos.
	 * @return
	 */
	List<Producto> verProductos();



	//Procedo a crear los métodos que me permitirán realizar las operaciones CRUD que implementaran esta interfaz. ==> [75].
}
