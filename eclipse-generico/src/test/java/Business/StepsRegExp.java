package Business;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

import Core.Appium.InitAppium;
import Core.AutoIt.InitAutoIt;
import Core.Cucumber.Runner;
import Core.Xml.LeerPasos;
import autoitx4java.AutoItX;
import cucumber.api.Scenario;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;



import java.util.ArrayList;
import java.util.List;


public class StepsRegExp extends Runner{
	
	public static WebDriver Driver = null;
	public String Aplicacion="SucursalVirtual";
    Core.Ini.LeerData DataIni = new Core.Ini.LeerData();
    Core.Propiedades.Propiedades Propie = new Core.Propiedades.Propiedades(); 
    Core.Selenium.AccionesWeb Acciones = new Core.Selenium.AccionesWeb();
		
	@Entonces("[A-Za-z ]* Plataforma (.*)")
	public void OpenPlataforma(String Nombre) throws IOException
	{
		Nombre = Nombre.replace("\"", "");		
		AbrirPlataforma(Nombre);
	}
	
	
	@Entonces("[A-Za-z ]* Click (.*)")
	public void Click(String Nombre) throws IOException, InterruptedException
	{		
		Acciones.Click(Nombre,Aplicacion);
	}
	
	@Entonces("[A-Za-z ]* CompararTexto (.*) Con texto (.*)")
	public void CompararTexto(String Nombre,String Text) throws InterruptedException
	{
		String TextCel = Acciones.LeerElemento(Nombre, Aplicacion);	
		
		System.out.println(TextCel);
		
	}
	
	@Entonces("[A-Za-z ]* CompararTexto Ini (.*) cliente (.*) key (.*)")
	public void CompararTextoIni(String Nombre,String Cliente, String Key) throws InterruptedException
	{
		Nombre = Nombre.replace("\"", "");
		Key    = Key.replace("\"", "");
		
		String ValueElem = Acciones.LeerElemento(Nombre, Aplicacion);	
		String Value     = DataIni.GetValue("Clientes",  Cliente, Key);
		
		if(Value == null)
			throw new java.lang.RuntimeException("No se encuentra "+Cliente+ " o Llave "+Key );
		else if(!ValueElem.contains(Value))
			throw new java.lang.RuntimeException(ValueElem+ " es distinto a: "+Value);
		
	}
	
	
	@Entonces("[A-Za-z ]* CompararValue (.*) Con texto (.*)")
	public void CompararValue(String Nombre,String Text)
	{
		Nombre = Nombre.replace("\"", "");
		Text = Text.replace("\"", "");
		System.out.print("VerifyValueByXml " + Nombre + "- " + Text);
	
			
	}
	
	@Entonces("[A-Za-z ]* Comparar Maximo Caracteres (.*) Con (.*)")
	public void CompararCaracteres(String Nombre,String Text)
	{
		Nombre = Nombre.replace("\"", "");
		Text = Text.replace("\"", "");
		System.out.print("CompararCaracteres " + Nombre + "- " + Text);	
		
	}
	
	@Entonces("[A-Za-z ]* Hover (.*)")
	public void Hover(String Nombre)
	{
		Nombre = Nombre.replace("\"", "");	
		
		System.out.print("Hover " + Nombre );	
	}
	
	@Entonces("[A-Za-z ]* HoverLink (.*)")
	public void HoverLink(String Nombre)
	{
		Nombre = Nombre.replace("\"", "");	
		
		System.out.print("HoverLink " + Nombre );	
	}
	
	@Entonces("[A-Za-z ]* Input (.*) Con texto (.*)")
	public void Input(String Nombre,String Text) throws InterruptedException
	{
		Nombre = Nombre.replace("\"", "");
		Text = Text.replace("\"", "");
		Acciones.ClearText(Nombre, Aplicacion);
		Acciones.InputText(Text, Nombre, Aplicacion);	
	}
	
	@Entonces("[A-Za-z ]* Frame (.*)")
	public void Frame(String Nombre)
	{
		Nombre = Nombre.replace("\"", "");
			
		System.out.print("Frame " + Nombre );
	}
	
	@Entonces("[A-Za-z ]* FrameExterno (.*)")
	public void FrameExterno(String Nombre)
	{
		
	}
	
	@Entonces("[A-Za-z ]* Combobox (.*) con (.*)")
	public void Seleccionar(String Nombre,String Text)
	{
		Nombre = Nombre.replace("\"", "");
		Text = Text.replace("\"", "");	
		
	}
	
	@Entonces("[A-Za-z ]* Espero (.*)")
	public void Espero(String Tiempo)
	{
		System.out.println("voy a esperar "+Tiempo);
		Tiempo = Tiempo.replace("\"", "");	
		int time = Integer.parseInt(Tiempo);
	    
	}
	
	@Entonces("[A-Za-z ]* Nombre del Link (.*)")
	public void NameLink(String Nombre)
	{
		Nombre = Nombre.replace("\"", "");	
		
		System.out.println("ClickBylinkText "+ Nombre);
	}
	
	
	
	
	
	
	@Entonces("Retroceder Pagina")
	public void RetrocerPagina()
	{
		
	}
	
	@Entonces("Esperar que cargue la Pagina")
	public void CargarPagina()
	{
		
	}
	
	@Entonces("Pagina Nueva")
	public void NuevaPagina()
	{
		
	}
	
	@Entonces("Pagina Antigua")
	public void PaginaAntigua()
	{
		
	}
	
	@Entonces("Cerrar Pestana")
	public void CerrarPestana()
	{
		
	}	

}
