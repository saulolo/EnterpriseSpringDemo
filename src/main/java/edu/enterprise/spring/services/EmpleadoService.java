package edu.enterprise.spring.services;

import edu.enterprise.spring.models.Empleado;
import edu.enterprise.spring.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//[38].Implementación de los servicios de Empleado.
/**
 * Clase que representa un servicio para manejar operaciones relacionadas con los empleados.
 * Esta clase se encarga de interactuar con el repositorio de empleados para realizar operaciones como
 * guardar, actualizar, eliminar y recuperar empleados.
 */
@Service
public class EmpleadoService {

	@Autowired
	EmpleadoRepository empleadoRepository;


	/* GUARDAR EMPLEADOS */
	/**
	 * Crea o actualiza un Empleado en la BD.
	 * @param empleado El objeto Empleado a guardar o actualizar.
	 * @return El objeto Empleado guardado o actualizado en la BD .
	 */
	public Empleado saveOrUpdateEmpleado(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}


	/* VER EMPLEADOS */
	/**
	 * Obtiene la lista de todos los empleados almacenados en la BD.
	 * @return Una lista que contiene todos los empleados almacenados en la BD.
	 */
	public List<Empleado> getAllEmpleados() {
		List<Empleado> empleadoList = new ArrayList<>();
		for (Empleado empleado : empleadoRepository.findAll()) { //El método findAll me devuelve un iterable y para recorrer cada uno de sus elementos:
			empleadoList.add(empleado);  //lo recorro con un bucle, en este caso el bucle foreach.
		}
		return empleadoList;
	}


	/* VER EMPLEADO POR ID */
	/* (Otro Método pero usando [Optional]): EL Optional me sirve para que me regrese cualquier cosa, por ejemplo si mi método me retorna Integer y hace una consulta a la BD
	y en un campo se encuentra con un String, él va a reventar, usando el Optional evito esta situación, es como decirle,
	regreseme a ver que encuentra. Por buenas practicas es mejor usar esta clase por los siguientes beneficios:
	 1. Seguridad contra valores nulos: Evita errores de NullPointerException al evitar valores nulos.
	 2. Claridad en la intención: Indica explícitamente que el valor de retorno puede estar presente o ausente.
	 3. Manejo explícito de la ausencia de valor: Obliga a los desarrolladores a decidir cómo manejar la ausencia de valor, en lugar de asumir que siempre estará presente.
	 4. Facilita la programación funcional: Proporciona métodos útiles para operaciones funcionales como transformación, filtrado y validación en los valores contenidos.
	 */
	/**
	 * Obtiene el empleado de la BD por su Id.
	 * @param id El Id del empleado que deseo obtener de la BD.
	 * @return EL empleado de la BD segun el Id especificado.
	 */
	public Optional<Empleado> getEmpleadoById(Integer id) {
		return empleadoRepository.findById(id); //No necesito el get(). porque lo tare de forma opcional.
	}


	/* VER EMPLEADO POR ID (Otro Método) [El que venimos usando por defecto] */
    /*public Empleado getEmpleadoById(Integer id) {
		return empleadoRepository.findById(id).get();
	}*/



	/* ELIMINAR EMPLEADO */
	/**
	 * Elimina un empleado de la BD según su Id.
	 * @param id El Id del empleado que se desea eliminar.
	 * @return true si elimino el empleado correctamente o false si no se encuentra el emppelado con el Id especificado.
	 */
	public boolean deleteEmpleado(Integer id) {
		empleadoRepository.deleteById(id);
		if (empleadoRepository.findById(id).isPresent()) { //Otra forma de validar con isPresent.
			return false;
		}
		return true;
	}

	/* VER EMPLEADOS POR EMPRESA */
	//[41]. Método que me devuelve una lista de Arrays
	public ArrayList<Empleado> getEmpleadosByEmpresa(Integer id) {
		return empleadoRepository.findByEmpresa(id);
	}

	//Una vez realizados los servicios, implemento sus respectivos controladores en empleados.


}
