package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.Empleado;
import edu.enterprise.spring.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//[39]. Implemento los respectivos controladores de Empleados.
@RestController
@RequestMapping("/employees")
public class EmpleadoRestController {

	@Autowired
	EmpleadoService empleadoService;

	/* Ver Empleados */
	@GetMapping
	public List<Empleado> verEmpleados() {
		return empleadoService.getAllEmpleados();
	}


	/* Ver Empleado por Id */
	@GetMapping("/{id}")
	public Optional<Empleado> verEmpleadoById(@PathVariable Integer id) { //Es un Optional porque el metodo getEmpleadoById del servide retorna un Optional.
		return empleadoService.getEmpleadoById(id);
	}

	/* [42]. Ver Empleados por Empresa */
	//Si quiero hacer una consulta con filtros debo de modificar con una Query mi EmpleadoRepository ==> [40].
	@GetMapping("/{id}/enterprises")
	public ArrayList<Empleado> verEmpleadoByEmpresa(@PathVariable Integer id) {
		return empleadoService.getEmpleadosByEmpresa(id);
	}


	/* Guardar Empleados */
	//Como hay una relación de Empleados a Empresas, de muchos a uno, debo de tener encuenta esta relación para construir mi request trayendo el Id de la empresa.
	@PostMapping //Lo hago que me develva un Optional para variar.
	public Optional<Empleado> guardarEmpleado(@RequestBody Empleado empl) { //Si retorna un Optional debo de hacer el casteo
		return Optional.ofNullable(empleadoService.saveOrUpdateEmpleado(empl));
	}



	/* Actualizar Empleados */
	@PutMapping("/{id}")
	public Empleado actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
		Optional<Empleado> emple = empleadoService.getEmpleadoById(id); //Siempre tengo que validar que me devuelve el método que llamo del service.
			empleado.setNombre(empleado.getNombre());
			empleado.setCorreo(empleado.getCorreo());
			empleado.setRol(empleado.getRol());
			return empleadoService.saveOrUpdateEmpleado(empleado);
	}


	/* Eliminar Empleados */
	@DeleteMapping("/{id}")
	public String eliminarEmpleado(@PathVariable Integer id) {
		boolean respuesta = empleadoService.deleteEmpleado(id);
		if(respuesta) {
			return "Se eliminó correctamente el empleado con ID: " + id;
	 }
		return "No se pudo eliminar el empleado con Id: " + id;
	}

	//Paso a generar el CRUD de movimientos empesando por el su repositorio ==> [43]

}
