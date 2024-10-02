/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Selenium;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import Core.Operaciones.OpcionesEjecucion;

/**
 *
 * @author Cristophe Carlier
 */
public class InitSelenium {
    private static ThreadLocal<WebDriver> Driver = new ThreadLocal<WebDriver>();
      
    public InitSelenium(){

    }
    
    /**
    *
    * Devolvemos el driver solicitado para selenium.
    * @return Selenium webdriver. 
    */        
    public static WebDriver getDriver(){
    	return Driver.get();
    }
    
    /**
    *
    * Iniciamos la ejecucion de las pruebas y la navegacion. 
    */        
    public void IniciarSelenium() throws IOException {
        // Obtenemos el nombre del sistema operativo actual.
        String SistemaOperativo = System.getProperty("os.name");
        System.out.println("Sistema Operativo detectado: " + SistemaOperativo);
        
        // Seleccionamos el chrome driver para windows.
        if(SistemaOperativo.contains("Windows"))
        	System.setProperty("webdriver.chrome.driver","src//test//resources//drivers//windows//Selenium//chromedriver.exe");
        
        // Seleccionamos el chrome driver para linux.
        else if(SistemaOperativo.contains("Linux"))
        	System.setProperty("webdriver.chrome.driver","src//test//java//Webdriver//chromedriver");
        
        // Establecemos los argumentos de ejecucion para el chrome dirver
        ChromeOptions Opciones = new ChromeOptions();
        
        // Agregamos la opcion de una ejecucion web en chrome con navegador oculto.
        if(OpcionesEjecucion.ChromeHeadless == true)
        	Opciones.addArguments("--headless");
        
        // Establecemos la dimension de la ventana del navegador en ejecucion.
        Opciones.addArguments("--window-size=1360,768");

        // Iniciamos el driver para la navegacion.
        Driver.set(new ChromeDriver(Opciones));
    }
    
    /**
    *
    * Finalizamos la navegacion y la ejecucion.
    */    
    public void FinalizarSelenium() throws InterruptedException{
        // Luego de una breve pausa de 2 segundos, finalizamos la sesion en el navegador.
        Thread.sleep(2000);
        Driver.get().quit();
    }
}
