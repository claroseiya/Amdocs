package Script.Ventas;

import java.util.Hashtable;

public class Servicios {	
	Business.Operaciones Oper  = new  Business.Operaciones();
	Business.Amdocs      Amdocs= new  Business.Amdocs();
	Business.Reporte     Rep = new Business.Reporte();
	
	Core.Propiedades.Propiedades Propi = new Core.Propiedades.Propiedades();	 
	String Ambiente=Propi.GetValue("Amdocs", "Ambiente");

	public void ConsultaQDN(Hashtable<String, String> Data) throws Exception
	{
		String Telefono = Data.get("Telefono");
		Oper.ConsultaQDN(Telefono);
	}
	
	//permite obtener el id de contacto para rut existente persona.
		public void ConsultaRut(Hashtable<String, String> Data) throws Exception
		{
			int IdAmbiente = Rep.GetAmbiente();
			String Rut = Oper.ConsultaRut(Data,IdAmbiente);
		}
	
	//Metodo Mejorado para la creación de casi todos los clientes (Falta Prepago)
	public void CrearClientePorServicio(Hashtable<String, String> Data) throws Exception
	{
		int IdAmbiente = Rep.GetAmbiente();
		String Rut = Oper.NuevoCrearClienteServicio(Data,IdAmbiente);
		Data.put("Rut",Rut);
		
	}

	/*
	 USO DEL PROVIDEWIRELESSIVR
	 Obsoleto
	 * 
	public void VentaPorServicio(Hashtable<String, String> Data) throws Exception
	{
		String Rut = Oper.VentaClienteServicio(Data);
	}*/
	/*Metodo creado por Jesus
	public void PortinPorServicio(Hashtable<String, String> Data) throws Exception
	{
		//int Ambiente = 10;//Rep.GetAmbiente();
		String Rut = Oper.PortabilidadClienteServicio(Data);
	}*/
	/*
	 * Obsoleto
	public void MigracionPorServicio(Hashtable<String, String> Data) throws Exception
	{
		String Rut = Oper.MigracionPorServicio(Data);
	}
	*/
	/*
	 USO DEL SERVICIO CHANGESERVICESTATUS
	 * */
	public void AgregarServicio4Servicio(Hashtable<String, String> Data) throws Exception
	{	
		String ChangeAction="0";
		int Ambiente = Rep.GetAmbiente();
		
		if(Data.get("Caso").equalsIgnoreCase("AGREGAR SERVICIO POR SERVICIO"))
			ChangeAction="1";
		
		Amdocs.DataRowExcel("Rut", Data.get("Caso"));
		Oper.AgregarServicio4Servicio(Data, Ambiente, ChangeAction);		
	}	
	
	/*
	 USO DEL SERVICIO IDENTIFYCALLER
	 */
	public void IdentificarLlamante(Hashtable<String, String> Data) throws Exception
	{
		//int Ambiente = Rep.GetAmbiente();
		//Oper.IdentificarLlamante(Data, Ambiente);
	}
	/*
	 USO DEL SERVICIO CHANGEOWNERSHIP 
	 */
	public void TraspasoPropiedad(Hashtable<String, String> Data) throws Exception
	{
		int Ambiente = Rep.GetAmbiente();
		//Oper.TraspasoPropiedad(Data, Ambiente);
		Oper.NuevoTraspasoPropiedad(Data, Ambiente);
		
	}
	
	
	/*
	 USO DEL SERVICIO REPLACEOFFER
	 * */

	public void CambioOferta(Hashtable<String, String> Data) throws Exception
	{
		int Ambiente = Rep.GetAmbiente();
		Oper.CambioOferta(Data, Ambiente);	
	}

	public void TerminoContrato(Hashtable<String, String> Data) throws Exception
	{
			
	}
	
	public void ConsultaPassCorrecta(Hashtable<String, String> Data) throws Exception
	{
			
	}
	

	public void ConsultaPagoDeuda(Hashtable<String, String> Data) throws Exception
	{
		int Ambiente = Rep.GetAmbiente();
		Oper.PagoDeuda(Data, Ambiente);	
	}
	

	
	//Metodo Nuevo que incluye venta, portabilidad y migración
	public void NuevaVServicio(Hashtable<String, String> Data) throws Exception
	{
		//Recibo el Ambiente
		int IdAmbiente = Rep.GetAmbiente();
		// Se llama a Operaciones para que revise lo que se hace
		String Rut = Oper.NuevoVentaClienteServicio(Data,IdAmbiente);

	}

	
}
