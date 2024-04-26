package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.Empresa;
import edu.enterprise.spring.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    //({"/Empresas", "/VerEmpresas"}) //"/Empresas", es la otra URL que podia direccionar este coontrolador.
    @GetMapping({"/", "/VerEmpresas"}) //Esta anotación @GetMapping indica que este método manejará solicitudes GET a las URL "/" (que es el home) o "/VerEmpresas", esta anotación permite definir múltiples rutas.
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

    /*[26.Ctrll.E].Voy al package templates y creo un archivo html con el nombre tal cual del return
    en este caso verEmpresas en el cual voy a crear la plantilla estetica donde mostraré lo que me
    retorna este controlador de viewEmpresas.*/
    //[26.Ctrll.F]. Para que la vista en html sea posible, debo de inyectar la dependecia Thymelief en el archivo pom.xml.


    /* AGREGAR EMPRESAS */
    //[26.Ctrll.K].Creó el servicio que me guarde la empresa.
    @GetMapping("/AgregarEmpresa")
    public String nuevaEmpresa(Model model) { //Devuelve un String porque llama el nombre del template.
        Empresa emp = new Empresa();
        model.addAttribute("emp", emp); //Apuntamos a un objeto vacio para rellenar luego en la BD.
        return "agregarEmpresa";
    }
    //Despues de crear el servicio de AgregarEmpresa creamos el html agregarEmpresa


    /* AGREGAR EMPRESAS */
   // [26.Ctrll.M].Servicio del controlador para guardar la empresa del bóton Crear empresa.
    //Recibe 2 atributos,uno de tipo de Empresa que es la que se va a guardar y otro para hacer un redireccionamiento
    @PostMapping("/GuardarEmpresa") //Como se va a settear info a la BD, debe de ser un método post.
    public String guardarEmpresas(Empresa emp, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(emp)) { //Establecemos la condición para saber que si se guardó devuelva un mensaje.
             return "redirect:/VerEmpresas"; //Le decimos que se quede en el Template de verEmpresa si guardo correctamente.
        }
        return  "redirect:/AgregarEmpresa";//Se queda en el template de agregarEmpresa
        /*Nota: Si quiero ir a un template de html, solo pongo el nombre del template si direcciono a un servicio
        * debo de poner la palabra redirect*/
    }


    /* EDITAR EMPRESAS */
    // [26.Ctrll.N]. Servicio del controlador para editar la empresa del bóton Editar.
    //@PathVariable: Porque llega como una ruta URL pasa saber el donde y el id del elemento a editar.
    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id) {
        //Creo un atributo para el modelo que se llame igualmente emp y es el que ira al html para llenar o alimentar campos
        Empresa enp = empresaService.getEmpresaById(id);  //Me traigo la empresa que ya exite por Id.
        model.addAttribute("emp", enp); //la agregamos al modelo
        return "editarEmpresa"; //Regressa este HTML(Template)
    }


    /* ACTUALIZAR EMPRESA*/
    // [26.Ctrll.O]. Servicio del controlador para actalizar la empresa del bóton Actualizar Empresa.
    @PostMapping("/ActualizarEmpresa") //Es PostMapping porque lleva información.
    public String updateEmpresa(Empresa emp)  {
        if (empresaService.saveOrUpdateEmpresa(emp)) {
            return "redirect:/VerEmpresas";
        }
        return  "redirect:/EditarEmpresa"; //sino se ejecuta me deja en la pagina de EditarEmpresa
    }


    /* ELIMINAR EMPRESA*/
    // [26.Ctrll.P]. Servicio del controlador para elimibar la empresa del bóton Eliminar.
    //Para este servicio no se necesita una plantilla(Template) html.
    @GetMapping("/EliminarEmpresa/{id}") //Es GetMapping porque consulta y quita, pero no lleva..
    public String eliminarEmpresa(@PathVariable Integer id) { //No necesito el modelo porque no estoy enviando información a alguna página html, solo estoy eliminando.
        try {
            empresaService.deleteEmpresa(id); //Generamos un Try catch para el manejo de la excepción.
        } catch (Exception e) {
            return "redirect:/VerEmpresas";
        }
        return "redirect:/VerEmpresas";  //En ambas se queda aqui porque me queda en la misma HTML.
    }



}
