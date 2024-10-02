package Core.Selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Core.Selenium.InitSelenium;

/**
*
* @author 
*/
public class AccionesWeb {    
	private WebDriver Driver;
	/**
    *
    * Constructor de la clase.
    */ 
	public AccionesWeb() {

	}

	public WebElement FindElemento(String Nombre, String Plataforma) throws InterruptedException {
		
		WebElement Elem=null;
		InitSelenium Selenium = new InitSelenium();
		Driver = Selenium.getDriver();
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

	public void Click(String Nombre, String Plataforma) throws InterruptedException{
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		Elem.click();
	}
	
    public void AlertAccept(String Plataforma){
	
	}
	
	public void InputText(String Value, String Nombre, String Plataforma) throws InterruptedException{
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		Elem.sendKeys(Value);
	}
	
	public void ClearText(String Nombre, String Plataforma) throws InterruptedException{
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		Elem.clear();
	}
	
    public String LeerElemento(String Nombre, String Plataforma) throws InterruptedException {
		// Iniciamos el objeto del webdriver.
		WebElement Elem = FindElementoWait(Nombre, Plataforma, 60);
		String Resultado = Elem.getText();
		// Imprimimos y devolvemos el resultado.
		System.out.println("Lectura: " + Resultado);
		return Resultado;
	}
    
    public void CompararString(String Value, String Nombre, String Plataforma) throws InterruptedException {
		
		String  Resultado = LeerElemento(Nombre, Plataforma);
		int time =60;
		for(int i=0;i<time;i++)
		{
			
			if(Resultado.contains(Value))
				return;
	        Thread.sleep(1000);
		}
		
		throw new java.lang.RuntimeException(Resultado+ " es distinto a: "+Value);
		
		
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
	
	public void AbrirDireccion(String Url) throws InterruptedException {
		// Solicitamos el driver de selenium ya iniciado.
		WebDriver Driver = InitSelenium.getDriver();
	    
	    // Hacemos una breve pausa antes de comenzar con la accion.
	    Thread.sleep(500);
		
	    // Navegamos a la url indicada.
	    Driver.get(Url);    
	    
	    // Hacemos una breve pausa antes de continuar.
	    Thread.sleep(500);
	}
    
    public void CloseDriver() {
    	 WebDriver Driver = InitSelenium.getDriver();
    	 Driver.close();
    	
    }
    
    public void Iframe() {
   	 WebDriver Driver = InitSelenium.getDriver();
   	 Driver.switchTo().frame(1);
   	
   }
}


