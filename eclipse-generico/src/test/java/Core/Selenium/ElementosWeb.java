package Core.Selenium;

/**
*
* @author Cristophe Carlier
* Esta es una clase con funciones temporales para devolver los objetos web a solicitud, se debe extraer de un excel.
*/
public class ElementosWeb {
	
    /**
    *
    * Contructor e inicializador de la clase ElementosWeb.
    */
	public ElementosWeb() {
	}
	
    /**
    *
    * Busqueda del elemento indicado con xpath.
    * @param Clave La clave del elemento al cual debemos devolver la ruta xpath, devuleve la misma clave en caso de no encontrarse (para testeo de xpath en bruto).
    */
	public String RutaElemento(String Clave) {
		String Ruta = "";
		
		switch(Clave) {
			case "alquiler_usuario" 	: Ruta = "//*[@id='loginUsername']"; break; 
			case "alquiler_clave" 		: Ruta = "//*[@id='loginPassword']"; break;
			case "alquiler_ingresar"	: Ruta = "//*[@id='submit-login']"; break; 
			case "nuevo_alquiler" 		: Ruta = "//*[@id='menu-state-TransactionAddState']"; break; 
			case "nombre_cliente" 		: Ruta = "/html/body/div[1]/div[5]/div[3]/div/div[5]/div/div[2]/div/div[1]/div[1]/div[2]/div/div/div[1]/div/div[2]/input"; break; 
			case "duracion_cotizacion" 	: Ruta = "//*[@id='Duration']"; break; 
			case "crear_cotizacion" 	: Ruta = "//*[@id='main-wrapper']/div[3]/div/div[5]/div/div[4]/div/div[2]/div[3]/div/span[1]/span[4]/button"; break; 
			case "busqueda_sku" 		: Ruta = "//*[@id='ItemSearch']"; break; 
			case "cantidad_item" 		: Ruta = "//*[@id='Quantity']"; break; 
			case "boton_articulos" 		: Ruta = "//*[@id='dgrid_7-row-22874']/div[2]/div[2]/button"; break; 
			case "placas_definir" 		: Ruta = "//*[@id='main-wrapper']/div[3]/div/div[5]/div[3]/div/div[2]/div[5]/div[3]/div/div/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/div/div"; break; 
			case "1ra_placa"			: Ruta = "//*[@id='main-wrapper']/div[3]/div/div[5]/div[3]/div/div[2]/div[5]/div[3]/div/div/div[2]/div[1]/div[2]/div[3]/div[2]/div[2]/table/tbody/tr/td[3]/div[2]/div/div[1]"; break; 
			case "2da_placa"			: Ruta = "//*[@id='main-wrapper']/div[3]/div/div[5]/div[3]/div/div[2]/div[5]/div[3]/div/div/div[2]/div[1]/div[2]/div[3]/div[2]/div[2]/table/tbody/tr/td[3]/div[2]/div"; break;
			case "seleccionar_reserva"	: Ruta = "//*[@id='main-wrapper']/div[3]/div/div[5]/div[3]/div/div[1]/div[3]";
			default						: Ruta = Clave; 
		}
		
		return Ruta;
	}
	
    /**
    *
    * Busqueda del elemento indicado con ruta css.
    * @param Clave La clave del elemento al cual debemos devolver la ruta css, devuleve la misma clave en caso de no encontrarse (para testeo de ruta css en bruto).
    */
	public String CssElemento(String Clave) {
		String Ruta = "";
		
		switch(Clave) {
			case "seleccion_cliente" : Ruta = "#dgrid_5-row-326 > div > h4 > a"; break; 
		}
		
		return Ruta;
	}	
}