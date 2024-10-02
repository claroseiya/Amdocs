package Core.Excel;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Business.ObjEPC;
import Business.ObjExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccionesExcel {
	public boolean ExisteExcel(String NombreArchivo)
	{
		
	    File RutaRaiz = new File("Reportes//Excel"+ "//" + NombreArchivo + ".xlsx");
        File CarpetaRaiz = (RutaRaiz);
        if (CarpetaRaiz.exists())
        	return true;
        return false;
	}
	
    public void CrearNuevoExcel(String NombreArchivo, String NombreHoja, String[] Datos) {
    	// Iniciamos la creacion de un archivo excel.
    	System.out.println("Iniciamos la creacion del archivo: " + NombreArchivo);
    	
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	String CarpetaUsuario = System.getProperty("user.home");
    	
    	try {
	        // Definimos la ruta para ambas carpetas, raiz y la de capturas.
	        //String RutaRaiz = CarpetaUsuario + "//reportes//Excel";
    		//String RutaRaiz = "//reportes//Excel";
	        //String RutaExcel = RutaRaiz + "//" + NombreArchivo + ".xlsx";
	        
	        File RutaRaiz = new File("Reportes//Excel");
	        String RutaExcel = RutaRaiz + "//" + NombreArchivo + ".xlsx";
	        
	        // Creamos la carpeta raiz si esta no existe.
	        File CarpetaRaiz = (RutaRaiz);
	        if (!CarpetaRaiz.exists())
	       	CarpetaRaiz.mkdir();
	        	        
	        // Creamos un nuevo libro con una nueva hoja con los nombres indicados.
	        XSSFWorkbook LibroExcel = new XSSFWorkbook();
	        XSSFSheet HojaExcel = LibroExcel.createSheet(NombreHoja);
	        
	        // Creamos la cabecera e iniciamos un correlativo para la columna.
	        Row FilaExcel = HojaExcel.createRow(0);
	      
	        
	        int NumColumna = 0;
	
	        // LLenamos la primera columna con el correlativo siguiente a la fila anterior. 
	        Cell CeldaExcel = FilaExcel.createCell(NumColumna);
	        CeldaExcel.setCellValue(0);
	        
	        // Configuramos estilo para color de fondo negro.
	        CellStyle EstiloCelda = LibroExcel.createCellStyle(); 
	        EstiloCelda.setFillForegroundColor(IndexedColors.BLACK.getIndex()); 
	        EstiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        
	        // Configuramos estilo para color de fuente.
	        Font Fuente = LibroExcel.createFont();
	        Fuente.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
	        EstiloCelda.setFont(Fuente);
	        
	        //Prueba del colspan
	        int ColspanInicio= 0;
	        int ColspanFin   = 0;
	        
	        
	        // Inicamos el llenado de cada columna en la cabecera.
	        for (String Dato : Datos) {
	        	
	        	
	        	String DatoOk = Dato.replaceAll("((\\s+)([a-zA-Z=]+)(\\d{1,4}))", "");
	        	//CeldaExcel = FilaExcel.createCell(NumColumna++);
	        	CeldaExcel = FilaExcel.createCell(ColspanInicio);
	        	CeldaExcel.setCellValue(DatoOk);
	        	CeldaExcel.setCellStyle(EstiloCelda);
	        	
	        	if(Dato.contains("Colspan"))
	        	{
	        		int NumColspan=0;
	        		String NameColspan="";
	        		Pattern pattern = Pattern.compile("(([a-zA-Z=]+)(\\d{1,4}))");
					Matcher matcher = pattern.matcher(Dato);
				    
				   
					if(matcher.find())
				   {
						 NameColspan = matcher.group(1);
						 
						 
						 Pattern pattern2 = Pattern.compile("(\\d{1,4})");
						 Matcher matcher2 = pattern2.matcher(NameColspan);
						 
						 if(matcher2.find())
						 {
							 NumColspan = Integer.parseInt(matcher2.group(1));
							 NumColspan = NumColspan-1;
						 }
					}					
					
	        		ColspanFin=ColspanInicio+NumColspan;
		        	HojaExcel.addMergedRegion(new CellRangeAddress(0,0,ColspanInicio,ColspanFin));
		        	ColspanInicio=ColspanFin+1;
	        	}
	        	else
	        		ColspanInicio++;
	        }	        
	        
	        // Creamos un archivo de salida para guardar los cambios.
	        FileOutputStream ArchivoSalida = new FileOutputStream(RutaExcel);
	        LibroExcel.write(ArchivoSalida);
	        
	        // Cerramos el archivo de salida y el libro excel.
	        ArchivoSalida.close();
	        LibroExcel.close();
	                
	        // Finalizamos la actualizacion del archivo excel.
	    	System.out.println("Creacion del archivo: " + NombreArchivo + ", finalizado de manera exitosa!");
    	}
    
    	// En caso de algun problema con la actualizacion del excel, arrojamos un error.
        catch (IOException | EncryptedDocumentException ex) {
        	System.err.println("Hubo un error al crear el archivo: " + NombreArchivo);
        	ex.printStackTrace();
        }

    }
    
    public void AgregarLineaExcel(String ArchivoExcel, String Datos[]){
    	// Iniciamos actualizacion de archivo excel.
    	//System.out.println("Iniciamos actualizacion del archivo: " + ArchivoExcel + ".xlsx");
    	
    	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	String CarpetaUsuario = System.getProperty("user.home");
        
        try {
        	// Abrimos el archivo excel indicado desde la carpeta de usuario.
        	//String RutaRaiz = CarpetaUsuario + "//reportes//Excel//" + ArchivoExcel + ".xlsx";
        	
        	File RutaRaiz = new File("Reportes/Excel/"+ArchivoExcel + ".xlsx");
        	FileInputStream ArchivoEntrada = new FileInputStream(RutaRaiz);
            
            // Abrimos el libro y nos posicionamos en la primera hoja de trabajo.
            Workbook LibroExcel = WorkbookFactory.create(ArchivoEntrada);
            Sheet HojaExcel = LibroExcel.getSheetAt(0);            
 
            // Obtenemos el numero de la ultima fila con datos.
            int NumFilaActual = HojaExcel.getLastRowNum();
            int NumNuevaFila = NumFilaActual + 1;
            
            // Creamos una fila e iniciamos un correlativo para la columna.
            Row FilaExcel = HojaExcel.createRow(NumNuevaFila);            
            
            int NumColumna = 0;
 
            // LLenamos la primera columna con el correlativo siguiente a la fila anterior. 
            Cell CeldaExcel = FilaExcel.createCell(NumColumna);
            CeldaExcel.setCellValue(NumNuevaFila);
            
            // Inicamos el llenado de cada columna en la fila.
            for (String Dato : Datos) {
            	CeldaExcel = FilaExcel.createCell(NumColumna++);
            	CeldaExcel.setCellValue(Dato);
            }
 
            // Cerramos el archivo de entrada.
            ArchivoEntrada.close();
 
            // Creamos un archivo de salida para guardar los cambios.
            FileOutputStream ArchivoSalida = new FileOutputStream(RutaRaiz);
            LibroExcel.write(ArchivoSalida);
            
            // Cerramos el libro y el archivo de salida.
            LibroExcel.close();
            ArchivoSalida.close();
            
            // Finalizamos la actualizacion del archivo excel.
        	System.out.println("Actualizacion del archivo: " + ArchivoExcel + ".xlsx, finalizada de manera exitosa!");
        } 
        
        // En caso de algun problema con la actualizacion del excel, arrojamos un error.
        catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
        	System.err.println("Hubo un error al actualizar el archivo: " + ArchivoExcel + ".xlsx");
        	ex.printStackTrace();
        }
    }
    
    public ArrayList<ObjExcel> CountRow(String ArchivoExcel)
    {
    	  int Cant=0;
    	  int NumCol;
    	  ArrayList<ObjExcel> objExcelList  = new ArrayList<>();
    	  try
          {
    		  DataFormatter dataFormatter = new DataFormatter();
              FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
   
              //Create Workbook instance holding reference to .xlsx file
              XSSFWorkbook workbook = new XSSFWorkbook(file);
   
              //Get first/desired sheet from the workbook
              XSSFSheet sheet = workbook.getSheetAt(0);
   
              //Iterate through each rows one by one
              Iterator<Row> rowIterator = sheet.iterator();
              while (rowIterator.hasNext()) 
              {
            	  ObjExcel objExcel = new ObjExcel();
            	  Cant++;
            	  Row row = rowIterator.next();
                  //For each row, iterate through all the columns
                  Iterator<Cell> cellIterator = row.cellIterator();
                   
                  NumCol=0;
                  while (cellIterator.hasNext()) 
                  {   
                	  NumCol++;
                	  Cell cell = cellIterator.next();
                	  String cellStringValue    = dataFormatter.formatCellValue(cell);                	  
                	  switch (NumCol)
                	  {
	                	  case 1:  objExcel.setID(cellStringValue);      break;
	                	  case 2:  objExcel.setCaso(cell.getStringCellValue());    break;
	                	  case 3:  objExcel.setCiclo(cellStringValue);             break;
	                	  case 4:  objExcel.setTipoCliente(cellStringValue);       break;
	                	  case 5:  objExcel.setSubTipoCliente(cellStringValue);    break;
	                	  case 6:  objExcel.setTipoAdquisicion(cellStringValue);   break;
	                	  case 7:  objExcel.setNombrePlan(cellStringValue); 	   break;
	                	  case 8:  objExcel.setRut(cellStringValue);               break;
	                	  case 9:  objExcel.setPerfilAPN(cellStringValue);         break;
	                	  case 10:  objExcel.setSkuEquipo(cellStringValue);        break;
	                	  case 11: objExcel.setTelefono(cellStringValue);          break;
	                	  case 12: objExcel.setPlanBase(cellStringValue);          break;
	                	  case 13: objExcel.setSubProducto(cellStringValue);       break;
	                	  case 14: objExcel.setTipo(cellStringValue);              break;
	                	  case 15: objExcel.setServicioAdicional(cellStringValue); break;
	                	  case 16: objExcel.setPlay1(cellStringValue);             break;
	                	  case 17: objExcel.setPlay2(cellStringValue);             break;
	                	  case 18: objExcel.setPlay3(cellStringValue);             break;
                	  }
                  }
                  
                  objExcelList.add(objExcel);
              }
              file.close();
          } 
          catch (Exception e) 
          {
              e.printStackTrace();
          }
    	  return objExcelList;
    }  
   
    public ArrayList<ObjExcel> CountRowEcommerce(String ArchivoExcel)
    {
    	  int Cant=0;
    	  int NumCol;
    	  ArrayList<ObjExcel> objExcelList  = new ArrayList<>();
    	  try
          {
    		  DataFormatter dataFormatter = new DataFormatter();
              FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
   
              //Create Workbook instance holding reference to .xlsx file
              XSSFWorkbook workbook = new XSSFWorkbook(file);
   
              //Get first/desired sheet from the workbook
              XSSFSheet sheet = workbook.getSheetAt(0);
   
              //Iterate through each rows one by one
              Iterator<Row> rowIterator = sheet.iterator();
              while (rowIterator.hasNext()) 
              {
            	  ObjExcel objExcel = new ObjExcel();
            	  Cant++;
            	  Row row = rowIterator.next();
                  //For each row, iterate through all the columns
                  Iterator<Cell> cellIterator = row.cellIterator();
                   
                  NumCol=0;
                  while (cellIterator.hasNext()) 
                  {   
                	  NumCol++;
                	  Cell cell = cellIterator.next();
                	  String cellStringValue    = dataFormatter.formatCellValue(cell);                	  
                	  switch (NumCol)
                	  {
	                	  case 1:  objExcel.setID(cellStringValue);      break;
	                	  case 2:  objExcel.setCaso(cell.getStringCellValue());    break;
	                	  case 3:  objExcel.setCiclo(cellStringValue);             break;
	                	  case 4:  objExcel.setTipoCliente(cellStringValue);       break;
	                	  case 5:  objExcel.setSubTipoCliente(cellStringValue);    break;
	                	  case 6:  objExcel.setTipoAdquisicion(cellStringValue);   break;
	                	  case 7:  objExcel.setNombrePlan(cellStringValue); 	   break;
	                	  case 8:  objExcel.setRut(cellStringValue);               break;
	                	  case 9:  objExcel.setPerfilAPN(cellStringValue);         break;
	                	  case 10:  objExcel.setSkuEquipo(cellStringValue);        break;
	                	  case 11: objExcel.setTelefono(cellStringValue);          break;
	                	  case 12: objExcel.setPlanBase(cellStringValue);          break;
	                	  case 13: objExcel.setSubProducto(cellStringValue);       break;
	                	  case 14: objExcel.setTipo(cellStringValue);              break;
	                	  case 15: objExcel.setServicioAdicional(cellStringValue); break;
	                	  case 16: objExcel.setPlay1(cellStringValue);             break;
	                	  case 17: objExcel.setPlay2(cellStringValue);             break;
	                	  case 18: objExcel.setPlay3(cellStringValue);             break;
                	  }
                  }
                  
                  objExcelList.add(objExcel);
              }
              file.close();
          } 
          catch (Exception e) 
          {
              e.printStackTrace();
          }
    	  return objExcelList;
    }  
  
    
    public String[] GetSimcard(String ArchivoExcel)
    {
    	  try
          {
    		  DataFormatter dataFormatter = new DataFormatter();
              FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
   
              //Create Workbook instance holding reference to .xlsx file
              XSSFWorkbook workbook = new XSSFWorkbook(file);
   
              //Get first/desired sheet from the workbook
              XSSFSheet sheet = workbook.getSheetAt(0);
   
              //Iterate through each rows one by one
              Iterator<Row> rowIterator = sheet.iterator();
              int Cont=0;
              while (rowIterator.hasNext()) 
              {
            	  Row row = rowIterator.next();
            	  if(Cont>0)
            	  {  
                	  String Simcard = dataFormatter.formatCellValue(row.getCell(0));
                	  String Imsi    = dataFormatter.formatCellValue(row.getCell(1));
                	  String Estado  = dataFormatter.formatCellValue(row.getCell(2));
                      String [] SimcardImsi = {Simcard,Imsi};
                	  if(Estado.contains("Disponible"))
                	  {
                		  workbook.close();
                		  file.close();
                    	  return SimcardImsi;
                	  }  
            	  }  
            	  Cont=1;
              }
              file.close();
          } 
          catch (Exception e) 
          {
              e.printStackTrace();
          }
    	  return null;
    } 
    
    public void SetSimcardUtilizada(String ArchivoExcel, String NumSimcard)
    {
    	try
        {
  		    DataFormatter dataFormatter = new DataFormatter();
  		    File filepath= new File("Input/"+ArchivoExcel + ".xlsx");
            FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            
            while (rowIterator.hasNext()) 
            {
          	  Row row = rowIterator.next();
          	  	  String Simcard = dataFormatter.formatCellValue(row.getCell(0));
              	  String Imsi    = dataFormatter.formatCellValue(row.getCell(1));
              	  String Estado  = dataFormatter.formatCellValue(row.getCell(2));
              	  if(Simcard.contains(NumSimcard))
              	  {
              	    row.createCell(2).setCellValue("Utilizada");
              	    FileOutputStream out = new FileOutputStream(filepath, false);
              	    workbook.write(out);
              	    out.close();
              	    file.close();
              	    System.out.println("Pase por aca con "+ Simcard);
              		return;
              	  }	  
            }
            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    } 
    
    public String LecturaCeldaExcel(String ArchivoExcel, String HojaExcel, String celdaExcel) throws IOException {
        
		String datoExcel=null;
   
	
        try (FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"))) {

            // Lee el archivo de Excel
            XSSFWorkbook excelBook = new XSSFWorkbook(file);
            // Obtiene la hoja del archivo de Excel
            XSSFSheet excelPage = excelBook.getSheet(HojaExcel);

            // Obtiene la referencia de la celda que debe seleccionar
            CellReference ref = new CellReference(celdaExcel);
            // Obtiene la fila dependiendo la celda que se configura en el archivo config.properties
            Row fila = excelPage.getRow(ref.getRow());
            
         
            if (fila != null) {
                // Obtiene la columna dependiendo la celda que se configura en el archivo config.properties
                Cell columna = fila.getCell(ref.getCol());
                // Obtiene la informacion que tiene la celda pero no puede ser el resultado de una formula
                datoExcel = columna.getRichStringCellValue().getString();
                System.out.println("La informacion es: " + datoExcel);
            }
        } catch (Exception e) {
            System.out.println("Error Controlado");
            e.getMessage();
        }
   
        return datoExcel;
}
    
    public ArrayList<String> GetCountSheet(String ArchivoExcel, String NombreHoja)
    {
    	
    	ArrayList<String> Planes = new ArrayList<String>();
    	  int Count=0;
    	  try
          {
    		  
    		  DataFormatter dataFormatter = new DataFormatter();
              FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
   
              XSSFWorkbook workbook = new XSSFWorkbook(file);
              
              //Get first/desired sheet from the workbook
              XSSFSheet sheet = workbook.getSheet(NombreHoja);
   
              //Iterator<Row> rowIterator = sheet.iterator();
              
              //Iterate through each rows one by one
              Iterator<Row> rowIterator = sheet.iterator();
              int Cont=0;
              while (rowIterator.hasNext()) 
              {   
            	  Row row = rowIterator.next();
            	  Planes.add(dataFormatter.formatCellValue(row.getCell(0)));
            	  
            	  Count++;
              }
              file.close();
          } 
          catch (Exception e) 
          {
              e.printStackTrace();
          }
    	  return Planes;
    }
    
  public List<ObjEPC> LecturaColumnasExcel(String ArchivoExcel, String HojaExcel) throws IOException {
        
    	///ArraList<ObjExcel> Planes = new ArrayList<ObjExcel>();
    	List<ObjEPC> PlanesEPCList = new ArrayList<>();
    	int Count=0;
  	
          try {
        		  
        		  DataFormatter dataFormatter = new DataFormatter();
                  FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
       
                  XSSFWorkbook workbook = new XSSFWorkbook(file);
                  
                  XSSFSheet sheet = workbook.getSheet(HojaExcel);

                  Iterator<Row> rowIterator = sheet.iterator();
                  int Cont=0;
                  while (rowIterator.hasNext()) 
                  {   
                	  Row row = rowIterator.next();
                	  //Utiliza columna A y la D del archivo Excel
                	  PlanesEPCList.add(new ObjEPC(dataFormatter.formatCellValue(row.getCell(1)),dataFormatter.formatCellValue(row.getCell(3))));
                	  Count++;
                  }
                  file.close();
          } catch (Exception e) {
              System.out.println("Error Controlado");
              e.getMessage();
          }
     
          return PlanesEPCList;
  }
 public List<ObjEPC> LecturaColumnasExcelOtros(String ArchivoExcel, String HojaExcel) throws IOException {
        
    	///ArraList<ObjExcel> Planes = new ArrayList<ObjExcel>();
    	List<ObjEPC> PlanesEPCList = new ArrayList<>();
    	int Count=0;
  	
          try {
        		  
        		  DataFormatter dataFormatter = new DataFormatter();
                  FileInputStream file = new FileInputStream(new File("Input/"+ArchivoExcel + ".xlsx"));
       
                  XSSFWorkbook workbook = new XSSFWorkbook(file);
                  
                  XSSFSheet sheet = workbook.getSheet(HojaExcel);

                  Iterator<Row> rowIterator = sheet.iterator();
                  int Cont=1;
                  while (rowIterator.hasNext()) 
                  {   
                	  Row row = rowIterator.next();
                	//Utiliza columna A y la B del archivo Excel
                	  PlanesEPCList.add(new ObjEPC(dataFormatter.formatCellValue(row.getCell(0)),dataFormatter.formatCellValue(row.getCell(1))));
                	  Count++;
                  }
                  file.close();
          } catch (Exception e) {
              System.out.println("Error Controlado");
              e.getMessage();
          }
     
          return PlanesEPCList;
  }
 
 public void ReemplazarLineaExcel(String ArchivoExcel, String Value, int Posicion){
 	// Iniciamos actualizacion de archivo excel.
 	//System.out.println("Iniciamos actualizacion del archivo: " + ArchivoExcel + ".xlsx");
 	
 	// Obtenemos la ruta principal de la carpeta del usuario de windows.
 	String CarpetaUsuario = System.getProperty("user.home");
     
     try {
     	// Abrimos el archivo excel indicado desde la carpeta de usuario.
     	//String RutaRaiz = CarpetaUsuario + "//reportes//Excel//" + ArchivoExcel + ".xlsx";
     	
     	File RutaRaiz = new File("Reportes/Excel/"+ArchivoExcel + ".xlsx");
     	FileInputStream ArchivoEntrada = new FileInputStream(RutaRaiz);
         
         // Abrimos el libro y nos posicionamos en la primera hoja de trabajo.
         Workbook LibroExcel = WorkbookFactory.create(ArchivoEntrada);
         Sheet HojaExcel = LibroExcel.getSheetAt(0);                     
         Cell CellUpdate = HojaExcel.getRow(0).getCell(Posicion);         
         CellUpdate.setCellValue(Value);
         // Cerramos el archivo de entrada.
         ArchivoEntrada.close();

         // Creamos un archivo de salida para guardar los cambios.
         FileOutputStream ArchivoSalida = new FileOutputStream(RutaRaiz);
         LibroExcel.write(ArchivoSalida);
         
         // Cerramos el libro y el archivo de salida.
         LibroExcel.close();
         ArchivoSalida.close();
         
         // Finalizamos la actualizacion del archivo excel.
     	System.out.println("Actualizacion del archivo: " + ArchivoExcel + ".xlsx, finalizada de manera exitosa!");
     } 
     
     // En caso de algun problema con la actualizacion del excel, arrojamos un error.
     catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
     	System.err.println("Hubo un error al actualizar el archivo: " + ArchivoExcel + ".xlsx");
     	ex.printStackTrace();
     }
 }
     
     public void ReemplazarLineasExcel(String ArchivoExcel, Hashtable<String, String> Columna){
    	 	// Iniciamos actualizacion de archivo excel.
    	 	//System.out.println("Iniciamos actualizacion del archivo: " + ArchivoExcel + ".xlsx");
    	 	
    	 	// Obtenemos la ruta principal de la carpeta del usuario de windows.
    	 	String CarpetaUsuario = System.getProperty("user.home");
    	     
    	     try {
    	     	// Abrimos el archivo excel indicado desde la carpeta de usuario.
    	     	//String RutaRaiz = CarpetaUsuario + "//reportes//Excel//" + ArchivoExcel + ".xlsx";
    	     	
    	     	File RutaRaiz = new File("Reportes/Excel/"+ArchivoExcel + ".xlsx");
    	     	FileInputStream ArchivoEntrada = new FileInputStream(RutaRaiz);
    	         
    	         // Abrimos el libro y nos posicionamos en la primera hoja de trabajo.
    	         Workbook LibroExcel = WorkbookFactory.create(ArchivoEntrada);
    	         Sheet HojaExcel = LibroExcel.getSheetAt(0);                     
    	         
    	         
	        	 Cell CellUpdate = HojaExcel.getRow(0).getCell(0);
	        	 CellUpdate.setCellValue(Columna.get("A"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(1);
	        	 CellUpdate.setCellValue(Columna.get("B"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(2);
	        	 CellUpdate.setCellValue(Columna.get("C"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(3);
	        	 CellUpdate.setCellValue(Columna.get("D"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(4);
	        	 CellUpdate.setCellValue(Columna.get("E"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(5);
	        	 CellUpdate.setCellValue(Columna.get("F"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(6);
	        	 CellUpdate.setCellValue(Columna.get("G"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(7);
	        	 CellUpdate.setCellValue(Columna.get("H"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(8);
	        	 CellUpdate.setCellValue(Columna.get("I"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(9);
	        	 CellUpdate.setCellValue(Columna.get("J"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(10);
	        	 CellUpdate.setCellValue(Columna.get("K"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(11);
	        	 CellUpdate.setCellValue(Columna.get("L"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(12);
	        	 CellUpdate.setCellValue(Columna.get("M"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(13);
	        	 CellUpdate.setCellValue(Columna.get("N"));
	        	 CellUpdate = HojaExcel.getRow(0).getCell(14);
	        	 CellUpdate.setCellValue(Columna.get("O"));
    	         
    	         
    	         
    	         
    	         
    	         // Cerramos el archivo de entrada.
    	         ArchivoEntrada.close();

    	         // Creamos un archivo de salida para guardar los cambios.
    	         FileOutputStream ArchivoSalida = new FileOutputStream(RutaRaiz);
    	         LibroExcel.write(ArchivoSalida);
    	         
    	         // Cerramos el libro y el archivo de salida.
    	         LibroExcel.close();
    	         ArchivoSalida.close();
    	         
    	         // Finalizamos la actualizacion del archivo excel.
    	     	System.out.println("Actualizacion del archivo: " + ArchivoExcel + ".xlsx, finalizada de manera exitosa!");
    	     } 
    	     
    	     // En caso de algun problema con la actualizacion del excel, arrojamos un error.
    	     catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
    	     	System.err.println("Hubo un error al actualizar el archivo: " + ArchivoExcel + ".xlsx");
    	     	ex.printStackTrace();
    	     }

 }

 
 
}