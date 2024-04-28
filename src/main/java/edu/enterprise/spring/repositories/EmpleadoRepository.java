package edu.enterprise.spring.repositories;

import edu.enterprise.spring.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//[37].Implementar el repositorio de la clase Empleado.
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

	//[40].Consultar los empleados que pertenecen a una empresa.
    /* Forma 1 Usando JPQL. Puedo crear un método parecido al los que tiene por defecto JpaRepository pero como yo hice una
	relación desde el principio con los modelos, Spring ya lo sabe, entonces esta forma la debemos de evitar.
	Esta por defecto y con la ayuda del IDE me establece la query por JPQL con la anotación @Query.
	*/
	//Query directo de JPQL
	@Query("SELECT e FROM Empleado e WHERE e.empresa.id = ?1") //?1: Hace referencia a la posición del parametro que recibe el método, en este caso recibi un parametro.
	ArrayList<Empleado> findByEmpresa(Integer id); //Por defecto es un método publico y abstracto.


	/* Forma 2 Usando Query Nativo(SQL): Con la misma anotación @Query y su valor value establezco la consulta nativa de SQL segiodo de nativeQuery = true*/
	/*	@Query(value = "SELECT * FROM empleado WHERE empresa_id = ?1 ", nativeQuery = true)
	public abstract ArrayList<Empleado> findByEmpresa(Integer id);*/

	//Despues de este Query, debo de agregar el servicio que me permite hacer esta consulta ==> [41].

}

//Ahora me dirijo a implementar los servicios de Empleado.
