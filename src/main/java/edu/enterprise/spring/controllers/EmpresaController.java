package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.Empresa;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*[25].Creo un package (capa) llamado controllers esta tiene la responsabilidad de “dialogar”
con el “mundo exterior”, es decir, sistemas externos como los front web o diferentes clientes
como un explorador web o un sistema REST especializado como POSTMAN, INSOMNIA, etc.
Este conjunto de servicios REST conforman lo que se denomina como la API de nuestro software,
esto significa, será la interfaz de uso y comunicación que propone este artefacto de Back End.
Nota 1: Tambien existe otro tipo distintos al Rest, como son Web SOAP.
Nota 2: en este punto voy a hacer otra clase llamada EjemploControllerConEmpresa para probar
el otro tipo de comunciación que es controller y que no es de tipo RestCntroller para aprender
ha utilizar ambos sistemas de comunciación, pero el resto de proyecto se utiliozara RestController;
para Controler utilizare los item Ctrll, ejemplo [26.Ctrll.A]. */


@RestController //[5]. indica que esta clase Java oficiara de controlador y expondrá varios endpoints de tipo REST.
public class EmpresaController {


    /*[6].Para lograr que el método “saludar” sea un servicio REST, tendremos que decorarlo
    con dicha anotación, y entre parentesis el nombre del servicio con el cual lo quiero
    exponer. Con esta anotación indicamos que este método es un servicio REST que se
    expondrá mediante el método HTTP GET, que es el método HTTP más frecuente. */
    @GetMapping("/hello")
    public String saludar() {
        return "Hola Mundo con spring boot";
    }

    //[12].Creo un servicio para probar los métodos de la clase Empresa.
    @GetMapping("/test")
    public String test() {
        Empresa emp = new Empresa("SOLAR SAS", "Calle la geta", "313313313", "1234567");
        emp.setNombre("SOLAR LTDA");
        return ("ID: " + emp.getId() + " Nombre: " + emp.getNombre() + " NIT: " + emp.getNIT());
    }
}
