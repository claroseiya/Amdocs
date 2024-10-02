package Business;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import Core.Excel.AccionesExcel;
import autoitx4java.AutoItX;
import java.text.Normalizer;

public class Operaciones extends Amdocs{
	
	Amdocs amd = new Amdocs();
	Core.Data.GeneradorRut Data = new Core.Data.GeneradorRut();
	Core.Data.StringRandom RandomString = new Core.Data.StringRandom();
	String Mail;
	Business.Reporte     Rep = new Business.Reporte();
	
	public String getMail() {return Mail;}
	public void setMail(String mail) {Mail = mail;}
	
	
   public void GuardaCBPFaCiclo() throws UnsupportedFlavorException, IOException, FindFailed
   {
	    String CBP =sikuli.GetTextRigth("Header\\\\PerfilFacturacionCliente",1);
		System.out.println("CBP: "+CBP);
		DataRowExcel("CBP", CBP);

		String FA =sikuli.GetTextDown("PaginaPrincipalInteraccion\\CtaFinanciera",1);
		System.out.println("FA: "+FA);
		DataRowExcel("FA", FA);
		
		sikuli.ClickTextRigth("Header\\Cliente.png", 1);
		sikuli.FindImg("CrearContacto\\RetenerFactura", 15);  	
		
		String Ciclo =sikuli.GetTextDown("Ciclos\\CicloFacturacion",1);

		int posicion = Ciclo.indexOf("-");
		Ciclo= Ciclo.substring(0,posicion);
		System.out.println("Ciclo: "+Ciclo);
		DataRowExcel("Ciclo", Ciclo);
		 
		sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 1);
   }
	
	public void CrearPersona(boolean Hogar) throws UnsupportedFlavorException, IOException, FindFailed, InterruptedException
	{	 
		 Au= AutoIt.getAutoit();		
		 Au.winActivate(PaginaAmdocs);
		 
		String Rut = Data.GetRutRandom();
		String randomString = RandomString.generateSessionKey(9);
		
		DataRowExcel("Rut", Rut);
		
		sikuli.ClickImg("Header\\EncontraQuienLlamaMismoContacto.png", 1);
		sikuli.ClickImg("EncontrarQuienLlama\\CrearContacto", 10);
		sikuli.FindImg("txtCrearContacto", 10);
		
		TimeSleep(3000);
		//sikuli.ClickImg("CrearContacto\\TipoIdentificacionPersonal", 10);
		TimeSleep(1000);
		
		SelectComboboxText("CrearContacto\\TipoIdentificacionPersonal", "R",5);
		//sikuli.ClickImg("CrearContacto\\SelecRut", 5);
		
		InputClearText("CrearContacto\\NumeroIdentificacionPersonal", Rut, 1); 
		sikuli.ClickImg("CrearContacto\\Validar", 1);  
		sikuli.FindImg("CrearContacto\\ValidacionIdentificacion", 15);				
		Au.controlSend(PaginaAmdocs, "", "",Keys.ENTER, false);
		InputClearText("CrearContacto\\NombreContacto", "NombreQA"+randomString, 1);
		InputClearText("CrearContacto\\ApellidoContacto", "ApellidoQA", 1);
		InputClearText("CrearContacto\\FechaNacimiento", "15/11/1984", 1);
		SelectCombobox("CrearContacto\\Genero","Masculino", 1);
		SelectCombobox("CrearContacto\\MetodoContacto","Correo", 1);
		SelectCombobox("CrearContacto\\EstadoCorreo","CorreoEntregado", 1);
		sikuli.ClickImg("CrearContacto\\DireccionCorreo", 10);		
		sikuli.InputText(Mail);		
		InputClearText("CrearContacto\\TelefonoDomiciliario", "56999999999", 1);
		InputClearText("CrearContacto\\TelefonoCelular", "56999999999", 1);
		sikuli.ClickImg("CrearContacto\\IconDireccion", 1);
		sikuli.FindImg("CrearContacto\\txtBuscarDireccion", 15);
		
		if(Hogar)
		{
			InputText("CrearContacto\\BuscarDireccion\\NombreCalle", "%%%", 1);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\Si", 15);
		
		}
		else
		{
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\IncluirDireccionesNoValida", 1);
			InputText("CrearContacto\\BuscarDireccion\\NombreCalle", "avenida el salto", 1);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\BuscarAhora", 1);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\AvElSalto", 15);
		}
		
		
		sikuli.ClickImg("CrearContacto\\BuscarDireccion\\Seleccionar", 1);
		sikuli.FindImg("CrearContacto\\txtCrearContacto", 15); 
		sikuli.ClickImg("CrearContacto\\Guardar", 1);
		
		
		}
	
	   public void CrearCBP(String Ciclo) throws UnsupportedFlavorException, IOException, FindFailed
	   {
		   DataRowExcel("Ciclo", Ciclo);
		   
		   sikuli.FindImg("PaginaPrincipalInteraccion\\TxtPaginaPrincipalInteraccion", 10);
		   TimeSleep(5000);
		   
		   sikuli.ClickImg("Header\\ApellidoQA", 1);   
		   sikuli.FindImg("VerContacto\\txtVerContacto", 15); 
		   sikuli.ClickImg("VerContacto\\Crear", 1);
		   sikuli.ClickImg("VerContacto\\TipoCliente", 15);
		   sikuli.ClickImg("VerContacto\\Persona", 1);
		   sikuli.ClickImg("VerContacto\\Consulta", 1);
		   SeleccionarCiclo(Ciclo);
		   sikuli.ClickImg("Ciclos\\Seleccionar", 1);
		   sikuli.FindImg("VerContacto\\txtVerContacto", 15);
		   sikuli.ClickImg("CrearContacto\\Guardar", 1);
		   TimeSleep(15000);
		   sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 3);
		   TimeSleep(15000);
		   sikuli.ClickImg("PaginaPrincipalInteraccion\\Refrescar.png", 3);
		   TimeSleep(15000);
		   String CBP =sikuli.GetTextRigth("Header\\\\PerfilFacturacionCliente",1);
		   System.out.println("CBP: "+CBP);
		   DataRowExcel("CBP", CBP);
	   }
	   
	   public void CrearCuentaFinanciera(String Tipo) throws Exception
	   {
		   System.out.println("###Este es el caso "+Tipo);
		   sikuli.ClickImg("Menu\\Crear", 1);
		   sikuli.ClickImg("Menu\\GestorFacturacion", 1);
		   sikuli.ClickImg("Menu\\CuentaFinancieraAcuerdoFacturacion", 1);
		   sikuli.FindImg("CrearCuentaFinanciera\\txtCuentaFinanciera", 15);
		   TimeSleep(3000);
		   
		   //Esto es para Hogar
		   if(Tipo.contains("VENTA HOGAR"))
		   {
			   sikuli.ClickImg("CrearCuentaFinanciera\\Wireless", 10);
		   	   sikuli.ClickImg("CrearCuentaFinanciera\\Wireline", 10);
		   }   
		   
		   sikuli.ClickImg("CrearCuentaFinanciera\\Correo", 10);
		   sikuli.InputText(Mail);
		   sikuli.ClickImg("CrearCuentaFinanciera\\Guardar", 5);
		   TimeSleep(5000);
		   sikuli.ClickDosImg("PaginaPrincipalInteraccion\\Refrescar.png","PaginaPrincipalInteraccion\\RefrescarSeleccionado.png", 30);
		   TimeSleep(10000);
		   String FA =sikuli.GetTextDown("PaginaPrincipalInteraccion\\CtaFinanciera",1);
		   System.out.println("FA: "+FA);
		   DataRowExcel("FA", FA);
	   }
	   
	   public void CrearCuentaFinancieraFacturaSencilla() throws Exception
	   {
		   amd.FindImgScroll("CrearCuentaFinanciera\\Correo","Down", 15);
		   sikuli.ClickImg("CrearCuentaFinanciera\\Correo", 10);
                   System.out.println("correo: "+Mail);
	
		   if(Mail==null || Mail.equals("null")) {
			   Mail = "claroseiya@gmail.com";
		   }
		   sikuli.InputText(Mail);
		   amd.FindImgScroll("CrearCuentaFinanciera\\Guardar","Down", 10);
		   sikuli.ClickImg("CrearCuentaFinanciera\\Guardar", 5);		   
	   }
	   
	   public void ObtenerCuentaFinancieraFacturaSencilla() throws FindFailed, UnsupportedFlavorException, IOException
	   {
		   String FA = sikuli.GetTextRigth("paginaDistribucionCargo\\\\AcuerdoFacturacion",1);
		   System.out.println("FA: "+FA);
		   DataRowExcel("FA", FA);
		   
	   }
	   
	   public void SeleccionTopicos(String Motivo1,String Motivo2,String Motivo3,String Resultado) throws FindFailed, InterruptedException
	   {
		   sikuli.FindImg("PaginaPrincipalInteraccion\\TxtPaginaPrincipalInteraccion", 10);
		   SelectComboboxText("PaginaPrincipalInteraccion\\Motivo1.png"  ,Motivo1,3);
		   SelectComboboxText("PaginaPrincipalInteraccion\\Motivo2.png"  ,Motivo2,3);
		   
		   if(Motivo2.equalsIgnoreCase("MODIFICACIONES")) {
			   SelectComboboxText("PaginaPrincipalInteraccion\\Motivo3MODIFICACIONES.png"  ,Motivo3,3);}
		   else	if(Motivo2.contains("SERVICIOS")) {
			   SelectComboboxText("PaginaPrincipalInteraccion\\Motivo3Servicios.png"  ,Motivo3,3);}
		   else {
			   SelectComboboxText("PaginaPrincipalInteraccion\\Motivo3.png"  ,Motivo3,3);}
		   
		   SelectComboboxText("PaginaPrincipalInteraccion\\Resultado.png",Resultado,3);
		   sikuli.ClickImg("PaginaPrincipalInteraccion\\Accion.png", 3);
	   }
	   
	   public void Acreditacion(String Tipo, String Caso, String Plan) throws FindFailed, IOException, InterruptedException
	   {
		  
		 if(Tipo.equalsIgnoreCase("Prepago"))
			 return;
		 else if(Tipo.equalsIgnoreCase("Persona"))
		 {
			 TimeSleep(3000);
			 SelectComboboxText("NuevaOferta\\TipoAcreditacion.png"  ,"Sin Acreditacion",60);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\TipoDocumento.png"  ,"CC",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\NombreDocumento.png"  ,"F",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\Domicilio.png"  ,"N",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\DomicilioNombreDocumento.png"  ,"N",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\Acreditacion.png"  ,"N",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\Acreditacion.png"  ,"N",3);
		 }
		 
		 else {
			 TimeSleep(3000);
			 SelectComboboxText("NuevaOferta\\TipoAcreditacion.png"  ,"2 IVA (Formulario 29) - Linea 142",30);
			 SelectComboboxText("NuevaOferta\\DescripcionCaso1.png"  ,"DIC/ENE",30);
			 
			 SelectComboboxText("NuevaOferta\\MontoMes.png"  ,"50000000",30);
			 SelectComboboxText("NuevaOferta\\MontoMes2.png"  ,"50000000",30);
			 
			 
			 SelectComboboxText("NuevaOferta\\TipoDocumento.png"  ,"CC",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\NombreDocumento.png"  ,"F",3);
			 TimeSleep(1000);
			 
			 
			 SelectComboboxText("NuevaOferta\\Domicilio.png"  ,"C",3);
			 TimeSleep(1000);
			 
			 SelectComboboxText("NuevaOferta\\DomicilioNombreDocumento.png"  ,"FF",3);
			 TimeSleep(1000);
			 
			 SelectComboboxText("NuevaOferta\\AcreditacionEmpresa.png"  ,"F",3);
			 TimeSleep(1000);
			 SelectComboboxText("NuevaOferta\\AcreditacionEmpresa2.png"  ,"I",3);
			
			 
		 }
		 
		 if(Caso.equalsIgnoreCase("Cambio Plan") && Plan.contains("PREPAGO"))
		 {
			 SelectComboboxText("NuevaOferta\\PrePost.png"  ,"P",3);
		 } 
		 
		 FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		 sikuli.ClickImg("NuevaOferta\\Evaluar.png", 3);
			
			
		 for(int i=0;i<80;i++)
		 {
			 boolean Existe = sikuli.FindImgWait("NuevaOferta\\exito.png",1);
			 if(Existe)
			 {
				 try {
					    String Status = SelectAllText("NuevaOferta\\exito.png",1);
					    Thread.sleep(1000);
					    DataRowExcel("Status", Status);
					    sikuli.ClickImg("NuevaOferta\\Siguiente.png", 10);
						return;
					} catch (UnsupportedFlavorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			 }
		 }	
			 
	   }
	   
	   public void SeleccionarPlan(String NombrePlan) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
	   {
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 
		   NombrePlan = NombrePlan.trim();
		   DataRowExcel("Plan", NombrePlan);
		   
		   if(sikuli.FindImgWait("PaginaPlanes\\ofertaPlan.png", 60) == true) {
			   sikuli.FindImgWait("NuevaOferta\\TxtNuevaOferta.png", 10);
			   FindImgScroll("NuevaOferta\\Buscar.png","up", 15);
			   sikuli.FindImg("PaginaPlanes\\ofertasMostradas.png", 60);
		   }
			   System.out.println("Se ingresa plan " + NombrePlan);
			   Amdocs amd = new Amdocs();
			   String[] plan = null;
				   sikuli.ClickImg("PaginaPlanes\\buscarCuenta.png", 10);
			   Au.controlSend(PaginaAmdocs, "", "", NombrePlan);
				   sikuli.ClickImg("PaginaPlanes\\btnBuscarCta.png", 10);
			   Thread.sleep(7000);
			   BusquedaPlan(NombrePlan);
			   
			   if(sikuli.FindImgWait("PaginaPlanes\\MayorLimiteCompra.png", 3))
				   Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
	   }
	   
	   public void motivoOrden(String txtRazon, String ReasonCode) throws FindFailed, IOException, UnsupportedFlavorException, InterruptedException {
		   
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
           Amdocs amd = new Amdocs();
		   
		   if(sikuli.FindImgWait("PaginaMotivoOrden\\TituloRazon.png", 60) == true) {
			   
			   //amd.SelectCombobox("PaginaMotivoOrden\\razon.png", ReasonCode, 20);
			   if(ReasonCode.equalsIgnoreCase("Petición del Cliente"))
				   ReasonCode="Peticion del Cliente";
			   
			   if(!ReasonCode.equalsIgnoreCase("Peticion del Cliente"))
			   {
				   sikuli.ClickDown("PaginaMotivoOrden\\TituloRazon.png", 3);
				   
				   if(ReasonCode.equalsIgnoreCase("Suspensión por Robo"))
					   sikuli.ClickImg("PaginaMotivoOrden\\SuspencionPorHurto.png", 10); 
				   //sikuli.ClickImg("PaginaMotivoOrden\\SuspencionPorRobo.png", 10);
				   else if (ReasonCode.equalsIgnoreCase("Suspensión por Extravío"))
					   sikuli.ClickImg("PaginaMotivoOrden\\SuspencionPorExtravio.png", 10);
				   else if (ReasonCode.equalsIgnoreCase("Activacion por Robo"))
					   sikuli.ClickImg("PaginaMotivoOrden\\DesbloqeoPorRobo.png", 10);
				   else if (ReasonCode.equalsIgnoreCase("Activacion por Extravío"))
					   sikuli.ClickImg("PaginaMotivoOrden\\DesbloqueoPorExtravio.png", 10);
				   
				   else
				   {
					   sikuli.InputText(ReasonCode);	
					   sikuli.InputText("\n");   
				   }
			   }
				   
			//amd.SelectComboboxText("PaginaMotivoOrden\\\\razon.png", ReasonCode, 15);
			  
			   sikuli.ClickImg("PaginaMotivoOrden\\textRazon.png", 10);
			   
			   Au.controlSend(PaginaAmdocs, "", "", txtRazon);
			   amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
			   sikuli.ClickImg("NuevaOferta\\Siguiente.png", 10);
			   
		   }
	   }
	   
	   
	   public void TipoAdquision(String tipAdquisicion, String imei, String marca, String modelo, Boolean roaming, Boolean ldi, String NumTelefono, String SKU, String ReasonCode) throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException{
		   
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 
		 
		   switch(tipAdquisicion) {
		   
		   case "Arrendamiento":
		   case "Arriendo":
		   case "Compra":
			   String TA= "A";
			   if(tipAdquisicion.equalsIgnoreCase("Compra"))
				  TA= "C";
			   
			   sikuli.ClickImg("PaginaConfiguracion\\NombreQA.png", 5);
			   
			   amd.FindImgScroll("PaginaConfiguracion\\tipoAdquisicionTitulo.png","down", 10);
			   
			   
			   if(sikuli.FindImgWait("PaginaConfiguracion\\\\tipoAdquisicionCompra.png", 5)) {
				   amd.FindImgScroll("PaginaConfiguracion\\\\tipoAdquisicionCompra.png","down", 10);
				   amd.SelectComboboxText("PaginaConfiguracion\\tipoAdquisicionCompra.png", TA, 15);
			   }else {
				   amd.FindImgScroll("PaginaConfiguracion\\\\tipoAdquisicion.png","down", 10);
				   amd.SelectComboboxText("PaginaConfiguracion\\tipoAdquisicion.png", TA, 15);
			   }
			   
			   Thread.sleep(1000);
			   amd.FindImgScroll("PaginaConfiguracion\\\\seleccionarEquipo.png","down", 10);
			   sikuli.ClickImg("PaginaConfiguracion\\seleccionarEquipo.png", 5);
			   sikuli.FindImg("PaginaConfiguracion\\Handset.png", 120);
			   
			   //Modificar desde acá
			   
			   if(!SKU.equalsIgnoreCase("null"))
			   {
				   sikuli.ClickDown("PaginaConfiguracion\\CodigoDelProducto.png", 5);
				   Au.controlSend(PaginaAmdocs, "", "",SKU );
				   sikuli.ClickImg("PaginaConfiguracion\\BuscarAhora.png", 5);
				   amd.TimeSleep(1000);
				   sikuli.FindImg("PaginaConfiguracion\\Handset.png", 60);
				   
				  
				   if(ReasonCode.equalsIgnoreCase("Venta Claro Up") || ReasonCode.equalsIgnoreCase("Portabilidad Claro Up") )
				   {
					   amd.TimeSleep(3000);
					   sikuli.ClickImg("PaginaConfiguracion\\ClaroUP_PR.png", 10);
				       sikuli.ClickImg("PaginaConfiguracion\\BtnSeleccionar.png", 5);
				   }
					   
				   else
				   {
					   sikuli.clickCordinates("PaginaConfiguracion\\equipoCel.png", 5, 0, 10);
					   sikuli.ClickImg("PaginaConfiguracion\\BtnSeleccionar.png", 5);   
				   }
				   
			   }
			   else if(ReasonCode.equalsIgnoreCase("Venta Claro Up") || ReasonCode.equalsIgnoreCase("Portabilidad Claro Up") )
			   {
				   System.out.println("Pase 2");
				   sikuli.ClickImg("PaginaConfiguracion\\ClaroUP_PR.png", 5);
				   sikuli.ClickImg("PaginaConfiguracion\\BtnSeleccionar.png", 5);
			   }
			   
			   else
			   {
				   sikuli.clickCordinates("PaginaConfiguracion\\equipoCel.png", 5, 0, 25);
				   sikuli.ClickImg("PaginaConfiguracion\\BtnSeleccionar.png", 5);
				      
			   }
			   
			   
			   amd.TimeSleep(5000);
			   sikuli.ClickImg("PaginaConfiguracion\\calcular.png", 5);
		     break;
		   case "Propio":
			   
			   sikuli.ClickImg("PaginaConfiguracion\\NombreQA.png", 5);
			   
			   amd.FindImgScroll("PaginaConfiguracion\\tipoAdquisicionTitulo.png","down", 10);
			   
			   if(sikuli.FindImgWait("PaginaConfiguracion\\\\tipoAdquisicionCompra.png", 5)) {
				   amd.FindImgScroll("PaginaConfiguracion\\\\tipoAdquisicionCompra.png","down", 10);
				   amd.SelectComboboxText("PaginaConfiguracion\\tipoAdquisicionCompra.png", "PP", 15);
				   
			   }else {
				   amd.FindImgScroll("PaginaConfiguracion\\\\tipoAdquisicion.png","down", 10);
				   amd.SelectComboboxText("PaginaConfiguracion\\tipoAdquisicion.png", "P", 15);
			   }
			   
			   Thread.sleep(1000);
			   amd.FindImgScroll("PaginaConfiguracion\\\\lblEmei.png","down", 10);
			   sikuli.clickCordinates("PaginaConfiguracion\\lblEmei.png", 5, 50, -10);
			   Au.controlSend(PaginaAmdocs, "", "", imei);
			   
			   amd.FindImgScroll("PaginaConfiguracion\\lblMarca.png", "Down", 10);
			   sikuli.clickCordinates("PaginaConfiguracion\\lblMarca.png", 5, 50, -10);
			   Au.controlSend(PaginaAmdocs, "", "", marca);
			   amd.FindImgScroll("PaginaConfiguracion\\lblModelo.png", "Down", 10);			   
			   sikuli.clickCordinates("PaginaConfiguracion\\lblModelo.png", 5, 50, -10);
			   Au.controlSend(PaginaAmdocs, "", "", modelo);
			 break;
		    
		 }
		 amd.TimeSleep(5000);
		 
		 
		 if(roaming == true) {
			 sikuli.ClickImg("PaginaConfiguracion\\subRoaming.png", 5);
			 sikuli.ClickImg("PaginaConfiguracion\\btnAgregar.png", 5);
		 }
		 if(ldi == true) {
			 sikuli.ClickImg("PaginaConfiguracion\\subLdi.png", 5);
			 sikuli.ClickImg("PaginaConfiguracion\\btnAgregar.png", 5);
		 }
				 
		 sikuli.goToImg("PaginaConfiguracion\\calcular", 10, 0, 0);
		 amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 30);
		 sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
		 DataRowExcel("Telefono", NumTelefono);
		   
		   
	   }
	   
	   /**
	    * Metodo que realiza la automatizacion de la página de configuración de amdocs
	    * @author: Felipe Toro Cerda
	    * @version: 27/01/2020 1.0
	    * @param tipAdquisicion define el tipo de adquisicion de equipo telefonico
	    * @imei parametro de entrada del imei del equipo
	    * @marca parametro de entrada de la marca del equipo
	    * @modelo parametro de entrada del modelo del equipo 
	    */	   
	   public void configuracion(String TipoCliente, String tipAdquisicion, String imei, String marca, String modelo, Boolean roaming, Boolean ldi, String SKU, String ReasonCode) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
		  		   
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 
		   
		   
		   
		   if(TipoCliente.equalsIgnoreCase("Prepago"))
		   {
			   sikuli.FindImgWait("PaginaConfiguracion\\EquipoyTarjetaSim.png", 20);
			   Thread.sleep(5000);
			   sikuli.DobleClickImg("PaginaConfiguracion\\EquipoyTarjetaSim.png", 20);
			   sikuli.DobleClickImg("PaginaConfiguracion\\TarjetaSim.png", 10);
			   sikuli.ClickImg("PaginaConfiguracion\\PYDDescuentoSimcard.png", 10);
			   sikuli.ClickImg("PaginaConfiguracion\\Agregar.png", 10);
		   }
		   
		 
		   sikuli.FindImg("PaginaConfiguracion\\asignarNumero.png", 60);
		   Thread.sleep(5000);
		   int Intentos=0;
           boolean estado    = true;
           boolean estadoNum = true;
           String NumTelefono=null;
           sikuli.ClickImg("PaginaConfiguracion\\asignarNumero.png", 20);
			   do {
				   estadoNum = sikuli.FindImgWait("PaginaConfiguracion\\numerosPropuestos.png", 10);
				   if(estadoNum == true) {
					   sikuli.clickCordinates("PaginaConfiguracion\\btnPropongaNum.png", 5, 0, -200);
					   sikuli.ClickImg("PaginaConfiguracion\\asignacionNumero.png", 5);
					   Thread.sleep(1500);
					   sikuli.ClickImg("PaginaConfiguracion\\configuracion.png", 5);
					   Thread.sleep(1500);
					   sikuli.ClickImg("PaginaConfiguracion\\MSISDN.png", 5);
					   String CeldaMSISDN =GetCopyClip();
					   
					   Pattern pattern2 = Pattern.compile("(\\d{11})");
					   Matcher matcher2 = pattern2.matcher(CeldaMSISDN);
					   
					   if(matcher2.find())
					   {
						   NumTelefono = matcher2.group(1);
						   estado=false;
					   }
					   else
						   sikuli.ClickImg("PaginaConfiguracion\\asignarNumero.png", 20);
				   }
				   else
					   sikuli.ClickImg("PaginaConfiguracion\\btnPropongaNum.png", 20);
				
				   Intentos++;
			   }while(estado==true && Intentos<6);
			   
			   TipoAdquision(tipAdquisicion, imei, marca, modelo, roaming, ldi,NumTelefono, SKU, ReasonCode);
	   }
		
	   
	   public void paginaDespacho(String opcionEntrega) throws FindFailed, IOException, InterruptedException {
		
		   if(sikuli.FindImgWait("paginaEntrega\\opcionEntrega.png", 120) == true) {
			   if (opcionEntrega == "M") {
				   amd.SelectComboboxText("paginaEntrega\\opcionEntrega.png", opcionEntrega, 60);
			   }
		  
			   if(sikuli.FindImgWait("paginaEntrega\\MetodoDevolucion.png", 2) == true) {
			   {
				   amd.SelectComboboxText("paginaEntrega\\MetodoDevolucion.png", "I", 60);
			   } 		
			}
		   
			   
			   amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
			   sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
		   }
		   
		   
	   }
	   
	   public void paginaDireccionEnvio(String numTelefonico) throws FindFailed, IOException, UnsupportedFlavorException {
		  
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs);
		
		   sikuli.ClickImg("paginaDireccionEnvio\\obtenerFechas.png", 20);
		   amd.TimeSleep(3000);
		   sikuli.clickCordinates("paginaDireccionEnvio\\fechaEntrega.png", 20, -100, 10);
		   sikuli.ClickImg("paginaDireccionEnvio\\numTelefonicoAdicional.png", 20);
		   Au.controlSend(PaginaAmdocs, "", "", numTelefonico);	
		 
		   sikuli.ClickImg("paginaDireccionEnvio\\numTelefonicoAdicional.png", 5);
		   Au.controlSend(PaginaAmdocs, "", "", numTelefonico);	
		   
		     
		   if(sikuli.FindImgWait("paginaDireccionEnvio\\NumeroDestinatario.png", 5))
		   {	
				sikuli.ClickImg("paginaDireccionEnvio\\NumeroDestinatario.png", 5);
				Au.controlSend(PaginaAmdocs, "", "", numTelefonico);
		   }
		   
		   if(sikuli.FindImgWait("paginaDireccionEnvio\\DireccionDeEnvio.png", 1))
		   {
			   sikuli.ClickImg("paginaDireccionEnvio\\DireccionDeEnvio.png", 5);
			   Au.controlSend(PaginaAmdocs, "", "", "El SALTO 4550");
		   }
		   
		  
			   
		   
		   amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		   sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
	   }
	   
	   public void distribucionCargo(String opcionEntrega, String FA, String TipoCliente) throws Exception {
		   
		   if(!TipoCliente.equalsIgnoreCase("Prepago")) {
			   		   
	           if(sikuli.FindImgWait("paginaDistribucionCargo\\nuevoAcuerdo.png", 60) == true) {
				   amd.TimeSleep(7000);
				   
				   if(FA.equalsIgnoreCase("FA"))
					   CrearCuentaFinancieraFacturaSencilla();
				   
				   amd.FindImgScroll("paginaDistribucionCargo\\radioFacturasSeparadas","up", 30);
				   sikuli.ClickImg("paginaDistribucionCargo\\radioFacturasSeparadas", 1);
				   amd.TimeSleep(2000);
				   
				   if(FA.equalsIgnoreCase("FA"))
					   ObtenerCuentaFinancieraFacturaSencilla();
				  
				   PagoInmediato(opcionEntrega);
			   }	
	   		}
	   }
	   
	   public void PagoInmediato(String opcionEntrega) throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException
	   {
		   sikuli.ClickImg("paginaDistribucionCargo\\pagoInmediato", 10);
		   
		   if(sikuli.FindImgWait("paginaDistribucionCargo\\metodoEntrega", 5))
		   {
			   amd.SelectComboboxText("paginaDistribucionCargo\\metodoEntrega", opcionEntrega, 10);
			   amd.TimeSleep(1000);
			   sikuli.ClickImg("paginaDistribucionCargo\\enviarPago", 10);
			   amd.TimeSleep(2000);
		   }
		   else
		   {
			   sikuli.clickCordinates("paginaDistribucionCargo\\checkNombre", 10, -60, -10);
			   sikuli.ClickImg("paginaDistribucionCargo\\pagoInmediato", 10);
			   amd.TimeSleep(5000);
			   amd.SelectComboboxText("paginaDistribucionCargo\\metodoEntrega", opcionEntrega, 10);
			   amd.TimeSleep(1000);
			   sikuli.ClickImg("paginaDistribucionCargo\\enviarPago", 10);
			   amd.TimeSleep(2000);
		   }
		   
		   amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		   sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
	   }
	   
	   
	   public void resumenOrden(Hashtable<String, String> Data) throws FindFailed, IOException, UnsupportedFlavorException {
		   
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs);
		   String TipoCliente = Data.get("TipoCliente");
		   String Caso    = Data.get("Caso");
		   String Plan    = Data.get("NombrePlan");
			
		   
		   if(Caso.equalsIgnoreCase("Venta")&&
			  TipoCliente.equalsIgnoreCase("Prepago"))
		   {
			   amd.FindImgScroll("paginaResumenOrden\\enviarResumen","down", 10);
			   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
		   }
		   else if(Plan.contains("Empresas"))					  
		   {
					   amd.FindImgScroll("paginaResumenOrden\\enviarResumen","down", 10);
					   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
		   }
		   else if(Plan.contains("PREPAGO"))					  
		   {
					   amd.FindImgScroll("paginaResumenOrden\\enviarResumen","down", 10);
					   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
		   }
		   else {
	
			   sikuli.ClickImg("paginaResumenOrden\\pestDocumentos", 20);
			   if(sikuli.FindImgWait("paginaResumenOrden\\checkDocumentos", 15) == true) {
	
            	  sikuli.clickCordinates("paginaResumenOrden\\checkDocumentos", 5, -80, -10);
              	}	
			   sikuli.ClickImg("paginaResumenOrden\\pestPrecios", 5);
			   
			   if(sikuli.FindImgWait("paginaResumenOrden\\generarContrato.png", 5))
			   {
				  
				  if(Caso.equalsIgnoreCase("Cambio Numero"))  {
					   if(sikuli.FindImgWait("paginaResumenOrden\\enviarResumenSelecionada", 5))
						   sikuli.ClickImg("paginaResumenOrden\\enviarResumenSelecionada", 1);
					   else
						   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 1);
					   
					  sikuli.ClickImg("paginaOrden\\cerrarPagina.png", 10);
					
				  }
				  else
				  {
					  
					   sikuli.ClickImg("paginaResumenOrden\\generarContrato.png", 5);
					  
					  //cambiar tiempo a 80 seg despues
					   if(sikuli.FindImgWait("paginaConfiguracion\\verContrato.png", 20) == true) { // 80) == true) {

						   amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
						   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
					   }
					   else
					   {

						   sikuli.FindImgWait("paginaResumenOrden\\MensajeFallaGeneracionContrato.png", 20); //60) == true) {
						   sikuli.InputText(Key.ENTER);
						  
						   amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
						   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
						   
					   }
				  }

			   }
			   else
			   {

				   DataRowExcel("GeneraContrato", "No Aplica");
				   amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
				   
				   if(sikuli.FindImgWait("paginaResumenOrden\\enviarResumenSelecionada", 10))
					   sikuli.ClickImg("paginaResumenOrden\\enviarResumenSelecionada", 1);
				   else
					   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 1);
				   
			   }
		   }
	   }



   public void BuscarPersonaExistente(String Value, String TipoBusqueda) throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException
	   {
		   System.out.println("Este es el rut: "+Value);
		   
		   sikuli.ClickImg("EncontrarQuienLlama.png", 8);
		   sikuli.ClickImg("EncontrarQuienLlama\\TxtEncontrarQuienLlama.png", 15);
		   
		   if(TipoBusqueda.equalsIgnoreCase("Rut"))
		   {
			   DataRowExcel("Rut", Value);
			   //Comentado para convergente
			  // SelectComboboxText("EncontrarQuienLlama\\TipoIdentificacion"  ,"RUT",3);
			   InputText("EncontrarQuienLlama\\ValorIdentificador", Value, 1);
		   }
		   else
		   {
			   DataRowExcel("Telefono", Value);
			   InputText("EncontrarQuienLlama\\NumeroSuscripcion.png", Value, 1);
		   }   
			   
		   sikuli.ClickImg("EncontrarQuienLlama\\BuscarAhora.png", 15);
		   boolean ContaExt = sikuli.FindImgWait("BusquedaContactoExtendida\\TxtTitle.png", 5);
		   if(ContaExt && TipoBusqueda.equalsIgnoreCase("Rut"))
		   {
			   sikuli.ClickImg("BusquedaContactoExtendida\\PrimerRadioButton.png", 10);
			   sikuli.ClickImg("BusquedaContactoExtendida\\Seleccionar.png", 10);
		   }

		   else if(ContaExt)
		   {
			   //BusquedaContactoActivo();			   
               boolean Estad = sikuli.FindImgWait("BusquedaContactoExtendida\\Activo.png", 5);
			   if(Estad) {
				   sikuli.ClickImg("BusquedaContactoExtendida\\Activo.png", 10);
			   }else {
				   sikuli.ClickImg("BusquedaContactoExtendida\\Suspender.png", 10);
			   }
			   sikuli.ClickImg("BusquedaContactoExtendida\\Seleccionar.png", 10);
		   }
		   
		   Thread.sleep(10000);
	   }
	   
	   public void VentaCliente(Hashtable<String, String> Data) throws Exception
	   {   
		   String Caso 		  	 = Data.get("Caso");
		   String Rut  		  	 = Data.get("Rut");
		   String TipoCliente	 = Data.get("TipoCliente");
		   String SubTipoCliente = Data.get("SubTipoCliente");
		   String Ciclo 		 = Data.get("Ciclo");
		   String Plan 			 = Data.get("NombrePlan");
		  
		   if(Data.get("Mail")==null || Data.get("Mail").equals("null"))
			   Mail = "claroseiya@gmail.com";
		   else			   
			   Mail = Data.get("Mail");
		   
		   if(Rut.equalsIgnoreCase("null"))
		   {
			   if((TipoCliente.equalsIgnoreCase("Empresa"))||
			     (TipoCliente.equalsIgnoreCase("Pyme"))||
			     (TipoCliente.equalsIgnoreCase("Empresas"))||
			     (TipoCliente.equalsIgnoreCase("Corporacion")))
			   {
				   System.out.println("Creando empresa!!!");
				   CrearEmpresa(TipoCliente,SubTipoCliente,Ciclo);
				   //CrearCBPEmpresa(TipoCliente,SubTipoCliente,Ciclo);
				   CrearCuentaFinancieraEmpresa();
				   //VentaPoolShared("POOL Shared Allowance");
			   }
			   else if((TipoCliente.equalsIgnoreCase("Prepago")))
			   {
				   CrearPrepago();
			   }
			   
			   
			   else
			   {   
				   if((TipoCliente.equalsIgnoreCase("VENTA HOGAR")))
					   CrearPersona(true);
				   else
					   CrearPersona(false);
				   
				   CrearCBP(Ciclo);
				   CrearCuentaFinanciera(Caso);   
			   }
			   
		   }
		   else {
			   BuscarPersonaExistente(Rut,"Rut");
		   }
	   }
	   
	   public void CrearEmpresaMenu() throws FindFailed, InterruptedException, IOException
	   {
		   sikuli.ClickImg("Header\\Crear.png", 2);
		   sikuli.ClickImg("Header\\Organizacion.png", 2);
		   
		   
	   }
	   public void CrearEmpresaEncontrarQuienLlama() throws FindFailed, InterruptedException, IOException
	   {
		   sikuli.ClickImg("Header\\EncontraQuienLlamaMismoContacto.png", 2);
		   sikuli.ClickImg("BusquedaContactoExtendida\\CrearOrganizacion.png", 15);
			
	   }
	   
	   public void EncontrarQuienLlama(String Rut) throws FindFailed, InterruptedException, IOException
	   {
		
		    sikuli.ClickImg("EncontrarQuienLlama.png", 8);
			sikuli.ClickImg("EncontrarQuienLlama\\TxtEncontrarQuienLlama.png", 15);
			//SelectComboboxText("EncontrarQuienLlama\\TipoIdentificacion"  ,"RUT",3);
			InputText("EncontrarQuienLlama\\ValorIdentificador", Rut, 1);
			sikuli.ClickImg("EncontrarQuienLlama\\BuscarAhora.png", 15);
	   }
	   
	   public void CrearEmpresa(String TipoCliente,String SubTipoCliente,String Ciclo) throws FindFailed, InterruptedException, IOException, UnsupportedFlavorException
	   {
		   	 Amdocs amd = new Amdocs();
			 Au= AutoIt.getAutoit();		
			 Au.winActivate(PaginaAmdocs);
			 
			String Rut = Data.GetRutRandomEmpresa();
			String randomString = RandomString.generateSessionKey(9);
			DataRowExcel("Rut", Rut);	
			
			//Se cambia la creación de la empresa por el menú puesto que hay un error en amdocs			
			CrearEmpresaMenu();
			//CrearEmpresaEncontrarQuienLlama();
			
			sikuli.FindImg("CrearOrganizacion\\TxtTitulo.png", 30);
			InputText("CrearOrganizacion\\NombreOrganizacion", "OrganizacionQA"+randomString, 1);
			InputText("CrearOrganizacion\\Telefono", "56999999999", 1);
			sikuli.ClickImg("CrearOrganizacion\\DireccionCorreo", 10);
			sikuli.InputText(Mail);
			InputText("CrearOrganizacion\\IdentificadorNegocio", Rut, 1);
			sikuli.ClickImg("CrearOrganizacion\\Verificar.png", 1);
			sikuli.FindImg("CrearOrganizacion\\EstadoValidacion.png", 30);
			sikuli.ClickImg("CrearOrganizacion\\Aceptar.png", 1);
			
			//Se elimina creacion de CSR 
//			sikuli.ClickImg("CrearOrganizacion\\UtilizarConsulta.png", 10);
//			sikuli.ClickImg("CrearOrganizacion\\BuscarAhora.png", 20);
//			sikuli.ClickImg("CrearOrganizacion\\test_one_supervisor.png", 20);
//			sikuli.ClickImg("CrearOrganizacion\\Seleccionar.png", 20);
			//
			sikuli.ClickImg("CrearOrganizacion\\Descripcioncodigo.png", 20);
			sikuli.ClickImg("CrearOrganizacion\\BuscarAhora.png", 20);
			sikuli.ClickImg("CrearOrganizacion\\CultivoCebada.png", 20);
			sikuli.ClickImg("CrearOrganizacion\\Seleccionar.png", 20);
			
			sikuli.FindImg("CrearOrganizacion\\TxtTitulo.png", 30);
			
			//Tengo que ver si aca mejoro esto
			sikuli.ClickImg("CrearOrganizacion\\GuardarYContinuar.png", 20);
			
			sikuli.ClickImg("CrearOrganizacion\\Guardar.png", 10);
			//sikuli.FindImg("PaginaPrincipalInteraccion\\TxtPaginaPrincipalInteraccion", 30);
			
			CrearCBPEmpresa(TipoCliente,SubTipoCliente,Ciclo, Rut);
			
			//Thread.sleep(5000);			
			
			//sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 15);
			
			
			//sikuli.ClickImg("Alert\\Descartar.png", 5);
			//amd.Errores();
			
	   }
	   
	   public void CrearCBPEmpresa(String TipoCliente, String SubTipoCliente, String Ciclo, String Rut) throws FindFailed, InterruptedException, IOException, UnsupportedFlavorException
	   {
		   Amdocs amd = new Amdocs();
		   DataRowExcel("Ciclo", Ciclo);
		   //sikuli.FindImg("PaginaPrincipalInteraccion\\TxtPaginaPrincipalInteraccion", 10);
		   TimeSleep(5000);
		   //sikuli.ClickImg("Header\\ApellidoOrganizacion", 20);   
		   sikuli.FindImg("Organizacion\\TxtTitle", 20);
		   sikuli.ClickImg("Organizacion\\ComboboxSelContacto.png", 20);
		   sikuli.ClickImg("Organizacion\\Contactos.png", 20);
		   sikuli.ClickImg("Organizacion\\DescripcionOrganizacion.png", 20);
		   amd.FindImgScroll("Organizacion\\Guardar","down", 10);
		   sikuli.ClickImg("Organizacion\\BusquedaContacto", 10);
		   Thread.sleep(3000);
		   InputText("Organizacion\\Nombre",   "SA", 15);
		   InputText("Organizacion\\Apellido", "SA", 15);
		   sikuli.ClickImg("Organizacion\\BuscarAhora", 1);
		   
		   //Este no esta en ambos ambientes
		   //sikuli.ClickImg("Organizacion\\SamuelSaladillo", 20);		   
		   //sikuli.ClickImg("Organizacion\\SamuelSalazar", 20);
		   
		   sikuli.ClickImg("Organizacion\\TitularActivo", 20);
		   
		   Thread.sleep(1000);
		   sikuli.ClickImg("Organizacion\\Seleccionar.png", 5);
		   sikuli.ClickImg("Organizacion\\Agregar.png", 5);
		   sikuli.ClickImg("Organizacion\\crear.png", 5);
		   Thread.sleep(3000);
		   if(TipoCliente.equalsIgnoreCase("Empresas"))
			   SelectCombobox("Organizacion\\TipoCliente"  ,"Organizacion\\Empresas",3);
		   else if(TipoCliente.equalsIgnoreCase("Pyme"))
			   SelectCombobox("Organizacion\\TipoCliente"  ,"Organizacion\\Pyme",3);
		   else
		   SelectComboboxText("Organizacion\\TipoCliente"  ,TipoCliente,3);
		   
		   if(SubTipoCliente.equalsIgnoreCase("Empresas")||SubTipoCliente.equalsIgnoreCase("CORPORACIONES"))
			   Thread.sleep(500);
		   else	   
			   SelectComboboxText("Organizacion\\SubTipoCliente"  ,SubTipoCliente,3);
		   
		   sikuli.ClickImg("Organizacion\\DireccionPrincipal.png", 5);
		   sikuli.FindImg("Organizacion\\TxtTitle", 20);
		   Thread.sleep(3000);		   
		   InputText("Organizacion\\Ciudad",   "SA", 15);
		   InputText("Organizacion\\Comuna", "HU", 15);
		   sikuli.ClickImg("Organizacion\\IncluirDirecciones.png", 5);
		   sikuli.ClickImg("Organizacion\\BuscarAhora", 1);
		   
		   //Esta direccion no esta en ambos ambientes
		   sikuli.ClickImg("Organizacion\\AvElSalto", 15);
		   //sikuli.ClickImg("Organizacion\\ricardoCumming", 15);
		   
		   //sikuli.ClickImg("Organizacion\\SantiagoSantiago", 15);
		   
		   
		   
		   sikuli.ClickImg("Organizacion\\Seleccionar.png", 5);
		   sikuli.ClickImg("Organizacion\\CicloFacturacion.png", 5);
		   
		   SeleccionarCiclo(Ciclo);
		   sikuli.ClickImg("Ciclos\\Seleccionar", 1);
		   sikuli.FindImg("Organizacion\\TxtTitle", 20);
		    
		   sikuli.ClickImg("CrearContacto\\Guardar", 1);
		   TimeSleep(20000);
		   sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 3);
		   //TimeSleep(15000);
		   
		   EncontrarQuienLlama(Rut);
		   Thread.sleep(1000);
			
		   
		   
		   sikuli.ClickImg("PaginaPrincipalInteraccion\\Refrescar.png", 3);
		   TimeSleep(15000);
		   String CBP =sikuli.GetTextRigth("Header\\\\PerfilFacturacionCliente",1);
		   System.out.println("CBP: "+CBP);
		   DataRowExcel("CBP", CBP);
	   }
	   
	   public void CrearCuentaFinancieraEmpresa() throws UnsupportedFlavorException, IOException, FindFailed
	   {
		   sikuli.ClickImg("Menu\\Crear", 1);
		   sikuli.ClickImg("Menu\\GestorFacturacion", 1);
		   sikuli.ClickImg("Menu\\CuentaFinancieraAcuerdoFacturacion", 1);
		   sikuli.FindImg("CrearCuentaFinanciera\\txtCuentaFinanciera", 15);
		   TimeSleep(3000);
		   sikuli.ClickImg("CrearCuentaFinanciera\\Nombre", 15);
		   FindImgScroll("Organizacion\\Guardar","down", 10);
		   sikuli.ClickImg("CrearCuentaFinanciera\\Correo", 10);		   
		   sikuli.InputText(Mail);
		   sikuli.ClickImg("CrearCuentaFinanciera\\Guardar", 1);
		   TimeSleep(5000);
		   sikuli.ClickImg("PaginaPrincipalInteraccion\\Refrescar.png", 30);
		   TimeSleep(10000);
		   String FA =sikuli.GetTextDown("PaginaPrincipalInteraccion\\CtaFinanciera",1);
		   System.out.println("FA: "+FA);
		   DataRowExcel("FA", FA);
		  
	   }
	   
	   public void VentaPoolShared(String PoolShared) throws Exception {
		   
			SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");
			Acreditacion("Empresa","","");
			SeleccionarPlan(PoolShared); 
			motivoOrden("prueba de automatizacion QA","");
		    sikuli.FindImg("PaginaConfiguracion\\TituloNegociarConfiguracion", 30);
			amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
			sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
			
			distribucionCargo("Mensajero","SinFA", "");
	   }
	   
	   public void SeleccionarCiclo(String Ciclo) throws FindFailed, UnsupportedFlavorException, IOException
	   {
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 	
			
		   
		   sikuli.FindImg("Ciclos\\CodigoCiclo.png", 30);
		   sikuli.ClickTextDown("Ciclos\\CodigoCiclo.png", 10);
		   
		   for(int i=0;i<20;i++)
		   {
			   String cicloAmdocs = GetCopyClip();
			   if(cicloAmdocs.equals(Ciclo))
				   return;
			   sikuli.InputText(Keys.DOWN);
		   }
		   
	   }
	   
  public String paginaOrden() throws UnsupportedFlavorException, IOException, FindFailed {		    
		    
		    AutoIt.IniciarAutoIt();
		    AutoItX Au= AutoIt.getAutoit();	
		    Au.winActivate(PaginaAmdocs);
		    
		    String dato = "";
		    sikuli.FindImgWait("paginaOrden\\TituloOrden.png", 30);
		    
		    amd.TimeSleep(5000);
		   dato = sikuli.GetTextRandom("paginaOrden\\accionOrden", 10, 0, -40);
		    
		   // dato = sikuli.GetTextRigth("paginaOrden\\\\accionOrden",1);
                    String str = dato;
		    String orden = "";
		    String [] cadenas = str.split("[^0-9_A].");
		    for(int i = 0; i<cadenas.length; i++){
				 orden = orden+cadenas[i];
		    }		    
		    System.out.println("Orden: "+orden);
		
		    //cerrar ventana --para cambio de numero
		    //sikuli.ClickImg("paginaOrden\\cerrarPagina.png", 10);
		    
		    DataRowExcel("Orden", orden);
		    
		   	return orden;
	   }
	   	
  	  public void ManejoManual(String orden) throws UnsupportedFlavorException, IOException, FindFailed {		    
  		sikuli.ClickImg("raizAmdocs\\buscar.png", 10);
	   	sikuli.ClickImg("raizAmdocs\\btnOrden.png", 10);
	   	//amd.TimeSleep(1000);
	   	sikuli.goToCoordinates("raizAmdocs\\productoAsignado.png", 10);
	   	//amd.TimeSleep(1000);
	   	sikuli.ClickImg("raizAmdocs\\poolOrdenes.png", 10);
	   	amd.TimeSleep(2000);	   	
	   	sikuli.ClickImg("PaginaPollOrden\\pestPropietario.png", 10);
	   	amd.TimeSleep(2000);
	   	sikuli.clickCordinates("PaginaPollOrden\\inputFecha.png", 10, 30, -10);
	   	System.out.println("aqui pase");amd.TimeSleep(2000);
	   	Au.controlSend(PaginaAmdocs, "", "", orden);
	   	Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
	   	
  	  }     
  
	   public void paginaPoolOrden(String dato) throws FindFailed, UnsupportedFlavorException, IOException {
		   
		   AccionesExcel excelAccion = new AccionesExcel();
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs);
		   
		   if(sikuli.FindImgWait("PaginaPollOrden\\radioOrden.png", 20)) {
			   sikuli.clickCordinates("PaginaPollOrden\\radioOrden.png", 10, -35, -10);			   
			   sikuli.ClickImg("PaginaPollOrden\\btnManejoManual.png", 10);		 
		   }
		   if(sikuli.FindImgWait("PaginaPollOrden\\errorBusqueda.png", 20) == true) {
			   sikuli.ClickImg("PaginaPollOrden\\pestExcepcion.png", 10);
			   sikuli.clickCordinates("PaginaPollOrden\\inputFecha.png", 10, 30, -10);
			   Au.controlSend(PaginaAmdocs, "", "", dato);
			   Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
			   sikuli.clickCordinates("PaginaPollOrden\\radioOrden.png", 10, -35, -10);
			   sikuli.ClickImg("PaginaPollOrden\\btnManejoManual.png", 10);
			   if(sikuli.FindImgWait("PaginaPollOrden\\btnEnviarExcep.png", 20)) {
				   sikuli.ClickImg("PaginaPollOrden\\btnEnviarExcep.png", 10);
			   }
			   
			   sikuli.ClickImg("PaginaPollOrden\\pestPropietario.png", 10);
			   sikuli.clickCordinates("PaginaPollOrden\\inputFecha.png", 10, 30, -10);
			   Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
			   sikuli.clickCordinates("PaginaPollOrden\\radioOrden.png", 10, -35, -10);
			   sikuli.ClickImg("PaginaPollOrden\\btnManejoManual.png", 10);
		   }	   
		   
	   }
	   
	   public void ventanaSolicitudEquipo(boolean CambioEquipo, boolean ManejoDoble) throws UnsupportedFlavorException, IOException, FindFailed {
		   
		   Amdocs amd = new Amdocs();
		   AccionesExcel excelAccion = new AccionesExcel();
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();
		   boolean estado;
		   String datoSim   = "";
		   String datoHNDST = "";
		   do
		   {
			   String[] sim = excelAccion.GetSimcard("SimCard");
			   datoSim = sim[0];
			   datoHNDST = sim[1];
			   
			   if(CambioEquipo)
			   {
				   sikuli.ClickImg("paginaEnvioSolicitudEquipo\\IdentificacionEquipo.png", 15);
				   amd.clearText();
				   Au.controlSend(PaginaAmdocs, "", "", datoHNDST);
				  
			   }				   
			   else
			   {
				   sikuli.clickCordinates("paginaEnvioSolicitudEquipo\\colorSim.png", 10, 30, -30);
				   amd.clearText();
				   Au.controlSend(PaginaAmdocs, "", "", datoHNDST);
				   sikuli.clickCordinates("paginaEnvioSolicitudEquipo\\colorSim.png", 10, 30, -10);
				   amd.clearText();			  
				   Au.controlSend(PaginaAmdocs, "", "", datoSim);
			   }
			   excelAccion.SetSimcardUtilizada("SimCard", sim[0]);
			   sikuli.ClickImg("paginaEnvioSolicitudEquipo\\checkDocumentos.png", 15);		   
			   sikuli.ClickImg("paginaEnvioSolicitudEquipo\\enviarSolicitudEquipo.png", 15);
			   amd.TimeSleep(3000);
			   estado = sikuli.FindImgWait("paginaEnvioSolicitudEquipo\\errorSimCard.png", 15);
				
			   if(estado == true) {
					   sikuli.ClickImg("paginaEnvioSolicitudEquipo\\cerrarValidacion.png", 15);
					   amd.TimeSleep(2000);
					   sikuli.ClickImg("paginaEnvioSolicitudEquipo\\checkDocumentos.png", 15);
				   }
				   excelAccion.SetSimcardUtilizada("SimCard", sim[0]);
		   }while(estado == true); 
		   
		   DataRowExcel("Sim", datoSim);
		   DataRowExcel("Imsi", datoHNDST);
		   
		   if(ManejoDoble)
			   ManejoManualDoble();
		   
		   sikuli.ClickImg("PaginaPollOrden\\cerrarVentana.png", 15);
	   }
	   
	   public void paginaPrincipalInteraccion(String dato) throws FindFailed, UnsupportedFlavorException, IOException {
		   int cant = 0;
		   amd.TimeSleep(2000);
		   sikuli.ClickImg("PaginaPrincipalInteraccion\\pestTrabajo.png", 20);
		   amd.TimeSleep(2000);
		   boolean estado = false;
		   String info2="";
		   do {
			   sikuli.ClickImg("PaginaPrincipalInteraccion\\Refrescar.png", 20);
			   if(sikuli.FindImgWait("PaginaPrincipalInteraccion\\radioOpciones.png", 20)) {
				   amd.TimeSleep(5000);
				   String info = sikuli.GetTextRandomCelda("PaginaPrincipalInteraccion\\radioOpciones.png", 15, -150, 50);
				   if(info.equals(dato) ) {
					     info2 = sikuli.GetTextRandomCelda("PaginaPrincipalInteraccion\\tituloEstado.png", 15, -20, 10);
					   System.out.println(info2);
					   if(info2.equals("Submitted")) {
						   estado = false;
						   System.out.println("Orden aun Enviada");
					   }
					   if(info2.equals("Abierto")) {
						   estado = false;
						   System.out.println("Orden aun Abierta");
					   }
					   if(info2.equals("Cerrado")) {
						   estado = true;						   
						   System.out.println("Orden Cerrada");
						   break;
					   }					   
					   //sikuli.goToImg("PaginaPrincipalInteraccion\\tituloEstado.png", 15, 0, 20);
					   //String estado1 = sikuli.GetText("PaginaPrincipalInteraccion\\estadoOrden.png", 15, 0, 0);
					   
				   }
				   
			   }
			   cant++;
			   System.out.println(cant);
		   } while (cant <= 7);
		   
		   DataRowExcel("EstadoTrx", info2);
	   }
	   
	   public void NegociarPortabilidad(Hashtable<String, String> Data,String tipAdquisicion, String imei, String marca, String modelo, Boolean roaming, Boolean ldi, String SKU) throws IOException, FindFailed, InterruptedException, UnsupportedFlavorException
	   {
		   String Cel = Data.get("Telefono");
		   String Reasoncode  = Data.get("ReasonCode");
//		   int largo = Cel.length();
//		   if(largo>9)
//			   Cel= Cel.substring(2, 11);
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 
		
		   sikuli.FindImg("PaginaConfiguracion\\asignarNumero.png", 60); 
		   sikuli.ClickImg("PaginaConfiguracion\\asignarNumero.png", 20);
		   sikuli.ClickImg("PaginaConfiguracion\\NumeroPortar.png", 20);
		   Au.controlSend(PaginaAmdocs, "", "", Cel);
		   SelectComboboxText("PaginaConfiguracion\\ProveedorExterno.png"  ,"ENTEL MOVIL",3);
		   SelectComboboxText("PaginaConfiguracion\\TipoNumeroOriginal.png"  ,"Postpago",3);
		   SelectComboboxText("PaginaConfiguracion\\TipoANombreDe.png"  ,"RUT",3);
		   sikuli.ClickImg("PaginaConfiguracion\\ValorANombreDe.png", 3);
		   Au.controlSend(PaginaAmdocs, "", "", "243351545");
		   sikuli.ClickImg("PaginaConfiguracion\\DocumentosRecibidos.png",   3);
		   
		   //Obtener zona geografica
		   sikuli.ClickImg("PaginaConfiguracion\\ObtenerZonaGeografica.png", 3);
		   sikuli.FindImg("PaginaConfiguracion\\ZonaGeografica.png", 60);
		   SelectComboboxText("PaginaConfiguracion\\Departamento.png"  ,"METROPOLITANA DE SANTIAGO",3);
		   SelectComboboxText("PaginaConfiguracion\\Ciudad.png"  ,"Santiago",3);
		   SelectComboboxText("PaginaConfiguracion\\Barrio.png"  ,"Santiago",3);
		   sikuli.ClickImg("PaginaConfiguracion\\OK.png", 3);
		   sikuli.ClickImg("PaginaConfiguracion\\Agregar.png", 3);
		   sikuli.FindImg("PaginaConfiguracion\\IdentificarTipo.png", 5);
		   sikuli.ClickImg("PaginaConfiguracion\\Validar.png", 3);
		   
		   sikuli.FindImg("PaginaConfiguracion\\MensajesValidacion.png", 30);
		   sikuli.ClickImg("PaginaConfiguracion\\CerrarMensajesValidacion.png", 3);
		   sikuli.ClickImg("PaginaConfiguracion\\PortIn.png", 3);
		   sikuli.ClickImg("PaginaConfiguracion\\TipoDeNumero.png", 3);
		   
		   Thread.sleep(1500);
		   sikuli.ClickImg("PaginaConfiguracion\\configuracion.png", 5);
		   sikuli.ClickImg("PaginaConfiguracion\\MSISDN.png", 10);
		   TipoAdquision(tipAdquisicion, imei, marca, modelo, roaming, ldi,Cel, SKU, Reasoncode);
		   
		   
	   }
	   
	   public void CrearPrepago() throws UnsupportedFlavorException, IOException, FindFailed, InterruptedException
	   {
			 
			 Au= AutoIt.getAutoit();		
			 Au.winActivate(PaginaAmdocs);
			 
			String Rut = Data.GetRutRandom();
			String randomString = RandomString.generateSessionKey(9);
			DataRowExcel("Rut", Rut);
			
			sikuli.ClickImg("Header\\EncontraQuienLlamaMismoContacto.png", 1);
			sikuli.ClickImg("EncontrarQuienLlama\\CrearContacto", 10); 
			sikuli.FindImg("txtCrearContacto", 10);
			sikuli.ClickImg("CrearContacto\\\\ContactoParaPrepago", 10);
			
			//sikuli.ClickImg("CrearContacto\\TipoIdentificacionPersonal", 10);
			
			//sikuli.ClickImg("CrearContacto\\SelecRut", 1);
			SelectComboboxText("CrearContacto\\TipoIdentificacionPersonal", "R",5);
			
			InputClearText("CrearContacto\\NumeroIdentificacionPersonal", Rut, 1);
			sikuli.ClickImg("CrearContacto\\Validar", 1);
			sikuli.FindImg("CrearContacto\\ValidacionIdentificacion", 15);				
			Au.controlSend(PaginaAmdocs, "", "",Keys.ENTER, false);
			InputClearText("CrearContacto\\NombreContacto", "NombreQA"+randomString, 1);
			InputClearText("CrearContacto\\ApellidoContacto", "ApellidoQA", 1);
			InputClearText("CrearContacto\\FechaNacimiento", "15/11/1984", 1);
			SelectCombobox("CrearContacto\\Genero","Masculino", 1);
			SelectCombobox("CrearContacto\\MetodoContacto","Correo", 1);
			SelectCombobox("CrearContacto\\EstadoCorreo","CorreoEntregado", 1);
			sikuli.ClickImg("CrearContacto\\DireccionCorreo", 10);		
			sikuli.InputText(Mail);		
			InputClearText("CrearContacto\\TelefonoDomiciliario", "56999999999", 1);
			InputClearText("CrearContacto\\TelefonoCelular", "56999999999", 1);
			sikuli.ClickImg("CrearContacto\\IconDireccion", 1);
			sikuli.FindImg("CrearContacto\\txtBuscarDireccion", 15);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\IncluirDireccionesNoValida", 1);
			InputText("CrearContacto\\BuscarDireccion\\NombreCalle", "El salto", 1);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\BuscarAhora", 1);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\AvElSalto", 15);
			sikuli.ClickImg("CrearContacto\\BuscarDireccion\\Seleccionar", 1);
			sikuli.FindImg("CrearContacto\\txtCrearContacto", 15);
			sikuli.ClickImg("CrearContacto\\Guardar", 1);
			
			sikuli.FindImg("BusquedaContactoExtendida\\TxtTitle", 60);
			sikuli.ClickImg("BusquedaContactoExtendida\\TitularDeCuenta", 1);
			sikuli.ClickImg("BusquedaContactoExtendida\\Seleccionar", 1);
			
			//Obtener CBP
			TimeSleep(15000);
			String CBP =sikuli.GetTextRigth("Header\\PerfilFacturacionCliente",1);
			System.out.println("CBP: "+CBP);
			DataRowExcel("CBP", CBP);
			
			//Obtener FA
			 String FA =sikuli.GetTextDown("PaginaPrincipalInteraccion\\CtaFinanciera",1);
			 System.out.println("FA: "+FA);
			 DataRowExcel("FA", FA);
			
			}
	   public void buscarProductoAsignado(String Telefono) throws FindFailed, IOException, InterruptedException
	   {
		   Amdocs amd = new Amdocs();
		   AccionesExcel excelAccion = new AccionesExcel();
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();
		
		   sikuli.FindImgWait("BuscarProductosAsignados\\Titulo.png", 60);
		   InputText("BuscarProductosAsignados\\Identificacion",   Telefono, 15);
		   sikuli.ClickImg("BuscarProductosAsignados\\BuscarAhora.png", 3);
                   if(sikuli.FindImgWait("BuscarProductosAsignados\\SinRegistros", 10) == true) {
		    	 sikuli.ClickImg("BuscarProductosAsignados\\IncluirSubProdCancelados.png", 60);
		    	 sikuli.ClickImg("BuscarProductosAsignados\\BusquedaSinGuardar.png", 60);
		    	 SelectComboboxText("BuscarProductosAsignados\\MostrarTodo.png"  ,"M",3);
//		    	 sikuli.FindImg("BuscarProductosAsignados\\PopUpCriterios.png", 60);
//		    	 sikuli.ClickImg("BuscarProductosAsignados\\Aceptar.png", 60);
		    	 InputText("BuscarProductosAsignados\\Identificacion",   Telefono, 15);
				 sikuli.ClickImg("BuscarProductosAsignados\\BuscarAhora.png", 3);
			   }
		
		   sikuli.ClickImg("BuscarProductosAsignados\\SeleccionarCheck.png", 60);
		   TimeSleep(5000);
		   sikuli.ClickImg("BuscarProductosAsignados\\IniciarOrden.png", 15);
	   }
	   
	   
	   
	   public void CambiarOferta(String Telefono) throws FindFailed, IOException
	   {
		   sikuli.FindImgWait("CambiarOferta\\Titulo.png", 90);
		   FindImgScrollBarra("CambiarOferta\\Siguiente.png", "down",15);
		   sikuli.ClickImg("CambiarOferta\\Siguiente.png", 10);
	   }
	   
	   public void configuracionSaltar() throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
		AutoIt.IniciarAutoIt();
		AutoItX Au= AutoIt.getAutoit();	
		Au.winActivate(PaginaAmdocs);
		 
		sikuli.FindImg("PaginaConfiguracion\\asignarNumero.png", 60);
		Thread.sleep(5000);
		sikuli.goToImg("PaginaConfiguracion\\calcular", 10, 0, 0);
		amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
	   }
	   
	   
	   public void configuracionCambioEquipo(String TipoCliente, String tipAdquisicion, String imei, String marca, String modelo, Boolean roaming, Boolean ldi, String NumTelefono, String SKU, String ReasonCode) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
			AutoIt.IniciarAutoIt();
			AutoItX Au= AutoIt.getAutoit();	
			Au.winActivate(PaginaAmdocs);
			 
		   
			sikuli.FindImg("PaginaConfiguracion\\asignarNumero.png", 60);
			Thread.sleep(5000);
			sikuli.ClickImg("PaginaConfiguracion\\DeEquipo.png", 5);
			Thread.sleep(5000);
			sikuli.ClickImg("PaginaConfiguracion\\Borrar.png", 5);
			Thread.sleep(5000);
			sikuli.DobleClickImg("PaginaConfiguracion\\EquipoyTarjetaSim.png", 20);
			sikuli.ClickImg("PaginaConfiguracion\\DeEquipo.png", 5);
			Thread.sleep(5000);
			sikuli.ClickImg("PaginaConfiguracion\\Agregar.png", 10);
			
			TipoAdquision(tipAdquisicion, imei, marca, modelo, roaming, ldi,NumTelefono, SKU,ReasonCode);
			
		   }
	   
	   public void ConsultaCredito() throws FindFailed, IOException
	   {
		   if(sikuli.FindImgWait("ConsultarCredito\\Titulo.png", 60))
		   {
			   sikuli.ClickImg("ConsultarCredito\\AceptarCondiciones.png", 5);
			   FindImgScrollBarra("ConsultarCredito\\Siguiente.png", "down",15);
			   sikuli.ClickImg("ConsultarCredito\\Siguiente.png", 5);   
		   }
	   }
	   
	   public void CerrarVentanaProductosAsignados() throws FindFailed
	   {
		   sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 1);
		   sikuli.FindImgWait("BuscarProductosAsignados\\Titulo.png", 60);
		   sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 1);
	   }
	   
	   public void CerrarVentanaOrden() throws FindFailed
	   {
		   sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 1);
	
	   }
	   
	   public void resumenOrdenCambioEquipo(Hashtable<String, String> Data) throws FindFailed, IOException, UnsupportedFlavorException {
		   
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs);
		   String TipoCliente = Data.get("TipoCliente");
		   String Caso    = Data.get("Caso");
			
		   
		   sikuli.ClickImg("paginaResumenOrden\\pestDocumentos", 30);
		   if(sikuli.FindImgWait("paginaResumenOrden\\checkDocumentos", 15) == true) {
			   	sikuli.clickCordinates("paginaResumenOrden\\checkDocumentos", 5, -80, -10);
		   }	
		   
		   sikuli.ClickImg("paginaResumenOrden\\pestPrecios", 5);
		   sikuli.ClickImg("paginaResumenOrden\\Totales", 10);
		   FindImgScroll("paginaResumenOrden\\enviarResumen","down", 10);
		   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
	   }

	   
	   public void FinalizarCambioEquipo(String TipoAdquisicion, Hashtable<String, String> Data) throws Exception
	   {
		   String TipoCliente = Data.get("TipoCliente");
		   
		   if(!TipoAdquisicion.equalsIgnoreCase("Propio"))
		   {
			    paginaDespacho("M");
				paginaDireccionEnvio("987654321");
			    distribucionCargo("M","SinFA", TipoCliente);
		   }
		   
		   resumenOrdenCambioEquipo(Data);
		   String Orden = paginaOrden();
		   
		   if(!TipoAdquisicion.equalsIgnoreCase("Propio"))
		   {
			   ManejoManual(Orden);
			   paginaPoolOrden(Orden);
			   ventanaSolicitudEquipo(true,false);
			   
			   //CerrarVentanaProductosAsignados();
			   //paginaPrincipalInteraccion(Orden);
		   }
	   }
	   
	   //Este resumen de orden valida menos paso, siver para operaciones que no sean ventas
	   public void ResumenDeOrdenExpress(Hashtable<String, String> Data) throws FindFailed, IOException, UnsupportedFlavorException {
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs);
		   String TipoCliente = Data.get("TipoCliente");
		   String Caso    = Data.get("Caso");
			
		   
		   	   sikuli.ClickImg("paginaResumenOrden\\pestDocumentos", 30);
		   	   
		   	   for(int i=0; i<30;i++)
		   	   {
		   		   if(sikuli.FindImgWait("paginaResumenOrden\\NoHayDocumentos", 1))
		   			   break;
			   	   else if(sikuli.FindImgWait("paginaResumenOrden\\checkDocumentos", 1)){
					   sikuli.clickCordinates("paginaResumenOrden\\checkDocumentos", 5, -80, -10);
					   break;
				   }   
		   	   }
			 
               sikuli.ClickImg("paginaResumenOrden\\pestPrecios", 5);
		   	   
		   	   if(Caso.equals("Reconexion")) {
		   		    
		   		 amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
		   		 
		   		 if(sikuli.FindImgWait("paginaResumenOrden\\enviarResumenSelecionada", 10)) { 
		   			 sikuli.ClickImg("paginaResumenOrden\\enviarResumenSelecionada", 1);
		   		 } else {
					   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 1);
				 }
				  
		   	   } else {

				   FindImgScrollBarra("paginaResumenOrden\\enviarResumen", "down",15);
				   amd.FindImgScroll("paginaResumenOrden\\EnviarOrden","down", 10);
				   //sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
				   if(sikuli.FindImgWait("paginaResumenOrden\\enviarResumenSelecionada", 5)) { 
			   			 sikuli.ClickImg("paginaResumenOrden\\enviarResumenSelecionada", 1);
			   		 } else {
						   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 1);
					 }
		   	   }
		   
	   }
	   
	   public void ManejoManualDoble() throws IOException, UnsupportedFlavorException, FindFailed
	   {
		   AccionesExcel excelAccion = new AccionesExcel();
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs);
		   amd.TimeSleep(3000);
		   if(sikuli.FindImgWait("PaginaPollOrden\\radioOrden.png", 20)) {
			   sikuli.clickCordinates("PaginaPollOrden\\radioOrden.png", 10, -35, -10);			   
			   sikuli.ClickImg("PaginaPollOrden\\btnManejoManual.png", 10);	
			   sikuli.ClickImg("paginaEnvioSolicitudEquipo\\enviarSolicitudEquipo.png", 15);
			   amd.TimeSleep(3000);
			  
		   }
	
	   }
	   public void BuscarEstadoOrden(String Orden) throws FindFailed, UnsupportedFlavorException, IOException
	   {
		   	
		    sikuli.ClickImg("raizAmdocs\\buscar.png", 10);
		   	sikuli.ClickImg("raizAmdocs\\btnOrden.png", 10);
			sikuli.goToCoordinates("raizAmdocs\\productoAsignado.png", 10);
			sikuli.ClickImg("raizAmdocs\\Ordenes.png", 10);
			amd.TimeSleep(2000);
			sikuli.FindImgWait("BuscarOrden\\1 Registro.png", 20);
			sikuli.ClickDown("BuscarOrden\\Motivo.png", 10);
			String Estado="";
			int Intentos=0;
			do
			{
				for(int i = 0;i<20;i++)
				{
					   String row = GetCopyClip();
					   if(row.contains(Orden))
					   {
						   Estado=GetEstadoOrden(row);
						   DataRowExcel("EstadoTrx", Estado);
						   break;
					   }	   
						   
					   sikuli.InputText(Keys.DOWN);
				}
			Intentos++;
			if(!Estado.equals("Cerrado"))
			{
				sikuli.ClickImg("BuscarOrden\\Buscar Ahora.png", 10);
				WaitBuscando(30);
				sikuli.FindImgWait("BuscarOrden\\1 Registro.png", 20);
				sikuli.ClickDown("BuscarOrden\\Motivo.png", 10);
				
				
			}	
				
			}while(!Estado.equals("Cerrado")&&Intentos<6 );
			
			System.out.println("Fin de la Operacion!!... ");
	   }
		
	   public String GetEstadoOrden(String value)
	   {
		   String Estado=null;
		   if(value.contains("Abierto"))Estado="Abierto ";
		   else if(value.contains("Cerrado"))Estado="Cerrado";
		   else if(value.contains("Cancelado"))Estado="Cancelado";
		   else if(value.contains("Descontinuado"))Estado="Descontinuado";
		   else if(value.contains("Enviado"))Estado="Enviado";
		   else if(value.contains("Expirado"))Estado="Expirado";
		   else if(value.contains("Futuro"))Estado="Futuro ";
		   else if(value.contains("Inicial"))Estado="Inicial";
		   else if(value.contains("Para ser cancelado"))Estado="Para ser cancelado";
		   else if(value.contains("Rechazado "))Estado="Rechazado";
		   else Estado="Desconocido";
		  
		   return Estado;
	   }
	   
	   public void WaitBuscando(int Time)
	   {
		   int Intentos=0;
		do{		
			Intentos++;
	    }while(sikuli.FindImgWait("BuscarOrden\\Buscando.png", 1)&&Intentos<Time );
		   
	   }
	   
	   public void ConfiguracionCambioNumero() throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException {
		   
		   sikuli.FindImg("PaginaConfiguracion\\asignarNumero.png", 60);
		   Thread.sleep(5000);
		   int Intentos=0;
           boolean estado    = true;
           boolean estadoNum = true;
           String NumTelefono=null;
           sikuli.ClickImg("PaginaConfiguracion\\asignarNumero.png", 20);
			   do {
				   estadoNum = sikuli.FindImgWait("PaginaConfiguracion\\numerosPropuestos.png", 10);
				   if(estadoNum == true) {
					   sikuli.clickCordinates("PaginaConfiguracion\\btnPropongaNum.png", 5, 0, -200);
					   sikuli.ClickImg("PaginaConfiguracion\\asignacionNumero.png", 5);
					   Thread.sleep(1500);
					   sikuli.ClickImg("PaginaConfiguracion\\configuracion.png", 5);
					   Thread.sleep(1500);
					   sikuli.ClickImg("PaginaConfiguracion\\MSISDN.png", 5);
					   String CeldaMSISDN =GetCopyClip();
					   
					   Pattern pattern2 = Pattern.compile("(\\d{11})");
					   Matcher matcher2 = pattern2.matcher(CeldaMSISDN);
					   
					   if(matcher2.find())
					   {
						   NumTelefono = matcher2.group(1);
						   estado=false;
					   }
					   else
						   sikuli.ClickImg("PaginaConfiguracion\\asignarNumero.png", 20);
				   }
				   else
					   sikuli.ClickImg("PaginaConfiguracion\\btnPropongaNum.png", 20);
				
				   Intentos++;
			   }while(estado==true && Intentos<6);
			   
			   sikuli.goToImg("PaginaConfiguracion\\calcular", 10, 0, 0);
			   amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
			   sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
			   DataRowExcel("Telefono", NumTelefono);
		   
	   }
    public void ConfiguracionCambioSimCard(String Telefono) throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException {
	
    	AutoIt.IniciarAutoIt();
	    AutoItX Au= AutoIt.getAutoit();	
	    Au.winActivate(PaginaAmdocs);
	
	    sikuli.FindImg("PaginaConfiguracion\\asignarNumero.png", 60);
	   
	    Thread.sleep(5000);
	    sikuli.ClickImg("PaginaConfiguracion\\TituloBarraSubproductos.png", 5);	    
	    amd.FindImgScroll("PaginaConfiguracion\\TarjetSim.png", "down", 10);
	    sikuli.ClickImg("PaginaConfiguracion\\TarjetSim.png", 5);
	    Thread.sleep(5000);
		sikuli.ClickImg("PaginaConfiguracion\\Borrar.png", 5);
		Thread.sleep(5000);
		sikuli.DobleClickImg("PaginaConfiguracion\\EquipoyTarjetaSim.png", 20);
		sikuli.ClickImg("PaginaConfiguracion\\TarjetSim.png", 5);
		Thread.sleep(5000);
		sikuli.ClickImg("PaginaConfiguracion\\Agregar.png", 10);			   
	    sikuli.goToImg("PaginaConfiguracion\\calcular", 10, 0, 0);
		amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
		DataRowExcel("Telefono", Telefono);
	}
    
    public void ValidarClienteEcommerce(Hashtable<String, String> Data) throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException {
    	
    	String Plan = Data.get("Plan");
		String Rut  = Data.get("Rut");
		
		DataRowExcelEcommerce("Rut", Rut);
    	
    	AutoIt.IniciarAutoIt();
	    AutoItX Au= AutoIt.getAutoit();	
	    Au.winActivate(PaginaAmdocs);
	    
	    TimeSleep(5000);
	    
	    String NombreTitular =sikuli.GetTextDown("PaginaPrincipalInteraccion\\Titular",1);
	    
	    
	    sikuli.clickCordinates("Header\\IconContacto.png", 10, 30, -25);   
		
	    
	    sikuli.FindImg("VerContacto\\txtVerContacto", 15);
		
		
		
	    sikuli.ClickImg("VerContacto\\HistorialInteracciones.png", 15);
	    sikuli.ClickDown("VerContacto\\IdentificacionInteraccion.png", 10);	    
	    TimeSleep(1000);
	    Au.controlSend(PaginaAmdocs, "", "",Keys.ENTER, false);
	    String Text = amd.SelectAllTextClicDown("VerContacto\\NotasInteraccion.png", 10);
	    System.out.println(Text); 	
    }
    
    public void DireccionDeInstalacion() throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException {
    	
    	AutoIt.IniciarAutoIt();
		Au= AutoIt.getAutoit();
    	
    	sikuli.FindImgWait("NuevaOferta\\DireccionDeInstalacion.png", 20);
    	 TimeSleep(2000);
    	Au.controlSend(PaginaAmdocs, "", "",Keys.ENTER, false);
    	
    	if(sikuli.FindImgWait("NuevaOferta\\AlertVentaMovil.png", 5))
    		Au.controlSend(PaginaAmdocs, "", "",Keys.ENTER, false);
    		
    	
    }
    
    public void AgregarServicios(String Servicio, String ServicioAdicional) throws FindFailed, IOException{
    	
    	sikuli.FindImg("Servicios\\ColapsarTodo", 60);
    	TimeSleep(5000); 
    	
    	if(!Servicio.equals("null"))
    		AgregarServicio(Servicio);
    	if(!ServicioAdicional.equals("null"))
    		AgregarServicio(ServicioAdicional);
    }
    
    
	public void AgregarServicio(String Servicio) throws FindFailed, IOException{
    	
    	AutoIt.IniciarAutoIt();
		Au= AutoIt.getAutoit();
		
		TimeSleep(1000);
		sikuli.ClickImg("Servicios\\ColapsarTodo", 10);
		TimeSleep(1000);
		
		
    	if(Servicio.contains("Bolsa Adicional"))
    	{
    		if(Servicio.contains("Roaming"))
    		{
    			sikuli.ClickImg("PaginaConfiguracion\\subRoaming", 10);
    			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
    			
    			TimeSleep(5000);
    			
    			sikuli.DobleClickImg("Servicios\\BolsasAdicionalesRoaming", 10);
    			boolean existe = FindArrowDownText(Servicio);
    			if(existe)
    				sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);	
    		}
    		else if(Servicio.contains("LDI"))
    		{
    			sikuli.ClickImg("PaginaConfiguracion\\subldi", 10);
    			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
    			
    			TimeSleep(5000);
    			
    			sikuli.DobleClickImg("Servicios\\BolsasAdicionalesLDIVoz", 10);
    			boolean existe = FindArrowDownText(Servicio);
    			if(existe)
    				sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
    		}
    	} 
    	
    	else if(Servicio.contains("Roaming-"))
		{
    		System.out.println("/.. equals(\"Roaming-y mas\" ..");
    		sikuli.ClickImg("PaginaConfiguracion\\subRoaming", 10);
			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
			
			TimeSleep(2000);
    		sikuli.DobleClickImg("PaginaConfiguracion\\subRoamingSelec", 10);
    		
    		String PlanRoa = Servicio.substring(Servicio.indexOf("-") +1, Servicio.length());
		    System.out.println("/..Plan Roaming: "+ PlanRoa);

		    if(PlanRoa.contains(" MB ")) 
    		{
    			System.out.println("/..ROA Datos ..");
    			sikuli.ClickImg("PaginaConfiguracion\\RoamingDeDatosGris", 10);
    			TimeSleep(2000);
    			sikuli.DobleClickImg("PaginaConfiguracion\\RoamingDeDatosGris", 10);
    		}
			if(PlanRoa.contains(" SMS ")) 
    		{
    			System.out.println("/..ROA SMS ..");
    			TimeSleep(2000);
    			sikuli.DobleClickImg("PaginaConfiguracion\\RoamingSMSGris", 10);
    			
    		}
    		if(PlanRoa.contains(" Min ")) 
    		{
    			System.out.println("/.. ROA Voz ..");
    			TimeSleep(2000);
    			sikuli.DobleClickImg("PaginaConfiguracion\\RoamingDeVozGris", 10);
    		}
    		
    		TimeSleep(2000);
    		boolean existe = FindArrowDownText(PlanRoa);
			if(existe)
				sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);	

		}
    	else if(Servicio.equals("Roaming LTE"))
		{
    		System.out.println("/.. ROA RoamingLTE ..");
    		sikuli.ClickImg("PaginaConfiguracion\\subRoaming", 10);
			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
			
			TimeSleep(2000);
    		sikuli.ClickImg("PaginaConfiguracion\\subRoamingSelec", 10);
    		sikuli.ClickImg("PaginaConfiguracion\\RoamingLTE", 10);
			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
		}
    	
    	else if(Servicio.equals("Roaming"))
		{
    		System.out.println("/.. equals(\"Roaming\" ..");
    		
			sikuli.ClickImg("PaginaConfiguracion\\subRoaming", 10);
			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
		}
    	
    	else if(Servicio.contains("PYD"))
    	{		
			sikuli.DobleClickImg("Servicios\\DescuentosYPromociones", 10);
			boolean existe = FindArrowDownText(Servicio);
			if(existe)
				sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
    	}
    	
//    	else if(Servicio.contains("Roaming"))
//    	{
//    		
//    		sikuli.ClickImg("PaginaConfiguracion\\subRoaming", 10);
//			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
//			
//			TimeSleep(5000);
//			
//			sikuli.DobleClickImg("Servicios\\BolsasAdicionalesRoaming", 10);
//			boolean existe = FindArrowDownText(Servicio);
//			if(existe)
//				sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
//    	}
    		
    	else if(Servicio.equals("LDI"))
		{
			sikuli.ClickImg("PaginaConfiguracion\\subldi", 10);
			sikuli.ClickImg("PaginaConfiguracion\\Agregar", 10);
			
			
		}
    	
    }
    
    public boolean FindArrowDownText(String Value)
    {
    	try
    	{ 
 		   for(int i=0;i<20;i++)
 		   {
 			   String Valuetext = GetCopyClip();
 			   
 			   System.out.println(Valuetext);
 			   if(Valuetext.equals(Value))
 				   return true;
 			   sikuli.InputText(Keys.DOWN);
 		   }
    	}
    	   
    	catch(Exception e)
    	{}
    	return false;
    }
    
	   public void buscarProductoTraspasoTitularidad(String Telefono) throws FindFailed, IOException
	   {
		   Amdocs amd = new Amdocs();
		   AccionesExcel excelAccion = new AccionesExcel();
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();
		
		   sikuli.FindImgWait("BuscarProductosAsignados\\TituloSeleccionarProducto.png", 60);
		   InputText("BuscarProductosAsignados\\IdentificacionTraspaso",   Telefono, 15);
		   sikuli.ClickImg("BuscarProductosAsignados\\BuscarAhora.png", 3);
		   sikuli.ClickImg("BuscarProductosAsignados\\SeleccionarCheck.png", 60);
		   sikuli.ClickImg("BuscarProductosAsignados\\MoverCuotasNuevoCliente", 60);
		   sikuli.ClickImg("BuscarProductosAsignados\\Cambiar de Titularidad", 60);		   
		   TimeSleep(5000);
		   sikuli.ClickImg("CambiarOferta\\Siguiente.png", 15);
	   }
	   
	   public void SeleccionarPlanBase(String NombrePlan) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
	   {
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 
		   NombrePlan = NombrePlan.trim();
		   DataRowExcel("Plan", NombrePlan);
		   
		   if(sikuli.FindImgWait("PaginaPlanes\\ofertaPlan.png", 60) == true) {
			   sikuli.FindImgWait("NuevaOferta\\TxtNuevaOferta.png", 10);
			   FindImgScroll("NuevaOferta\\Buscar.png","up", 15);
			   sikuli.FindImg("PaginaPlanes\\ofertasMostradas.png", 60);
		   }
			   System.out.println("Se ingresa plan: " + NombrePlan);
			   Amdocs amd = new Amdocs();
			   /*
			   String[] plan = null;
				   sikuli.ClickImg("PaginaPlanes\\buscarCuenta.png", 10);
			   Au.controlSend(PaginaAmdocs, "", "", NombrePlan);
				   sikuli.ClickImg("PaginaPlanes\\btnBuscarCta.png", 10);
			   Thread.sleep(7000);
			   */
			   BusquedaPlanHogar(NombrePlan);
			   //BusquedaPlan(NombrePlan);
			   
			   FindImgScroll("PaginaPlanes\\Regresar.png","Down", 15);
			   FindImgScrollArrow("PaginaPlanes\\Siguiente","Right", 30);
			   sikuli.ClickImg("PaginaPlanes\\Siguiente.png", 10);
			   
			   
			   if(sikuli.FindImgWait("PaginaPlanes\\AlertaTasa.png", 10))
				   Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
			   
			   if(sikuli.FindImgWait("PaginaPlanes\\MayorLimiteCompra.png", 3))
				   Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
	   }
	   
	   
	   //Deprecado Se usa NuevoCrearClienteServicio(Hashtable<String, String>, int)
	   /*
	   public String CrearClienteServicio(Hashtable<String, String> Data) throws IOException, SQLException
	   {
		   Business.SoapAmdocs SA 	= new Business.SoapAmdocs();
		   Business.BDAmdocsNew base 	= new Business.BDAmdocsNew();
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
		   
		   	String Rut				= Data.get("Rut");
		    String TipoCliente      = Data.get("TipoCliente");
			String Ciclo           	= Data.get("Ciclo");
			int IdAmbiente 			= Rep.GetAmbiente();

		    String CO="";			
			String Modo ="1";
			
		    amd.DataRowExcel("Ciclo", Ciclo);
		    

			String AddressId  = Ini.GetValue("Ambientes", "Ambiente"+IdAmbiente, "AddressId");
			System.out.println("Dirección: "+AddressId);
			
		    if (Rut.equalsIgnoreCase("null"))
			{
				System.out.println("Campo Rut Vacio, se creará Cliente");
			    
			    Rut =   GRut.GetRutRandom();
			    String Email = Data.get("Mail");
			    Hashtable<String, String> CBP;
			    String FA;
				Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
				String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
				String Apellido = "Claro "+stringRandom.generateSessionKey(9);
				
				 
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					System.out.println("Cliente Tipo Organización");
					System.out.println("Modo de CBP: "+Modo);
					CO  = SA.CrearOrganizacion(IdAmbiente ,Rut, Nombre, Email);
					CBP = SA.CrearCBP2(IdAmbiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(IdAmbiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
							
				else
				{
					System.out.println("Cliente Tipo Residencial");
					System.out.println("Modo de CBP: "+Modo);
					CO  = SA.CrearPersona(IdAmbiente, Rut, Nombre, Apellido, Email);
					CBP = SA.CrearCBP2(IdAmbiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(IdAmbiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
				}
				
			}else
			{
			
				Modo = "2";
				String Email = Data.get("Mail");
				Hashtable<String, String> CBP;
			    String FA;
			    
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					System.out.println("Cliente Tipo Organización Existente");
					System.out.println("Modo de CBP: "+Modo);

					CO = base.ConsultaORG(IdAmbiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(IdAmbiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(IdAmbiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
				else
				{
					System.out.println("Cliente Tipo Residencial Existente");
					System.out.println("Modo de CBP: "+Modo);

					CO = SA.ConsultaPersona(IdAmbiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(IdAmbiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(IdAmbiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
				}
				
			}
			
			DataRowExcel("Rut", Rut);
			return Rut;
	   }*/
	   //Actualizado, obtiene el Ambiente desde el Excel (Campo Ambiente de la pestaña "Datos del Proyecto").
	   public String NuevoCrearClienteServicio(Hashtable<String, String> Data,int IdAmbiente) throws IOException, SQLException
	   {
		   Business.SoapAmdocs SA 	= new Business.SoapAmdocs();
		   Business.BDAmdocsNew base 	= new Business.BDAmdocsNew();
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
		    
		   String TipoCliente		= Data.get("TipoCliente");
		   String CodigoPlan		= Data.get("NombrePlan");
		   String NumeroCel			= Data.get("Telefono");
		   String Sim				= Data.get("PerfilAPN");
		   String Imsi				= Data.get("SkuEquipo");
		   String Ciclo				= Data.get("Ciclo");
		   String TipoAdquisicion	= Data.get("TipoAdquisicion");
		   String Rut				= Data.get("Rut");
		   int Ambiente;
		   String CO="";
		   String Modo ="1";
		   
		   amd.DataRowExcel("Sim", Sim);
		   amd.DataRowExcel("Imsi", Imsi);
		   amd.DataRowExcel("Plan", CodigoPlan);
		   amd.DataRowExcel("Ciclo", Ciclo);
		   amd.DataRowExcel("Telefono", NumeroCel);

		   Ambiente = IdAmbiente;
		   String AddressId  = Ini.GetValue("Ambientes", "Ambiente"+Ambiente, "AddressId");
		   System.out.println("Dirección: "+AddressId);
		   
			if (Rut.equalsIgnoreCase("null"))
			{
				System.out.println("Campo Rut Vacio, se creará Cliente");
				//System.out.println("Campo Rut: "+Rut);
				

			    
			    Rut =   GRut.GetRutRandom();
			    String Email = Data.get("Mail");
			    Hashtable<String, String> CBP;
			    String FA;
				Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
				String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
				String Apellido = "Claro "+stringRandom.generateSessionKey(9);
				
				 
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					System.out.println("Cliente Empresa");
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
				else if	(TipoCliente.equalsIgnoreCase("PYME"))
				{
					System.out.println("Creación de Cliente PYME");
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}		
				else
				{
					System.out.println("Cliente Persona");
					CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido, Email);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
				}

			}
			else
			{
				System.out.println("Campo Rut Llenado desde Excel");
				//System.out.println("Campo Rut: "+Rut);
				
				Modo = "2";
				String Email = Data.get("Mail");
				Hashtable<String, String> CBP;
			    String FA;
			    
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					//Contultar ORG_ID por Base de Datos
					//Pasar Ambiente y Rut
					System.out.println("Cliente Empresa");

					CO = base.ConsultaORG(Ambiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
				else if	(TipoCliente.equalsIgnoreCase("PYME"))
				{
					System.out.println("Creación de Cliente PYME");
					CO = base.ConsultaORG(Ambiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
				else
				{
					System.out.println("Cliente Persona");
					CO = SA.ConsultaPersona(Ambiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
				}
			}

			DataRowExcel("Rut", Rut);
			return Rut;
	   }
	   /*
	   public String VentaClienteServicio(Hashtable<String, String> Data) throws IOException
	   {
		    String TipoCliente      = Data.get("TipoCliente");
			String CodigoPlan      = Data.get("NombrePlan");
			String NumeroCel       = Data.get("Telefono");
			String Sim             = Data.get("PerfilAPN");
			String Imsi            = Data.get("SkuEquipo");
			String Ciclo           = Data.get("Ciclo");
			String TipoAdquisicion = Data.get("TipoAdquisicion");
		
			amd.DataRowExcel("Sim", Sim);
			amd.DataRowExcel("Imsi", Imsi);
			amd.DataRowExcel("Plan", CodigoPlan);
			amd.DataRowExcel("Ciclo", Ciclo);
			amd.DataRowExcel("Telefono", NumeroCel);
		    String CO="";
		   
		   
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
			
		    String AddressId  = Ini.GetValue("Ambientes", Ambiente, "AddressId");;
		   
		    int Ambiente = 10;
		    
		    String Rut =   GRut.GetRutRandom();
		    String Email = Data.get("Mail");
		    Hashtable<String, String> CBP;
		    String FA;
			
			//System.out.println("....AddressId: "+AddressId);
			Business.SoapAmdocs SA = new Business.SoapAmdocs();
			Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
			String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
			String Apellido = "Claro "+stringRandom.generateSessionKey(9);
			
			if(TipoCliente.equalsIgnoreCase("Empresas"))
			{
				//CO  = SA.CrearOrganizacion(Ambiente ,Rut, "Robot "+Rut, Email);
				CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
			}
			else if(TipoCliente.equalsIgnoreCase("PYME"))
			{
				CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
			}		
			else
			{
				//CO  = SA.CrearPersona(Ambiente, Rut, "Robot "+Rut,"Claro "+Rut);
				CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido, Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
			}
			
			if(TipoAdquisicion.equalsIgnoreCase("Propio"))
				SA.VentaEquipoPropio(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim);
			
			else if(TipoAdquisicion.equalsIgnoreCase("Sin Equipo"))
				SA.VentaSinEquipo(Ambiente, TipoAdquisicion ,CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, Imsi, Rut);
			else
				SA.VentaEquipoCompraArriendo(Ambiente, TipoAdquisicion ,CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, Imsi);
			
			DataRowExcel("Rut", Rut);
		   return Rut;
	   }
	   */
	   
	   /*Codigo Nuevo para Portabilidad por Servicio - Ajustar agregar Email*/
	   /*
	   public String PortabilidadClienteServicio(Hashtable<String, String> Data) throws IOException
	   {
		    String TipoCliente     = Data.get("TipoCliente");
			String CodigoPlan      = Data.get("NombrePlan");
			String NumeroCel       = Data.get("Telefono");
			String Sim             = Data.get("PerfilAPN");
			String Imsi            = Data.get("SkuEquipo");
			String Ciclo           = Data.get("Ciclo");
			String TipoAdquisicion = Data.get("TipoAdquisicion");
			String IdProveedor	   = Data.get("Play1");
		
			amd.DataRowExcel("Sim", Sim);
			amd.DataRowExcel("Imsi", Imsi);
			amd.DataRowExcel("Plan", CodigoPlan);
			amd.DataRowExcel("Ciclo", Ciclo);
			amd.DataRowExcel("Telefono", NumeroCel);
		    String CO="";
		   
		    Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
			   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
				
			    String AddressId  = Ini.GetValue("Ambientes", Ambiente, "AddressId");;
			   
			    int Ambiente = 10;
		   
		    
		    
		    String Rut =   GRut.GetRutRandom();
		    String Email = Data.get("Mail");
		    Hashtable<String, String> CBP;
		    String FA;
			
			//System.out.println("....AddressId: "+AddressId);
			Business.SoapAmdocs SA = new Business.SoapAmdocs();
			Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
			String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
			String Apellido = "Claro "+stringRandom.generateSessionKey(9);
			
			
			if(TipoCliente.equalsIgnoreCase("Empresas"))
			{
				CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
			}
						
			else if(TipoCliente.equalsIgnoreCase("PYME"))
			{
				CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
			}
			else
			{
				CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido,Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
			}
			
			if(TipoAdquisicion.equalsIgnoreCase("Propio"))
				SA.PortabilidadEquipoPropio(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim,IdProveedor);
			else
				SA.PortabilidadEquipoCompraArriendo(Ambiente, TipoAdquisicion ,CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, Imsi,IdProveedor);
			
			
			DataRowExcel("Rut", Rut);
		   return Rut;
	   }	
	   */

	   public String CrearPersonaServicio(String TipoEmpresa) throws IOException
	   {
		   return Rut;
	   }
	   
	   public String CrearEmpresaServicio(String TipoEmpresa) throws IOException
	   {
		   return Rut;
	   }
	   
	   
	   public void ValidarExistenciaPlan() throws IOException, FindFailed, UnsupportedFlavorException, InterruptedException
	   {
		   Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
		   ArrayList<String> PlanesExcel = Excel.GetCountSheet("Restriccion_de_Roles", "Planes");
		   Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
		   String Estado;
	       String [] DataExcel={"",""};
		   
		   AutoIt.IniciarAutoIt();
		   AutoItX Au= AutoIt.getAutoit();	
		   Au.winActivate(PaginaAmdocs); 
		  // NombrePlan = NombrePlan.trim();
		  // DataRowExcel("Plan", NombrePlan);
		   
		   LimpiarErrorPlan(); 
		   
		   if(sikuli.FindImgWait("PaginaPlanes\\ofertaPlan.png", 60) == true) {
			   sikuli.FindImgWait("NuevaOferta\\TxtNuevaOferta.png", 10);
			   FindImgScroll("NuevaOferta\\Buscar.png","up", 15);
			   sikuli.FindImg("PaginaPlanes\\ofertasMostradas.png", 60);
		   }
			  // System.out.println("se ingresa plan " + NombrePlan);
			   Amdocs amd = new Amdocs();
			   
			   for(int i=0;i<PlanesExcel.size();i++)
			   {
				   LimpiarErrorPlan();
				   
				   boolean ExistePlan = BusquedaPlanExiste(PlanesExcel.get(i));   
				   System.out.println("Este es el plan que necesitamos: " + PlanesExcel.get(i));
				   if(ExistePlan)
					   Estado="Vendible";
				   else
					   Estado="No Vendible";
				   
				    DataExcel[0]=PlanesExcel.get(i);
		        	DataExcel[1]=Estado;
		        	Excel.AgregarLineaExcel(Event.NombreExcel, DataExcel);
			   }
			   
	   }
	   
	   public void LimpiarErrorPlan() throws FindFailed
	   {
		   if(sikuli.FindImgWait("NuevaOferta\\No hay ofertas elegibles.png", 10))
			{
				sikuli.ClickImg("NuevaOferta\\Aceptar.png", 1);
			}
	   }
	   
	   public static String  limpiaTildes(String texto) {
	        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
	        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	        return texto;
	    }
	   
	   public String  VentaPoolShred(Hashtable<String, String> Data) throws Exception
	   {
		   String Plan = Data.get("NombrePlan");
		   
		   
		   if(Plan.contains("POOL Shared Allowance")
		    ||Plan.contains("Bolsa"))
		   {
			   String TipoCliente  = Data.get("TipoCliente");
			   String Reasoncode  = Data.get("ReasonCode");
			   
			   SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");
			   DireccionDeInstalacion();	
			   Acreditacion(TipoCliente, "", "");
			   SeleccionarPlanBase(Plan);
			   motivoOrden("prueba de automatizacion QA", Reasoncode);
			   
			   sikuli.FindImg("Servicios\\ColapsarTodo", 60);
		       TimeSleep(5000);   
			   
			   amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
			   sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
			   
			   distribucionCargo("M", "SinFA", TipoCliente);
			   resumenOrden(Data);
			   String Orden = paginaOrden();
			   CerrarVentanaOrden();
			   sikuli.ClickImg("PaginaPrincipalInteraccion\\Refrescar.png", 8);
			   TimeSleep(5000);
			   
			   Plan= Data.get("Play1");
		   }
		   
		   return Plan;
	   }
	   
	   public void ConsultaQDN(String Telefono) throws IOException
	   {
		   SoapAmdocs soap = new SoapAmdocs();
		   soap.ConsultaQDN(Telefono);
	   }
	   
	   public void AgregarServicio4Servicio(Hashtable<String, String> Data, int Ambiente, String ChangeAction) throws IOException
	   {
		   SoapAmdocs soap = new SoapAmdocs();
		   soap.AgregarEliminarServicio(Data, Ambiente, ChangeAction);
	   }
	   
	 //acá deberia tener cambios
	   
	   public void CambioOferta(Hashtable<String, String> Data, int Ambiente) throws IOException
	   { 
		   SoapAmdocs soap = new SoapAmdocs();
		   Hashtable<String, String> Cliente = soap.IdentificarLlamente(Data, Ambiente);
		   String CBP = Cliente.get("cbpId"); 
		   soap.CambioOferta(Data, CBP, Ambiente);
	   }
	   
	   public void TraspasoPropiedad(Hashtable<String, String> Data, int Ambiente) throws IOException
	   {
		   Business.SoapAmdocs SA = new Business.SoapAmdocs();
		   String Caso= Data.get("Caso");
		   String TipoCliente= Data.get("Caso");
		   String Ciclo = Data.get("Ciclo");
		   String CBP = Data.get("PerfilAPN");
		   
		   amd.DataRowExcel("Rut", Caso);
		   
		   String CO  = "";
		   Hashtable<String, String> CBPNueva;
		   String FA  = "";
		   
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
		   String AmbienteSt = "Ambiente"+Ambiente;
		   
		   String AddressId  = Ini.GetValue("Ambientes", AmbienteSt, "AddressId");
		   
		   String Rut =   GRut.GetRutRandom();
		   String Email = Data.get("Mail");
		   
		   Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
		   String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
		   String Apellido = "Claro "+stringRandom.generateSessionKey(9);
		   
		   if(Caso.equalsIgnoreCase("SERVICIO TRASPASO PROPIEDAD CLIENTE NUEVO"))
		   {
			   if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBPNueva = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBPNueva.get("cbpId"), CBPNueva.get("contactId"), AddressId,Email);
				}
							
				else
				{
					CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido,Email);
					CBPNueva = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBPNueva.get("cbpId"), CO, AddressId,Email);
				}
	   		
			   CBP = CBPNueva.get("cbpId");
		   }
		   
		   Hashtable<String, String> Cliente = SA.IdentificarLlamente(Data, Ambiente);
		   String CBPOriginal =Cliente.get("cbpId"); 
		   
		   
		   SoapAmdocs soap = new SoapAmdocs();
		   soap.TraspasoTitularidad(Data, Ambiente, CBPOriginal, CBP);
	   }
	   
	   public void IdentificarLlamante(Hashtable<String, String> Data, int Ambiente) throws IOException
	   {
		   SoapAmdocs soap = new SoapAmdocs();
		   soap.IdentificarLlamente(Data, Ambiente);
	   }
	   
	   public void TerminoContrato(Hashtable<String, String> Data, int Ambiente) throws IOException
	   {
		   SoapAmdocs soap = new SoapAmdocs();
		   soap.TerminoContrato(Data, Ambiente);
	   }
	   
	   public void ConsultaPassCorrecta(Hashtable<String, String> Data, int Ambiente) throws IOException
	   {
		   SoapAmdocs soap = new SoapAmdocs();
		   soap.ConsultaPassCorrecta(Data, Ambiente);
	   }
	   
	   /*
	    * CODIGO PARA MIGRACION DE PRE A POST POR SERVICIO
	    */
	   /*
	   public String MigracionPorServicio(Hashtable<String, String> Data) throws IOException
	   {
		    String TipoCliente      = Data.get("TipoCliente");
			String CodigoPlan      = Data.get("NombrePlan");
			String NumeroCel       = Data.get("Telefono");
			String Sim             = Data.get("PerfilAPN");
			String Imsi            = Data.get("SkuEquipo");
			String Ciclo           = Data.get("Ciclo");
			String TipoAdquisicion = Data.get("TipoAdquisicion");
		
			amd.DataRowExcel("Sim", Sim);
			amd.DataRowExcel("Imsi", Imsi);
			amd.DataRowExcel("Plan", CodigoPlan);
			amd.DataRowExcel("Ciclo", Ciclo);
			amd.DataRowExcel("Telefono", NumeroCel);
		   String CO="";
		   
		   
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
			
		    String AddressId  = Ini.GetValue("Ambientes", Ambiente, "AddressId");
		    System.out.println("Dirección: "+AddressId);
		    int Ambiente = 10;
		    
		    String Rut =   GRut.GetRutRandom();
		    String Email = Data.get("Mail");
		    Hashtable<String, String> CBP;
		    String FA;
			
			//System.out.println("....AddressId: "+AddressId);
			Business.SoapAmdocs SA = new Business.SoapAmdocs();
			Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
			String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
			String Apellido = "Claro "+stringRandom.generateSessionKey(9);
			
			if(TipoCliente.equalsIgnoreCase("Empresas"))
			{
				//CO  = SA.CrearOrganizacion(Ambiente ,Rut, "Robot "+Rut, Email);
				CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
			}
						
			else
			{
				//CO  = SA.CrearPersona(Ambiente, Rut, "Robot "+Rut,"Claro "+Rut);
				CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido,Email);
				CBP = SA.CrearCBP(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo);
				FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
			}
			
			SA.MigracionEquipoPropio(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim);
	
			
			DataRowExcel("Rut", Rut);
		   return Rut;
	   }
	   */
	   public void PagoDeuda(Hashtable<String, String> Data, int Ambiente) throws IOException
	   { 
		   SoapAmdocs soap = new SoapAmdocs();
		   Hashtable<String, String> Deuda = soap.ConsultaDeuda(Data, Ambiente); //aRBalance,invoiceId
		   String Saldo = Deuda.get("ARBalance");
		   String Boleta = Deuda.get("InvoiceId");
		   String FA = Data.get("NombrePlan");
		   soap.PagoDeuda(FA,Saldo,Boleta,Ambiente);
	   }
	   
	   public String ConsultaRut(Hashtable<String, String> Data, int IdAmbiente) throws IOException
	   {
		   String Rut			= Data.get("Rut"); 
		   		
		   amd.DataRowExcel("Rut", Rut);
		   String CO="";
		   int Ambiente = IdAmbiente;
	   
		   SoapAmdocs soap = new SoapAmdocs();
		   //pasar Rut + Ambiente
		   //Retornar ContactId
//		   SA.MigracionEquipoPropio(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim);
		   CO=soap.ConsultaPersona(Ambiente, Rut);
		   DataRowExcel("ContactId", CO);
		   System.out.println("Contacto: "+CO);
		   
		   return Rut;
	   }

	   
	   public String NuevoVentaClienteServicio(Hashtable<String, String> Data,int IdAmbiente) throws IOException, SQLException
	   {
		   Business.SoapAmdocs SA 	= new Business.SoapAmdocs();
		   Business.BDAmdocsNew base 	= new Business.BDAmdocsNew();
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
		    
		   String TipoCliente		= Data.get("TipoCliente");
		   String CodigoPlan		= Data.get("NombrePlan");
		   String NumeroCel			= Data.get("Telefono");
		   String Sim				= Data.get("PerfilAPN");
		   String Imei				= Data.get("SkuEquipo");
		   String Ciclo				= Data.get("Ciclo");
		   String TipoAdquisicion	= Data.get("TipoAdquisicion");
		   String Rut				= Data.get("Rut");
		   int Ambiente;
		   
		   String OrderType ="PR";
		   String ReasonID ="CREQ";
		   String Provider ="";
		   
		   String AdquisicionType="OWN";
		   String EquipoSKU="70002567";
		   String Marca ="Propio";
		   String Modelo ="Propio";
		   String Contrato ="18";
		   
		   amd.DataRowExcel("Sim", Sim);
		   amd.DataRowExcel("Imsi", Imei);
		   amd.DataRowExcel("Plan", CodigoPlan);
		   amd.DataRowExcel("Ciclo", Ciclo);
		   amd.DataRowExcel("Telefono", NumeroCel);
		   String CO="";
		   String Modo ="1";
		   Ambiente = IdAmbiente;
		   String AddressId  = Ini.GetValue("Ambientes", "Ambiente"+Ambiente, "AddressId");
		   System.out.println("Dirección: "+AddressId);
		   		   
			if (Rut.equalsIgnoreCase("null"))
			{
				System.out.println("Campo Rut Vacio, se creará Cliente");
				System.out.println("Tipo Cliente: "+TipoCliente);
				

			    
			    Rut =   GRut.GetRutRandom();
			    String Email = Data.get("Mail");
			    Hashtable<String, String> CBP;
			    String FA;
				Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
				String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
				String Apellido = "Claro "+stringRandom.generateSessionKey(9);
				
				 //Sección para el Tipo de Cliente
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					System.out.println("Creación de Cliente Empresa");
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}	
				else if	(TipoCliente.equalsIgnoreCase("PYME"))
				{
					System.out.println("Creación de Cliente PYME");
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}	
				else
				{
					System.out.println("Creación de Cliente Persona");
					CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido, Email);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
				}
				// Sección para el Tipo de Orden
				if (Data.get("Caso").equalsIgnoreCase("PORTABILIDAD POR SERVICIO"))
				{
					//OrderType ="PR"; --> PI
					//ReasonID ="CREQ"; -->No Cambia
					//Provider =""; --> 220 (hay que ver que sea dinamico
				   	
					OrderType ="PI";
				   	Provider ="220";
				   	System.out.println("Portabilidad: "+OrderType);
				   	System.out.println("Proveedor: "+Provider);
					
				}
				else if(Data.get("Caso").equalsIgnoreCase("MIGRACION POR SERVICIO")) 
				{
					//OrderType ="PR"; --> No Cambia
					//ReasonID ="CREQ"; --> PRMIG
					//Provider =""; --> No Cambia
					ReasonID ="PRMIG";
					System.out.println("Migración con Reason: "+ReasonID);
				}
				else
				{
					//OrderType ="PR"; No Cambia
					//ReasonID ="CREQ"; -->No Cambia
					//Provider =""; --> No Cambia
					System.out.println("Venta Normal: "+OrderType+", "+ReasonID);
				}
				// Seccion para El tipo de Adquisición
				if(TipoAdquisicion.equalsIgnoreCase("Arriendo"))
				{
					//AdquisicionType="OWN"; --> LSG
					//EquipoSKU="70002567"; --> No cambia
					//Marca ="Propio"; --> Vacio
					//Modelo ="Propio"; --> Vacio
					//Imei = Data.get("SkuEquipo"); --> No Cambia
					//Contrato = "18"; --> No cambia
					
					AdquisicionType="LSG";
					Marca ="";
					Modelo ="";
					System.out.println("Equipo en Arriendo: "+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					//SA.VentaEquipoPropio(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
					//ProvideWirelessIVR(int Ambiente,String CBP,String FA,String CodigoPlan,String NumeroCel,String Sim,String OrderType,String ReasonID,String Provider,String AdquisicionType,String EquipoSKU,String Marca, String Modelo,String Imei,String Contrato)
				}
				
				else if (TipoAdquisicion.equalsIgnoreCase("Compra"))
				{
					//AdquisicionType="OWN"; --> PUR
					//EquipoSKU="70002567"; --> No cambia
					//Marca ="Propio"; --> Vacio
					//Modelo ="Propio"; --> Vacio
					//Imei = Data.get("SkuEquipo"); --> No Cambia
					//Contrato = "18"; --> No cambia
					
					AdquisicionType="PUR";
					Marca ="";
					Modelo ="";
					System.out.println("Equipo en Compra"+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					//SA.VentaEquipoCompraArriendo(Ambiente, TipoAdquisicion ,CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, Imei);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
				}
				else
				{
					//AdquisicionType="OWN"; --> No Cambia
					//EquipoSKU="70002567"; --> Vacio
					//Marca ="Propio"; --> No Cambia
					//Modelo ="Propio"; --> No Cambia
					//Imei = Data.get("SkuEquipo"); --> No Cambia
					//Contrato = "18"; --> No cambia
					Contrato ="";
					EquipoSKU="";
					System.out.println("Equipo Propio"+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
				}		
			}	
			else
			{
				System.out.println("Campo Rut Llenado desde Excel");
				//System.out.println("Campo Rut: "+Rut);
				
				Modo = "2";
				String Email = Data.get("Mail");
				Hashtable<String, String> CBP;
			    String FA;
			    
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					//Contultar ORG_ID por Base de Datos
					//Pasar Ambiente y Rut
					System.out.println("Cliente Empresa");

					CO = base.ConsultaORG(Ambiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
				else if(TipoCliente.equalsIgnoreCase("PYME"))
				{
					System.out.println("Cliente Pyme");

					CO = base.ConsultaORG(Ambiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBP.get("cbpId"), CBP.get("contactId"), AddressId,Email);
				}
				else
				{
					System.out.println("Cliente Persona");
					CO = SA.ConsultaPersona(Ambiente, Rut);
					System.out.println("Campo Contacto: "+CO);
					CBP = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBP.get("cbpId"), CO, AddressId,Email);
				}
				// Sección para el Tipo de Orden
				if (Data.get("Caso").equalsIgnoreCase("PORTABILIDAD POR SERVICIO"))
				{
					//OrderType ="PR"; --> PI
					//ReasonID ="CREQ"; -->No Cambia
					//Provider =""; --> 220 (hay que ver que sea dinamico
				   	
					OrderType ="PI";
				   	Provider ="220";
				   	System.out.println("Portabilidad: "+OrderType);
				   	System.out.println("Proveedor: "+Provider);
					
				}
				else if(Data.get("Caso").equalsIgnoreCase("MIGRACION POR SERVICIO")) 
				{
					//OrderType ="PR"; --> No Cambia
					//ReasonID ="CREQ"; --> PRMIG
					//Provider =""; --> No Cambia
					ReasonID ="PRMIG";
					System.out.println("Migración con Reason: "+ReasonID);
				}
				else
				{
					//OrderType ="PR"; No Cambia
					//ReasonID ="CREQ"; -->No Cambia
					//Provider =""; --> No Cambia
					System.out.println("Venta Normal: "+OrderType+", "+ReasonID);
				}
				// Seccion para El tipo de Adquisición
				if(TipoAdquisicion.equalsIgnoreCase("Arriendo"))
				{
					//AdquisicionType="OWN"; --> LSG
					//EquipoSKU="70002567"; --> No cambia
					//Marca ="Propio"; --> Vacio
					//Modelo ="Propio"; --> Vacio
					//Imei = Data.get("SkuEquipo"); --> No Cambia
					//Contrato = "18"; --> No cambia
					
					AdquisicionType="LSG";
					Marca ="";
					Modelo ="";
					System.out.println("Equipo en Arriendo: "+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					//SA.VentaEquipoPropio(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
				}
				
				else if (TipoAdquisicion.equalsIgnoreCase("Compra"))
				{
					//AdquisicionType="OWN"; --> PUR
					//EquipoSKU="70002567"; --> No cambia
					//Marca ="Propio"; --> Vacio
					//Modelo ="Propio"; --> Vacio
					//Imei = Data.get("SkuEquipo"); --> No Cambia
					//Contrato = "18"; --> No cambia

					AdquisicionType="PUR";
					Marca ="";
					Modelo ="";
					System.out.println("Equipo en Compra: "+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					//SA.VentaEquipoCompraArriendo(Ambiente, TipoAdquisicion ,CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, Imei);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
				}
				else if(TipoAdquisicion.equalsIgnoreCase("Sin Equipo"))
				{
					//AdquisicionType="OWN"; --> No Cambia
					//EquipoSKU="70002567"; --> Vacio
					//Marca ="Propio"; --> No Cambia
					//Modelo ="Propio"; --> No Cambia
					//Imei = Data.get("SkuEquipo"); --> Vacio
					//Contrato = "18"; --> No cambia
					EquipoSKU="";
					Contrato ="";
					Imei ="";
					System.out.println("Sin Equipo: "+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
				}
				else
				{
					//AdquisicionType="OWN"; --> No Cambia
					//EquipoSKU="70002567"; --> Vacio
					//Marca ="Propio"; --> No Cambia
					//Modelo ="Propio"; --> No Cambia
					//Imei = Data.get("SkuEquipo"); --> No Cambia
					//Contrato = "18"; --> No cambia
					Contrato ="";
					EquipoSKU="";
					System.out.println("Equipo Propio: "+AdquisicionType+", "+EquipoSKU+", "+Marca+", "+Modelo+", "+Contrato);
					SA.ProvideWirelessIVR(Ambiente, CBP.get("cbpId"), FA, CodigoPlan, NumeroCel, Sim, OrderType, ReasonID, Provider, AdquisicionType, EquipoSKU, Marca, Modelo, Imei, Contrato);
				}
			}
			
			 
			DataRowExcel("Rut", Rut);
			return Rut;
	   }
	   public void NuevoTraspasoPropiedad(Hashtable<String, String> Data, int Ambiente) throws IOException, SQLException
	   {
		   
		   //Arreglar el 
		   Business.SoapAmdocs SA = new Business.SoapAmdocs();
		   Business.BDAmdocsNew base 	= new Business.BDAmdocsNew();
		   String Caso= Data.get("Caso");
		   String TipoCliente= Data.get("Caso");
		   String Ciclo = Data.get("Ciclo");
		   String CBP = "";
		   
		   
		   String Modo ="1";
		   Core.Data.GeneradorRut GRut = new Core.Data.GeneradorRut();
		   Core.Ini.LeerData Ini = new Core.Ini.LeerData();
		   String AmbienteSt = "Ambiente"+Ambiente;
		   
		   amd.DataRowExcel("Rut", Caso);
		   
		   String CO  = "";
		   Hashtable<String, String> CBPNueva;
		   String FA  = "";
		   String Email = Data.get("Mail");
		   String AddressId  = Ini.GetValue("Ambientes", AmbienteSt, "AddressId");
		   
		   
		   if(Caso.equalsIgnoreCase("SERVICIO TRASPASO PROPIEDAD CLIENTE NUEVO"))
		   {
			   String Rut =   GRut.GetRutRandom();
			   Core.Data.StringRandom stringRandom = new Core.Data.StringRandom();
			   String Nombre   = "Robot "+stringRandom.generateSessionKey(9);
			   String Apellido = "Claro "+stringRandom.generateSessionKey(9);
			   
			   if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBPNueva = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBPNueva.get("cbpId"), CBPNueva.get("contactId"), AddressId,Email);
				}
			   else if	(TipoCliente.equalsIgnoreCase("PYME"))
				{
					System.out.println("Creación de Cliente PYME");
					CO  = SA.CrearOrganizacion(Ambiente ,Rut, Nombre, Email);
					CBPNueva = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBPNueva.get("cbpId"), CBPNueva.get("contactId"), AddressId,Email);
				}			
				else
				{
					CO  = SA.CrearPersona(Ambiente, Rut, Nombre, Apellido,Email);
					CBPNueva = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBPNueva.get("cbpId"), CO, AddressId,Email);
				}
	   		
			   CBP = CBPNueva.get("cbpId");
		   }
		   else
		   {
			   Modo = "2";
			   String RutCpb = Data.get("Rut");//Hace rerferencia al Rut de la hoja Input, sino trae el Rut de la hoja Datos del Proyecto
			   
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					//Contultar ORG_ID por Base de Datos
					//Pasar Ambiente y Rut del Cliente de la hoja Input
					System.out.println("Cliente Empresa");
	
					CO = base.ConsultaORG(Ambiente, RutCpb);
					
					System.out.println("Campo Contacto: "+CO);
					CBPNueva = SA.CrearCBP2(Ambiente, TipoCliente, RutCpb, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBPNueva.get("cbpId"), CBPNueva.get("contactId"), AddressId,Email);
				}
				else if	(TipoCliente.equalsIgnoreCase("PYME"))
				{
					System.out.println("Cliente PYME");
					CO = base.ConsultaORG(Ambiente, RutCpb);
					System.out.println("Campo Contacto: "+CO);
					CBPNueva = SA.CrearCBP2(Ambiente, TipoCliente, RutCpb, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente,TipoCliente, CBPNueva.get("cbpId"), CBPNueva.get("contactId"), AddressId,Email);
				}
				else
				{
					System.out.println("Cliente Persona");
					CO = SA.ConsultaPersona(Ambiente, RutCpb);
					System.out.println("Campo Contacto: "+CO+", Rut: "+RutCpb);
					CBPNueva = SA.CrearCBP2(Ambiente, TipoCliente, Rut, CO, AddressId, Ciclo,Modo);
					FA = SA.CrearFA(Ambiente, TipoCliente, CBPNueva.get("cbpId"), CO, AddressId,Email);
				}
				CBP = CBPNueva.get("cbpId");
		   }
		   Hashtable<String, String> Cliente = SA.IdentificarLlamente(Data, Ambiente);
		   String CBPOriginal =Cliente.get("cbpId"); 
		   
		   
		   SoapAmdocs soap = new SoapAmdocs();
		  soap.TraspasoTitularidad(Data, Ambiente, CBPOriginal, CBP);
	   }

	   public void ValidaCiclo() throws IOException, SQLException, FindFailed, UnsupportedFlavorException{
		   
		sikuli.ClickTextRigth("Header\\PerfilFacturacionCliente", 3);
		sikuli.FindImg("CrearContacto\\RetenerFactura", 15); 
		String CicloFact =sikuli.GetTextDown("Ciclos\\CicloFacturacion",3);
	  
		
		int indice = CicloFact.indexOf("-");;
		String Ciclo = CicloFact.substring(0, indice);
		System.out.println(Ciclo);
		DataRowExcel("Ciclo", Ciclo);
	}
	   
}