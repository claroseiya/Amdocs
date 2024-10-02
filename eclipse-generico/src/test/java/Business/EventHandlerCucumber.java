package Business;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.poi.util.SystemOutLogger;
import org.apache.tools.ant.taskdefs.optional.Script;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;

import Core.Appium.InitAppium;
import Core.BD.ConsultasAmdocs;
import Core.Cucumber.Runner;
import Core.Selenium.AccionesWeb;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import Script.Ventas.*;
import Script.Ecommerce.*;


public class EventHandlerCucumber extends Runner {
	private Scenario MiEscenario;
	String [] DataAmdocs 		  = Business.Amdocs.DataAmdocs;
	String [] DataAmdocsEcommerce = Business.Amdocs.DataAmdocsEcommerce;
	
        Core.Propiedades.Propiedades Propi = new Core.Propiedades.Propiedades();
	
	public String HojaExcel=Propi.GetValue("Excel", "HojaExcel");
	String CeldaProyecto=Propi.GetValue("Excel", "CeldaProyecto");
	String CeldaJira=Propi.GetValue("Excel", "CeldaJira");
	public String CeldaAmbiente=Propi.GetValue("Excel", "CeldaAmbiente");
	Business.Reporte Rep = new Business.Reporte();
	String TeplateSalida ="Original";
	
	public static String NombreExcel = null;
	
	@Before()
	public void inicia(Scenario scenario)
	{
		MiEscenario =scenario;
		
	}
	

	@After()
	public void Termina(Scenario scenario) throws AWTException, IOException
	{
		TakeScreenshot();
	}
	
	@Entonces("Capturar Pantralla")
	public void TakeScreenshot() throws AWTException, IOException
	{
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( capture, "png", baos );
			baos.flush();
		    byte[] screenshot = baos.toByteArray();
			MiEscenario.embed(screenshot, "image/png");  // Stick it in the report
			baos.close();
       } catch (WebDriverException somePlatformsDontSupportScreenshots) {
       }
	}
	
    public void RunTestExcel() throws AWTException, UnknownHostException
	{	
          
        java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost(); 
        String nombreMaquina =  localMachine.getHostName();
	  	Date dtFechaActual = new Date ();
		DateFormat dfLocal = new SimpleDateFormat("HH:mm:ss");
		DateFormat dffecha = new SimpleDateFormat("dd-MM-yyyy");
		
		String horaSql = dfLocal.format(dtFechaActual);
		String fechaSql = dffecha.format(dtFechaActual);
		String HoraComienzoSql = null;
		String Caso = null;
		HoraComienzoSql = horaSql;

        Core.ScreenShot.ScreenShot Screen = new Core.ScreenShot.ScreenShot(); 

        Amdocs amdocs= new Amdocs();        
		Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
		Date fecha = new Date();
        String Setfecha = "dd-MMM-yyyy hh:mm:ss";  
        SimpleDateFormat objSDF = new SimpleDateFormat(Setfecha, new Locale("es_ES"));
        NombreExcel = objSDF.format(fecha);
        NombreExcel ="Reporte "+NombreExcel.replace(":", "."); 
        
        if(!Excel.ExisteExcel(NombreExcel))
		{
        	String [] CabeceraAmdocs= {"Id","Rut","CBP","FA","Ciclo","Status","Orden","Plan","Telefono","Sim","Imsi","Estado transacción","Duración Total Prueba","Duración Prueba","Duración Errores"};
		    Excel.CrearNuevoExcel(NombreExcel, "Data", CabeceraAmdocs);
		}
	  
		try
		{
			 CAC VentasCac = new CAC();
			 Servicios servicios = new Servicios();
			 Hogar VentasHogar = new Hogar();
                         String NombreProyecto = Excel.LecturaCeldaExcel("Transaciones", HojaExcel, CeldaProyecto);
         		         String Jira = Excel.LecturaCeldaExcel("Transaciones", HojaExcel, CeldaJira);
         		         String Ambiente = "Amb "+Rep.GetAmbiente();
         		       			
			ArrayList<ObjExcel> objExcelList =Excel.CountRow("Transaciones");
			
			int SizeList=objExcelList.size();
			for(int i=1;i<objExcelList.size();i++)
			{	
				try
				{
					 Date HoraInicio = new Date ();
					 Hashtable<String, String> Data = 
			                 new Hashtable<String, String>(); 
					 
					 if(objExcelList.get(i).getID()!=null)
						 Data.put("ID",   objExcelList.get(i).getID());
					 if(objExcelList.get(i).getCaso()!=null)
						 Data.put("Caso", objExcelList.get(i).getCaso());
					 if(objExcelList.get(i).getCiclo()!=null)
						 Data.put("Ciclo",   objExcelList.get(i).getCiclo());
					 if(objExcelList.get(i).getTipoCliente()!=null)
						 Data.put("TipoCliente", objExcelList.get(i).getTipoCliente());
					 if(objExcelList.get(i).getSubTipoCliente()!=null)
						 Data.put("SubTipoCliente",   objExcelList.get(i).getSubTipoCliente());
					 if(objExcelList.get(i).getTipoAdquisicion()!=null)
						 Data.put("TipoAdquisicion",   objExcelList.get(i).getTipoAdquisicion());
					 if(objExcelList.get(i).getNombrePlan()!=null)
						 Data.put("NombrePlan", objExcelList.get(i).getNombrePlan());					 
					 if(objExcelList.get(i).getRut()!=null)
						 Data.put("Rut",   objExcelList.get(i).getRut());
					 if(objExcelList.get(i).getPerfilAPN()!=null)
						 Data.put("PerfilAPN", objExcelList.get(i).getPerfilAPN());
					 if(objExcelList.get(i).getSkuEquipo()!=null)
						 Data.put("SkuEquipo",   objExcelList.get(i).getSkuEquipo());
					 if(objExcelList.get(i).getTelefono()!=null)
						 Data.put("Telefono", objExcelList.get(i).getTelefono());
					 if(objExcelList.get(i).getPlanBase()!=null)
						 Data.put("ReasonCode",   objExcelList.get(i).getPlanBase());
					 if(objExcelList.get(i).getSubProducto()!=null)
						 Data.put("Mail", objExcelList.get(i).getSubProducto());
					 if(objExcelList.get(i).getTipo()!=null)
						 Data.put("Tipo",   objExcelList.get(i).getTipo());	 
					 if(objExcelList.get(i).getServicioAdicional()!=null)
						 Data.put("ServicioAdicional", objExcelList.get(i).getServicioAdicional());
					 if(objExcelList.get(i).getPlay1()!=null)
						 Data.put("Play1",   objExcelList.get(i).getPlay1());	 
					 if(objExcelList.get(i).getPlay2()!=null)
						 Data.put("Play2", objExcelList.get(i).getPlay2());
					 if(objExcelList.get(i).getPlay3()!=null)
						 Data.put("Play3", objExcelList.get(i).getPlay3());
					 
					System.out.println("Ejecutando caso "+objExcelList.get(i).getCaso());
					System.out.println("Caso "+ i +" De.... "+ (SizeList-1));
					
					try
					{
						if(amdocs.BanderaBD)
						{
							if(i==1) {
								 HoraComienzoSql = horaSql;
								 ConsultasAmdocs.insertaEjec(i +" De "+ (SizeList-1), nombreMaquina, NombreProyecto, Jira, Ambiente, fechaSql, horaSql);
							}
							else
								 ConsultasAmdocs.actualizaEjec(i +" De "+ (SizeList-1), nombreMaquina, fechaSql, HoraComienzoSql);							
					    }
						Caso = objExcelList.get(i).getCaso();
						amdocs.DataRowExcel("Id", objExcelList.get(i).getID());
						switch (objExcelList.get(i).getCaso().toUpperCase()){
						/*
						@Descripcion 1
						CASOS DE PRUEBA QUE SE EJECUTAN SOBRE CRM ONE.
						Hoja: "InputCRMOne"						
						*/
						case"VENTA":
							VentasCac.Venta(Data);
							break;
						//Caso no está en Uso
						//case"VENTA CLAROUP":
							//VentasCac.VentaClaroUp(Data);
							//break;
						case"PORTABILIDAD":
							VentasCac.Portabilidad(Data);
							break;
						case"CAMBIO PLAN":
						      VentasCac.CambioPlan(Data);
						break;
						case"CAMBIO DE EQUIPO":
						case"CAMBIO EQUIPO":
							VentasCac.CambioEquipo(Data);
						break;
						case"SUSPENSION POR ROBO":							 
						case"SUSPENSION POR EXTRAVIO":							
							VentasCac.SuspencionRobo(Data);
						break;
						case"ACTIVACION POR ROBO":
						case"ACTIVACION POR EXTRAVIO":
							VentasCac.ActivacionRobo(Data);
						break;
						case"CAMBIO NUMERO":
							VentasCac.CambioNumero(Data);
						break;
						case"CAMBIO SIMCARD":
							VentasCac.CambioSimCard(Data);
						break;
						case"TRASPASO TITULARIDAD EMPRESA":
							VentasCac.TraspasoTitularidad(Data);
						break;
                        case"TERMINO CONTRATO":
							VentasCac.TerminoContrato(Data);
						break;
						case"RECONEXION":
							VentasCac.Reconexion(Data);
						break;
						case"EXISTENCIA PLAN":
							VentasCac.ValidacionExistenciaPlan(Data);
						break;
						/*
						@Descripcion 2
						CASOS DE PRUEBA QUE SE EJECUTAN POR WEB SERVICE Y CRM ONE.
						Hoja: "InputVentasHogar"							
						*/
						case"VENTA HOGAR":
							VentasHogar.Venta(Data);
						break;
						/*
						@Descripcion 3
						CASOS DE PRUEBA QUE SE EJECUTAN POR WEB SERVICE Y CRM ONE.						
						*/
						case"VENTA CLIENTE SERVICIO":
							VentasCac.VentaClienteServicio(Data);
						break;
						
						/*
						@Descripcion 4
						CASOS DE PRUEBA QUE SE EJECUTAN SOLO POR WEB SERVICE.
						Hoja: "InputVentas"					
						*/
						//Caso para consultar Id de contacto
						case"CONSULTARUT":
							//Agregado el 14-11-2022
							servicios.ConsultaRut(Data);
						break;
						/*ACTUALIZADO EL 29-11
						 * Se Mejora la creación de Clientes, se incluye Pyme y la la creación 
						 * sobre un Rut Existente y Cliente Nuevo
						 *Opciones:
						 *Sin Rut (Rut = null)
						 *Va a crear al cliente, Persona, Pyme o Empresa
						 *	Contacto u Organización
						 *	CBP
						 *	FA
						 *Con Rut (Rut existente en CRM)
						 *Va a crear un nuevo CBP y una nueva FA
						 *	Consulta Id de contacto para crearo 
						 *	CBP
						 *	FA
						 *
						 *ACTUALIZACION 30-11
						 *Se mejora el uso del servicio ProvideWirelessIVR
						 *Queda un request dinamico, según tipo de Adquisicion
						 *
						 */
						/*Agregado el 29-11-2022
						*Unificación del servicio provideWirelessIVR
						
						case"NUEVO_VENTA":
						case"NUEVO_PORTABILIDAD":
						case"NUEVO_MIGRACION":
							servicios.NuevaVServicio(Data);
						break;
						*/
						case"CREAR CLIENTE POR SERVICIO":							
							//VentasCac.CrearClientePorServicio(Data);
							servicios.CrearClientePorServicio(Data);
						break;
						
						
						//Servicio Mejorado para el uso de 1 sólo request
						case"VENTA POR SERVICIO":
						case"PORTABILIDAD POR SERVICIO":
						case"MIGRACION POR SERVICIO":
							servicios.NuevaVServicio(Data);
						break;
						
						case"SERVICIO TRASPASO PROPIEDAD CLIENTE EXISTENTE":
						case"SERVICIO TRASPASO PROPIEDAD CLIENTE NUEVO":
							//Cuando el Cliente Existe, se pasa el CBP en el PerfilAPN
							TeplateSalida="TraspasoTitularidad";
							servicios.TraspasoPropiedad(Data);
						break;
						
						//case"SERVICIO IDENTIFICAR LLAMANTE":
							//NO TIENE RESULTADO
							//servicios.IdentificarLlamante(Data);
						//break;
						case"SERVICIO CAMBIO OFERTA":							
							TeplateSalida="CambioOferta";
							servicios.CambioOferta(Data);
						break;
						case"CONSULTA QDN":
							servicios.ConsultaQDN(Data);
						break;
						case"AGREGAR SERVICIO POR SERVICIO":
						case"ELIMINAR SERVICIO POR SERVICIO":
							TeplateSalida="AgregarEliminarServicio";
							servicios.AgregarServicio4Servicio(Data);
						break;
						
						case"PAGO POR SERVICIO":
							//Agregado el 24-06-2022
							servicios.ConsultaPagoDeuda(Data);
						
						break;
						
						/*
						 * NO USAR POR EL MOMENTO
						 * */
						case"EMAIL":
							//No funciona
							VentasCac.PruebaMail(Data);
						break;						
						
						/*
						METODOS OBSOLETOS
						case"VENTA POR SERVICIO":
							//VentasCac.VentaPorServicio(Data);
							servicios.VentaPorServicio(Data);						
						break;
						case"PORTABILIDAD POR SERVICIO":							
							//VentasCac.PortabilidadPorServicio(Data);
							servicios.PortinPorServicio(Data);
						break;
						case"MIGRACION POR SERVICIO":
							//Agregado el 24-06-2022
							//VentasCac.VentaPorServicio(Data);
							String TipoCaso = "Migracion";
							servicios.MigracionPorServicio(Data);
						break;
						 */
						
						}
						
						
					}
					catch(Exception e)
					{}
					finally
					{	
						Rep.TemplateSalida(TeplateSalida);
						Date HoraTermino = new Date();
						
						if(amdocs.HoraFinErrores!=null)
							amdocs.DataRowExcel("HoraTermino", amdocs.DiferenciaHora(amdocs.HoraFinErrores,HoraTermino, "Prueba"));
						amdocs.DataRowExcel("HoraTotalTermino", amdocs.DiferenciaHora(HoraInicio,HoraTermino,"Prueba Total"));
						
						Screen.Capturar("", objExcelList.get(i).getID());
						Core.Excel.AccionesExcel excel = new Core.Excel.AccionesExcel();
						System.out.println(DataAmdocs[0]);
						System.out.println(NombreExcel);
						excel.AgregarLineaExcel(NombreExcel, DataAmdocs);                        
						
						if(amdocs.BanderaBD)
						{						
							ConsultasAmdocs.insertaResultados(DataAmdocs, nombreMaquina, fechaSql, HoraComienzoSql, NombreProyecto, Jira, Caso);										
							if(i==SizeList-1) 
								ConsultasAmdocs.finalizaEjec(i +" De "+ (SizeList-1), nombreMaquina, fechaSql, HoraComienzoSql);
						}
					}
				}
				catch(Exception e)
				{
					System.out.println(e.toString());
					TakeScreenshot();
				}
			}
		}
		catch(Exception e)
		{}		
	}  
    
	public void RunTestExcelEcommerce() throws AWTException
	{	
        Core.ScreenShot.ScreenShot Screen = new Core.ScreenShot.ScreenShot(); 
		
        
        Amdocs amdocs= new Amdocs();        
		Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
		Date fecha = new Date();
        String Setfecha = "dd-MMM-yyyy hh:mm:ss";  
        SimpleDateFormat objSDF = new SimpleDateFormat(Setfecha, new Locale("es_ES"));
        String NombreExcel = objSDF.format(fecha);
        NombreExcel ="Reporte Ecommerce "+NombreExcel.replace(":", "."); 
        
        if(!Excel.ExisteExcel(NombreExcel))
		{
        	String [] CabeceraAmdocs= {"Rut","Nombre Colspan=2","DataSinUso Colspan=3","DataSinUso Colspan=4","DataSinUso","DataSinUso","DataSinUso","DataSinUso","DataSinUso","DataSinUso","DataSinUso","DataSinUso","DataSinUso"};
		    Excel.CrearNuevoExcel(NombreExcel, "Data", CabeceraAmdocs);
		}
	  
		try
		{
			
			Ecommerce Ecommerce = new Ecommerce(); 
			
			ArrayList<ObjExcel> objExcelList =Excel.CountRow("TransacionesEcommerce");
			
			//ObjExcel objExcel = new ObjExcel(); 
			int SizeList=objExcelList.size();
			for(int i=1;i<objExcelList.size();i++)
			{
				try
				{
					 Hashtable<String, String> Data = 
			                 new Hashtable<String, String>(); 
					 
					 if(objExcelList.get(i).getID()!=null)
						 Data.put("Rut",   objExcelList.get(i).getID());
					 if(objExcelList.get(i).getCaso()!=null)
						 Data.put("Nombre", objExcelList.get(i).getCaso());
					 if(objExcelList.get(i).getCiclo()!=null)
						 Data.put("Direccion",   objExcelList.get(i).getCiclo());
					 if(objExcelList.get(i).getTipoCliente()!=null)
						 Data.put("NumeroPedido", objExcelList.get(i).getTipoCliente());
					 if(objExcelList.get(i).getSubTipoCliente()!=null)
						 Data.put("SKU",   objExcelList.get(i).getSubTipoCliente());
					 if(objExcelList.get(i).getTipoAdquisicion()!=null)
						 Data.put("Plan",   objExcelList.get(i).getTipoAdquisicion());
					 if(objExcelList.get(i).getNombrePlan()!=null)
						 Data.put("Observaciones", objExcelList.get(i).getNombrePlan());					 
					 if(objExcelList.get(i).getRut()!=null)
						 Data.put("Estado",   objExcelList.get(i).getRut());
					 
					 
					System.out.println("Ejecutando caso "+objExcelList.get(i).getCaso());
					System.out.println("Caso "+ i +" De.... "+ (SizeList-1));
					
					try
					{
						amdocs.DataRowExcel("Id", objExcelList.get(i).getID());
						Ecommerce.Prepago(Data);
						/*
						switch (objExcelList.get(i).getCaso()){
						
						case"Prepago":
							Ecommerce.Prepago(Data);;
							break;
							
						}*/
					}
					catch(Exception e)
					{}
					finally
					{
						Screen.Capturar("", objExcelList.get(i).getID());
						Core.Excel.AccionesExcel excel = new Core.Excel.AccionesExcel();
						System.out.println(DataAmdocsEcommerce[0]);
						System.out.println(NombreExcel);
						excel.AgregarLineaExcel(NombreExcel, DataAmdocsEcommerce);
					}
				}
				catch(Exception e)
				{
					System.out.println(e.toString());
					TakeScreenshot();
				}
			}
		}
		catch(Exception e)
		{}		
	}


}
