package Core.ScreenShot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import Core.Appium.InitAppium;

public class ScreenShot {
	
	
	public void Capturar(String Ruta, String Nombre) throws AWTException
	{
	
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			File RutaRaiz = new File("Reportes\\Capturas\\01");
			File CarpetaRaiz = (RutaRaiz);
		    if (!CarpetaRaiz.exists())
		      	CarpetaRaiz.mkdir();
			
		        BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "png",new File(
					"Reportes\\Capturas\\01\\"+Nombre+".png"));
			
         } catch (IOException e) {
	}
		
		
	}

}
