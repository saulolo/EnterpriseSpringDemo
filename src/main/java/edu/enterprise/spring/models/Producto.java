package edu.enterprise.spring.models;

import edu.enterprise.spring.enums.ProductoCategoriaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

/* [70]. CRUD de Producto con mejores practicas de programación. */

//[61]. Clase producto para implementar como ejemplo la lectura de un archivo Json y no de memoria/
//[71]. Anotaciones con lombok para mapear la clase Producto.(despues de haber agregado la dependencia de lombok en el archivo pom.xml e instalado el puggin Lombok en el marketplace).
//@Getter //[71.A]Anotación para generar los Getters.
//@Setter //[71.B]Anotación para generar los Setters.
//@ToString //[71.E] Anotación para generar el método toString.
//@EqualsAndHashCode //[71.F] Anotación para generar los métodos equals y hashCode., estos métodos son necesarios para comparar objetos y asegurarnos que no se repitan en una colección garantizando la unicidad de los objetos.
@FieldDefaults(level = AccessLevel.PRIVATE) //[71.Q ]Anotación para establecer el nivel de acceso de los atributos de la clase.
@NoArgsConstructor //[71.C] Anotación para generar un constructor vacío.
@AllArgsConstructor //[71.D] Anotación para generar un constructor con todos los atributos.
@Data //[71.G]Anotación para generar los Getters, Setters, toString, equals y hashCode pero no los constructores.
@Builder //[71.H] Anotación para generar un constructor con todos los atributos.
@Entity
@Table(name = "producto")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotEmpty //[71.I].Anotación para indicar que el atributo nombre no puede ser nulo.
	@Column(name = "nombre", length = 100, nullable = false) //[71.J].Anotación para indicar que el atributo nombre es una columna de la tabla producto, con un tamaño de 100 caracteres y no puede ser nulo.
	String nombre;

	@Size(max = 255) //[71.K].Anotación para indicar que el atributo descripción tiene un tamaño máximo de 255 caracteres.
	@Column(name = "descripcion")
	String descripcion;

	@PositiveOrZero //[71.L].Anotación para indicar que el atributo precio debe ser un número positivo o cero.
	@Column(name = "precio") //[71.M].Si establesco las propiedades de: precisión(Digitos en total incluyendo decimales) y scala (número de decimales).
	Double precio;

	@Column(name = "stock")
	Integer stock;

	@ManyToOne //[71.N]. Indica la relación muchos a uno con la entidad Empresa
	@JoinColumn(name = "empresa_id") //[71.O]. Nombre de la columna que hace referencia a la clave primaria de la entidad Empresa
	Empresa empresa;

	@NotNull //[71.R].Anotación para indicar que el atributo categoria no puede ser nulo.
	@Enumerated(EnumType.STRING) //[71.P].Anotación para indicar que el atributo categoria es un enumerado de tipo String.
	@Column(name = "categoria")
	ProductoCategoriaEnum categoria; // Creo un Enum para las categorias de los productos. ==> [72].

	//Creo el respositorio de Producto para poder hacer las operaciones CRUD de la clase Producto. ==> [73].
}
