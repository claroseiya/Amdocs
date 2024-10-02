package Business;

import cucumber.api.java.en.Given;

import cucumber.api.java.en.When;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.en.Then;


import java.io.IOException;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import Core.Selenium.AccionesWeb;
import Core.Selenium.InitSelenium;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;



public class Steps{
    	
	public static WebDriver Driver = null;
	public String Plataforma="Amdocs";
    Core.Ini.LeerData DataIni = new Core.Ini.LeerData();
    Core.Propiedades.Propiedades Propie = new Core.Propiedades.Propiedades(); 
    Amdocs amdocs = new Amdocs();    
    Operaciones operaciones = new Operaciones();
 	
    @Dado("Se da inicio Amdocs")
	public void InicioAmdocs() throws InterruptedException, IOException, UnsupportedFlavorException, FindFailed, AWTException{
    	
    	Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
    	Event.RunTestExcel();
    	
	}
    
    @Dado("Se da inicio AmdocsEcommerce")
	public void InicioAmdocsEcommerce() throws InterruptedException, IOException, UnsupportedFlavorException, FindFailed, AWTException{
    	
    	System.out.println("Llego acá");
    	Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
    	Event.RunTestExcelEcommerce();
    	
	}
	    
    @Dado("Se Crea un Cliente (.*)")
	public void CrearCliente(String Cliente) throws InterruptedException, IOException, UnsupportedFlavorException, FindFailed{
    	
    	operaciones.CrearPersona(false);
	}
}
