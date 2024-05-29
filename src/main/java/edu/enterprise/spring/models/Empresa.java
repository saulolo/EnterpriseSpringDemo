package edu.enterprise.spring.models;

import jakarta.persistence.*;

import java.util.List;


/*[7].Creo el package de models, domain o entities y alli creo las clases o entidades, y como estamos
programando para la web con spring boot debo de utilizar las anotaciones qne me provee dicho
framework para que entienda que dicha clase se va a comunicar con la BD a travez de @Entity.*/
@Entity
@Table(name = "empresa") //[8].Con esta anotación la BD a traves de Hibernate se da cuenta que esta entidad va enlazada a una tabla.
public class Empresa { //La clase codelo que se conoce como POJO: Se trata de una clase básica de dominio o simplemente contendrá atributos básicos y los respectivos métodos “getter” y “setter” para acceder a estos.

    //[9].Creó los atributos de la clase.
    @Id //[9.A]. Debo de poner un atributo con ID unico y ordinal por "tabla" de la clase ya que está será una tabla de una BD.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //[9.B]. Asi decimos que cada empresa cuando se creé en la BD, lo haga de forma autoincremental, con Identity aunque elimines guarda el consucutivo.
    //@Column(columnDefinition = "serial") //Para que sea autoincremental por columnas de manera separada en caso de que colocara GenerationType.AUTO. (con AUTO se genera un id global)
    private int id;

    private String nombre;

    private String direccion;

    private String telefono;

    private String NIT;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL) //Con esta anotación indico que la relación es de uno a muchos, porque una empresa puede tener muchos productos.
    private List<Producto> productos;



    //[10].Genero los constructores para inicializar los atributos de clase.
    public Empresa() {
    }

    //Constructor con todos los parametros sin el id porque este es incremental.
    public Empresa(String nombre, String direccion, String telefono, String NIT) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.NIT = NIT;
    }

//[11].Genero los respectivos getters and seters para acceder o modificar los atributos.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }
}
