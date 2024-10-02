package Business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.tigervnc.rfb.Exception;

public class BDAmdocsPYP {
	
	 private static Connection conn = null;
	    private static String usuario= "chi8oms";
	    private static String contraseña= "chi8oms";
	    private static String url = "jdbc:oracle:thin:@10.35.2.22:1710:Chiomsa1";
	    Business.Operaciones Oper = new Business.Operaciones();

	    
	    public Connection getConnection() throws SQLException
	    {
	    	try {
	            Class.forName("oracle.jdbc.OracleDriver");
	            conn = DriverManager.getConnection(url,usuario,contraseña);
	            if (conn != null) {
	                System.out.println("Conexion Exitosa");
	            }
	        } catch (ClassNotFoundException | SQLException e) 
	        {
	        	System.out.println("Conexion Erronea " + e.getMessage());
	        }
	     	
	    	return conn;
	    }
	    
	    public void CerrarConexion() throws SQLException{
	    	conn.close();
	    }
	    
	    
	    public boolean ConsultaPYD(String OfferId) throws SQLException, IOException{
	    	
	    	Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
	    	
	    	conn = getConnection();
	    	boolean IsEmpty = false;
	    	
	        Statement stmt;
	        ResultSet rs;
	        
	        Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
	        
	        ArrayList<String> PlanesExcel = Excel.GetCountSheet("PS-23997 DNP 5057 V3 12112021 _Ampliación RCS a parrilla de planes", "Planes");
	        ArrayList<String> PlanesBd = new ArrayList<String>(); 
	        
	           
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery("select noffer.name_text as Product_Offering,\r\n" + 
	        		"      ROOT_CID Product_id,\r\n" + 
	        		"      r.pcversion_id,\r\n" + 
	        		"      dOffer.from_date PO_Sales_Effective_Date,\r\n" + 
	        		"      dOffer.until_date PO_Sales_Effective_End_Date,\r\n" + 
	        		"      nParent.name_text as Parent,\r\n" + 
	        		"      n.name_text as Billing_Offer,n.CID billing_offer_id,\r\n" + 
	        		"       case\r\n" + 
	        		"          when (minimum_quantity = 1) then 'Mandatory' \r\n" + 
	        		"          when (minimum_quantity = 0 and maximum_quantity >= 1 and default_quantity = 0) then 'Optional not selected by default'\r\n" + 
	        		"          when (minimum_quantity = 0 and maximum_quantity >= 1 and default_quantity = 1) then 'Optional selected by default'\r\n" + 
	        		"          when (minimum_quantity = 0 and maximum_quantity = 0 and default_quantity = 0) then 'Removed from offer'\r\n" + 
	        		"      end as BO_Behaviour, \r\n" + 
	        		"      decode(r.is_enabled,0,'No','Yes') as Is_Enable_for_Selection_by_CSR,\r\n" + 
	        		"      d.FROM_DATE as BO_Sales_Effective_Date,\r\n" + 
	        		"      d.until_date as BO_Sales_Effective_End_Date,\r\n" + 
	        		"      pp.promote_duration,\r\n" + 
	        		"      pp.promote_interval,\r\n" + 
	        		"      pp.cut_date,\r\n" + 
	        		"      pp.is_promote\r\n" + 
	        		"from tbrelation r, tbname n, tbcatalog_item it_offer, tbname nOffer, tbdate d, tbname nParent, tbdate dOffer, tbprice_plan pp\r\n" + 
	        		"where r.child_id = n.cid \r\n" + 
	        		"and n.cid = pp.cid\r\n" + 
	        		"and noffer.cid IN (select cid \r\n" + 
	        		"from tbname  n\r\n" + 
	        		"where 1=1\r\n" + 
	        		"and n.pcversion_id in (select max(pcversion_id) from tbname where cid = n.cid)\r\n" + 
	        		"and language ='ES'\r\n" + 
	        		") \r\n" + 
	        		"and n.CID IN ('"+OfferId+"')\r\n" + 
	        		"and n.pcversion_id = pp.pcversion_id \r\n" + 
	        		"and r.is_deleted = '0'\r\n" + 
	        		"and r.root_cid = it_offer.cid\r\n" + 
	        		"and r.pcversion_id = it_offer.pcversion_id\r\n" + 
	        		"and it_offer.pcversion_id = (select max(pcversion_id) from tbcatalog_item where it_offer.cid = cid)\r\n" + 
	        		"and it_offer.is_deleted = '0'\r\n" + 
	        		"and it_offer.item_type = 'OF'\r\n" + 
	        		"and r.child_item_type = 'PP'\r\n" + 
	        		"and n.language = 'EN'\r\n" + 
	        		"and it_offer.cid =nOffer.cid\r\n" + 
	        		"and it_offer.pcversion_id = noffer.pcversion_id\r\n" + 
	        		"and nOffer.language = 'EN'\r\n" + 
	        		"and n.cid = d.cid\r\n" + 
	        		"and n.pcversion_id = d.pcversion_id\r\n" + 
	        		"and d.context = 'SL' \r\n" + 
	        		"and r.parent_id = nParent.cid\r\n" + 
	        		"and nParent.pcversion_id = (select max(pcversion_id) from tbcatalog_item where nParent.cid = cid)\r\n" + 
	        		"and nParent.language = 'EN'\r\n" + 
	        		"and it_offer.cid = dOffer.cid\r\n" + 
	        		"and it_offer.pcversion_id = doffer.pcversion_id\r\n" + 
	        		"and dOffer.context = 'SL'\r\n" + 
	        		"--AND nParent.name_text LIKE 'Miscellaneous Discount%'\r\n" + 
	        		"Order by 1, 2, 5, 6");
	        System.out.println("Pase por acá");  
	        if(rs.next() == false)
	        	IsEmpty= true;	        
	        else
	        {			
	        	do
	        	{	
	        			PlanesBd.add(rs.getString(1));
	        	}while(rs.next());
	        }
	        
	        CerrarConexion();
	        String Estado;
	        String [] DataExcel={"",""};
	        
	        for(int i=1;i<PlanesExcel.size();i++)
		    {
	        	Estado ="NO Encontrado";
	        	for(int t=0;t<PlanesBd.size();t++)
			    {
		        	if(PlanesExcel.get(i).equalsIgnoreCase(PlanesBd.get(t)))
		        	{
		        		Estado ="Ok";		        		
		        		break;
		        	}
			    }
	        	
	        	DataExcel[0]=PlanesExcel.get(i);
	        	DataExcel[1]=Estado;
	        	Excel.AgregarLineaExcel(Event.NombreExcel, DataExcel); 
		    }
	               
	        return true;
	    }
	    
	   public void ConsultaComparaPlan() throws SQLException, IOException {
		   
		   Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
		   String [] DataExcel={"","",""};
	    	
	    	conn = getConnection();
	    	boolean IsEmpty = false;
	    	String Estado   = "";  	
	        Statement stmt;
	        ResultSet rs=null;
	        
	        Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
	        
	        List<ObjEPC> PlanesEPCList = Excel.LecturaColumnasExcel("Incorporar Nombres comerciales en EPC V2", "NOMBRES COMERCIALES EPC");
	        stmt = conn.createStatement();   
 	
	        for (ObjEPC objEPC : PlanesEPCList) {
	       
		        	rs = stmt.executeQuery("select CID,DESCRIPTION_TEXT\r\n" + 
			        						"from tbdescription\r\n" + 
							        		"where 1=1\r\n" + 
							        		"and CID in('"+objEPC.getOffer_id()+"')\r\n" + 
							        		"and LANGUAGE = 'ES'");
	        	
	        	
	        	if(rs.next() == false)
		        	IsEmpty= true;	        
		        else
		        {	
		        	if (objEPC.getNombre().isEmpty()) {
		        		//return;
		        	}
		        	else if(objEPC.getNombre().equalsIgnoreCase(rs.getString(2))) {
		        		Estado = "Iguales";}
		        	else {
		        		Estado = "Distintos";}

		        	DataExcel[0]=objEPC.getNombre();
		        	DataExcel[1]=rs.getString(2);
		        	DataExcel[2]=Estado;
		        }
	        	if (!objEPC.getNombre().isEmpty()) 
	        		Excel.AgregarLineaExcel(Event.NombreExcel, DataExcel); 
	        }
	        
	        CerrarConexion();
	   }
	   
   public void ConsultaComparaDescripcion() throws SQLException, IOException {
		   
		   Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
		   String [] DataExcel={"","",""};
	    	
	    	conn = getConnection();
	    	boolean IsEmpty = false;
	    	String Estado   = "";  	
	        Statement stmt;
	        ResultSet rs=null;
	        
	        Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
	        
	        List<ObjEPC> PlanesEPCList = Excel.LecturaColumnasExcel("Incorporar Nombres comerciales en EPC V2", "NOMBRES COMERCIALES EPC");
	        stmt = conn.createStatement();   
 	
	        for (ObjEPC objEPC : PlanesEPCList) {
	       
	        	
		        	rs = stmt.executeQuery("select A.CID, B.DESCRIPTION_TEXT \r\n" + 
		        			"from tbname a, tbdescription b\r\n" + 
		        			"where 1=1 and a.CID in ('"+objEPC.getOffer_id()+"')\r\n" + 
		        			"and A.CID = B.CID and A.PCVERSION_ID =\r\n" + 
		        			"(select max(pcversion_id) from tbname where A.CID = CID and pcversion_id <= \r\n" + 
		        			"(select max(pcversion_id) from tbpc_version\r\n" + 
		        			"where status in ('PR')))\r\n" + 
		        			"and A.PCVERSION_ID = B.PCVERSION_ID\r\n" + 
		        			"and A.LANGUAGE = 'ES' and B.LANGUAGE = 'ES'");
	        	
	        	
	        	if(rs.next() == false)
		        	IsEmpty= true;	        
		        else
		        {	
		        	if (objEPC.getNombre().isEmpty()) {
		        		//return;
		        	}
		        	else if(objEPC.getNombre().equalsIgnoreCase(rs.getString(2))) {
		        		Estado = "Iguales";}
		        	else {
		        		Estado = "Distintos";}

		        	DataExcel[0]=objEPC.getNombre();
		        	DataExcel[1]=rs.getString(2);
		        	DataExcel[2]=Estado;
		        }
	        	if (!objEPC.getNombre().isEmpty()) 
	        		Excel.AgregarLineaExcel(Event.NombreExcel, DataExcel); 
	        }
	        
	        CerrarConexion();
	   }
   
   public void ConsultaComparaOtros() throws SQLException, IOException {
	   
	   Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();
	   String [] DataExcel={"","",""};
    	
    	conn = getConnection();
    	boolean IsEmpty = false;
    	String Estado   = "Estado";  	
        Statement stmt;
        ResultSet rs=null;
        String TextSinTildeExc = "";
        String TextSinTildeBd = "";
        
        Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel();
        
        List<ObjEPC> PlanesEPCList = Excel.LecturaColumnasExcelOtros("Cambio Descripcion AUT", "Hoja1");
        DataExcel[0]="Dato Excel"; //objEPC.getNombre();
    	DataExcel[1]="Dato Base de datos"; //rs.getString(2);
    	DataExcel[2]=Estado;
    	Excel.AgregarLineaExcel(Event.NombreExcel, DataExcel); 
    	
        stmt = conn.createStatement();   
	
        for (ObjEPC objEPC : PlanesEPCList) {
    
	        	rs = stmt.executeQuery("select A.CID, B.DESCRIPTION_TEXT \r\n" + 
	        			"from tbname a, tbdescription b\r\n" + 
	        			"where 1=1 and a.CID in ('"+objEPC.getOffer_id()+"')\r\n" + 
	        			"and A.CID = B.CID and A.PCVERSION_ID =\r\n" + 
	        			"(select max(pcversion_id) from tbname where A.CID = CID and pcversion_id <= \r\n" + 
	        			"(select max(pcversion_id) from tbpc_version\r\n" + 
	        			"where status in ('PR')))\r\n" + 
	        			"and A.PCVERSION_ID = B.PCVERSION_ID\r\n" + 
	        			"and A.LANGUAGE = 'ES' and B.LANGUAGE = 'ES'");
        	 
	        	
        	
        	if(rs.next() == false)
	        	IsEmpty= true;	        
	        else
	        {	
	        	
	        	if (!objEPC.getNombre().isEmpty()) {
	        		
	        		TextSinTildeExc = Oper.limpiaTildes(objEPC.getNombre());
	        		TextSinTildeBd = Oper.limpiaTildes(rs.getString(2));
//	        		System.out.println("nomb Exce :"+TextSinTildeExc);
//	        		System.out.println("nomb bd   :"+TextSinTildeBd);
	        	}
	        	
	        	
	        	if (objEPC.getNombre().isEmpty()) {
	        		//return;
	        	}
	        	else if(TextSinTildeExc.equalsIgnoreCase(TextSinTildeBd)) {
	        		Estado = "Iguales";}
	        	else {
	        		Estado = "Distintos";}

	        	DataExcel[0]=TextSinTildeExc; //objEPC.getNombre();
	        	DataExcel[1]=TextSinTildeBd; //rs.getString(2);
	        	DataExcel[2]=Estado;
	    
	        }
        	if (!objEPC.getNombre().isEmpty()) 
        		Excel.AgregarLineaExcel(Event.NombreExcel, DataExcel); 
        }
        
        CerrarConexion();
   }
   
   
}

