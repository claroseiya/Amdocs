package Business;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import autoitx4java.AutoItX;

public class OperacionesHogar extends Amdocs{
	
	Amdocs amd = new Amdocs();
	Operaciones Oper = new Operaciones();
	Core.Data.GeneradorRut Data = new Core.Data.GeneradorRut(); 
	String Mail;
	
	public String getMail() {return Mail;}
	public void setMail(String mail) {Mail = mail;}
	
   public void SeleccionarPlan(Hashtable<String, String> Data) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
   {
	   String Plan = Data.get("NombrePlan");
	   String Play1 = Data.get("Play1");
	   String Play2 = Data.get("Play2");
	   String Play3 = Data.get("Play3");
	   
	   
	   AutoIt.IniciarAutoIt();
	   AutoItX Au= AutoIt.getAutoit();	
	   Au.winActivate(PaginaAmdocs);
	   
	   if(sikuli.FindImgWait("PaginaPlanes\\ofertaPlan.png", 60) == true) {
		   sikuli.FindImgWait("NuevaOferta\\TxtNuevaOferta.png", 10);
		   FindImgScroll("NuevaOferta\\Buscar.png","up", 15);
		   sikuli.FindImg("PaginaPlanes\\ofertasMostradas.png", 60);
	   }
	   
	   SeleccionarFiltroPlan("HFC");	   
	   
	   Thread.sleep(2000);
	   
	   System.out.println(Plan);

	   if(Plan.contains("1 Play"))
	   {
		   BusquedaPlanHogar(Play1);
		   DataRowExcel("Plan", Play1);	   
	   }
		   
	   else if(Plan.contains("2 Play"))
	   {
		   BusquedaPlanHogar(Play1);
		   BusquedaPlanHogar(Play2);   
		   DataRowExcel("Plan", Play1+ "\n" + Play2);
	   }	   
	   else if(Plan.contains("3 Play"))
	   {
		   BusquedaPlanHogar(Play1);
		   BusquedaPlanHogar(Play2);
		   BusquedaPlanHogar(Play3);
		   DataRowExcel("Plan", Play1+"\n" + Play2+"\n" + Play3);   
	   }
	      
	   BusquedaPlanHogar(Plan);
	   
	   FindImgScroll("PaginaPlanes\\Regresar.png","Down", 15);
	   FindImgScrollArrow("PaginaPlanes\\Siguiente","Right", 30);
	   sikuli.ClickImg("PaginaPlanes\\Siguiente.png", 10);
	   
	   
	   if(sikuli.FindImgWait("PaginaPlanes\\AlertaTasa.png", 10))
		   Au.controlSend(PaginaAmdocs, "", "" , Keys.ENTER, false);
	   	
   }   

   public void SeleccionarFiltroPlan(String Tipo) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
   {
	   SelectComboboxText("PaginaPlanes\\FiltroCategoria","F", 1);
	   sikuli.ClickImg("PaginaPlanes\\FiltroSubCategoria.png", 10);
	   sikuli.ClickImg("PaginaPlanes\\TodosPlanesFija.png", 10);
	   if(Tipo.contains("HFC"))
		   sikuli.ClickImg("PaginaPlanes\\HFC.png", 10);
	   else
		   sikuli.ClickImg("PaginaPlanes\\DTH.png", 10);
	   
	   TimeSleep(5000);
   }
   
   public void configuracionSaltar(String Plan) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
		AutoIt.IniciarAutoIt();
		AutoItX Au= AutoIt.getAutoit();	
		Au.winActivate(PaginaAmdocs);
		
		sikuli.FindImg("PaginaConfiguracion\\NegociarConfiguracion", 60);
		Thread.sleep(5000);
		
		if(Plan.contains("Telefon"))
		{
			
			
			sikuli.ClickImg("PaginaConfiguracion\\Totales.png", 5);			
			amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
			sikuli.ClickTextDown("PaginaConfiguracion\\NombreDelProducto.png", 5);
			amd.FindImgScroll("NuevaOferta\\TelefoniaFija.png","down", 10);
			sikuli.ClickImg("NuevaOferta\\TelefoniaFija.png", 5);			
			
			sikuli.FindImg("PaginaConfiguracion\\TelefoniaFija.png", 30);			
			
			
			sikuli.ClickImg("PaginaConfiguracion\\ScrollUp.png", 5);
			sikuli.ClickImg("PaginaConfiguracion\\ScrollUp.png", 5);
			sikuli.ClickImg("PaginaConfiguracion\\ScrollUp.png", 5);
			sikuli.ClickImg("PaginaConfiguracion\\ScrollUp.png", 5);
			sikuli.ClickImg("PaginaConfiguracion\\ScrollUp.png", 5);
			
			
			amd.FindImgScroll("PaginaConfiguracion\\asignarNumero.png","up", 15);			
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
				   
				   DataRowExcel("Telefono", NumTelefono);
		}
		
		
		
		sikuli.FindImg("PaginaConfiguracion\\NegociarConfiguracion.png", 60);
		Thread.sleep(5000);
		sikuli.goToImg("PaginaConfiguracion\\calcular", 10, 0, 0);
		amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
		
   }
   
   public void NegociarOpciones() throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
		AutoIt.IniciarAutoIt();
		AutoItX Au= AutoIt.getAutoit();	
		Au.winActivate(PaginaAmdocs);
		 
		sikuli.FindImg("NegociarOpciones\\NegociarOpciones.png", 60);
		//Thread.sleep(10000);	//descomentar  y eliminar el de abajo Nely	
		Thread.sleep(20000);
		amd.FindImgScroll("NuevaOferta\\Siguiente.png","down", 10);
		sikuli.ClickImg("NuevaOferta\\Siguiente.png", 5);
   }
   
   public void NegociarDistribucion(String Mail) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
		AutoIt.IniciarAutoIt();
		AutoItX Au= AutoIt.getAutoit();	
		Au.winActivate(PaginaAmdocs);
		 
		sikuli.FindImg("paginaDistribucionCargo\\NegociarDistribucion.png", 60);
		//Thread.sleep(5000);	//descomentar y eliminar el de abajo Nely	
		Thread.sleep(10000);
		amd.FindImgScroll("CrearCuentaFinanciera\\Correo","Down", 15);
		sikuli.ClickImg("CrearCuentaFinanciera\\Correo", 10);
			System.out.println("correo: "+ Mail);
		
		if(Mail==null || Mail.equals("null")) {
		   Mail = "claroseiya@gmail.com";
		}
		sikuli.InputText(Mail);
		
		amd.FindImgScroll("paginaDistribucionCargo\\Guardar.png","down", 20);
		sikuli.ClickImg("paginaDistribucionCargo\\Guardar.png", 5);
		Thread.sleep(5000);
		amd.FindImgScroll("paginaDistribucionCargo\\radioFacturasSeparadas.png","up", 20);
		sikuli.ClickImg("paginaDistribucionCargo\\radioFacturasSeparadas.png", 5);
		Oper.PagoInmediato("Mensajero");
			
			
  }
   
   public void resumenOrden(Hashtable<String, String> Data) throws FindFailed, IOException, UnsupportedFlavorException, InterruptedException {
	   
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
		   sikuli.ClickImg("paginaResumenOrden\\pestDocumentos", 30);
		   if(sikuli.FindImgWait("paginaResumenOrden\\checkDocumentos", 15) == true) {
        	  sikuli.clickCordinates("paginaResumenOrden\\checkDocumentos", 5, -80, -10);
          	}	
		   sikuli.ClickImg("paginaResumenOrden\\pestPrecios", 5);
		   amd.TimeSleep(5000);
		   
		   Oper.paginaOrden();
		   
		   /*   
		   sikuli.ClickImg("paginaOrden\\cerrarPagina.png", 10);
		   
		   if(sikuli.FindImgWait("paginaResumenOrden\\CerrarOrden", 5))
			   sikuli.InputText(Key.ENTER);
		   */
		  
		   sikuli.ClickImg("paginaResumenOrden\\AgendarTecnico", 5);
		
		   System.out.println("Ahora 1");
		   amd.TimeSleep(5000);
		   System.out.println("Ahora 2");
		   Au.winActive(PaginaInicio);
		   
		   //Au.controlGetFocus(PaginaInicio);
		   System.out.println("Ahora 3");
		   Thread.sleep(5000);
		   System.out.println("Ahora 4");
		   
		   amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
		   
		   
		   if(sikuli.FindImgWait("paginaResumenOrden\\generarContrato.png", 5))
		   {
			   sikuli.ClickImg("paginaResumenOrden\\generarContrato.png", 5);
			  
			   if(sikuli.FindImgWait("paginaConfiguracion\\verContrato.png", 80) == true) {
				   DataRowExcel("GeneraContrato", "Si");
				   amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
				   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
			   }
			   else
			   {
				   sikuli.FindImgWait("paginaResumenOrden\\MensajeFallaGeneracionContrato.png", 60);
				   sikuli.InputText(Key.ENTER);
				   DataRowExcel("GeneraContrato", "No");
				   amd.FindImgScrollBarra("paginaResumenOrden\\enviarResumen","down", 10);
				   sikuli.ClickImg("paginaResumenOrden\\enviarResumen", 10);
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

}