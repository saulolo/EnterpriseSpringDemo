package edu.enterprise.spring.services;

import edu.enterprise.spring.models.Empleado;
import edu.enterprise.spring.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//[27.Ctrll.B].Implementación del servicio de la clase Empleado.
//NOTA: Los comentarios de esta clase se encuentran en EmpleadoService de la rama feature/RestController.
@Service
public class EmpleadoService {


	@Autowired
	EmpleadoRepository empleadoRepository;


	/* CREAR EMPLEADOS */
	public boolean saveOrUpdateEmpleado(Empleado empl) {
		Empleado emp = empleadoRepository.save(empl);
		if (empleadoRepository.findById(emp.getId()) != null) {
			return true;
		}
		return false;
	}


	/* VER EMPLEADOS */
	public List<Empleado> getAllEmpleados() {
		List<Empleado> empleadoList = new ArrayList<>();
		empleadoList.addAll(empleadoRepository.findAll());
		return empleadoList;
	}


	/* VER EMPLEADO POR ID */
	public Optional<Empleado> getEmpladoById(Integer id) {
		return empleadoRepository.findById(id);
	}


	/* VER EMPLEADO POR EMPRESA */
	public ArrayList<Empleado> obtenerEmpleadoByEmpresa(Integer id){
		return empleadoRepository.findByEmpresa(id);
	}


	/* ELIMINAR EMPLEADO */
	public boolean deleteEmpleado(Integer id) {
		empleadoRepository.deleteById(id);
		if (empleadoRepository.findById(id).isPresent()) {
			return false;
		}
		return true;
	}


	//Me dirijo a crear el EmpleadoController ==> [27.Ctrll.C]

}
