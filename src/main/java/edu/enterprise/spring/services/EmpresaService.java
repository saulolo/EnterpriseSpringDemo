package edu.enterprise.spring.services;

import edu.enterprise.spring.models.Empresa;
import edu.enterprise.spring.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*[17].Creo un package con el nombre de services donde irán las clases relacionadas con los
servicios de nuestro proyecto, o lo que es lo mismo, la lógica a implementar.*/
/*Nota: Es importante aclarar que la anotación @Service registra clases en el contenedor de dependencias Spring que luego
 generará un solo objeto de la misma durante toda la sesión. Esto significa que Spring inyecta estos beans de acuerdo al
 patrón “Singleton” (un objeto solo por clase).
*/
@Service //[18].Decoro clase con esta anotación para indicar que es una clase de tipo servicios. Estas anotaciones de clase se hacen con el fin de registar las clses em el contenedor de dependecnias de Spring.
public class EmpresaService {

    /* La anotación @Autowired indica a Spring que tiene que buscar en su contenedor de inyecciones una clase que implemente
    a la interfaz que decora. Luego Spring, creará una instancia de “EmpresaRepository” y la guardará en la referencia
    “empresaRepository” de manera transparente tanto al programador como a la clase controladora.
    El simple cambio de que Spring sea el encargado de instanciar los objetos por nosotros, plasma un diseño con muy bajo
    acoplamiento entre dos clases.*/

    @Autowired //[20].Anotación que me permite conectar con el repositorio de Empresao (Inyección de dependencias).
    private EmpresaRepository empresaRepository;  //[19].Creo un Objeto de tipo EmpresaRepository y asi usar todas las funciones que alli están y que heredan de hibernate.
    /*Nota: si no utilizaramos la anotacón @Autowired, tendriamos que hacer la instancia de la clase EmpresaRepository de forma manual al estilo "java puro" asi:
     private EmpresaRepository empresaRepository = new EmpresaRepository(); */


    //---------------- CREACIÓN DE LOS SERVICIOS -----------------------------------------


    /* GUARDAR O ACTUALIZAR EMPRESA */
    //[21].Método que me permite actualizar o guardar una empresa.
    public Empresa saveOrUpdateEmpresa(Empresa empresa) {//Me retorna un booleano para que me diga si guardo/actalizó o no y le debo pasar el objeto Empresa como parámetro.
        Empresa emp = empresaRepository.save(empresa); //Creo una variable emp de tipo Empresa donde me va a guardar la emrpesa por el método.save de JPA repository.
        return emp; //Retorna la empresa.
    }


    /* VER EMPRESAS */
    //[22].Método que me permite ver una lista de todas las empresas.
    public List<Empresa> getAllEmpresas() {
        List<Empresa> empresaList = new ArrayList<>(); //Creo una variable tipo objeto de tipo lista donde se almacenaran todas las empresas que quiero ver y lo hago con el método ArrayList.
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa)); //llamo el objeto que hereda de Jparepository (empresaRepository) para traerme el metodo
        // que necesite, en este caso findAll que me permite ver todas las empresas.
        //El método .findAll me devuelve un iterable, por ende debo de recorrelo para poderlas ver con un foreach.
        //Este metodo: .forEach(empresa -> empresaList.add(empresa)) es lo mismo que este otro:
        /*for (Empresa empresa : empresaList) {
            empresaList.add(empresa);
        }*/
        return empresaList;//Luego las retorno.
    }


    /* VER EMPRESA POR ID */
    //[23].Método que me permite ver una empresa por su Id.
    public Empresa getEmpresaById(Integer id) { //Me retorna una empresa cuando le pase el Id
        return empresaRepository.findById(id).get(); //Me trae la empresa por su id.
    }
    //Nota: devolverun null es una pésima practica.


    /* ELIMINAR EMPRESA */
    //[24].Método que me permite eliminar una empresa por Id.
    public boolean deleteEmpresa(Integer id) { //Me retorna un booleano este método y recibe por párametro el id de la empresa.
        empresaRepository.deleteById(id); //Utilizo este metodo de JPA para eliminar por id.
        if (empresaRepository.findById(id) != null) { //Hago una validación del servicio de eliminar, y verifica que si al eliminar no hay nada (que llega null), retorne un true y en caso contrario un false.
            return true; ////Otra forma de validar es con isPresent en vez de null.
        }
        return false;
    }

}
