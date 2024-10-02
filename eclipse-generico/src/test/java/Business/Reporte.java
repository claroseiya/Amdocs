package Business;

import java.io.IOException;
import java.util.Hashtable;

import Core.Excel.AccionesExcel;

public class Reporte {
	

	
	
	public void UpdateTituloExcel(String Value, int Cell)
	{
		Business.EventHandlerCucumber event = new Business.EventHandlerCucumber(); 
		Core.Excel.AccionesExcel excelAccion = new Core.Excel.AccionesExcel();
		excelAccion.ReemplazarLineaExcel(event.NombreExcel, Value, Cell);
	}
	
	public int GetAmbiente() throws IOException
	{
		Business.EventHandlerCucumber event = new Business.EventHandlerCucumber(); 
		Core.Excel.AccionesExcel excelAccion = new Core.Excel.AccionesExcel();
		
		int Amb=0;
		String Ambiente = excelAccion.LecturaCeldaExcel("Transaciones", event.HojaExcel, event.CeldaAmbiente);
		
		System.out.println("Se selecciona el Ambiente: " + Ambiente);
		
		if(Ambiente.contains("4"))
           Amb=4;
		else if(Ambiente.contains("8"))
	           Amb=8;
		else if(Ambiente.contains("10"))
	           Amb=10;
		else if(Ambiente.contains("12"))
	           Amb=12;
		else if(Ambiente.contains("2"))
	           Amb=2;
		else if(Ambiente.contains("3"))
	           Amb=3;
		return Amb;
	}
	

		
	public void TemplateSalida(String Tipo)
	{
		Business.EventHandlerCucumber event = new Business.EventHandlerCucumber(); 
		Core.Excel.AccionesExcel excelAccion = new Core.Excel.AccionesExcel();
		
		Hashtable<String, String> Columna= new Hashtable<String, String>();
		
		switch(Tipo)
		{
			case"AgregarEliminarServicio":
				Columna.put("A", "ID");
				Columna.put("B", "Caso");
				Columna.put("C", "Estado");
				Columna.put("D", "Telefono");
				Columna.put("E", "Orden");
				Columna.put("F", "Request");
				Columna.put("G", "Response");
				Columna.put("H", "");
				Columna.put("I", "");
				Columna.put("J", "");
				Columna.put("K", "");			
				Columna.put("L", "");
				Columna.put("M", "Duración Total Prueba");
				Columna.put("N", "Duración Prueba");
				Columna.put("O", "Duración Errores");
				excelAccion.ReemplazarLineasExcel(event.NombreExcel, Columna);
			 break;
			case"CambioOferta":
				Columna.put("A", "ID");
				Columna.put("B", "Caso");
				Columna.put("C", "Estado");
				Columna.put("D", "Telefono");
				Columna.put("E", "Plan");
				Columna.put("F", "Orden");
				Columna.put("G", "Request");
				Columna.put("H", "Response");				
				Columna.put("I", "");
				Columna.put("J", "");
				Columna.put("K", "");			
				Columna.put("L", "");
				Columna.put("M", "Duración Total Prueba");
				Columna.put("N", "Duración Prueba");
				Columna.put("O", "Duración Errores");
				excelAccion.ReemplazarLineasExcel(event.NombreExcel, Columna);
			break;
			case"TraspasoTitularidad":
				Columna.put("A", "ID");
				Columna.put("B", "Caso");
				Columna.put("C", "Estado");
				Columna.put("D", "Telefono");
				Columna.put("E", "Descripcion");
				Columna.put("F", "Orden");
				Columna.put("G", "Request");
				Columna.put("H", "Response");				
				Columna.put("I", "");
				Columna.put("J", "");
				Columna.put("K", "");			
				Columna.put("L", "");
				Columna.put("M", "Duración Total Prueba");
				Columna.put("N", "Duración Prueba");
				Columna.put("O", "Duración Errores");
				excelAccion.ReemplazarLineasExcel(event.NombreExcel, Columna);
			break;
		}
	}
}
