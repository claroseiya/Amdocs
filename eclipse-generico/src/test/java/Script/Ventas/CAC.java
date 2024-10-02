package Script.Ventas;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import org.sikuli.script.FindFailed;

import Business.SoapAmdocs;

public class CAC {

	Business.Operaciones Oper  = new  Business.Operaciones();
	Business.Amdocs      Amdocs= new  Business.Amdocs();
	Core.Propiedades.Propiedades Propi = new Core.Propiedades.Propiedades();
	String Ambiente=Propi.GetValue("Amdocs", "Ambiente");
	Business.Reporte     Rep = new Business.Reporte();

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
			
		String FA = "SinFA";
		System.out.println("FA: "+FA);
			
		Amdocs.IniciarAmdocs();
		Oper.VentaCliente(Data);		
		Plan = Oper.VentaPoolShred(Data);	
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");		
		//if(Ambiente.equals("Ambiente12"))  	
			Oper.DireccionDeInstalacion();	
	    Oper.Acreditacion(TipoCliente, "", "");
		Oper.SeleccionarPlanBase(Plan);		
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);
		Oper.AgregarServicios(Servicio, ServicioAdicional);
		Oper.configuracion(TipoCliente, TipoAdquisicion, "123456789876543", "iphone", "7", false, false,SkuEquipo,Reasoncode);
		//Oper.ConsultaCredito();
		Oper.paginaDespacho("M");
		Oper.paginaDireccionEnvio("987654321");
		Oper.distribucionCargo("M", FA, TipoCliente);
		
		Oper.resumenOrden(Data);
		String Orden = Oper.paginaOrden();
		Oper.CerrarVentanaOrden();
		Oper.ManejoManual(Orden);
		Oper.paginaPoolOrden(Orden);
		Oper.ventanaSolicitudEquipo(false,false);
		//Oper.paginaPrincipalInteraccion(Orden);
		Oper.BuscarEstadoOrden("Orden");
		Oper.ValidaCiclo();
	   
	}
	
	public void VentaClaroUp(Hashtable<String, String> Data) throws Exception
	{
		String ID = Data.get("ID");
		String Plan = Data.get("NombrePlan");
		String Rut  = Data.get("Rut");
		String TipoCliente  = Data.get("TipoCliente");
		String Reasoncode  = Data.get("ReasonCode");
		String TipoAdquisicion = Data.get("TipoAdquisicion");
		String SkuEquipo = Data.get("SkuEquipo");
		
		String FA = "SinFA";		
		if(Rut.equalsIgnoreCase("null"))
			FA ="FA";
   	
		Amdocs.IniciarAmdocs();
		Oper.VentaCliente(Data);
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.SeleccionarPlanBase(Plan);	 //Oper.SeleccionarPlan(Plan);  
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", "Venta Claro Up");
		Oper.configuracion(TipoCliente, TipoAdquisicion, "123456789876543", "iphone", "7", false, false,SkuEquipo, Reasoncode);
		Oper.paginaDespacho("M");
		Oper.paginaDireccionEnvio("987654321");
		Oper.distribucionCargo("M", FA, TipoCliente);
		Oper.resumenOrden(Data);
		String Orden = Oper.paginaOrden();
		Oper.ManejoManual(Orden);
		Oper.paginaPoolOrden(Orden);
		Oper.ventanaSolicitudEquipo(false, false);
		Oper.paginaPrincipalInteraccion(Orden);
	}
	
	public void Portabilidad(Hashtable<String, String> Data) throws Exception
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
			
		String FA = "SinFA";		
		if(Rut.equalsIgnoreCase("null"))
			FA ="FA";		
		System.out.println("FA: "+FA);
	    
		Amdocs.IniciarAmdocs();
		Oper.VentaCliente(Data);
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION PORTABILIDAD","SE GENERA ORDEN");	
		//if(Ambiente.equals("Ambiente12")) 
			Oper.DireccionDeInstalacion();		
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.SeleccionarPlanBase(Plan);	 //Oper.SeleccionarPlan(Plan);  
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);
		Oper.AgregarServicios(Servicio, ServicioAdicional);
		Oper.NegociarPortabilidad(Data,TipoAdquisicion, "123456789876543", "iphone", "7", false, false,SkuEquipo);			
		Oper.paginaDespacho("M");
		Oper.paginaDireccionEnvio("987654321");
		Oper.distribucionCargo("M",FA, TipoCliente);
		Oper.ResumenDeOrdenExpress(Data);//		Oper.resumenOrden(Data);
		String Orden = Oper.paginaOrden();		
		Oper.CerrarVentanaOrden();		
		Oper.ManejoManual(Orden);
		Oper.paginaPoolOrden(Orden);
		Oper.ventanaSolicitudEquipo(false, true);		
		//Oper.paginaPrincipalInteraccion(Orden);
		Oper.BuscarEstadoOrden("Orden");
	}
	
	public void CambioPlan(Hashtable<String, String> Data) throws Exception
	{
		
		String ID = Data.get("ID");
		String Plan = Data.get("NombrePlan");
		String Rut  = Data.get("Rut");
		String TipoCliente  = Data.get("TipoCliente");
		String Reasoncode  = Data.get("ReasonCode");
		String TipoAdquisicion = Data.get("TipoAdquisicion");
		String Telefono = Data.get("Telefono");
	    String ReasonCambio = "Cambio de Plan";
	    if(Reasoncode.equalsIgnoreCase("Activacion Claro Up"))
	    	ReasonCambio = "Activacion Claro Up";
	    
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");	
		Oper.GuardaCBPFaCiclo();
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES AL CONTRATO","CAMBIO DE OFERTA COMERCIAL","SE GENERA ORDEN");	
		Oper.buscarProductoAsignado(Telefono);	
		//if(Ambiente.equals("Ambiente12")) 
			Oper.DireccionDeInstalacion();
		Oper.Acreditacion(TipoCliente, "Cambio Plan", Plan);
		Oper.SeleccionarPlanBase(Plan);	 //Oper.SeleccionarPlan(Plan);  
		Oper.CambiarOferta(Telefono);
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", ReasonCambio);
		Oper.configuracionSaltar();
		Oper.ConsultaCredito(); 
		Oper.distribucionCargo("M","SinFA", TipoCliente);
		Oper.resumenOrden(Data);
		String dato = Oper.paginaOrden();
		Oper.CerrarVentanaProductosAsignados();	
		Oper.BuscarEstadoOrden("Orden");
		
	}
	
	public void CambioEquipo(Hashtable<String, String> Data) throws Exception
	{
		String ID = Data.get("ID");
		String Plan = Data.get("NombrePlan");
		String Rut  = Data.get("Rut");
		String TipoCliente  = Data.get("TipoCliente");
		String Reasoncode  = Data.get("ReasonCode");
		String TipoAdquisicion = Data.get("TipoAdquisicion");
		String Telefono = Data.get("Telefono");
		String SkuEquipo = Data.get("SkuEquipo");	
	
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");
		Oper.GuardaCBPFaCiclo();		
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES","CAMBIO DE EQUIPO","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", "Cambio de equipo");		
		Oper.configuracionCambioEquipo(TipoCliente, TipoAdquisicion, "123456789876543", "iphone", "7", false, false,Telefono,SkuEquipo,Reasoncode);
		//Oper.ConsultaCredito();
		Oper.FinalizarCambioEquipo(TipoAdquisicion, Data);
	}
	
	public void SuspencionRobo(Hashtable<String, String> Data) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
	{
		
		String Telefono = Data.get("Telefono");
		String Caso = Data.get("Caso");
		String Reasoncode="Suspensión por Robo";
		if(!Caso.equalsIgnoreCase("Suspension por Robo"))
				Reasoncode="Suspensión por Extravío";
	   
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");	
		Oper.GuardaCBPFaCiclo();
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "SUSPENSION-ACTIVACION DE SERVICIO","SUSPENSION DE SERVICIO ROBO/PERDIDA","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);	
		Oper.ResumenDeOrdenExpress(Data);
		String Orden = Oper.paginaOrden();
		Oper.BuscarEstadoOrden("Orden");
	}
	
	public void ActivacionRobo(Hashtable<String, String> Data) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
	{
		String Telefono = Data.get("Telefono");
		String Caso = Data.get("Caso");
		String Reasoncode="Activacion por Robo";
		if(!Caso.equalsIgnoreCase("Activacion por Robo"))
				Reasoncode="Activacion por Extravío";	
			
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");	
		Oper.GuardaCBPFaCiclo();
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "SUSPENSION-ACTIVACION DE SERVICIO","RE-ACTIVACION DE SERVICIO ROBO/PERDIDA","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);	
		Oper.ResumenDeOrdenExpress(Data);
		String Orden = Oper.paginaOrden();
		Oper.BuscarEstadoOrden("Orden");
	}
	
	public void CambioNumero(Hashtable<String, String> Data) throws Exception
	{
		String Telefono = Data.get("Telefono");
		String Caso = Data.get("Caso");
		String TipoCliente  = Data.get("TipoCliente");
		
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");
		Oper.GuardaCBPFaCiclo();		
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES","CAMBIO DE NUMERO","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
	//	Oper.DireccionDeInstalacion();
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", "Otros");	
		Oper.ConfiguracionCambioNumero();
		Oper.distribucionCargo("M","SinFA", TipoCliente);
		String Orden = Oper.paginaOrden();
		Oper.ResumenDeOrdenExpress(Data);//		Oper.resumenOrden(Data);
		Oper.BuscarEstadoOrden("Orden");
	
	}
	
	public void CambioSimCard(Hashtable<String, String> Data) throws Exception
	{
		String Telefono = Data.get("Telefono");
		String Caso = Data.get("Caso");
		String TipoCliente  = Data.get("TipoCliente");
	
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");
		Oper.GuardaCBPFaCiclo();		
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES","CAMBIO DE SIMCARD","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", "Cambio de SIM");
		Oper.ConfiguracionCambioSimCard(Telefono);
		Oper.paginaDespacho("M");
		Oper.paginaDireccionEnvio("987654321");
		Oper.distribucionCargo("M","SinFA",TipoCliente);
		Oper.ResumenDeOrdenExpress(Data);//		Oper.resumenOrden(Data);
		String Orden = Oper.paginaOrden();
		Oper.CerrarVentanaOrden();
		Oper.ManejoManual(Orden);
		Oper.paginaPoolOrden(Orden);
		Oper.ventanaSolicitudEquipo(false,false);
		Oper.BuscarEstadoOrden("Orden");
	}
	
	public void TraspasoTitularidad(Hashtable<String, String> Data) throws Exception
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
		String Telefono = Data.get("Telefono");
		String CBPPosicion = "Inicio";	
		
		String FA = "SinFA";
			System.out.println("FA: "+FA);
			
		Amdocs.IniciarAmdocs();
		Oper.VentaCliente(Data);		
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES AL CONTRATO","TRASPASO DE TITULARIDAD","SE GENERA ORDEN");
		Oper.buscarProductoTraspasoTitularidad(Telefono);
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);
		Oper.AgregarServicios(Servicio, ServicioAdicional);
		Oper.configuracionCambioEquipo(TipoCliente, TipoAdquisicion, "123456789876543", "iphone", "7", false, false,Telefono,SkuEquipo,Reasoncode);
		Oper.distribucionCargo("M", FA, TipoCliente);
		Oper.resumenOrden(Data);
		String Orden = Oper.paginaOrden();
		Oper.CerrarVentanaOrden();
		Oper.BuscarEstadoOrden("Orden");
	}
	
	public void TerminoContrato(Hashtable<String, String> Data) throws Exception
	{
		String Telefono = Data.get("Telefono");
		String Caso = Data.get("Caso");
		String TipoCliente  = Data.get("TipoCliente");
		String Reasoncode  = Data.get("ReasonCode");	
	
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");	
		Oper.GuardaCBPFaCiclo();
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES AL CONTRATO","TERMINO DE CONTRATO","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);
		//Oper.paginaDespacho("I");  				//Inmediata
		Oper.ResumenDeOrdenExpress(Data);
		Oper.BuscarEstadoOrden("Orden");
			
	}
	
	public void Reconexion(Hashtable<String, String> Data) throws Exception
	{
		String Telefono = Data.get("Telefono");
		String Caso = Data.get("Caso");
		String TipoCliente  = Data.get("TipoCliente");
		String TipoAdquisicion = Data.get("TipoAdquisicion");
		String SkuEquipo = Data.get("SkuEquipo");
		String Reasoncode  = Data.get("ReasonCode");
		String Plan  = Data.get("NombrePlan");
	
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");	
		Oper.GuardaCBPFaCiclo();
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "MODIFICACIONES AL CONTRATO","RECONEXION DE SERVICIO","SE GENERA ORDEN");
		Oper.buscarProductoAsignado(Telefono);
		Oper.Acreditacion(TipoCliente,Caso,Plan);
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);
		Oper.configuracionCambioEquipo(TipoCliente, TipoAdquisicion, "123456789876543", "iphone", "7", false, false,Telefono,SkuEquipo,Reasoncode);
		Oper.distribucionCargo("M", "FA", TipoCliente);
		Oper.ResumenDeOrdenExpress(Data);
		Oper.BuscarEstadoOrden("Orden");
	}
	
		public void ServiciosAdicionales(Hashtable<String, String> Data) throws Exception{
		String Telefono = Data.get("Telefono");
		String TipoCliente  = Data.get("TipoCliente");
		String Servicio = Data.get("Tipo");
		String ServicioAdicional = Data.get("ServicioAdicional");
		
		Amdocs.IniciarAmdocs();		
		Oper.BuscarPersonaExistente(Telefono,"Suscripcion");	
		Oper.GuardaCBPFaCiclo();
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "SERVICIOS ADICIONALES","ACTIVACION/DESACTIVACION LARGA DISTANCIA INTERNACIONAL","SE GENERA ORDEN");	
		Oper.buscarProductoAsignado(Telefono);		
		Oper.DireccionDeInstalacion();
		Oper.Acreditacion(TipoCliente, "", "");
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", "Otros");
		Oper.AgregarServicios(Servicio, ServicioAdicional);
		Oper.configuracionSaltar();
		Oper.ResumenDeOrdenExpress(Data);
		Oper.BuscarEstadoOrden("Orden");
		
	}
	
	//Venta - creación de cliente por servicio + venta por CRM
	public void VentaClienteServicio(Hashtable<String, String> Data) throws Exception
	{
		/*
		System.out.println("llego......??? ");
	    Core.Soap.Soap soap = new Core.Soap.Soap();
	    soap.PagoPorCaja();
	    */
		
		String ID = Data.get("ID");
		String Plan = Data.get("NombrePlan");		
		String TipoCliente  = Data.get("TipoCliente");
		String Reasoncode  = Data.get("ReasonCode");
		String TipoAdquisicion = Data.get("TipoAdquisicion");
		String SkuEquipo = Data.get("SkuEquipo");
		String Servicio = Data.get("Tipo");
		String ServicioAdicional = Data.get("ServicioAdicional");
		String CBPPosicion = "Inicio";
									
		String FA = "SinFA";
		//String FA = "FA";
		
		//if(Rut.equalsIgnoreCase("null"))
		//	FA ="FA";
		System.out.println("FA: "+FA);
		
		Amdocs.IniciarAmdocs();
		int IdAmbiente = Rep.GetAmbiente();
		
		String Rut = Oper.NuevoCrearClienteServicio(Data,IdAmbiente);
		Data.put("Rut",Rut);
		
		Oper.VentaCliente(Data);
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");
		//if(Ambiente.equals("Ambiente12"))  	
	    Oper.DireccionDeInstalacion();	
	    Oper.Acreditacion(TipoCliente, "", "");
		Oper.SeleccionarPlanBase(Plan);
		Oper.motivoOrden("Caso ejecutado por Automatización del Equipo de Calidad QA", Reasoncode);
		Oper.AgregarServicios(Servicio, ServicioAdicional);
		Oper.configuracion(TipoCliente, TipoAdquisicion, "123456789876543", "iphone", "7", false, false,SkuEquipo,Reasoncode);
		//Oper.ConsultaCredito();
		Oper.paginaDespacho("M");
		Oper.paginaDireccionEnvio("987654321");
		Oper.distribucionCargo("M", FA, TipoCliente);
		Oper.resumenOrden(Data);
		String Orden = Oper.paginaOrden();
		Oper.CerrarVentanaOrden();
		Oper.ManejoManual(Orden);
		Oper.paginaPoolOrden(Orden);
		Oper.ventanaSolicitudEquipo(false,false);
		Oper.paginaPrincipalInteraccion(Orden);		
	}
	
	
	public void ValidacionExistenciaPlan(Hashtable<String, String> Data) throws Exception
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
		
		String FA = "SinFA";
		//String FA = "FA";
		
		//if(Rut.equalsIgnoreCase("null"))
		//	FA ="FA";
	
		System.out.println("FA: "+FA);
		Amdocs.IniciarAmdocs();
		Oper.VentaCliente(Data);
		Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");
	    Oper.DireccionDeInstalacion();			
	    Oper.Acreditacion(TipoCliente, "", "");
		Oper.ValidarExistenciaPlan();
	}
	
	public void PruebaMail(Hashtable<String, String> Data) throws Exception
	{
		Business.Email email = new Business.Email();
		email.EnviarEmail2();
	}

		
}
