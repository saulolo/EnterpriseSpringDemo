package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.MovimientoDinero;
import edu.enterprise.spring.services.MovimientoDineroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//[50].RestControllers de  movimientoDinero
@RestController
@RequestMapping("/movements")
public class MovimientoDineroRestController {


	@Autowired
	MovimientoDineroService movimientoDineroService;


	/* Ver MovimientoDinero */
	@GetMapping
	public List<MovimientoDinero> verMovimientos() {
		return movimientoDineroService.getAllMovimientoDinero();
	}


	/* Guardar MovimientoDinero */
	@PostMapping
	public MovimientoDinero guardarMovimiento(@RequestBody MovimientoDinero movimiento) {
		return movimientoDineroService.saveOrUpdateMovimiento(movimiento);
	}

	/* Ver MovimientoDinero por ID */
	@GetMapping("/{id}")
	public Optional<MovimientoDinero> verMovimientoById(@PathVariable Integer id) {
		return movimientoDineroService.getMoviminetoById(id);
	}


	/* Editar MovimientoDinero por Id  */
	@PatchMapping("/{id}")
	public MovimientoDinero actulizaciónParcialMovimiento(@PathVariable Integer id, @RequestBody MovimientoDinero movimiento) {
		MovimientoDinero mov = movimientoDineroService.getMoviminetoById(id).get();
		if (mov != null) {
			mov.setConcepto(movimiento.getConcepto());
			mov.setMonto(movimiento.getMonto());
			mov.setUsuario(movimiento.getUsuario());
			return movimientoDineroService.saveOrUpdateMovimiento(mov);
		} else {
			return null;
		}
	}


	/* Eliminar MovimientoDinero por Id  */
	@DeleteMapping("/{id}")
	public String EliminarMovimiento(@PathVariable Integer id) {
		boolean respuesta = movimientoDineroService.deleteMovimiento(id);
		if (respuesta) {
			return "Se eliminó correctamente el movimiento de dinero con Id: " + id;
		}
		return "NO se eliminó el movimiento de dinero con Id: " + id;
	}


	/* Ver MovimientoDinero filtrado por Empleado  */
	@GetMapping("/{id}/employees")
	public ArrayList<MovimientoDinero> verMovimientosPorEmpleado(@PathVariable Integer id) {
		return movimientoDineroService.obtenerPorEmpleado(id);
	}


	/* Ver MovimientoDinero filtrado por Empresa */
	@GetMapping("/{id}/enterprises")
	public ArrayList<MovimientoDinero> verMovimientosPorEmpresa(@PathVariable Integer id) {
		return movimientoDineroService.obtenerPorEmpresa(id);
	}

	//Voy para La clase 16 y para el punto [51]

}
