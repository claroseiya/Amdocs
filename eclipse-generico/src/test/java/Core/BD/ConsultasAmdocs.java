package Core.BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasAmdocs extends Conexion{

		public static ResultSet ejecutarQuery(String query) {
			Connection conexion = getConexion();
			ResultSet rs = null;
		    try {
		
		        Statement stmt = conexion.createStatement();
		         rs = stmt.executeQuery(query);

		        // conexion.close();
		    } catch (SQLException sqe) {}
		    
		    return rs;
		}

		public static ResultSet insertaEjec(String id, String nomMaquina, String nomProyecto, String jira, String Ambiente, String fecha, String hora) throws SQLException{
		   ResultSet res = null;
		   
		   //Si alguna ejecucion no finalizo correctamente esta funcion la cierra con mensaje de Error.
		   cierraError(nomMaquina);
		
		   String query = "insert into procesoAmdocs (id_ejec, nomMaquina, nomProyecto, jira,  ambiente, fecha, estado, hora) "
		   		+ "values ('"+ id +"','"+ nomMaquina +"','"+  nomProyecto +"','"+ jira +"','"+ Ambiente +"','"+fecha +"','En proceso','"+ hora +"')";

		   res = ejecutarQuery(query);
		   return res;
		}

		public static ResultSet actualizaEjec(String id, String nomMaquina, String fecha, String hora) throws SQLException{
		    ResultSet res = null;

		   String query = "update procesoAmdocs "+
				   		  "set id_ejec = '" + id + "'" +
				   		" where nomMaquina = '"+ nomMaquina + "' " +
				   		" and fecha = '" + fecha + "' " +
				   		" and hora = '" + hora + "' " +
				   		" and estado = 'En proceso' ";

		   res = ejecutarQuery(query);
		   return res;
		}
		
		public static ResultSet finalizaEjec(String id, String nomMaquina, String fecha, String hora) throws SQLException{
		   ResultSet res = null;

		   String query = "update procesoAmdocs "+
				   		  "set id_ejec = '" + id + "', estado = 'Finalizado' " +
				   		" where nomMaquina = '"+ nomMaquina + "' " +
				   		" and fecha = '" + fecha + "' " +
				   		" and hora = '" + hora + "' " +
				   		" and estado = 'En proceso' ";
  
		   res = ejecutarQuery(query);
		   return res;
		}
		
		public static ResultSet cierraError(String nomMaquina) {
			ResultSet res = null;
			
			String querys = "update procesoAmdocs " + 
			   		"set estado = 'Error' " + 
			   		" where nomMaquina = '"+ nomMaquina + "' " +
			   		" and estado = 'En proceso' ";
			
			 res = ejecutarQuery(querys);
			 return res;

		}
		
		public static ResultSet insertaResultados(String Datos[], String nombreMaquina, String fechaSql, String HoraComienzoSql,String  NombreProyecto, String Jira, String Caso) {
			ResultSet res = null;
			
			String id	= "";
			String rut	= "";
			String cbp  = "";
			String fa   = "";
			String ciclo	= "";
			String estado	= "";
			String orden	= "";
			String nombrePlan	= "";
			String telefono	= "";
			String sim	= "";
			String imsi = "";
			String estadoTrx   = "";
			String HoraTotalTermino = "";
			String HoraTermino = "";
			String HoraErrores = "";
			
			String codTrx = Jira+"_"+fechaSql+"_"+HoraComienzoSql;
 
            for (int i = 0 ; i < Datos.length ; i++) {
               switch(i)
		       {
					case 0:   id			= Datos[i];		break;
		       		case 1:   rut			= Datos[i];		break;
		       		case 2:   cbp			= Datos[i];		break;
		       		case 3:   fa			= Datos[i];		break;
		       		case 4:   ciclo			= Datos[i];		break;
		       		case 5:   estado		= Datos[i];		break;
		       		case 6:   orden			= Datos[i];		break;
		       		case 7:   nombrePlan	= Datos[i];		break;
		       		case 8:   telefono		= Datos[i];		break;
		       		case 9:   sim			= Datos[i];		break;
		       		case 10:  imsi			= Datos[i];		break;
		       		case 11:  estadoTrx		= Datos[i];		break;
		       		case 12:  HoraTotalTermino	= Datos[i];		break;
		       		case 13:  HoraTermino	= Datos[i];		break;
		       		case 14:  HoraErrores	= Datos[i];		break;
		       		
		       		
		        }
                         
            }

            String querys = "insert into resultadosAmdocs " + 
      			 		"(codTrx,nomMaquina,fecha,hora,nomProyecto,jira,id,rut,cbp,fa,ciclo,"+
      			 		"estado,orden,nombrePlan,telefono,sim,imsi,estadoTrx,DuracionPruebaTotal, caso, DuracionPrueba, DuracionErrores)"+
      			 		" values ('"+ codTrx +"','"+ nombreMaquina	+"','"+ fechaSql +"','"+ HoraComienzoSql +"','"+ NombreProyecto +"','"+
      			 		Jira + "','"+ id +"','"+ rut +"','"+ cbp +"','"+ fa +"','"+ ciclo +"','"+ estado +"','"+ orden +"','"+ 
      			 		nombrePlan +"','"+ telefono +"','"+ sim +"','"+ imsi +"','"+ estadoTrx +"','"+ HoraTotalTermino +"','" + Caso + "','" 
      			 		+HoraTermino +"','"+ HoraErrores  +"')";
    	
       	 res = ejecutarQuery(querys);
		 return res;
			
		}
		
}