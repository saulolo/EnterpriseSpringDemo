package edu.enterprise.spring.services;

import edu.enterprise.spring.models.Empresa;
import edu.enterprise.spring.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*[17].Creo un package con el nombre de services donde irán las clases relacionadas con los
servicios de nuestro proyecto, o lo que es lo mismo, la lógica a implementar.*/
@Service //[18].Decoro clase con esta abotación para indicar que es una clase de tipo servicios.
public class EmpresaService {


    @Autowired //[20].Anotación que me permite conectar con el repositorio de Empresao.
    EmpresaRepository empresaRepository;  //[19].Creo un Objeto de tipo EmpresaRepository y asi usar todas las funciones que alli están y que heredan de hibernate.


    //---------------- CREACIÓN DE LOS SERVICIOS -----------------------------------------


    /* GUARDAR O ACTUALIZAR EMPRESA */
    //[21].Método que me permite actualizar o guardar una empresa.
    public boolean saveOrUpdateEmpresa(Empresa empresa) {//MMe retorna un booleano para que me diga si guardo/actalizó o no y le debo pasar el objeto Empresa como parámetro.
        Empresa emp = empresaRepository.save(empresa); //Creo una variable emp de tipo Empresa donde me va a guardar la emrpesa por el método.save.
        if (empresaRepository.findById(emp.getId()) != null) { //Tengo que la empresa existe y eso lo hago con un condicional. true guarde, false que no guarde.
            return true;
        }
        return false;
    }


    /* VER EMPRESAS */
    //[22].Método que me permite ver una lista de todas las empresass.
    public List<Empresa> getAllEmpresas() {
        List<Empresa> empresaList = new ArrayList<>(); //Creo una variable tipo objeto de tipo lista donde se almacenaran todas las empresas que quiero ver y o hago con el método ArrayList.
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa)); //llamo el objeto que heredA de Jparepository (empresaRepository) para traerme el metodo
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


    /* ELIMINAR EMPRESA */
    //[24].Método que me permite eliminar una empresa por Id.
    public boolean deleteEmpresa(Integer id) { //Me retorna un booleano este método y recibe por párametro el id de la empresa.
        empresaRepository.deleteById(id); //Utilizo este metodo de JPA para eliminar por id.
        if (getEmpresaById(id) != null) { //Hago una validación que si no llega vacio me retorne un false y en caso contrario un true.
            return false;
        }
        return true;
    }

}
