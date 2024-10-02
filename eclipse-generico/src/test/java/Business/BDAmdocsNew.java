package Business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BDAmdocsNew {
	
		private static Connection conn = null;
	    private static String usuario= "sa";
	    private static String contraseña= "sa";
	    private static String url = "jdbc:oracle:thin:@10.35.2.22:1710:chicrm4";
	    
	    Business.Operaciones Oper = new Business.Operaciones();
	    Core.Ini.LeerData Ini = new Core.Ini.LeerData();
	    Business.Reporte Rep = new Business.Reporte();

    
	    public Connection getConnection() throws SQLException, IOException
	    {   
	    	int Ambiente = Rep.GetAmbiente();
	    	String Usser = Ini.GetValue("BaseDatos", "Ambiente"+Ambiente, "Usuario");
			String Pass  = Ini.GetValue("BaseDatos", "Ambiente"+Ambiente, "Password");
			String Ruta="";
	    	
			if(Ambiente == 4)
				url = "jdbc:oracle:thin:@10.35.2.22:1710:chicrm4";
			else if(Ambiente == 8)
				url = "jdbc:oracle:thin:@10.35.2.22:1710:chicrm8";
			else if(Ambiente == 10)
				url = "jdbc:oracle:thin:@10.35.2.22:1710:chicrm10";
			
			Ruta=url;
			
	    	try {
	            Class.forName("oracle.jdbc.OracleDriver");
	            conn = DriverManager.getConnection(Ruta,Usser,Pass);
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
	    
	    public String ConsultaORG (int Ambiente, String Rut) throws SQLException, IOException
	    {
	    	
	    	//Business.EventHandlerCucumber Event = new Business.EventHandlerCucumber();

	    	conn = getConnection();
	        Statement stmt;
	        ResultSet rs;
	        boolean IsEmpty = false;
	        //Core.Excel.AccionesExcel Excel = new Core.Excel.AccionesExcel(); 
	        
	    	String OrgId="";
    	
	    	System.out.println("RUT a Consultar: "+Rut);
	    	/*
	    	 * Try catch es para revisar el por error en la query
	    	 * */
	    	try {
	    	stmt = conn.createStatement();
	    	rs = stmt.executeQuery("SELECT X_RCIS_ID\r\n" + 
	    			"FROM TABLE_CONTACT\r\n" + 
	    			"WHERE X_MAIN_IDENT_VALUE IN ('"+Rut+"')");
	    	/*
	    	 * next es para recorrer el contenido de la query 
	    	 * */
	    	if(rs.next() == false)
	        	IsEmpty= true;	        
	        
	    	
	    	OrgId = rs.getString(1);
	    	System.out.println("Id de Organizacion: "+OrgId);
	    	}catch(Exception e)
	    	{
	    		System.out.println("Error de Base :"+e);
	    	}
	    	finally {
	    	CerrarConexion();
	    	}
	    	

	    	return OrgId;
	    	
	    }
	  
}
