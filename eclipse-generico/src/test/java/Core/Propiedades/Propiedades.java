package Core.Propiedades;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.ini4j.Wini;

import gherkin.deps.net.iharder.Base64.InputStream;

public class Propiedades {
	
	Properties config = new Properties();
    FileInputStream configInput = null;
   
	public String GetValue(String Archivo, String Atributo)
	{
		String Value="";
		try{
			String RutaArchivo = "DataPropiedades\\"+Archivo+".properties";
	            configInput = new FileInputStream(RutaArchivo);
	            config.load(configInput);
	            Value= config.getProperty(Atributo);	           
	        } catch(Exception e){	            
	        }
	    return Value;
	}
}
