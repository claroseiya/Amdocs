package Core.AutoIt;

import java.io.File;
import java.io.IOException;
import autoitx4java.AutoItX;

/**
 *  
 * @author Ricardo Gatica
 * @author Cristophe Carlier
 */
public class InitAutoIt {
	public static AutoItX AutoIt;
	
	/**
	 * Constructor de la clase.
	 */
	public InitAutoIt() {
		
	}

	/**
	 * Devolver objeto ya iniciado de AutoIt.
	 */
	public static AutoItX getAutoit()
	{
		// Devolvemos el objeto iniciado de auto it.
		return AutoIt;
	}
	
	/**
	 * Inicializar objeto AutoIt.
	 */
	public void IniciarAutoIt() throws IOException {
		// Indicamos la ubicacion y definimos la propiedad de la libreria de jacob.
		String RutaJacob = new File("src\\test\\resources\\drivers\\windows\\AutoIt\\jacob-1.16.1-x64.dll").getAbsolutePath();		                             
		
		System.setProperty("jacob.dll.path", RutaJacob);

		// Iniciamos el objeto de autoit.
		AutoIt = new AutoItX();
		
	
	}
}
