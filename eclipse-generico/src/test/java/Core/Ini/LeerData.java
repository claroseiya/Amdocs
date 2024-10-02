package Core.Ini;

import java.io.File;
import org.ini4j.*;

public class LeerData {
	
	public String GetValue(String Archivo, String Llave, String Campo)
	{
		String Value=null;
		
		try{
		 String RutaArchivo = "DataIni\\"+Archivo+".ini";
		 Wini ini = new Wini(new File(RutaArchivo));
		 Value = ini.get(Llave, Campo);		 
		 
		 }catch(Exception e){
		     System.err.println(e.getMessage());
		 }
		return Value;
	}
		
}
