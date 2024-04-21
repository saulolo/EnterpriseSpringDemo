package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.Empresa;
import edu.enterprise.spring.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*[26.Ctrll.A]. Cree esta clase para trabajar con la anotación @Controller y tener como un
ejemplo de base sobre este protocolo de comuncicación. La diferencia principal entre @RestController
y @Controller radica en su propósito y la forma en que manejan las solicitudes HTTP en una
aplicación Spring MVC. @Controller se utiliza para manejar solicitudes HTTP y devolver vistas
HTML en una aplicación web tradicional (por ejemplo, un objeto ModelAndView), @RestController
se utiliza para crear servicios web RESTful que devuelven datos directamente al cliente en
formato JSON o XML. La elección entre @Controller y @RestController depende de si estás
construyendo una aplicación web tradicional que requiere la generación de vistas o un servicio
web RESTful que devuelve datos. */

@Controller //[26.Ctrll.B].Anotación para decorar la clase como Controller y asi indicarselo a Spring Boot
public class EjemploControllerConEmpresa {

    @Autowired //[26.Ctrll.D].Debo de utilizar esta anotación para inyectar o enlazar a la clase EmpresaService (No basta solo con instanciar).
    EmpresaService empresaService; //[26.Ctrll.C] Instancio el Servicio con el cual me quiero comunicar para traer sus métodos.

    /* VER EMPRESAS */
    //[26.Ctrll.D].Creo el metodo viewEmpresas que me va a permitir ver todas las empresas listadas.
    @GetMapping({"/Empresas", "/VerEmpresas"}) //Esta anotación @GetMapping indica que este método manejará solicitudes GET a las URL "/Empresas" o "/VerEmpresas", esta anotación permite definir múltiples rutas.
    public String viewEmpresas(Model model) { //El objeto model de la clase Model es que recibe cualquier cosa, y aparte de que la recibo como argumento, la puedo modelar dentro del método y devuelvo algo.
        //El objeto Model se utiliza para pasar datos desde el controlador a la vista. Es un objeto proporcionado por el framework que permite agregar atributos que se mostrarán en la vista.
        List<Empresa> listaEmpresas = empresaService.getAllEmpresas(); // Creo una variable llamada listaEmpresas de tipo List<Empresa> que va a ser igual al metodo que creé en EmpresaService que me devuelve todas las empresas (getAllEmpresas()).
        //Utiliza el objeto Model para agregar la lista de empresas al modelo, de modo que la vista pueda acceder a esta lista y mostrarla al usuario.
        model.addAttribute("emplist", listaEmpresas); //Agrega la lista de empresas obtenida en el paso anterior al modelo con el nombre "emplist".
        //Esto significa que estás agregando la lista de empresas al modelo con el nombre "emplist". En la vista asociada, podrás acceder a esta lista utilizando el nombre "emplist" y mostrar los datos de las empresas según sea necesario
        return "verEmpresas"; //Me retorna el nombre del link de la pagina HTML donde voy a ver lo retornado y el cual apunta al package templates para darle forma y estilo.
    }
    /* Nota: El uso del objeto Model permite que el controlador y la vista estén desacoplados. El controlador no necesita conocer los detalles de cómo se representan los datos en la vista.
        Esto hace que el código sea más mantenible y flexible, ya que podemos cambiar la vista sin afectar el controlador y viceversa.*/


    
}
