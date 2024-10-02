/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Operaciones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Cristophe Carlier
 */
public class Reporteria {
    private FileWriter ArchRepo;
    private BufferedWriter Salida;
    private String CarpetaUsuario;
    public String FechaReporte;    
    
    public Reporteria() {

    }

    /**
    *
    * Iniciamos la reporteria para evidencias.
    */    
    public void IniciarReporte() throws IOException {
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	ReiniciarCarpetaReportes();
    	
    	// Obtenemos el id del hilo en el cual estamos ejecutando.
    	long IdHilo = Thread.currentThread().getId();
    	
	    // Limpiamos la carpeta de capturas de reporte.
	    File CarpetaRep = new File(CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//capturas");
	    FileUtils.cleanDirectory(CarpetaRep);
	    
	    // Restablecemos el archivo de reportes.
	    ArchRepo = new FileWriter(CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//reporte_actual.html");
	    Salida = new BufferedWriter(ArchRepo);
	    Salida.write("<html><body style='font-family: verdana;'><div style='font-size: 16px;'><b>Evidencia de Ejecucion</b><br/><br/></div>");
	    Salida.close();
    }

    /**
    *
    * Registramos evidencia de ejecución con una captura y una breve descripcion.
    * @param Img La ruta de la imagen capturada durante la ejecucion.
    * @param Comentario La descriopcion que indica la accion relacionada a la imagen capturada.
    */        
    public void AgregarReporte(String Img, String Comentario) throws IOException {
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	CarpetaUsuario = System.getProperty("user.home");
    	
    	// Obtenemos el id del hilo en el cual estamos ejecutando.
    	long IdHilo = Thread.currentThread().getId();
    	
        // Añadimos evidencia al archivo de reportes.
        ArchRepo = new FileWriter(CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//reporte_actual.html", true);
        Salida = new BufferedWriter(ArchRepo);
        Salida.write("<div style='font-size: 11px;'>" + Comentario + "<br/><img style='border: solid 1px gray' src='capturas/" + Img + "'/><br/><br/></div>");
        Salida.close();
    }
    
    /**
    *
    * Cerramos el archivo de reporte y lo procesamos como PDF.
    */       
    public void FinalizarReporte() throws IOException {
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	CarpetaUsuario = System.getProperty("user.home");
    	
    	// Obtenemos el id del hilo en el cual estamos ejecutando.
    	long IdHilo = Thread.currentThread().getId();
    	
        // Añadimos cierre al archivo de reportes.
        ArchRepo = new FileWriter(CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//reporte_actual.html", true);
        Salida = new BufferedWriter(ArchRepo);
        Salida.write("</body></html>");
        Salida.close();
        
        // Realizamos una copia del reporte actual.
        /* File Original = new File("src//test//java//Reportes//reporte_actual.html");
        File Destino = new File("src//test//java//Reportes//reporte_" + FechaReporte + ".html");
        FileUtils.copyFile(Original, Destino); */       
    }
    
    /**
    *
    * Creamos la carpeta para reporteria en la carpeta del usuario si esta no existe y devolvemos la ruta de la carpeta del mismo usuario.
    */       
    public void ReiniciarCarpetaReportes() {
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	CarpetaUsuario = System.getProperty("user.home");
    	
    	// Obtenemos el id del hilo en el cual estamos ejecutando.
    	long IdHilo = Thread.currentThread().getId();
    	
        // Definimos la ruta para ambas carpetas, raiz y la de capturas.
        String RutaRaiz = CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo;
        String RutaCapturas = CarpetaUsuario + "//Reportes Eclipse//Ejecucion Hilo " + IdHilo + "//capturas";
        
        // Creamos la carpeta raiz si esta no existe.
        File CarpetaRaiz = new File(RutaRaiz);
        if (!CarpetaRaiz.exists())
       	CarpetaRaiz.mkdir();
        
        // Creamos la carpeta de capturas, dentro de la raiz, si esta no existe.
        File CarpetaCapturas = new File(RutaCapturas);
        if (!CarpetaCapturas.exists())
       	CarpetaCapturas.mkdir();
    }
}
