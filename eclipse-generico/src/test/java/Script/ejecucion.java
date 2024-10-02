package Script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.spi.LoggerFactory;



public class ejecucion {
	
	
	
	public static void main(String[] args) {
		
		String dato = "";
		String str = "Orden 632283545A : 1 acciones de orden";
		String [] cadenas = str.split("[^0-9_A].");
		
		for(int i = 0; i<cadenas.length; i++){
			 dato = dato+cadenas[i];
	     }
		
		System.out.println(dato);
		
		
	}

}
