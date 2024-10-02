package Business;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.velocity.runtime.directive.Parse;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

import Core.AutoIt.InitAutoIt;
import Core.Sikuli.Sikuli;
import autoitx4java.AutoItX;
import gherkin.lexer.It;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;

public class Amdocs {
	
	InitAutoIt AutoIt = new InitAutoIt();
	Core.Ini.LeerData Ini = new Core.Ini.LeerData(); 
	Core.Propiedades.Propiedades Propi = new Core.Propiedades.Propiedades();
	
	boolean BanderaBD = true; //true - activa el registro en la base de datos
	String Ambiente=Propi.GetValue("Amdocs", "Ambiente");
	String Rut  = Ini.GetValue("Ambientes", Ambiente, "Rut");
	String Pass = Ini.GetValue("Ambientes", Ambiente, "Pass");
	String Ruta = Ini.GetValue("Ambientes", Ambiente, "Url");
	
	
	Sikuli sikuli = new Sikuli();
	AutoItX Au;
	String PaginaInicio = "Login";
	String PaginaAmdocs="";
	
	
	static String [] DataAmdocs={"","","","","","","","","","","","","","",""};
	static String [] DataAmdocsEcommerce={"","","","","","","","","","","","",""};
	static String NombreExcel;
	Core.Excel.AccionesExcel excel = new Core.Excel.AccionesExcel();
	
	static Date HoraInicioErrores;
	static Date HoraFinErrores;
	
	
	public void IniciarAmdocs() throws IOException, UnsupportedFlavorException, FindFailed, InterruptedException
	{	
		
		PaginaAmdocs = GetNameAmdocs();
		
		System.out.println("Pagina "+PaginaAmdocs );
		System.out.println("Pruebas en: "+ Ambiente);
		/*  	
		if(!excel.ExisteExcel(NombreExcel))
		{
			String [] CabeceraAmdocs= {"Id","Rut","CBP","FA","Ciclo","Status","Orden","Plan","Telefono","Sim","Imsi","Estado transacción"};
		    excel.CrearNuevoExcel(NombreExcel, "Data", CabeceraAmdocs);
		}
	    */
		LimpiarDataRowExcel();
		LimpiarDataRowExcelEcommerce();
	    AutoIt.IniciarAutoIt();
		Au= AutoIt.getAutoit();	   
		
		boolean Existe=Au.winExists(PaginaAmdocs);		

		 if(!Existe)
			 IniciarIE();
		
		 Errores();
	}
	
	public void Errores() throws FindFailed, IOException, InterruptedException
	{
		HoraInicioErrores = new Date ();
		
		boolean Limpio= false;
		while(Limpio==false)
		{
			Limpio= LimpiarErrores();
			TimeSleep(500);
		}
		
		HoraFinErrores = new Date ();		
		DataRowExcel("HoraErrores", DiferenciaHora(HoraInicioErrores,HoraFinErrores, "Errores"));
		
	}

	public boolean LimpiarErrores() throws FindFailed, IOException, InterruptedException
	{	
				
		AutoIt.IniciarAutoIt();
		Au= AutoIt.getAutoit();		
		Au.winActivate(PaginaAmdocs);
	
		Business.Operaciones Oper = new Business.Operaciones();
		/*Au.winExists("Guardar Formulario")||
		if(
		   Au.winExists("Mensaje")||
		   Au.winExists("Cerrar Orden"))
		{
			Au.winActivate(PaginaAmdocs); 	
			Au.controlSend(PaginaAmdocs, "", "" , Key.ENTER, false);
			System.out.println("Click pop up");
			return false;
		}
		*/
		
		if(sikuli.FindImgWait("ErrorInterno.png", 1))
		{
			sikuli.InputText(Key.ENTER);
			return false;
		}
		
		if(sikuli.FindImgWait("EncontrarQuienLlama\\Maximizar.png", 1))
		{
			sikuli.ClickImg("EncontrarQuienLlama\\Maximizar.png", 1);
			return false;
		}
		
		if(sikuli.FindImgWait("Errores\\Maximizar.png", 1))
		{
			sikuli.ClickImg("Errores\\Maximizar.png", 1);
			return false;
		}
		
		if(sikuli.FindImgWait("Alert\\Descartar.png", 1))
		{
			sikuli.ClickImg("Alert\\Descartar.png", 1);
			return false;
		}
		
		if(sikuli.FindImgWait("Errores\\Temas.png", 1))
		{
			sikuli.ClickImg("Errores\\AceptarPopUp.png", 1);
			Oper.SeleccionTopicos("SOLICITUDES COMERCIALES", "VENTAS","NUEVA CONTRATACION","SE GENERA ORDEN");
			return false;
		}		
	
		
		if(sikuli.FindImgWait("Errores\\GuardarFormulario.png", 1))
		{
			sikuli.ClickImg("Errores\\Guardar.png", 1);
			
			return false;
		}
		
		if(sikuli.FindImgWait("Errores\\CerrarOrden.png", 1))
		{
			sikuli.ClickImg("Errores\\Si.png", 1);
			return false;
		}
		
		if(sikuli.FindImgWait("paginaResumenOrden\\FalloGeneracionDocumento.png", 1))
		{
			sikuli.ClickImg("paginaResumenOrden\\Aceptar.png", 1);
			return false;
		}
		
		if(sikuli.FindImgWait("Errores\\ErrorTopicos.png", 1))
		{
			System.out.println("Topicos!!..");
			sikuli.ClickImg("Errores\\AceptarPopUp.png", 1);
			
			sikuli.FindImg("PaginaPrincipalInteraccion\\TxtPaginaPrincipalInteraccion", 10);
			try {
				SelectComboboxText("PaginaPrincipalInteraccion\\Motivo1.png"  ,"SOLICITUDES COMERCIALES",3);
				SelectComboboxText("PaginaPrincipalInteraccion\\Motivo2.png"  ,"VENTAS",3);
				SelectComboboxText("PaginaPrincipalInteraccion\\Motivo3.png"  ,"NUEVA CONTRATACION",3);
				SelectComboboxText("PaginaPrincipalInteraccion\\Resultado.png","SE GENERA ORDEN",3);
				} 
			catch (InterruptedException e) {	e.printStackTrace();
			}
			sikuli.ClickImg("PaginaPrincipalInteraccion\\Accion.png", 3);
			sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 3);
			sikuli.ClickImg("EncontrarQuienLlama\\GuardarFormulario.png", 3);
			sikuli.ClickImg("CrearContacto\\Guardar.png", 3);
			return false;

		}
		
			
		
		if(sikuli.FindImgWait("Errores\\AceptarPopUp.png", 1))
		{
			sikuli.ClickImg("Errores\\AceptarPopUp.png", 1);
			return false;
		}	
		
		if(sikuli.FindImgWait("Errores\\IdDuplicado.png", 1))
		{
			sikuli.ClickImg("Errores\\Cerrar.png", 1);
			return false;
		}
						
		if(sikuli.FindImgWait("EncontrarQuienLlama\\CerrarPestana.png", 1))
		{
			sikuli.ClickImg("EncontrarQuienLlama\\CerrarPestana.png", 1);
			return false;
		}	
			
		return true;
	}	
	
	public void IniciarIE() throws IOException, FindFailed
	{	
		AutoIt.IniciarAutoIt();
		Au= AutoIt.getAutoit();		
			
		System.out.println("Internet Explorer is selected");
		System.setProperty("webdriver.ie.driver","src//test//resources//drivers//windows//Selenium//IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.get(Ruta);
		WebElement Elem =  driver.findElement(By.linkText("Launch Amdocs CRM"));
		Elem.sendKeys("\n");		
			
		sikuli.FindImg("login\\PaginaLogin.png", 60);
		
		Au.winActive(PaginaInicio);
		
		driver.close();
			
		sikuli.ClickImg("login\\NombreInicioSesion.png", 1);
		sikuli.InputText(Rut);
		//Au.controlSend(PaginaInicio, "", "",Rut, true);
		
		sikuli.ClickImg("login\\IngresarContrasena.png", 1);
		Au.controlSend(PaginaInicio, "", "",Pass, true);
		
		if(sikuli.FindImgWait("login\\IdiomaIngles.png", 1)) 
		{
			sikuli.ClickImg("login\\IdiomaIngles.png", 1);
			sikuli.ClickImg("login\\IdiomaEspanol.png", 1);
		}
		
		sikuli.ClickImg("login\\BotonIngresar.png", 1);
		
		sikuli.FindImg("EncontrarQuienLlama", 60);
		PaginaAmdocs = GetNameAmdocs();
	}
	
	
	public void InputText(String Img, String Text, int Time) throws FindFailed
	{
		Au= AutoIt.getAutoit();		
		Au.winActivate(PaginaAmdocs);
		sikuli.ClickImg(Img, Time);
		Au.controlSend(PaginaAmdocs, "", "" , Text, false);
	}
	
	public void InputClearText(String Img, String Text, int Time) throws FindFailed
	{
		sikuli.ClickImg(Img, Time);
		Au.controlSend(PaginaAmdocs, "", "" , "^a", false);
		Au.controlSend(PaginaAmdocs, "", "" , Keys.BACKSPACE, false);
		Au.controlSend(PaginaAmdocs, "", "" , Text, false);
	}
	
	public void clearText() throws IOException {
		
		AutoIt.IniciarAutoIt();
		AutoItX Au= AutoIt.getAutoit();	
		Au.winActivate("Buscar: Pool de Orden  > Envío de solicitud de equipo (Orden 1024510A para RobotQa Pizarro)"); 	
		Au.controlSend(PaginaAmdocs, "", "" , "^a", false);
		Au.controlSend(PaginaAmdocs, "", "" , Keys.BACKSPACE, false);
	}
	
	public String SelectAllText(String Img, int Time) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
	{
		 AutoIt.IniciarAutoIt();
		 AutoItX Au= AutoIt.getAutoit();	
		 Au.winActivate(PaginaAmdocs); 	
		sikuli.ClickImg(Img, Time);
		Au.controlSend(PaginaAmdocs, "", "" , "^a", false);
		Au.controlSend(PaginaAmdocs, "", "" , "^c", false);
		String Value = (String) Toolkit.getDefaultToolkit()
	               .getSystemClipboard().getData(DataFlavor.stringFlavor); 
       	Thread.sleep(1000);
		
		return Value;
		
	}
	
	public String SelectAllTextClicDown(String Img, int Time) throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException
	{
		 AutoIt.IniciarAutoIt();
		 AutoItX Au= AutoIt.getAutoit();	
		 Au.winActivate(PaginaAmdocs); 	
		sikuli.ClickDown(Img, Time);
		Au.controlSend(PaginaAmdocs, "", "" , "^a", false);
		Au.controlSend(PaginaAmdocs, "", "" , "^c", false);
		String Value = (String) Toolkit.getDefaultToolkit()
	               .getSystemClipboard().getData(DataFlavor.stringFlavor); 
       	Thread.sleep(1000);
		
		return Value;
		
	}
	
	public void SelectCombobox(String Img, String ImgValue, int Time) throws FindFailed
	{
		sikuli.ClickImg(Img, Time);
		sikuli.ClickImg(ImgValue, Time);		
	}
	
	public void SelectComboboxText(String Img, String Value, int Time) throws FindFailed, InterruptedException
	{
		Thread.sleep(2000);
		sikuli.ClickImg(Img, Time);	
	    sikuli.InputText(Value);	
	    sikuli.InputText("\n");
	}
	
	public void FindImgScroll(String Img, String Dieccion,int Time) throws FindFailed, IOException
	{
		 AutoIt.IniciarAutoIt();
		 AutoItX Au= AutoIt.getAutoit();	
		 Au.winActivate(PaginaAmdocs); 
		boolean Existe;
		for(int i=0;i<Time;i++)
		{
			Existe = sikuli.FindImgWait(Img, 1);
			if(Existe)
				return;			
			Au.mouseWheel(Dieccion);
		}
	}
	
	public void FindImgScrollArrow(String Img, String Dieccion,int Time) throws FindFailed, IOException
	{
		 AutoIt.IniciarAutoIt();
		 AutoItX Au= AutoIt.getAutoit();	
		 Au.winActivate(PaginaAmdocs); 
		boolean Existe;
		for(int i=0;i<Time;i++)
		{
			Existe = sikuli.FindImgWait(Img, 1);
			if(Existe)
				return;			
			if(Dieccion.equalsIgnoreCase("Right")) 
				sikuli.InputText(Key.RIGHT);
			else if(Dieccion.equalsIgnoreCase("Left")) 
				sikuli.InputText(Key.LEFT);
			else if(Dieccion.equalsIgnoreCase("Up")) 
				sikuli.InputText(Key.UP);
			else if(Dieccion.equalsIgnoreCase("Down")) 
				sikuli.InputText(Key.DOWN);
		}
	}
	
	public void FindImgScrollBarra(String Img, String Dieccion,int Time) throws FindFailed, IOException
	{
		 AutoIt.IniciarAutoIt();
		 AutoItX Au= AutoIt.getAutoit();	
		 Au.winActivate(PaginaAmdocs); 
		boolean Existe;
		for(int i=0;i<Time;i++)
		{
			Existe = sikuli.FindImgWait(Img, 1);
			if(Existe)
				return;
			if(Dieccion.equalsIgnoreCase("down"))
				sikuli.ClickImg("BtonAbajoScroll.jpg", 3);
			if(Dieccion.equalsIgnoreCase("up"))
				sikuli.ClickImg("BtonUpScroll.jpg", 3);
		}
	
	}
	
	
	public void TimeSleep(int Time)
	{
		try
	        {Thread.sleep(Time);}
	        catch(InterruptedException e)
	        {}
	}
	
public String[] busquedaPlan(String tipoPlan) throws UnsupportedFlavorException, IOException, FindFailed {
		
		String plan[] = new String[3];
		int w = 0;
		int h = 0;
			
		for (int i = 0; i < 2; i++) {
			
				//----------------primer registro---------------------
				w = -30;
				h = 45;
				String dato1 = sikuli.GetTextRandom("PaginaPLanes\\categorias", 2, w, h);
				if(dato1.equals(tipoPlan)) {
					plan[0] = dato1;
					plan[1] = String.valueOf(w);
					plan[2] = String.valueOf(h);
					break;
				}else {
					System.out.println("EL dato es incorrecto");
				}
				
				//----------------Segundo registro---------------------
				w = 210;
				h = 45;
				String dato2 = sikuli.GetTextRandom("PaginaPlanes\\categorias", 2, w, h);
				if(dato2.equals(tipoPlan)) {
					plan[0] = dato2;
					plan[1] = String.valueOf(w);
					plan[2] = String.valueOf(h);
					break;
				}else {
					System.out.println("EL dato es incorrecto");
				}
				
				//----------------Tercer registro---------------------
				w = 380;
				h = 45;
				String dato3 = sikuli.GetTextRandom("PaginaPlanes\\categorias", 2, w, h);
				if(dato3.equals(tipoPlan)) {
					plan[0] = dato3;
					plan[1] = String.valueOf(w);
					plan[2] = String.valueOf(h);
					break;
				}else {
					System.out.println("EL dato es incorrecto");
				}
				
				//----------------Cuarto registro---------------------
				w = -30;
				h = 265;
				String dato4 = sikuli.GetTextRandom("PaginaPlanes\\\\categorias", 2,w,h);
				if(dato4.equals(tipoPlan)) {
					plan[0] = dato4;
					plan[1] = String.valueOf(w);
					plan[2] = String.valueOf(h);
					break;
				}else {
					System.out.println("EL dato es incorrecto");
				}
				
				//----------------Quinto registro---------------------
				w = 200;
				h = 265;
				String dato5 = sikuli.GetTextRandom("PaginaPlanes\\\\categorias", 2,w,h);
				if(dato5.equals(tipoPlan)) {
					plan[0] = dato5;
					plan[1] = String.valueOf(w);
					plan[2] = String.valueOf(h);
					break;
				}else {
					System.out.println("EL dato es incorrecto");
				}
				
				//----------------Sexto registro---------------------
				w = 350;
				h = 265;
				String dato6 = sikuli.GetTextRandom("PaginaPlanes\\\\categorias", 2,w,h);
				if(dato6.equals(tipoPlan)) {
					plan[0] = dato6;
					plan[1] = String.valueOf(w);
					plan[2] = String.valueOf(h);
					break;
				}else {
					System.out.println("EL dato es incorrecto");
				}
				
				sikuli.ClickImg("PaginaPlanes\\siguienteDerecha", 2);
			}
		
		return plan;
	
	}
	
    public String GetCopyClip() throws HeadlessException, UnsupportedFlavorException, IOException
    {
    	 try
	        {  
    		   AutoIt.IniciarAutoIt();
			   AutoItX Au= AutoIt.getAutoit();	
			   Au.winActivate(PaginaAmdocs); 	
			   Au.controlSend(PaginaAmdocs, "", "" , "^c", false);
			   
			   System.out.println(PaginaAmdocs);
			   Thread.sleep(500);
			   String data = (String) Toolkit.getDefaultToolkit()
			           .getSystemClipboard().getData(DataFlavor.stringFlavor); 
				Thread.sleep(500);
	return data;
	 		
	        }
	        catch(InterruptedException e)
	        {
	        	return null;
	        }
    	
    }
    
    public void BusquedaPlan(String Plan) throws UnsupportedFlavorException, IOException, FindFailed, InterruptedException
    {
    	int x, y,yUp, xSel, ySel,xSelBack;
    	String PlanAmdocs;
    	boolean Siguiente;
        do
        {
        	Iterator<Match> it    = sikuli.FindAllImgs("SelecPlan");
        	Iterator<Match> itSel = sikuli.FindAllImgs("PaginaPlanes\\Seleccione.png");
        	
	    	while(it.hasNext()){
	    		   Match planSel = itSel.next();
	    		   xSel          = planSel.getX();
		    	   ySel          = planSel.getY();	   
		    	   xSelBack 	 = xSel-10;
		    	   
	    		   Match plan = it.next();
	    		   x = plan.getX();
		    	   y = plan.getY();	   
		    	   yUp = y-10;
		    	 
		    	   PlanAmdocs= sikuli.GetText("", 3, x, yUp);
			       if(PlanAmdocs.equalsIgnoreCase(Plan))
			       {
			    	   			    	
			    	   sikuli.ClickLocation(x, yUp);
			    	   sikuli.ClickImg("PaginaPlanes\\agregarOrden.png", 60);
			    	   return;
			       }
		    	   
		    	   System.out.println(PlanAmdocs);
	    	}
	    	
	    	sikuli.ClickImg("PaginaPlanes\\siguienteDerecha", 2);
	    	Thread.sleep(2000);
        }while(true);
    }
    
    public void BusquedaPlanHogar(String Plan) throws UnsupportedFlavorException, IOException, FindFailed, InterruptedException
    {
       AutoIt.IniciarAutoIt();
   	   AutoItX Au= AutoIt.getAutoit();	
   	   Au.winActivate(PaginaAmdocs);
   	   
 	   sikuli.ClickImg("PaginaPlanes\\buscarCuenta2.png", 10);
 	   clearText();
 	   Au.controlSend(PaginaAmdocs, "", "", Plan);
 	   sikuli.ClickImg("PaginaPlanes\\btnBuscarCta.png", 10);
 	   Thread.sleep(7000);
 	   
    	
    	int x, y,yUp, xSel, ySel,ySelBack;
    	String PlanAmdocs;
    	boolean Siguiente;
        do
        {
        	//Iterator<Match> it    = sikuli.FindAllImgs("SelecPlan");
        	//sikuli.ClickImg("PaginaPlanes\\CheckSeleccione.png", 2);
        	
        	System.out.println("Posicion de seleccion");
        	Iterator<Match> it = sikuli.FindAllImgs("PaginaPlanes\\CheckSeleccione.png");
        	
	    	while(it.hasNext()){
	    		   /*
	    		   itSel.hasNext();
	    		   Match planSel = itSel.next();
	    		   xSel          = planSel.getX();
		    	   ySel          = planSel.getY();	   
		    	   ySelBack 	 = ySel;
		    	   */
	    		
	    		   Match plan = it.next();
	    		   x = plan.getX()+10;
		    	   y = plan.getY();	   
		    	   yUp = y+10;
		    	   
		    	   xSel          = x+10;
		    	   ySel          = y-130;	   
		    	   
		    	 		    	   
		    	   PlanAmdocs= sikuli.GetText("", 3, xSel, ySel);
			       if(PlanAmdocs.equalsIgnoreCase(Plan))
			       {
			    	   sikuli.ClickLocation(x, yUp);
			    	   //sikuli.ClickLocation(x, yUp);
			    	   //sikuli.ClickImg("PaginaPlanes\\agregarOrden.png", 60);
			    	   return;
			       }
		    	   
		    	   System.out.println(PlanAmdocs);
	    	}
	    	
	    	FindImgScrollArrow("PaginaPlanes\\siguienteDerecha","Right", 30);
	    	sikuli.ClickImg("PaginaPlanes\\siguienteDerecha", 2);
	    	
	    	Thread.sleep(2000);
	    	
	    	FindImgScrollArrow("PaginaPlanes\\siguenteIzquierda","Left", 30);
	    	
        }while(true);
    }
    
    public void BusquedaContactoActivo() throws UnsupportedFlavorException, IOException, FindFailed, InterruptedException
    {
      	AutoIt.IniciarAutoIt();
		AutoItX Au= AutoIt.getAutoit();	
		Au.winActivate(PaginaAmdocs); 
	
    	int x, y,yUp;
    	String PlanAmdocs;
    	boolean Siguiente;
        do
        {
        	Iterator<Match> it = sikuli.FindAllImgs("BusquedaContactoExtendida\\Activo");
        	
	    	while(it.hasNext()){
	    		     		 
	    		Match BtnActivo = it.next();
	    		BtnActivo.click();
	    		
	    		Au.controlSend(PaginaAmdocs, "", "" , Keys.TAB, false);
	    		Au.controlSend(PaginaAmdocs, "", "" , Keys.TAB, false);
	    		Au.controlSend(PaginaAmdocs, "", "" , Keys.TAB, false);
	    		Au.controlSend(PaginaAmdocs, "", "" , Keys.TAB, false);
	    		Au.controlSend(PaginaAmdocs, "", "" , Keys.TAB, false);
	    		String Rol =  GetCopyClip();
	    		System.out.println("Este es el rol encontrado "+Rol);
	    		if(Rol.contains("Titular de Cuenta"))
			    	   return;
	    	}
	    	Thread.sleep(2000);
        }while(true);
        
        
        
    }
    
    
    public void DataRowExcel(String Key, String Value)
    {
    	switch(Key)
    	{
    		case "Id"               :DataAmdocs[0]=Value;break;//A
    		case "Rut"              :DataAmdocs[1]=Value;break;//B
    		case "CBP"              :DataAmdocs[2]=Value;break;//C
    		case "FA"               :DataAmdocs[3]=Value;break;//D
    		case "Ciclo"            :DataAmdocs[4]=Value;break;//E
    		case "Status"           :DataAmdocs[5]=Value;break;//F
    		case "Orden"            :DataAmdocs[6]=Value;break;//G
    		case "Plan"             :DataAmdocs[7]=Value;break;//H
    		case "Telefono"         :DataAmdocs[8]=Value;break;//I
    		case "Sim"              :DataAmdocs[9]=Value;break;//J
    		case "Imsi"             :DataAmdocs[10]=Value;break;//K
    		case "EstadoTrx"        :DataAmdocs[11]=Value;break;//L
    		case "HoraTotalTermino" :DataAmdocs[12]=Value;break;//M
    		case "HoraTermino"      :DataAmdocs[13]=Value;break;//N
    		case "HoraErrores"      :DataAmdocs[14]=Value;break;//O
    		
    	}
    	
    }
    
    public void LimpiarDataRowExcel()    
    {
	DataAmdocs[1]="";
	DataAmdocs[2]="";
	DataAmdocs[3]="";
	DataAmdocs[4]="";
	DataAmdocs[5]="";
	DataAmdocs[6]="";
	DataAmdocs[7]="";
	DataAmdocs[8]="";
	DataAmdocs[9]="";
	DataAmdocs[10]="";
	DataAmdocs[11]="";
	DataAmdocs[12]="";
	DataAmdocs[13]="";
	DataAmdocs[14]="";
    }
    
    public void DataRowExcelEcommerce(String Key, String Value)
    {
    	switch(Key)
    	{
    		case "Rut"       :DataAmdocsEcommerce[0]=Value;break;
    		case "Nombre"    :DataAmdocsEcommerce[1]=Value;break;
    		
    	}
    }
    
    public void LimpiarDataRowExcelEcommerce()    
    {
    	DataAmdocsEcommerce[1]="";
    	DataAmdocsEcommerce[2]="";
    	DataAmdocsEcommerce[3]="";
    	DataAmdocsEcommerce[4]="";
    	DataAmdocsEcommerce[5]="";
    	DataAmdocsEcommerce[6]="";
    	DataAmdocsEcommerce[7]="";
    	DataAmdocsEcommerce[8]="";
    	DataAmdocsEcommerce[9]="";
    	DataAmdocsEcommerce[10]="";
    	DataAmdocsEcommerce[11]="";
    	DataAmdocsEcommerce[12]="";
    	
    }   
    	
    public String  GetNameAmdocs()
    {
    	String NameAmdocs="  ";
    	
    	try {
    	    String line;
    	    
    	    Process p = new ProcessBuilder("tasklist.exe","/v", "/FI", "imagename eq javaw.exe", "/FO","list").start();    	    
    	    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(),"CP437"));
    	    while ((line = input.readLine()) != null) {    	        
    	    	if(line.contains("Gestor de"))
    	        {
    	        	String [] splitLine = line.split(":  ");
    	        	int LargoArray = splitLine.length-1;    	        	
    	        	NameAmdocs=splitLine[LargoArray];
    	        	System.out.println(NameAmdocs);
    	        	return NameAmdocs;
    	        }    	       
    	    }
    	    input.close();
    	} catch (Exception err) {
    	    err.printStackTrace();
    	}
    	return NameAmdocs;
    }
    
    public boolean BusquedaPlanExiste(String Plan) throws UnsupportedFlavorException, IOException, FindFailed, InterruptedException
    {
       AutoIt.IniciarAutoIt();
   	   AutoItX Au= AutoIt.getAutoit();	
   	   Au.winActivate(PaginaAmdocs);
   	   
 	   sikuli.ClickImg("PaginaPlanes\\buscarCuenta2.png", 10);
 	   clearText();
 	   Au.controlSend(PaginaAmdocs, "", "", Plan);
 	   sikuli.ClickImg("PaginaPlanes\\btnBuscarCta.png", 10);
 	   Thread.sleep(7000);
 	   
    	
    	int x, y,yUp, xSel, ySel,ySelBack;
    	String PlanAmdocs;
    	boolean Siguiente;
        do
        {
        	
        	System.out.println("Posicion de seleccion");
        	Iterator<Match> it = sikuli.FindAllImgs("PaginaPlanes\\CheckSeleccione.png");
        	
	    	while(it.hasNext()){
	    		   	
	    		   Match plan = it.next();
	    		   x = plan.getX()+10;
		    	   y = plan.getY();	   
		    	   yUp = y+10;
		    	   
		    	   xSel          = x+10;
		    	   ySel          = y-130;	   
		    	   
		    	 		    	   
		    	   PlanAmdocs= sikuli.GetText("", 3, xSel, ySel);
			       if(PlanAmdocs.equalsIgnoreCase(Plan))
			       {  
			    	    return true;
			       }
		    	   
		    	   System.out.println(PlanAmdocs);
	    	}
	    	
	    	 return false;
	    	
        }while(true);
       
    }
    
    public String DiferenciaHora(Date HoraInicio, Date HoraTermino, String Detalle )
    {
    	System.out.println("Esta es la hora de inicio "+HoraInicio+" Esta es la hora de termino "+ HoraTermino + " para " + Detalle);
    	DateFormat dfLocal = new SimpleDateFormat("HH:mm:ss");
    	 
   	    String horaInicio = dfLocal.format(HoraInicio);
	    System.out.println("Hora de inicio de la prueba " + horaInicio);
	    long Inicio = HoraInicio.getTime();
    	String horaTermino = dfLocal.format(HoraTermino);
		System.out.println("Hora de fin de la prueba " + horaTermino);
		long Termino = HoraTermino.getTime();	
		long diferencia = (Termino-Inicio);
		long segundos = TimeUnit.MILLISECONDS.toSeconds(diferencia);
		long minutos  = TimeUnit.MILLISECONDS.toMinutes(diferencia);
		long Hora     = TimeUnit.MILLISECONDS.toHours(diferencia);
		long RestSeg  = segundos - (minutos * 60);
		long RestHora = minutos - (Hora*60);
		String TiempoDuracion=Hora+":"+RestHora+":"+RestSeg;		
		System.out.println(TiempoDuracion);
		
	
    	
    	return TiempoDuracion;
    }
    
}
