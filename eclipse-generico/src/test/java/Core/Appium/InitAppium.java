package Core.Appium;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import Core.Operaciones.OpcionesEjecucion;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class InitAppium {
	private static AppiumDriver<WebElement>Driver   =null;
	//private static WebDriver Driver;
	
	
	public InitAppium() {
		
	}
	
	public AppiumDriver<WebElement> getDriver() {
		return Driver;
	}	

	public void IniciarAplicacion() throws MalformedURLException, InterruptedException {
				
	    Core.Propiedades.Propiedades Prop = new Core.Propiedades.Propiedades();
	    
	    String Udid 		= Prop.GetValue("Movil", "Udid");
	    String deviceName 	= Prop.GetValue("Movil", "deviceName");
	    String appPackage 	= Prop.GetValue("Movil", "appPackage");
	    String appActivity 	= Prop.GetValue("Movil", "appActivity");
	    
	    System.out.println("Iniciando cel "+ deviceName);
	    
		
		if(Driver!=null)
			Driver.quit();

		
		DesiredCapabilities Capabilities = new DesiredCapabilities(); // Created object of DesiredCapabilities class.
		//Capabilities.setCapability("deviceName", OpcionesEjecucion.NombreDispositivo); // Set android deviceName desired capability. Set your device name.
		//Capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); // Set BROWSER_NAME desired capability. It's Android in our case here.

		// Set android VERSION desired capability. Set your mobile device's OS version.
		//Capabilities.setCapability(CapabilityType.VERSION, "4.4.2");

		// Set android platformName desired capability. It's Android in our case here.
		Capabilities.setCapability("platformName", "Android");
		Capabilities.setCapability("udid", "35383148345a3098");
		
		//Capabilities.setCapability("udid", "ce07182782f9793c04");//cellphone Claudio
		
		
		Capabilities.setCapability("deviceName", "Htc");
		Capabilities.setCapability("autoGrantPermissions", true);
		Capabilities.setCapability("appWaitDuration", 120000);
		//Capabilities.setCapability("automationName", "UiAutomator2");
		
		// Set android appPackage desired capability. It is
		// com.android.calculator2 for calculator application.
		// Set your application's appPackage if you are using any other app.
		Capabilities.setCapability("appPackage", "com.miclaro.app");

		// Set android appActivity desired capability. It is
		// com.android.calculator2.Calculator for calculator application.
		// Set your application's appPackage if you are using any other app.
		 Capabilities.setCapability("appActivity", "com.miclaro.app.view.activity.SplashVC");

		// Created object of RemoteWebDriver will all set capabilities.
		// Set appium server address and port number in URL string.
		// It will launch calculator app in android device.
		// Driver =  new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), Capabilities);
		 Driver =  new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), Capabilities);

		 Driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	public void FinalizaAplicacion() {		
			Driver.quit();
	}
}
