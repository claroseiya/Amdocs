package Core.AutoIt;

import java.awt.AWTException;
import java.io.IOException;

import Core.Operaciones.Screenshots;
import autoitx4java.AutoItX;

/**
 *  
 * @author Ricardo Gatica
 */
public class AccionesWindows {
	private AutoItX AutoIt;

	/**
	*  
	* Constructor de la clase.
	*/
	public AccionesWindows() {

	}

	/**
	 * Ingresamos un texto en un elemento de la ventana indicada.
	 * @param Ventana Titulo de la ventana de la aplicacion.
	 * @param Elemento Elemento al cual ingresar el texto.
	 * @param Texto Texto a ingresar.
	 * @param Capturar Indicamos si vamos a registrar la accion en reporteria, dejar parametro Comentario en 'null' si no se va a utilizar.
	 * @param Comentario El comentario personalizado que se reflejará en reporteria, de lo contrario, se registrará un comentario simple de la acción.
	 */
	public void IngresarTexto(String Ventana, String Elemento, String Texto, Boolean Capturar, String Comentario) throws InterruptedException, IOException, AWTException
	{	
		// Obtenemos el objeto iniciado de autoit.
		AutoIt = InitAutoIt.getAutoit();
		
		// Seleccionamos el elemento en la ventana indicada.
	    AutoIt.controlFocus(Ventana, "", Elemento);
	    Thread.sleep(500);
	    
		// Ingresamos el texto indicado.
	    AutoIt.send(Texto);
	    Thread.sleep(500);
	    
        // En caso que sea requerido, realizamos una captura del campo de texto con el texto ingresado.        
        if (Capturar == true){
        	// Creamos el objeto para captura de la accion.
        	Screenshots Captura = new Screenshots();
            
            // Realizamos una captura de la accion para registrar en el reporte.
        	if(Comentario != "") {
        		// Además de la captura, ingresamos un comentario personalizado.
        		Captura.CapturaElementoEscritorio(Ventana, Elemento, Comentario);
        	}
        	
        	else {
        		// Si no existe un comentario persoinalizado, ingresamos un comentario basico de la acción.
        		Captura.CapturaElementoEscritorio(Ventana, Elemento, "Ingresamos el texto: " + Texto + " en el campo: " + Elemento);        	
        	}
        }    
	}
	
	/**
	 * Hacemos un click en un elemento de la ventana indicada
	 * @param Ventana Titulo de la ventana de la aplicacion.
	 * @param Elemento Elemento al cual ingresar el texto.
	 * @param Capturar Indicamos si vamos a registrar la accion en reporteria, omitir parametro 'Comentario' si no se va a utilizar.
	 * @param Comentario El comentario personalizado que se reflejará en reporteria, de lo contrario, se registrará un comentario simple de la acción.
	 */
	public void Click(String Ventana, String Elemento, Boolean Capturar, String Comentario) throws InterruptedException, IOException, AWTException
	{
		// Obtenemos el objeto iniciado de autoit.
		AutoIt = InitAutoIt.getAutoit();
	    Thread.sleep(500);
		
        // En caso que sea requerido, realizamos una captura del campo de texto con el texto ingresado.        
        if (Capturar == true){
        	// Creamos el objeto para captura de la accion.
        	Screenshots Captura = new Screenshots();
            
            // Realizamos una captura de la accion para registrar en el reporte.
        	if(Comentario != "") {
        		// Además de la captura, ingresamos un comentario personalizado.
        		Captura.CapturaElementoEscritorio(Ventana, Elemento, Comentario);
        	}
        	
        	else {
        		// Si no existe un comentario persoinalizado, ingresamos un comentario basico de la acción.
        		Captura.CapturaElementoEscritorio(Ventana, Elemento, "Hacemos click en el objeto: " + Elemento);        	
        	}
        }  
	    
		// Hacemos click el elemento en la ventana indicada.
	    AutoIt.controlClick(Ventana, "", Elemento);
	    Thread.sleep(500);
	}
	
	/**
	 * Ejecutar aplicacion de windows.
	 * @param Ruta Ruta de la aplicacion la cual se va a ejecutar.
	 * @param Aplicacion Ejecutable el cual se encuentra del de la Ruta definida.
	 * @param Ventana Titulo de la ventana de la aplicacion a ejecutar.
	 */	
	public void IniciarAplicacion(String Ruta, String Aplicacion, String Ventana) {
		// Obtenemos el objeto iniciado de autoit.
		AutoIt = InitAutoIt.getAutoit();
		
		// Indicamos la ruta y el nombre de aplicacion, lo ejecutamos y lo mostramos.
		AutoIt.run(Aplicacion, Ruta, AutoItX.SW_SHOW);
		
		// Nos situamos en la ventana de la aplicacion ejecutada.
	    AutoIt.winActivate(Ventana);
	    AutoIt.winWaitActive(Ventana);
	}

	/**
	 * Cerrar aplicacion de windows.
	 * @param Ventana Titulo de la ventana de la aplicacion a cerrar.
	 */	
	public void CerrarAplicacion(String Ventana) throws InterruptedException {
		// Obtenemos el objeto iniciado de autoit.
		AutoIt = InitAutoIt.getAutoit();
		
		// Nos situamos en la ventana de la aplicacion ejecutada.
	    AutoIt.winActivate(Ventana);
	    AutoIt.winWaitActive(Ventana);
	    
	    // Cerramos la ventana con el titulo indicado.
	    AutoIt.winClose(Ventana);
	    Thread.sleep(1000);
	}

	/**
	 * @param objectWindow Objeto al cual mause se moverá
	 */
	public void MouseMove(String objectWindow)
	{
		AutoIt = InitAutoIt.getAutoit();
		
		int x  = PositionXObject(objectWindow);
		int y  = PositionYObject(objectWindow);
		
		AutoIt.mouseMove(x, y);
	}
	
	/**
	 * @param objectWindow Obtencion de la posicion X del objeto
	 */
	public int PositionXObject(String objectWindow)
	{	
		int x = AutoIt.controlGetPosX("", "", objectWindow);			
		return x;
	}
	
	/**
	 * @param objectWindow Obtencion de la posicion Y del objeto
	 */
	public int PositionYObject(String objectWindow)
	{
		int y = AutoIt.controlGetPosX("", "", objectWindow);		
		return y;
	}
}
