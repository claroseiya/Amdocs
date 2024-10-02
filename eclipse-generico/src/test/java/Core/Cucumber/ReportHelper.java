package Core.Cucumber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import net.masterthought.cucumber.*;

public class ReportHelper {

    public static void generateCucumberReport() {
    	Date fecha = new Date();
    	
       String Setfecha = "dd-MMM-yyyy hh:mm:ss"; // El formato de fecha está especificado  
       SimpleDateFormat objSDF = new SimpleDateFormat(Setfecha, new Locale("es_ES")); // La cadena de formato de fecha se pasa como un argumento al objeto 
       
        String NombreReporte = objSDF.format(fecha);
    	NombreReporte =NombreReporte.replace(":", ".");    	
    	File reportOutputDirectory = new File("Reportes/"+NombreReporte);
        ArrayList<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add("target/cucumber.json");

        String projectName = "Claro - Automatizacion";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.addClassifications("Platform", System.getProperty("os.name"));
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");
        
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}