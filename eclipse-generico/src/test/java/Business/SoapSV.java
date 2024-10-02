package Business;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SoapSV {
	
	public String ConsultarClienteSSO(String Rut) throws IOException {

		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL = "http://osbpublictest.clarochile.cl:80/SSO-AMX-SBLookUpService/ProxyServices/LookUpProxyService?wsdl";
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:look=\"http://lookup.sso.schema.amx.com\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <look:LookupRequest>\r\n" + 
				"         <look:userId>"+Rut+"</look:userId>\r\n" + 
				"      </look:LookupRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";

		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "http://lookup.sso.amx.com/lookup";
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length",
		String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		//Write the content of the request to the outputstream of the HTTP Connection.
		out.write(b);
		out.close();
		//Ready with sending the request.

		//Read the response.
		InputStreamReader isr = null;
		if (httpConn.getResponseCode() == 200) {
		  isr = new InputStreamReader(httpConn.getInputStream());
		} else {
		  isr = new InputStreamReader(httpConn.getErrorStream());
		}

		BufferedReader in = new BufferedReader(isr);

		//Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
		outputString = outputString + responseString;		
		}
		//Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
		Document document = parseXmlFile(outputString); // Write a separate method to parse the xml input.
		NodeList nodeLst = document.getElementsByTagName("<channel>");
		String elementValue = nodeLst.item(0).getTextContent();
		System.out.println(elementValue);

		//Write the SOAP message formatted to the console.
		String formattedSOAPResponse = formatXML(outputString); // Write a separate method to format the XML input.
		System.out.println(formattedSOAPResponse);
		return elementValue;
		}
	
	    private String formatXML(String outputString) {
		// TODO Auto-generated method stub
		return null;
	}

		private Document parseXmlFile(String outputString) {
		// TODO Auto-generated method stub
		return null;
	}
	
		public String CrearClienteSSO(String Rut, String Nombre, String Apellido, String Mail, String Cel, String Clave) throws IOException 
		{
			
			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL = "http://osbpublictest.clarochile.cl:80/SSO-AMX-SBAddService/ProxyServices/AddProxyService?wsdl";
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"http://add.sso.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <add:AddRequest>\r\n" + 
					"         <add:Source>MiClaroChile</add:Source>\r\n" + 
					"         <!--Optional:-->\r\n" + 
					"         <add:User>\r\n" + 
					"            <add:LoginName>"+Rut+"</add:LoginName>\r\n" + 
					"            <add:UserFormattedName>"+Rut+"</add:UserFormattedName>\r\n" + 
					"            <add:UserFamilyName>"+Nombre+"</add:UserFamilyName>\r\n" + 
					"            <add:UserGivenName>"+Apellido+"</add:UserGivenName>\r\n" + 
					"            <add:EmailOne>"+Mail+"</add:EmailOne>\r\n" + 
					"            <add:PhoneNumberOne>"+Cel+"</add:PhoneNumberOne>\r\n" + 
					"            <add:Password>"+Clave+"</add:Password>\r\n" + 
					"            <!--clave generica-->\r\n" + 
					"         </add:User>\r\n" + 
					"         <!--Zero or more repetitions:-->\r\n" + 
					"         <add:Parameters>\r\n" + 
					"            <add:Key>Version</add:Key>\r\n" + 
					"            <add:Value>Legacy</add:Value>\r\n" + 
					"            <add:Type>java.lang.String</add:Type>\r\n" + 
					"         </add:Parameters>\r\n" + 
					"      </add:AddRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>\r\n" + 
					"";

			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "http://add.sso.amx.com/add";
			// Set the appropriate HTTP parameters.
			httpConn.setRequestProperty("Content-Length",
			String.valueOf(b.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			//Write the content of the request to the outputstream of the HTTP Connection.
			out.write(b);
			out.close();
			//Ready with sending the request.

			//Read the response.
			InputStreamReader isr = null;
			if (httpConn.getResponseCode() == 200) {
			  isr = new InputStreamReader(httpConn.getInputStream());
			} else {
			  isr = new InputStreamReader(httpConn.getErrorStream());
			}

			BufferedReader in = new BufferedReader(isr);

			//Write the SOAP message response to a String.
			while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;		
			}
			//Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
			Document document = parseXmlFile(outputString); // Write a separate method to parse the xml input.
			NodeList nodeLst = document.getElementsByTagName("<channel>");
			String elementValue = nodeLst.item(0).getTextContent();
			System.out.println(elementValue);

			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXML(outputString); // Write a separate method to format the XML input.
			System.out.println(formattedSOAPResponse);
			return elementValue;
		}
		

		
}
