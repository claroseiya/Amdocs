package Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tigervnc.rfb.Exception;

public class BDAmdocs {
	
	 private static Connection conn = null;
	    private static String usuario= "chi4oms";
	    private static String contraseña= "chi4oms";
	    private static String url = "jdbc:oracle:thin:@10.35.2.22:1710:Chiomsa1";
	    
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
	    
	    
	    public boolean ConsultaPYD(String Cel) throws SQLException{
	        
	    	ArrayList<String> Venta = new ArrayList<String>();
	    	ArrayList<String> CambioPlan = new ArrayList<String>();
	    	ArrayList<String> CambioPlanPaso = new ArrayList<String>();
	    	String [][] MatrizCambioPlan     = new String[100][11];
	    	String [][] MatrizCambioPlanPaso = new String[100][11];;
	    	int Indice=0;
	    	
	    	ArrayList<String> VentaPYD = new ArrayList<String>();
	    	ArrayList<String> CambioPlanPYD = new ArrayList<String>();
	    	String PYDVentaConca="";
	    	String PYDCambioPlanConca="";
	    	String PYDCambioPlanConcaCancelado="";
	    	
	    	
	    	String BanderaOrden;
	    	String BanderaOrdencomparar;
	    	
	    	conn = getConnection();
	    	boolean IsEmpty = false;
	    	
	        Statement stmt;
	        ResultSet rs;
	        
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery("select AP_ID , tba.main_item_id, TBA.ctdb_last_updator, tba.CTDB_UPD_DATETIME, ORDER_ACTION_ID,  AP_VERSION_ID,END_DATE, STATE,STATUS, NAME_TEXT, tba.item_def_id, name.cid, item_def_ver, name.pcversion_id\r\n" + 
	        		"from tbap_price_plan tba, tbname name\r\n" + 
	        		"where main_item_id \r\n" + 
	        		"in ( select distinct main_item_id from tbap_item \r\n" + 
	        		"where end_date > sysdate and status != 'CE' and state = 'AS' and service_id  in ('"+Cel+"'))\r\n" + 
	        		"and tba.item_def_id = name.cid and item_def_ver = name.pcversion_id and language = 'ES'\r\n" + 
	        		"--and state = 'CA' \r\n" + 
	        		"--and status != 'CE' \r\n" + 
	        		"--and end_date > sysdate\r\n" + 
	        		"--and state != 'CA' \r\n" + 
	        		"--and item_def_id  in ('21838747')\r\n" + 
	        		"--and order_action_id  in ('246473') \r\n" + 
	        		"AND (NAME_TEXT LIKE 'PYD%' OR NAME_TEXT LIKE 'RP%')\r\n" + 
	        		"--and ap_id = '4267334'\r\n" + 
	        		"--and ap_version_id = '2'\r\n" + 
	        		"--and status = 'AC'\r\n" + 
	        		"--order by \r\n" + 
	        		"--TO_NUMBER(order_action_id), TO_NUMBER(ap_id), TO_NUMBER(ap_version_id)\r\n" + 
	        		"--name_text desc\r\n" + 
	        		"order by ORDER_ACTION_ID,STATUS, NAME_TEXT  asc");
	        System.out.println("Pase por acá");  
	        if(rs.next() == false)
	        	IsEmpty= true;	        
	        else
	        {	
	        	
	        	BanderaOrden=rs.getString(5);	        	
	        	BanderaOrdencomparar=BanderaOrden;
	        	int Bandera = 0;
	        	int BanderaCambioPlan=0;
	        	int BanderaCp =0;	        	
	        		
	        	do
	        	{
	        		BanderaOrdencomparar = rs.getString(5);
	        		
	        		if(BanderaOrden.equalsIgnoreCase(BanderaOrdencomparar) && Bandera== 0)
	        		{
	        			Venta.add(rs.getString(1));
	        			Venta.add(rs.getString(2));
	        			Venta.add(rs.getString(3));
	        			Venta.add(rs.getString(4));
	        			Venta.add(rs.getString(5));
	        			Venta.add(rs.getString(6));
	        			Venta.add(rs.getString(7));
	        			Venta.add(rs.getString(8));
	        			Venta.add(rs.getString(9));
	        			Venta.add(rs.getString(10));
	        		} 
	        		
	        		else if(!BanderaOrden.equalsIgnoreCase(BanderaOrdencomparar) && BanderaCp == 0) {
	        			
	        			BanderaCp = 1;
	        			Bandera=1;
	        			BanderaCambioPlan=0;
	        			CambioPlanPaso.clear();
	        			Indice=0;
	        			try
	        			{	
	        				MatrizCambioPlanPaso[0][0]="Cambio plan";	        				        			
	        				MatrizCambioPlanPaso[Indice][1]=rs.getString(1);
		        			MatrizCambioPlanPaso[Indice][2]=rs.getString(2);
		        			MatrizCambioPlanPaso[Indice][3]=rs.getString(3);
		        			MatrizCambioPlanPaso[Indice][4]=rs.getString(4);
		        			MatrizCambioPlanPaso[Indice][5]=rs.getString(5);
		        			MatrizCambioPlanPaso[Indice][6]=rs.getString(6);
		        			MatrizCambioPlanPaso[Indice][7]=rs.getString(7);
		        			MatrizCambioPlanPaso[Indice][8]=rs.getString(8);
		        			MatrizCambioPlanPaso[Indice][9]=rs.getString(9);
		        			MatrizCambioPlanPaso[Indice][10]=rs.getString(10);
		        			
		        			
	        			}
	        			catch(Exception e)
	        			{	System.out.println("Que pasa?????");
	        				//System.out.println(e);
	        			
	        			}
	        			
	        			CambioPlanPaso.add(rs.getString(1));
	        			CambioPlanPaso.add(rs.getString(2));
	        			CambioPlanPaso.add(rs.getString(3));
	        			CambioPlanPaso.add(rs.getString(4));
	        			CambioPlanPaso.add(rs.getString(5));
	        			CambioPlanPaso.add(rs.getString(6));
	        			CambioPlanPaso.add(rs.getString(7));
	        			CambioPlanPaso.add(rs.getString(8));
	        			CambioPlanPaso.add(rs.getString(9));
	        			CambioPlanPaso.add(rs.getString(10));
	        			if(rs.getString(10).contains("RP"))
	        				BanderaCambioPlan++;	        			
	        			BanderaOrden = BanderaOrdencomparar;
	        			
	        		}
	        		
	        		else if(BanderaOrden.equalsIgnoreCase(BanderaOrdencomparar) && Bandera == 1) {
	        			
	        			MatrizCambioPlanPaso[Indice][0]="Cambio plan";
	        			MatrizCambioPlanPaso[Indice][1]=rs.getString(1);
	        			MatrizCambioPlanPaso[Indice][2]=rs.getString(2);
	        			MatrizCambioPlanPaso[Indice][3]=rs.getString(3);
	        			MatrizCambioPlanPaso[Indice][4]=rs.getString(4);
	        			MatrizCambioPlanPaso[Indice][5]=rs.getString(5);
	        			MatrizCambioPlanPaso[Indice][6]=rs.getString(6);
	        			MatrizCambioPlanPaso[Indice][7]=rs.getString(7);
	        			MatrizCambioPlanPaso[Indice][8]=rs.getString(8);
	        			MatrizCambioPlanPaso[Indice][9]=rs.getString(9);
	        			MatrizCambioPlanPaso[Indice][10]=rs.getString(10);
	        			
	        			CambioPlanPaso.add(rs.getString(1));
	        			CambioPlanPaso.add(rs.getString(2));
	        			CambioPlanPaso.add(rs.getString(3));
	        			CambioPlanPaso.add(rs.getString(4));
	        			CambioPlanPaso.add(rs.getString(5));
	        			CambioPlanPaso.add(rs.getString(6));
	        			CambioPlanPaso.add(rs.getString(7));
	        			CambioPlanPaso.add(rs.getString(8));
	        			CambioPlanPaso.add(rs.getString(9));
	        			CambioPlanPaso.add(rs.getString(10));
	        			if(rs.getString(10).contains("RP"))
	        				BanderaCambioPlan++;
	        			BanderaCp = 0;
	        		}
	        		
	        		if(BanderaCambioPlan==2)
	        		{
	        			MatrizCambioPlan=MatrizCambioPlanPaso;
	        			
	        			System.out.println("Este es el cambio de plan"+rs.getString(5));
	        			
	        			CambioPlan = (ArrayList)CambioPlanPaso.clone();
	        			BanderaCambioPlan=0;
	        		}
	        		
	        	Indice++;
	        	}while(rs.next());
	        }	
	        
	        System.out.println("Termina el while");
	        
	        
	        for(int i=0;i<MatrizCambioPlan.length;i++)
	        {
	        	System.out.println(MatrizCambioPlanPaso[i][10]);
	        	if(MatrizCambioPlanPaso[i][10]==null)
                {}
	        	
	        	else if(MatrizCambioPlanPaso[i][10].contains("PYD"))
	        	{	
	        		if(MatrizCambioPlanPaso[i][9].contains("AC"))
	        			PYDCambioPlanConca=MatrizCambioPlan[i][10]+"\n"+PYDCambioPlanConca;
	        		else
	        			PYDCambioPlanConcaCancelado=MatrizCambioPlan[i][10]+"\n"+PYDCambioPlanConcaCancelado;
	        	}	
	        }
	        
	        for(int t=0;t<Venta.size();t++)
	        {
	        	if(Venta.get(t).contains("PYD"))
	        	{
	        		VentaPYD.add(Venta.get(t));
	        		PYDVentaConca=Venta.get(t)+"\n"+PYDVentaConca;
	        	}	        
	        }
	        
	        Amdocs.DataAmdocs[1]=Cel;	        
	        Amdocs.DataAmdocs[2]=PYDVentaConca;
	        Amdocs.DataAmdocs[3]=PYDCambioPlanConca;
	        Amdocs.DataAmdocs[4]=PYDCambioPlanConcaCancelado;
	        
	        CerrarConexion();
	        return true;
	    }
	    
	    
	   
}
