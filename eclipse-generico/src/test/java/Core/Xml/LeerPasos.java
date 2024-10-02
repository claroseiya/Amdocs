package Core.Xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LeerPasos {
	
	public List<String> getxmlSucursalVirtual(String NombrePaso, String Plataforma)
	{
		String RutaArchivo = "DataXml/"+Plataforma+".xml";
		List<String> List = getxml(NombrePaso, RutaArchivo);
		return List;
	}
	
	
	public List<String> getxml(String NombrePaso, String RutaArchivo)
	{
		try {
			List<String> listAtributos = new ArrayList<String>();
	        File archivo = new File(RutaArchivo);
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	        Document document = documentBuilder.parse(archivo);
	        document.getDocumentElement().normalize();
	        NodeList listaEmpleados = document.getElementsByTagName("paso");
	        
	        for (int temp = 0; temp < listaEmpleados.getLength(); temp++) {
	            Node nodo = listaEmpleados.item(temp);	            
	            Element element = (Element) nodo;
	            if(element.getAttribute("name").equals(NombrePaso))
	            {
	            	listAtributos.add(element.getAttribute("by"));
	            	listAtributos.add(element.getAttribute("Path"));
	            	listAtributos.add(element.getAttribute("descripcion"));
	            	return listAtributos;
	            }
	        }
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}


}
