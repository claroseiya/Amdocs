package Core.Operaciones;

/**
*
* @author Cristophe Carlier
*/
public class OpcionesEjecucion {
	// Cargamos las dependecias necesarias para ejecuciones web.
	public static Boolean IniciarSelenium = true;
	
	// Indicamos ejecucion web en chrome con el navegador visible u oculto.
	public static Boolean ChromeHeadless = false;
	
	// Cargamos las dependecias necesarias para appium android.
	public static Boolean IniciarAppiumAndroid = false;
	
	// Carga las dependecias necesarias para ejecucion de escritorio.
	public static Boolean IniciarAutoIt = true;
	
	// Indicamos el nombre de dispositivo a utilizar para ejecuciones de android.
	public static String NombreDispositivo = "HUAWEIP20CRIS";
}