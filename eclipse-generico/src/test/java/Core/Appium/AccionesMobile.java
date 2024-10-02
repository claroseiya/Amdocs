package Core.Appium;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Core.Xml.LeerPasos;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;



public class AccionesMobile {
	private WebDriver Driver;
	
	public AccionesMobile() {

	}
	
    /**
    *
    * Hacemos un click en el elemento indicado con el resource id.
    * @param ResourceId El resource id del elemento al cual vamos a hacer un click, utilizar un inspector como UI Automator Viewer (Android-SDK)
     * @throws InterruptedException 
    */  
	public void Click(String Nombre, String Plataforma) throws InterruptedException{
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		Elem.click();
	}
	
    /**
    *
    * Hacemos un click en el elemento indicado con el resource id.
    * @param ResourceId El resource id del elemento al cual vamos a hacer un click, utilizar un inspector como UI Automator Viewer (Android-SDK)
    */  
	public void AlertAccept(String Plataforma){
	
	}
	
	 /**
    *
    * Ingresamos un texto en el elemento indicado con el resource id.
    *   @param Plataforma, nombre del archivo donde se encuentra el xml
    *   @param Nombre, Nombre del nodo paso
    *   @param Value, valor que se desea ingresar.
	 * @throws InterruptedException 
    */  
	public void InputText(String Value, String Nombre, String Plataforma) throws InterruptedException{
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		Elem.sendKeys(Value);
	}
	
	public void ClearText(String Nombre, String Plataforma) throws InterruptedException{
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		Elem.clear();
	}
	
    /**
    *
    * Leemos el texto en un elemento de tipo resource id indicado.
    * @param ResourceId El resource id del elemento al cual vamos a leer.
    * @throws InterruptedException 
    */      
	public String LeerElemento(String Nombre, String Plataforma) throws InterruptedException {
		// Iniciamos el objeto del webdriver.
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		String Resultado = Elem.getText();
		// Imprimimos y devolvemos el resultado.
		System.out.println("Lectura: " + Resultado);
		return Resultado;
	}
    
	 public WebElement FindElementoWait(String Nombre, String Plataforma, int TimeOut) throws InterruptedException {
		 System.out.println(".. buscando elemento "+Nombre);
		 WebElement Elem=null;
		for(int i=0;i<TimeOut;i++)
		{
			try {Elem = FindElemento(Nombre,Plataforma);}
			catch(Exception e) {System.out.println(".. buscando elemento "+Nombre);Thread.sleep(1000);} 
			if(Elem!=null)return Elem;
			
		}
		return Elem;
	}
	
	

	public WebElement FindElemento(String Nombre, String Plataforma) throws InterruptedException {
		
		WebElement Elem=null;
		InitAppium Appium = new InitAppium();
		Driver = Appium.getDriver();
		
		Driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
		
		Nombre = Nombre.replace("\"", "");	
		Plataforma = Plataforma.replace("\"", "");	
		//Leer Xpath de los archivos xml
		Core.Xml.LeerPasos xml = new Core.Xml.LeerPasos(); 
		List <String> Atributos = xml.getxmlSucursalVirtual(Nombre,Plataforma);
	   if (Atributos == null)
			System.out.println("Objeto de nombre " + Nombre + " No ha se ha encontrado");
		
		String TipoBy =  Atributos.get(0);
		String Xpath  =  Atributos.get(1);
		
		
		switch(TipoBy)
		{
			case"Xpath":
					Elem = Driver.findElement(By.xpath(Xpath));
				break;
			case"Id":
				Elem = Driver.findElement(By.id(Xpath));
			break;
			case"LinkText":
				Elem = Driver.findElement(By.linkText(Xpath));
			break;
		}
		
		return Elem;
	}
	
	public boolean ExisteElemento(String Nombre, String Plataforma, int TimeOut) throws InterruptedException 
	{
		 WebElement Elem=null;
			for(int i=0;i<TimeOut;i++)
			{
				try {Elem = FindElemento(Nombre,Plataforma);}
				catch(Exception e) {System.out.println(".. buscando elemento "+Nombre);Thread.sleep(1000);}
				if(Elem!=null)return true;				
			}
			
		return false;
	}
	
	public void ClickCoordenadas(int x, int y) throws InterruptedException {		
		// Java

		 new TouchAction((PerformsTouchActions) Driver).tap(new PointOption().withCoordinates(x, y)).perform();
	
	}	
	
	public void Scroll()
	{	
		Dimension size = Driver.manage ()
			    .window ()
			    .getSize ();
			int startX = size.getWidth () / 2;
			int startY = size.getHeight () / 2;
			int endX = 0;
			int endY = (int) (startY * -1 * 0.75);
			
			new TouchAction((PerformsTouchActions) Driver).press(new PointOption().withCoordinates(startX, startY)).moveTo(new PointOption().withCoordinates(endX, endY)).perform();
	}
	
	public void ScrollMenu()
	{		
	       
			int startX = 249;
			int startY = 1018;
			int endX = 251;
			int endY = 440;
			
			TouchAction touch = new TouchAction((PerformsTouchActions) Driver);			
			touch.press(new PointOption().point(startX, startY))
			.waitAction()
			.moveTo(new PointOption().point(endX, endY))
			.release()
			.perform();
		   
			
	}
	
	  public void swipe()
	    {  
	    JavascriptExecutor js = (JavascriptExecutor) Driver;
	    HashMap<String, Double> scrollObject = new HashMap<String, Double>();
	    scrollObject.put("startX", 0.95);
	    scrollObject.put("startY", 0.5);
	    scrollObject.put("endX", 0.05);
	    scrollObject.put("endY", 0.5);
	    scrollObject.put("duration", 1.8);
	    js.executeScript("mobile: scrollBackTo", scrollObject);
	    
	    }

	public void hideKeyboard() {
		return;
	}


	
	
}
