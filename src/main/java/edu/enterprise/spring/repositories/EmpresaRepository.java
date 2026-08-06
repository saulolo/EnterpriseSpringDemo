package edu.enterprise.spring.repositories;

/*[15].Creo el packahe repositories. Repositorio: es donde hibernate crea una BD tipo objeto
 que me permite acceder a los datos, éste debe de ser tipo interface porque implementa los
 métodos que tiene hibernate para poderse comunicar y para ello debo de extender de JpaRepository.
 el cual debe de tener los siguientes parámetros, el primero es que se debe relacionar en los
 parentesis angulares con la entidad ala que apunta, en este caso de Empresa y el tipo de dato
 que tiene su Id, en este caso Integer. ósea el repositorio es el punto intermedio entre la
 entidad y la tabla.*/

import edu.enterprise.spring.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*[16].Con esta anotación identifico a spring boot que esta clase es un repositorio.
Nuevamente lo que hace esta anotación (repositorio) es crear una BD virtual que es copia de la
que tenemos en postgresql y la va a tarducir para que nuestra entidad la entieda y pueda
interactuar con ella.*/
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}
