package edu.enterprise.spring.models;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {


    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private String correo;
    private String rol;

    /*[14].Existe una asociación entre la entidad Empresa y Empleado y por eso creo un objeto de tipo Empresa
    y debo de poner la respectiva anotación que mas se me adapta a esta relación, en este
     caso es @ManyToOne, porque muchos empleados pueden tener solo una empresa, pero una
     empresa puede tener muchos empleados.*/
    @ManyToOne
    @JoinColumn(name = "empresa_id") //[15].Con esta anotación indico que el atributo empresa se va a relacionar bajo el nombre empresa_id
    private Empresa empresa;

    //Constructores
    public Empleado() {
    }

    public Empleado(String nombre, String correo, String rol, Empresa empresa) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.empresa = empresa;
    }

    //Geters and Setters
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }


}
