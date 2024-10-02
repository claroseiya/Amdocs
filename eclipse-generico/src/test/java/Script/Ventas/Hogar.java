package Script.Ventas;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.sikuli.script.FindFailed;

public class Hogar {

	Business.Operaciones Oper  = new  Business.Operaciones();
	Business.OperacionesHogar OperHogar  = new  Business.OperacionesHogar();
	Business.Amdocs      Amdocs= new  Business.Amdocs();
	
Core.Propiedades.Propiedades Propi = new Core.Propiedades.Propiedades();
	
	String Ambiente=Propi.GetValue("Amdocs", "Ambiente");
	
	public void Venta(Hashtable<String, String> Data) throws Exception
	{
		String ID = Data.get("ID");
		String Plan = Data.get("NombrePlan");
		String Rut  = Data.get("Rut");
		String TipoCliente  = Data.get("TipoCliente");
		String Reasoncode  = Data.get("ReasonCode");
		String TipoAdquisicion = Data.get("TipoAdquisicion");
		String SkuEquipo = Data.get("SkuEquipo");
		String Servicio = Data.get("Tipo");
		String ServicioAdicional = Data.get("ServicioAdicional");
		String CBPPosicion = "Inicio";
		
		String Play1 = Data.get("Play1");
		String Play2 = Data.get("Play2");
		String Play3 = Data.get("Play3");
		String Mail = Data.get("Mail");
		
		
		String Planes = Play1+Play2+Play3;
		
		String FA = "SinFA";
		//String FA = "FA";
		
		//if(Rut.equalsIgnoreCase("null"))
		//	FA ="FA";
		System.out.println(FA);
				
		Amdocs.IniciarAmdocs();
		Oper.VentaCliente(Data);		
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");		  	
	    Oper.DireccionDeInstalacion();			
	    Oper.Acreditacion(TipoCliente, "", "");   
	    
	    OperHogar.SeleccionarPlan(Data);	
	    
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);				
		
		
		OperHogar.configuracionSaltar(Planes);
		OperHogar.NegociarOpciones();
		OperHogar.NegociarDistribucion(Mail);
		OperHogar.resumenOrden(Data);
		//String Orden = Oper.paginaOrden();
		//Oper.BuscarEstadoOrden("Orden");
	    
	    
			
	}		
}
