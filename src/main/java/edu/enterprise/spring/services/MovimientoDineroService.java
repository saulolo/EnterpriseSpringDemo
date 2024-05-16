package edu.enterprise.spring.services;

import edu.enterprise.spring.models.MovimientoDinero;
import edu.enterprise.spring.repositories.MovimientoDineroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Lazy //[63.A]También es necesario anotar @Lazy sobre el bean que queremos qu inicialice con retardo.
@Service //[44].Implementación de los servicios de MovimientoDinero.
public class MovimientoDineroService {

	@Autowired
	private MovimientoDineroRepository movimientoDineroRepository;


	/* GUARDAR O ACTUALIZAR MOVIMIENTO DINERO */
	//Muestra todos los movimientos sin filtros
	public MovimientoDinero saveOrUpdateMovimiento(MovimientoDinero movimientoDinero) {
		return movimientoDineroRepository.save(movimientoDinero);
	}


	/* VER LISTA DE MOVIMIENTO DINERO */
	public List<MovimientoDinero> getAllMovimientoDinero() {
		List<MovimientoDinero> movimientosList = new ArrayList<>();
		movimientosList.addAll(movimientoDineroRepository.findAll()); //Otra forma de reemplaar la ieración con el metodo addAll de la coleccion de ArraysList.
		return movimientosList;
	}


	/* VER MOVIMIENTO DINERO POR ID */
	public Optional<MovimientoDinero> getMoviminetoById(Integer id) {
		return movimientoDineroRepository.findById(id);
	}


	/* ELIMINAR MOVIMIENTO DINERO */
	public boolean deleteMovimiento(Integer id) {
		movimientoDineroRepository.deleteById(id);
		Optional<MovimientoDinero> respuesta = movimientoDineroRepository.findById(id);
		if (respuesta.isPresent()) {
			return false; //"El movimiento de dinero no se elimino";
		}
		return true; //"El movimiento de dinero se elimino con Éxito!!!";
	}


	/*Como voy a realizar Servicios personalizados fueras de los de JpaRepository, con filtros,
	debo de modificar dichas consultas desde movimientoDineroRepository ==> [46]. */

	/* VER MOVIMIENTOS POR EMPELADO */
	//[47].Método para ver las consultas de movimientos por empleado.
	public ArrayList<MovimientoDinero> obtenerPorEmpleado(Integer id) { //Id de los empleados.
		return movimientoDineroRepository.findByEmpleado(id);
	}

	/* VER MOVIMIENTOS POR EMPRESA */
	//[49].Método para ver las consultas de movimientos por empresa deacuerdo a los id de los empleados que las registraron.
	public ArrayList<MovimientoDinero> obtenerPorEmpresa(Integer id) {
		return movimientoDineroRepository.findByEmpresa(id);
	}


	//Procedo a crear los controladores de MovimientoDinero ==> [50].

}
