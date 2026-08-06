package edu.enterprise.spring;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*[Test.1]Los test llevan el nombre de la clase a testear seguido de la palabta test. y van al mismo nivel que eb el
package java. Para ayudarnos con el ide a que nos genere los archivos por defecto pulsamos la siguiente combinación de
teclas: ALT + INS y en el menu desplegado pulsamos Test, alli seleccionamoe los métodos que dentro de la clase queremos
testear.*/
public class ExampleTest {


    /*[Test.2]Los métodos siempre retornan void y se pone la anotación @Test que viene precedido de JUnit y su nombre es
    el mismo pero precedidto de la palabra test, asi: testSumar.*/
    @Test
    public void testSumar() {
        Example example = new Example(); //Instanciamos la clase
        int result = example.sumar(4,4); //llano el méthod lo evaluo, yo espero un 8 como resultado.

        //[Test.3]Para realizar dicha evaluacion utilizo los Assertions de Junit con su method assertEquals.
        //Hago el Assertions como importación estática.
        /*[Test.4]Assertions mas Importantes: */
        //assertEquals: Evaluar un valor esperado con un valor actual.
        assertEquals(8, result); //Primero el valor esperado y segundo el resultado real.
        //assertTrue o assertFalse: Valida que yo tenga un verdadero o falso respectivamente, es un boolean.
        assertTrue(result > 1);
        //assertNotNull: Vlaida que el objeto respuesta no sea nulo.
        assertNotNull(result);
        //assertInstanceOf: Valído el tipo de objeto que tengo(clase esperada - clase obtenda)
        assertInstanceOf(Integer.class, result);
        //assertThrows: Valída excepciones en caso de retornar alguna excepecion.
        //assertThrows();

        //Voy minuto 24 de:  Dominando los Test Unitarios en JAVA | JUnit

    }
}