package edu.enterprise.spring.repositories;

import edu.enterprise.spring.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//[27.Ctrll.A].Implementar el repositorio de la clase Empleado.
//NOTA: Los comentarios de esta clase se encuentran en EmpleadoRepository de la rama feature/RestController.
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {


	/* Empleados filtrados por empresa */
	@Query("SELECT e FROM Empleado e WHERE e.empresa.id = ?1")
	ArrayList<Empleado> findByEmpresa(Integer id);

	//Me dirijo a crear el EmpleadoService ==> [27.Ctrll.B]

}
