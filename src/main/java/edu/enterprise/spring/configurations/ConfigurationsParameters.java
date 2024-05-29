package edu.enterprise.spring.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

//[65].Clase de cofiguración de parámetros globales que se setean en el archivo properties.
@Configuration //Anotación que inicia con Spring y su proposito servir como configuración global de la aplicación
@ConfigurationProperties(prefix = "app") //Esta anotación indica en primer lugar que Spring deberá cargar el valor de los atributos de esta clase con los valores indicados en el archivo “application.properties”.
public class ConfigurationsParameters {

	//Generamos los atributos exactamente igual que en el archivo properties(Configuración de logs [64]]
	private String nombre;
	private String lenguaje;
	private String pais;
	private String author;
	private String javaVersion;
	private String osName;
	private String userName;



	//Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLenguaje() {
		return lenguaje;
	}

	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@PostConstruct//Anotación que indica que el método que la contiene se ejecutará después de que el bean haya sido creado.
	public void initJavaVersion() {
		Properties properties = System.getProperties();
		this.javaVersion = properties.getProperty("java.version");
	}

	public String getJavaVersion() {
		return javaVersion;
	}

	@PostConstruct
	public void initOsName() {
		Properties properties = System.getProperties();
		this.osName = properties.getProperty("os.name");
	}


	public String getOsName() {
		return osName;
	}

	@PostConstruct
	public void initUserName() {
		Properties properties = System.getProperties();
		this.userName = properties.getProperty("user.name");
	}

	public String getUserName() {
		return userName;
	}


	//Método toString
	@Override
	public String toString() {
		System.out.println();
		return "ConfigurationsParameters{" +
				"nombre='" + nombre + '\'' +
				", lenguaje='" + lenguaje + '\'' +
				", pais='" + pais + '\'' +
				", author='" + author + '\'' +
				", javaVersion='" + getJavaVersion() + '\'' +
				", osName='" + getOsName() + '\'' +
				", userName='" + userName + '\'' +
				'}';
	}


	//Para probar esta clase voy a realizar un lookup de este bean dentro del controlador de MovimientoDinero ==? [66]
}
