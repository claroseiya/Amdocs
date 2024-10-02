package Core.BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*
 * Ejemplo de conexión a Oracle
 *  
 *  */
 

public class ConexionOracle {
	
	 private static Connection conn = null;
	    private static String usuario= "SUCVIRUSERS";
	    private static String contraseña= "SUCVIRUSERS";
	    private static String url = "jdbc:oracle:thin:@10.38.14.134:1545:SVUSERTS";
	    
	    public static Connection getConnection() throws SQLException{
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
	        
	        Statement stmt;
	        ResultSet rs;
	        
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery("select * \r\n" + 
	        		"from portal_user\r\n" + 
	        		"where 1=1\r\n" + 
	        		"and PUSR_RUT in ('14365137-1')");
	            
	        	while(rs.next())
	        	{
	        		System.out.println("encuentra algo?");
	        		System.out.println(rs.getString(1));
	        		System.out.println(rs.getString(2));
	        		System.out.println(rs.getString(3));
	        		System.out.println(rs.getString(4));
	        		System.out.println(rs.getString(5));
	        		System.out.println(rs.getString(6));
	        		System.out.println(rs.getString(7));
		        	System.out.println(rs.getString(8));
		   
	        	}
	        	
	        
	        return conn;
	    }

}
