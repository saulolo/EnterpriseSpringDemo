package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.Empleado;
import edu.enterprise.spring.models.Empresa;
import edu.enterprise.spring.services.EmpleadoService;
import edu.enterprise.spring.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;

//[27.Ctrll.C].Implementación del controlador de la clase Empleado.
//NOTA: Los comentarios de esta clase se complementan con EmpleadoRestController de la rama feature/RestController.

@Controller
public class EmpleadoController {


	@Autowired
	EmpleadoService empleadoService;

	@Autowired
	EmpresaService empresaService; //Inyecto esta dependencia porque la necesitó en el método nuevaEmpleado.



	/* VER EMPLEADOS*/
	//[27.Ctrll.D].Método que me permitira ver todos los empleados.
	@GetMapping({"/VerEmpleados"}) //No trabaja desde dos rutas sino desde una.
	public String viewEmpleados(Model model, @ModelAttribute String mensaje) {
		List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();
		model.addAttribute("emplelist", listaEmpleados);
		model.addAttribute("mensaje", mensaje);
		return "verEmpleados"; //llamar al html
	}
	/* Voy al package templates y creo un archivo html con el nombre tal cual del return en este caso verEmpleados
	en el cual voy a crear la plantilla estética donde mostraré lo que me retorna este controlador de
	viewEmpleados. ==> [27.Ctrll.E] */



	/* AGREGAR EMPLEADOS */
	//[27.Ctrll.H] .Creó el servicio que me guarde la empresa.
	@GetMapping("/AgregarEmpleado")
	public String nuevaEmpleado(Model model, @ModelAttribute ("mensaje") String mensaje) {
		Empleado empl = new Empleado();
		model.addAttribute("empl", empl);
		model.addAttribute("mensaje", mensaje);
		List<Empresa> listaEmpresas = empresaService.getAllEmpresas(); //llamo la lista de empresas.
		model.addAttribute("emprelist", listaEmpresas); //Agrego la lista al objeto emprelist para que el empelado pueda selecionar la empresa a la que pertence.
		return "agregarEmpleado"; //Llama al html.
	}
	//Despues de crear el servicio de AgregarEmpresa creamos el html agregarEmpleado ==> [27.Ctrll.I]


	/* GUARDAR EMPLEADOS (BOTÓN)*/
	//[27.Ctrll.L]. Método para la funcion del boton de Registrar Empleado.
	@PostMapping("/GuardarEmpleado")
	public String guardarEmpleado(Empleado empl, RedirectAttributes redirectAttributes) {
		if (empleadoService.saveOrUpdateEmpleado(empl)) {
			redirectAttributes.addAttribute("mensaje", "saveOK");
			return "redirect:/VerEmpleados";
		}
		return "redirect:/AgregarEmpleado";
	}


	/* EDITAR EMPLEADO */
	//[27.Ctrll.M] Método para agregar la funcionalidad del botón de editar empleado
	@GetMapping("/EditarEmpleado/{id}")
	public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute ("mensaje") String mensaje) {
		Empleado empl = empleadoService.getEmpladoById(id).get();
		model.addAttribute("empl", empl);
		model.addAttribute("mensaje", mensaje);
		List<Empresa> listaEmpresas = empresaService.getAllEmpresas();
		model.addAttribute("emprelist", listaEmpresas);
		return "editarEmpleado";
		//Procedo a crear el templade de editarEmpleado ==> [27.Ctrll.N]
	}


	/* ACTUALIZAR EMPLEADO*/
	// [27.Ctrll.Ñ]. Servicio del controlador para actualizar el empleado del bóton Actualizar Empleado.
	@PostMapping("/ActualizarEmpleado")
	public String updateEmpleado(@ModelAttribute("empl") Empleado empl, RedirectAttributes redirectAttributes)  {
		if (empleadoService.saveOrUpdateEmpleado(empl)) {
			redirectAttributes.addFlashAttribute("mensaje", "updateOK");
			return "redirect:/VerEmpleados";
		}
		redirectAttributes.addFlashAttribute("mensaje", "updateError");
		return  "redirect:/EditarEmpleado"; //sino se ejecuta me deja en la pagina de EditarEmpleado
	}

	/* ELIMINAR EMPLEADO*/
	// [27.Ctrll.O]. Servicio del controlador para eliminar el empleado del bóton Eliminar Empleado.
	@GetMapping("/EliminarEmpleado/{id}")
	public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		if(empleadoService.deleteEmpleado(id)) {
			redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
			return "redirect:/VerEmpleados";
		}
		redirectAttributes.addFlashAttribute("mensaje", "deleteError");
		return "redirect:/VerEmpleados";
	}

	/* Me dirijo al template verEmpresas y creo para crear un camo nuevo y ver el filtro de empleados por empresas ==> [27.Ctrll.P]*/

	/* VER EMPLEADOS POR EMPRESA*/
	//[27.Ctrll.P] Método filtrar los empleados por empresa, para la función del botón Ver Empleados.
	@GetMapping("/Empresa/{id}/Empleados")
	public String verEmpleadosPorEmpresa(@PathVariable Integer id, Model model) {
		List<Empleado> listaEmpleados = empleadoService.obtenerEmpleadoByEmpresa(id);
		model.addAttribute("emplelist", listaEmpleados);
		return "verEmpleados"; //Llamo al html con el emlelist de los empleados filtrados.
	}

	//Me disponngo a crear los botones de atras y demas de los template de verEmpresas y verEmpleados ==> [27.Ctrll.Q].
	// Y despues me dirijo a crear el CRUD de MovimientoDinero [28.Ctrll.A].


}
