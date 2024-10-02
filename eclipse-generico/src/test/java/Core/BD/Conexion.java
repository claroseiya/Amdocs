package Core.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import Core.Ini.LeerData;



public class Conexion {
	//static Logger log = Logger.getLogger(Conexion.class);
	static Connection contacto = null;
	private static LeerData DataIni = new LeerData();
	static String usuario = DataIni.GetValue("Conexion", "BD", "usuario");
	static String password = DataIni.GetValue("Conexion", "BD", "password");
	static String baseDatos = DataIni.GetValue("Conexion", "BD", "baseDatos");
	public static boolean status = false;
    
	
    public static Connection getConexion(){
        status = false;
        String url = "jdbc:sqlserver://Claro800:1433;databaseName="+baseDatos;
        try {
            	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	            
        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver" + e.getMessage(),
                    "Error de Conexion ",JOptionPane.ERROR_MESSAGE);
        }
        try{
            contacto = DriverManager.getConnection(url, Conexion.usuario, Conexion.password);
            //log.info("Conexion exitosa - log!!");
            status = true;
        }catch (SQLException e){
        	 //log.info("Conexion Erronea!!");
        	System.out.println("Conexion a la base de datos Erronea!!");
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
                    "Error de Conexion ",JOptionPane.ERROR_MESSAGE);
        }
        return contacto;
    }    


}
