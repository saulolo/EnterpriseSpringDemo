package edu.enterprise.spring.services.implementation;

import edu.enterprise.spring.models.Producto;
import edu.enterprise.spring.repositories.ProductoRepository;
import edu.enterprise.spring.services.interfaces.IProductoService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//[75]. Implementación de los servicios de Producto.
@Service
public class ProductoServiceImpl implements IProductoService {

	/*[75.A]. Inyecto el repositorio de Producto por constructores y no usando la anotacion @Autowired, esto es una
	buena práctica ya que facilita la implementación de las pruebas unitarias. */
	private final ProductoRepository productoRepository; //Se declara como final para que no pueda ser modificado.
	// productoRepository es una variable de instancia final que se inicializa en el constructor y no se puede modificar después de la inicialización.

	public ProductoServiceImpl(ProductoRepository productoRepository) {  //Se inyecta el repositorio de Producto por constructores.
		this.productoRepository = productoRepository;
	}


	@Override
	@Transactional //[75.B]. Permite realizar transacciones en la base de datos de forma segura, si ocurre un error en la transacción, se realiza un rollback.
	public Producto crearProducto(Producto producto) {
		try {
			Producto productoCreado = Producto.builder() //Utiliza el constructor de la clase Producto generado por Lombok con la anotación @Builder para crear un nuevo objeto Producto
					.nombre(producto.getNombre())
					.descripcion(producto.getDescripcion()) //Copia los valores del producto pasado como parámetro al nuevo objeto productoCreado.
					.precio(producto.getPrecio())
					.stock(producto.getStock())
					.empresa(producto.getEmpresa())
					.categoria(producto.getCategoria())
					.build();
			return productoRepository.save(productoCreado);
		} catch (Exception e) {
			throw new RuntimeException("Error al crear el producto", e);
		}
	}


	/*[75.C] Es una buena practica manejar los métodos capturando excepciones por si falla algo.*/
	/**
	 * Método que permite ver todos los productos.
	 * @return Lista de productos.
	 * @throws RuntimeException Excepción que se lanza si ocurre un error al obtener los productos.
	 * @throws DataAccessException Excepción que se lanza si ocurre un error al acceder a los datos de los productos.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Producto> verProductos() {
		try {
			return productoRepository.findAll();
		} catch (DataAccessException dae) {
			throw new RuntimeException("Error al acceder a los datos del producto ", dae);
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener los productos", e);
		}
	}

	//Me diriho al contrlador para implementar los métodos de la capa de servicios. ==> [76].
}