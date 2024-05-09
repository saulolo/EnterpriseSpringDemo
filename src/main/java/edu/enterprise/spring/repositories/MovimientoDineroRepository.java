package edu.enterprise.spring.repositories;

import edu.enterprise.spring.models.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//[43].Implementar el repositorio de la clase MovimientoDinero.
@Repository
public interface MovimientoDineroRepository extends JpaRepository<MovimientoDinero, Integer> {


	//[46].Método para filtrar movimientosDinero por empleados.
	@Query(value = "SELECT * FROM movimientos WHERE empleado_id = ?1", nativeQuery = true)
	ArrayList<MovimientoDinero> findByEmpleado(Integer id);

	//[48].Método para filtrar movimientosDinero por empresa.
	@Query(value = "SELECT * FROM movimientos WHERE empleado_id IN (SELECT id FROM empleado WHERE empresa_id = ?1)", nativeQuery = true)
	ArrayList<MovimientoDinero> findByEmpresa(Integer id);


	//Implementos los servicios de consultas de movimientos especificos de estas query ==> [47] y [49].


}
