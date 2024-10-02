/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core.Operaciones;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Core.AutoIt.InitAutoIt;
import Core.Selenium.InitSelenium;
import autoitx4java.AutoItX;

/**
 *
 * @author Cristophe Carlier
 */
public class Screenshots {
	private AutoItX AutoIt;
	private Reporteria Reporte;
    
    public Screenshots() {
        
    }
    
	/**
	 * Funcion llamada para capturar imagen de algun elemento o la ventana completa.
	 * @param Ventana Ventana en la cual se va a procesar la captura.
	 * @param Elemento El elemento indicado al cual vamos a realizar una captura.
	 * @param Comentario Una breve descripcion de lo realizado para la reporteria.
	 * @throws java.io.IOException
	 */
	public void CapturaElementoEscritorio(String Ventana, String Elemento, String Comentario) throws IOException, AWTException {
		// Cargamos el driver y realizamos una captura de la pantalla.
		AutoIt = InitAutoIt.getAutoit();
		Reporte = new Reporteria();	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot Robot = new Robot();
		BufferedImage Screenshot = Robot.createScreenCapture(screenRectangle);
		
		// Nos situamos en la ventana de la aplicacion ejecutada.
	    AutoIt.winActivate(Ventana);
	    AutoIt.winWaitActive(Ventana);
		
        if (Ventana != null) {
        	// Inicializamos las variables para guardar las coordenadas y las dimensiones.
			int Height = 0;
			int Width = 0;
			int PosX = 0;
			int PosY = 0;
        	
        	// Obtenemos las coordenadas de la ventana.
	    	int WinPosX = AutoIt.winGetPosX(Ventana);
	    	int WinPosY = AutoIt.winGetPosY(Ventana);
	    	
	    	if(Elemento != null) {
		    	// Obtenemos la dimension del elemento.
	        	Height = AutoIt.controlGetPosHeight(Ventana, "", Elemento) + 2;
	        	Width = AutoIt.controlGetPosWidth(Ventana, "", Elemento) + 2;
	        	
	        	// Obtenemos las coordenadas del elemento.
	        	PosX = AutoIt.controlGetPosX(Ventana, "", Elemento);
	        	PosY = AutoIt.controlGetPosY(Ventana, "", Elemento);	    		
	    	}
	    	 	
	    	else {
		    	// Obtenemos la dimension de la ventana.
		    	Height = AutoIt.winGetPosHeight(Ventana);
		    	Width = AutoIt.winGetPosWidth(Ventana);	    		
	    	}
        	
        	// Realizamos unos ajustes para centralizar correctamente el elemento o ventana.
        	int ResultPosX = WinPosX + PosX + 7;
        	int ResultPosY = WinPosY + PosY + 30;
        	
        	System.out.println(ResultPosX + ", " + ResultPosY + ", " + Width + ", " + Height);
        	
	        // Realizamos un recorte de la imagenes con las coordenadas obtenidas.
        	Screenshot = Screenshot.getSubimage(ResultPosX, ResultPosY, Width, Height);
        }
		
		// Obtenemos el indice para el siguiente archivos de captura.
		int Index = ObtenerIndice();
		   
		// Obtenemos la ruta principal de la carpeta del usuario de windows.
		String CarpetaUsuario = System.getProperty("user.home");
		long IdHilo = Thread.currentThread().getId();
		   
		// Guardamos la imagen en la carpeta de reporteria.
		String RutaImg = CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//capturas//captura_" + Index + ".png";
		String NombImg = "captura_" + Index + ".png";
		   
		FileOutputStream Archivo = new FileOutputStream(RutaImg);
		ImageIO.write(Screenshot, "png", Archivo);
		 
		// Registramos la captura en el reporte.
		Reporte.AgregarReporte(NombImg, Comentario);
	}
    
	/**
	 * Funcion llamada para capturar imagen de algun elemento.
	 * @param Elemento El elemento indicado al cual vamos a realizar una captura
	 * @param Comentario Una breve descripcion de lo realizado para la reporteria.
	 * @throws java.io.IOException
	 */
	public void CapturaElementoWeb(WebElement Elemento, String Comentario) throws IOException {
		// Imprimimos en consola captura y mensaje de la accion.
		System.out.println("Capturando accion: " + Comentario);

        // Cargamos el driver y realizamos una captura de la pantalla completa.
		WebDriver Driver = InitSelenium.getDriver();
        File Screenshot = ((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
        BufferedImage dest = ImageIO.read(Screenshot);
        Reporteria Reporte = new Reporteria();
        
        if (Elemento != null) {
	        // Obtenemos las coordenadas y la dimension del elemento.
	        Point Puntos = Elemento.getLocation();
	        int width = Elemento.getSize().getWidth();
	        int height = Elemento.getSize().getHeight();
	
	        // Realizamos un recorte de la imagenes con las coordenadas obtenidas.
	        BufferedImage img = ImageIO.read(Screenshot);
	        dest = img.getSubimage(Puntos.getX(), Puntos.getY(), width, height);
        }
        
        // Obtenemos el indice para el siguiente archivos de captura.
        int Index = ObtenerIndice();
        
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	String CarpetaUsuario = System.getProperty("user.home");
    	
    	// Obtenemos el id del hilo en el cual estamos ejecutando.
    	long IdHilo = Thread.currentThread().getId();
    	
        // Guardamos la imagen en la carpeta de reporteria.
        ImageIO.write(dest, "png", Screenshot);
        String RutaImg = CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//capturas//captura_" + Index + ".png";
        String NombImg = "captura_" + Index + ".png";
        FileUtils.copyFile(Screenshot, new File(RutaImg));
        
        // Registramos la captura en el reporte.
        Reporte.AgregarReporte(NombImg, Comentario);
    }
    
    /**
     * Funcion para onbtener el nombre del ultimo archivo modificado.
     */
    private String UltimoModificado() {
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	String CarpetaUsuario = System.getProperty("user.home");
    	
    	// Obtenemos el id del hilo en el cual estamos ejecutando.
    	long IdHilo = Thread.currentThread().getId();
    	
        // Leemos los archivos de la carpeta de capturas.
        File Archivo = new File(CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//capturas//");
        File[] Archivos;
        Archivos = Archivo.listFiles(File::isFile);
        
        // Iniciamos la busqueda indicando el ultimo modificado.
        long Ultimo = Long.MIN_VALUE;
        File Seleccion = null;
        for (File archivo : Archivos) {
            if (archivo.lastModified() > Ultimo) {
                Seleccion = archivo;
                Ultimo = archivo.lastModified();
            }
        }
        
        // Si el archivo no existe, no devolvemos nada.
        if(Seleccion == null){
            return "";
        }
        
        // De lo contrario, obtenemos el nombre del archivo y lo devolvemos.
        else {
            String Nombre = Seleccion.getName();
            return Nombre;
        }
    }
    
    /**
     * Funcion para onbtener el inidice del ultimo archivo modificado en la carpeta de capturas.
     */    
    private int ObtenerIndice() {
        int ImgIndex;
        
        // Obtenemos el ultimo archivo de la carpeta de reportes.
        String UltimoArch = UltimoModificado();

        // Si este es nulo, devolvemos como indice 1
        if ("".equals(UltimoArch)){
            ImgIndex = 1;
        }
            
        // De lo contrario, extraemos el numero de la captura realizada y lo aumentamos en un valor.
        else {
            UltimoArch = UltimoArch.replace("captura_", "");
            UltimoArch = UltimoArch.replace(".png", "");
            ImgIndex = Integer.parseInt(UltimoArch);
            ImgIndex++;
        }
        
        // Devolvemos el indice solicitado.
        return ImgIndex;
    }    
}
