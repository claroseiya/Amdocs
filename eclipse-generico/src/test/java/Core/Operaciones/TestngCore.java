package Core.Operaciones;

import java.io.IOException;
import org.testng.annotations.*;
import Core.AutoIt.InitAutoIt;
import Core.Operaciones.OpcionesEjecucion;
import Core.Operaciones.Reporteria;
import Core.Selenium.InitSelenium;

/**
*
* @author Cristophe Carlier
*/
public class TestngCore {
	private Reporteria Reporte = new Reporteria();
	
	@BeforeMethod
	public void beforeMethod() throws IOException {
		// Enviamos estatus por consola.
		System.out.println("Inicializando reporteria para el caso de prueba...");
		
		// Limpiamos y reestablecemos la reporteria para el siguiente caso de prueba.
		Reporte.IniciarReporte();
	}

	@AfterMethod
	public void afterMethod() throws IOException, InterruptedException {
		// Enviamos estatus por consola.
		System.out.println("Finalizando reporteria para el caso de prueba...");

		// Finalizamos la reporteria y generamos la evidencia necesaria.
		Reporte.FinalizarReporte();
	}

	@BeforeClass
	public void beforeClass() throws IOException, InterruptedException {
		// Enviamos estatus por consola.
		System.out.println("Cargando dependencias necesarias...");
		
		if (OpcionesEjecucion.IniciarAppiumAndroid == true) {
	        // Iniciamos el servidor y dependencias para appium.
		}
		
		if (OpcionesEjecucion.IniciarSelenium == true) {
			// Iniciamos el driver y dependencias para selenium.
			InitSelenium Selenium = new InitSelenium();
        	Selenium.IniciarSelenium();
		}
		
		if (OpcionesEjecucion.IniciarAutoIt == true) {
	        // Iniciamos el driver y dependencias para winium.
			InitAutoIt AutoIt = new InitAutoIt();
			AutoIt.IniciarAutoIt();
		}
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		// Enviamos estatus por consola.
		System.out.println("Cerrando dependencias necesarias...");

		if (OpcionesEjecucion.IniciarSelenium == true) {
			// Finalizamos las dependencias para selenium.
			InitSelenium Selenium = new InitSelenium();
        	Selenium.FinalizarSelenium();
		}
		
		if (OpcionesEjecucion.IniciarAppiumAndroid == true) {
	        // Finalizamos las dependencias para appium.
	        // [En Construccion] ...
		}
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		// Enviamos estatus por consola.
		System.out.println("Iniciando ejecucion...");
	}

	@AfterTest
	public void afterTest() throws IOException {
		// Enviamos estatus por consola.
		System.out.println("Finalizando ejecucion...");
	}

	@BeforeSuite
	public void beforeSuite() throws IOException {

	}

	@AfterSuite
	public void afterSuite() throws InterruptedException {

	}
}