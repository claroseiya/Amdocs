package Core.DataSource;

import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import javax.swing.JOptionPane;
        import java.sql.Statement;

public class SqlServer {

    static Connection contacto = null;
    public static String usuario = "RobotQa";
    public static String password = "Reset.0316";
    public static boolean status = false;

    public static Connection getConexion(){
        status = false;
        String url = "jdbc:sqlserver://Claro800:1433;databaseName=Robot";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver" + e.getMessage(),
                    "Error de Conexion",JOptionPane.ERROR_MESSAGE);
        }
        try{
            contacto = DriverManager.getConnection(url, SqlServer.usuario, SqlServer.password);
            status = true;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
                    "Error de Conexion",JOptionPane.ERROR_MESSAGE);
        }
        return contacto;
    }


    public static void setcuenta(String usuario, String password){
        SqlServer.usuario = usuario;
        SqlServer.password = password;
    }

    public static boolean getstatus(){
        return  status;
    }

    public static ResultSet Consulta(String consulta){
        Connection con = getConexion();
        Statement declara;
        try{
            declara=con.createStatement();
            ResultSet respuesta = declara.executeQuery(consulta);
            return respuesta;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
                    "Error de Conexion",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}
