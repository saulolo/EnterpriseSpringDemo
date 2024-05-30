package edu.enterprise.spring.controllers;

import edu.enterprise.spring.models.Empresa;
import edu.enterprise.spring.services.impl.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*[27]. llego a este archivo para comenzar a trabajar con mi capa RestController*/

/*[37].@RequestMapping: De que de acuerdo a las convenciones REST, todos los métodos enfocados a la gestión
de un mismo recurso deben mantener el mismo nombre. Por consiguiente, ahora, deberemos remover el nombre del
endpoint para cada uno de nuestros métodos. Por ejemplo, el método verEmpresas que recupera todas las empresas
ahora presenta este aspecto.
@GetMapping
public List<Empresa> verEmpresas()......
*/

@RestController//[28].Indica que esta clase Java oficiara de controlador y expondrá varios endpoints de tipo REST.
@RequestMapping("/enterprises") //Permite unificar todos los nombres de los endpoints que contiene la clase.
public class EmpresaRestController {

	@Autowired //Esta anotación me permite inyectar automáticaente la instancia de EmpresaService.
	private EmpresaServiceImpl empresaServiceImpl; //[29]. Hago la inyección de el servicio de Empresa con el modificador de acceso private para encapsular y hacer el código mas seguro..

	/* Hola Mundo RestControler */
	//[30]. Metodo de prueba para ver como generar un saludo personalizado por el nombre que el usuario ingrese.
	@GetMapping("/hola/{nombreUsuario}") //@GetMapping: convierte a este método Java en un endpoint REST mediante el mecanismo HTTP GET.
	public String saludar(@PathVariable String nombreUsuario) { //@PathVariable: que indica que este parámetro java normal se transformará en un parámetro web de tipo Path Variable, es decir, un parámetro de tipo web que estará contenido en la URL del endpoint.
		return "Hola " + nombreUsuario;
	}


	/*-------------------------------------- CRUD EMPRESAS----------------------------------------- */

	/* Ver Empresas */
	//[31]. Creamos el controlador de verEmpresas
	/* @GetMapping: respuesta, Spring, ha convertido la lista de Empresas (List<Empresa>) a un array de tipo JSON. Esdecir,
	ha realizado por debajo un minucioso proceso de serialización desde JAVA a JSON. En otros frameworks más antiguos esta
	era una ardua tarea que le correspondía al desarrollador y era necesario lidiar con sofisticados mappers de JSON o XML.*/
	@GetMapping //ruta donde retornará la lista de empresas, lo consulto en la ruta:http://localhost:8080/enterprises
	public List<Empresa> verEmpresas() { //Deve devolver una lista por eso el método List.
		return empresaServiceImpl.getAllEmpresas();
	}
	/* Importante: firma de los endpoints definidos llevan el nombre “enterprises” que es un sustantivo y plural.
	Se recomienda seguir estas reglas gramaticales para la definición de API’s REST, ya que este subprotocolo está
	orientado a recursos y no a acciones. Es por eso que debemos evitar definir nombres como “getEmpresa” o
	“recuperarEmpresas” para la definición de endpoints.*/



	/* Guardar Empresas */
	//[32]. Metodo para guardar la empresa.
	/* A diferencia de los anteriores servicios de recupero, este endpoint de alta lógicamente necesitará
	la información de la Empresa a crear. Esta información es proveída por el consumidor del servicio en formato JSON, y
	es Spring Boot, quien se encargará del trabajo de convertir ese JSON suministrado a un objeto de la clase “Empresa”.
	Esta transformación es posible gracias a la anotación @RequestBody sobre el parámetro “emp”, que lo que realmente
	está indicando es que este objeto se construirá a partir de la información enviada en el “Body” del “Request” para la
	petición de este servicio. */
	@PostMapping //@PostMapping: convierte a este método Java en un endpoint REST mediante el mecanismo HTTP POST.
	public Empresa guardarEmpresa(@RequestBody Empresa emp) { //@RequestBody: El cuerpo que esta solicitando va a llegar aqui que es de Empresa (el modelo).
		return empresaServiceImpl.saveOrUpdateEmpresa(emp);
	}
	/*Nota: EL RequestBody lo creo como Json en el insomnia para mandarlo como un POST, estos salen
	del modelo Empresa que cree sin el Id porque este es automatico, asi:
	{
	"nombre": "Chass",
	"direccion": "Itagui",
	"telefono": "31431432345",
	"nit": "999876534"
	}

	Este método tambien me sierve para actualizar una empresa, pero en el JSON le debo pasar el ID: "id": #, y modificar el campo o campos a actualizar
	*/

	/* Ver Empresas por Id */
	//[33]. Metodo para ver una empresa por Id.
	//LA mejor manera de hacer este método es retornando un Optional y no la clase Empresa para que me retorne un null en caso de no encontrar una empresa y no un error como si no lo manejara un un optional
	//Puedo poner tambien el GetMapping asi: @GetMapping("path = "enterprises/{id}")
	// y la firma del método debe de ser asi: (@PathVariable ("id") Integer id)
	@GetMapping("/{id}")
	public Empresa empresaPorId(@PathVariable Integer id) {
		return empresaServiceImpl.getEmpresaById(id);
	}



	/* Actualizar Empresa Parcialmente */
	//[34]. Método para ver actualizar una empresa de modo parcial según mi criterio.
	@PatchMapping("/{id}") ////@PatchMapping: convierte a este método Java en un endpoint REST mediante el mecanismo HTTP PATCH.
	public Empresa actualizacionParcialEmpresa(@PathVariable Integer id, @RequestBody Empresa empresa) { //@PathVariable: que indica que este parámetro java normal se transformará en un parámetro web de tipo Path Variable, es decir, un parámetro de tipo web que estará contenido en la URL del endpoint.
		//@RequestBody Empresa emp: Porque tengo que consultar la empresa que traigo del body para actualizar.
		Empresa emp = empresaServiceImpl.getEmpresaById(id); //Creo una variable emp donde voy a almacenar la empresa que me traigo por Id.
		emp.setNombre(empresa.getNombre()); //Seteo los campos que necesito actualizar
		emp.setDireccion(empresa.getDireccion());
		emp.setTelefono(empresa.getTelefono());
		emp.setNIT(empresa.getNIT());
		return empresaServiceImpl.saveOrUpdateEmpresa(emp) ; //Guardo los campos actualizados en la empresa.
	}


	/* Actualizar Empresa Completa */
	//[35]. Método para ver actualizar una empresa por completo.
	@PutMapping("/{id}") //@PutMapping: convierte a este método Java en un endpoint REST mediante el mecanismo HTTP PUT.
	public Empresa actualizarEmpresa(@PathVariable Integer id, @RequestBody Empresa empresa) {
		Empresa emp = empresaServiceImpl.getEmpresaById(id); //Obtengo la empresa existente por su id.
		if (emp != null) { //Verificar si la empresa existe.
			// Actualizar todos los campos de la empresa con los valores proporcionados en el cuerpo de la solicitud
			emp.setNombre(empresa.getNombre());
			emp.setDireccion(empresa.getDireccion());
			emp.setTelefono(empresa.getTelefono());
			emp.setNIT(empresa.getNIT());
			return empresaServiceImpl.saveOrUpdateEmpresa(emp); //Guardar y retornar la empresa actualizada
		} else {
			// Si la empresa no existe, retornar null o lanzar una excepción, dependiendo del requerimiento
			return null;
		}
	}
	/*Nota: La diferencia principal entre los dos métodos de actualizar, radica en la semántica de la operación y cómo
	se espera que se utilice. En resumen:
	@PutMapping: Para actualizaciones completas de un recurso.
	@PatchMapping: Para actualizaciones parciales de un recurso.
	Pero por buenas practicas, es mejor utulizar el servicio PUT para actualizaciones en generales,
	ya sean parciales o completas.*/


	/* Eliminar Empresa  */
	//[36]. Método para ver eliminar una empresa pasandole su Id.
	@DeleteMapping("/{id}") //@DeleteMapping: convierte a este método Java en un endpoint REST mediante el mecanismo HTTP DELETE.
	public String eliminarEmpresa(@PathVariable Integer id) {
		boolean respuesta = empresaServiceImpl.deleteEmpresa(id); //Utilizo el metodo deleteEmpresa del service
		if (respuesta) {
			return "Se eliminó la empresa con ID: " + id;
		}
		return "No se pudo eliminar la empresa con ID: " + id;
	}
	//Nota: el metodo eliminar no tiene encuenta el body, solo pasarle el id y ya.

	//Ahora debo de implementar los Repositirios, Servicios y controlleres de la clase Empleado.

}
