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
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SoapAmdocs extends Amdocs{ 
	
	Amdocs amd = new Amdocs();
	
	//Creacion Cliente Persona
	public String CrearPersona(int Ambiente, String Rut, String Nombre, String Apellido, String Email) throws IOException {

		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL="";
		if(Ambiente == 4)
			 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
		else if(Ambiente == 8)
			 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		
		else if(Ambiente == 2)/*PMX2*/
			 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
		else if(Ambiente == 3)/*PMX3*/
			 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://contactService.contact.ws.crm.crmimpl.com\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <con:createContact>\r\n" + 
				"         <contactRequest>\r\n" + 
				"            <contactInfoDetails>\r\n" + 
				"               <additionalAuthInd>1</additionalAuthInd>\r\n" + 
				"               <birthDate>1981-03-11T00:00:00.000-04:00</birthDate>\r\n" + 
				"               <businessPhone>56222222222</businessPhone>\r\n" + 
				"               <firstName>"+Nombre+"</firstName>\r\n" + 
				"               <gender>M</gender>\r\n" + 
				"               <genderDecode>M</genderDecode>\r\n" + 
				"				<emailAddress>"+Email+"</emailAddress> \r\n"+
				"               <homePhone>56222222222</homePhone>\r\n" + 
				"               <isCompany>false</isCompany>\r\n" + 
				"               <lastName>"+Apellido+"</lastName>\r\n" + 
				"               <maritalStatus>Please Specify</maritalStatus>\r\n" + 
				"               <maritalStatusDecode>Please Specify</maritalStatusDecode>\r\n" + 
				"               <mobilePhone>56988888888</mobilePhone>\r\n" + 
				"               <nunca>N</nunca>\r\n" + 
				"               <nuncaDecode>NO</nuncaDecode>\r\n" + 
				"               <profession>Please Specify</profession>\r\n" + 
				"            </contactInfoDetails>\r\n" + 
				"            <demographicInfo>\r\n" + 
				"               <maritalStatus>MARRIED</maritalStatus>\r\n" + 
				"            </demographicInfo>\r\n" + 
				"            <identifierInfo>\r\n" + 
				"               <identifierNumber>"+Rut+"</identifierNumber>\r\n" + 
				"               <identifierType>RUT</identifierType>\r\n" + 
				"               <identifierTypeDecode>RUT</identifierTypeDecode>\r\n" + 
				"               <isPrimary>true</isPrimary>\r\n" + 
				"            </identifierInfo>\r\n" + 
				"         </contactRequest>\r\n" + 
				"      </con:createContact>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>\r\n" + 
				"";
	
		amd.DataRowExcel("EstadoTrx", xmlInput);
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "";
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
		
		
		//Write the SOAP message formatted to the console.
		String formattedSOAPResponse = formatXMLCliente(outputString); // Write a separate method to format the XML input.
		
		return formattedSOAPResponse;
		}
	
	 private String formatXMLCliente(String outputString) {
	    	
	    	String Result="";
	    	Pattern pattern = Pattern.compile("<contactId>(.*?)</contactId>");
	    	Matcher matcher = pattern.matcher(outputString);
	        if( matcher.find())
	        	Result = matcher.group(1);
	        else
	        {
	        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
		    	Matcher matcherOk = patternOk.matcher(outputString);
		        if( matcherOk.find()) 
		        	Result = matcherOk.group(1);
	        }
	        
	        //amd.DataRowExcel("Orden", Result);
	        
		return Result;
	}// finalizar cliente
	 
	 
	//Creacion Cliente Organizacion
		public String CrearOrganizacion(int Ambiente, String Rut, String Nombre, String Email) throws IOException {
            
			//String CO="CO1000023821";
			Core.Data.GeneradorRut RutPersona = new Core.Data.GeneradorRut();
			String RutPer = RutPersona.GetRutRandom();
			
			
			String CO  = CrearPersona(Ambiente, RutPer, Nombre ,"Claro",Email);
			
			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="";
			if(Ambiente == 4)
				 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/controladora/amdocs/proxy/createOrganization?wsdl";
			else if(Ambiente == 8)
				 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/controladora/amdocs/proxy/createOrganization?wsdl";
			else if(Ambiente == 10)
				 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/controladora/amdocs/proxy/createOrganization?wsdl";
			else if (Ambiente ==12)
			    wsURL = "";
			else if(Ambiente == 2)/*PMX2*/
				 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/controladora/amdocs/proxy/createOrganization?wsdl";
			else if(Ambiente == 3)/*PMX3*/
				 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/controladora/amdocs/proxy/createOrganization?wsdl";
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:org=\"http://organizationService.organization.ws.crm.crmimpl.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <org:createOrganization>\r\n" + 
					"         <!--Optional:-->\r\n" + 
					"         <organizationRequest>\r\n" + 
					"            <orgName>"+Nombre+"</orgName>\r\n" + 
					"            <phone>56222222222</phone>\r\n" + 
					"            <fax>56222222222</fax>\r\n" + 
					"            <email>"+Email+"</email>\r\n" + 
					"            <orgType>Empresa</orgType>\r\n" + 
					"            <industryType>SYE</industryType>\r\n" + 
					"            <status>3</status>\r\n" + 
					"            <businessIdent>"+Rut+"</businessIdent><serialNo>0</serialNo>\r\n" + 
					"            <businessCode>000001</businessCode>\r\n" + 
					"            <legalRepresentativeInfo><contactId>"+CO+"</contactId></legalRepresentativeInfo>\r\n" + 
					"            <isRutVerified>true</isRutVerified>\r\n" + 
					"         </organizationRequest>\r\n" + 
					"      </org:createOrganization>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
		
			amd.DataRowExcel("EstadoTrx", xmlInput);
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			
			
			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXMLOrganizacion(outputString); // Write a separate method to format the XML input.
			
			return formattedSOAPResponse;
			}
		
		 private String formatXMLOrganizacion(String outputString) {
		    	
		    	String Result="";
		    	Pattern pattern = Pattern.compile("<organizationID>(.*?)</organizationID>");
		    	Matcher matcher = pattern.matcher(outputString);
		        if( matcher.find())
		        	Result = matcher.group(1);
		        else
		        {
		        	Pattern patternOk = Pattern.compile("<errorMsg>(.*?)</errorMsg>");
			    	Matcher matcherOk = patternOk.matcher(outputString);
			        if( matcherOk.find()) 
			        	Result = matcherOk.group(1);
		        }
		        
		        amd.DataRowExcel("GeneraContrato", outputString);
		        System.out.println(Result);
		        //amd.DataRowExcel("Orden", Result);		   
			return Result;
		}// finalizar Empresa

	 
	 
	 

		public String CrearDireccion(int Ambiente, String Rut, String Nombre, String Apellido) throws IOException {

			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="";
			if(Ambiente == 4)
				 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/crm/controladora/proxy/AddressProviderService?wsdl";
			else if(Ambiente == 8)
				 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/AddressProviderService?wsdl";
			else if(Ambiente == 10)
				 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/crm/controladora/proxy/AddressProviderService?wsdl";
			else if (Ambiente ==12)
			    wsURL = "";
			else if(Ambiente == 2)/*PMX2*/
				 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/AddressProviderService?wsdl";
			else if(Ambiente == 3)/*PMX3*/
				 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/crm/controladora/proxy/AddressProviderService?wsdl";
			
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "";
					
		
			
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			
			
			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXMLCliente(outputString); // Write a separate method to format the XML input.
			
			return formattedSOAPResponse;
			}
		    
		public Hashtable<String, String> CrearCBP(int Ambiente,String TipoCliente,String Rut,String ContacId, String AddressId, String Ciclo) throws IOException {

			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="";
			if(Ambiente == 4)
				 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
			else if(Ambiente == 8)
				 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
			else if(Ambiente == 10)
				 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
			else if (Ambiente ==12)
			    wsURL = "";
			else if(Ambiente == 2)/*PMX2*/
				 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
			else if(Ambiente == 3)/*PMX3*/
				 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
			
			
			String Id = "<contactId>"+ContacId+"</contactId>";
			String customerSubType="<customerSubType>NNVO</customerSubType>";
			String customerType="<customerType>N</customerType>";
			String Mode="1";
			if(TipoCliente.equalsIgnoreCase("Empresas"))
			{
				Id= "<orgId>"+ContacId+"</orgId>";
				customerSubType="<customerSubType>ENRM</customerSubType>";
				customerType= "<customerType>E</customerType>";
				Mode="1";
			}
			else if (TipoCliente.equalsIgnoreCase("PYME"))
				{
					Id= "<orgId>"+ContacId+"</orgId>";
					customerSubType="<customerSubType>PNVO</customerSubType>";
					customerType= "<customerType>P</customerType>";
					Mode="1";
			}
				
			
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://customerService.customer.ws.crm.crmimpl.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <cus:createCustomerBillingProfile>\r\n" + 
					"         <request>\r\n" + 
					"            <addressId>"+AddressId+"</addressId>\r\n" + 
					"            <billCycle>"+Ciclo+"</billCycle>\r\n" + 
					"            <billFrequency>1</billFrequency>\r\n" + 
					"            "+customerSubType+"\r\n" + 
					"            "+customerType+"\r\n" + 
					"            <identifierInfo>\r\n" + 
					"               <identifierNumber>"+Rut+"</identifierNumber>\r\n" + 
					"               <identifierType>RUT</identifierType>\r\n" + 
					"               <isPrimary>true</isPrimary>\r\n" + 
					"            </identifierInfo>\r\n" + 
					"            <mode>"+Mode+"</mode>\r\n" + 
					"            <!--<orgId>ORG1000006219</orgId>-->\r\n" + 
					"            "+Id+"\r\n" + 
					"         </request>\r\n" + 
					"      </cus:createCustomerBillingProfile>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>\r\n" + 
					"";							
			
			amd.DataRowExcel("EstadoTrx", xmlInput);
			System.out.println(xmlInput);
			
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			
			
			//Write the SOAP message formatted to the console.
			Hashtable<String, String> formattedSOAPResponse = formatXMLCBPCliente(outputString); // Write a separate method to format the XML input.
			
			return formattedSOAPResponse;
			}
		
		 private Hashtable<String, String> formatXMLCBPCliente(String outputString) {
		    	
			 Hashtable<String, String> ResultadoHash = 
	                 new Hashtable<String, String>();
		    	
			    String Result="";
		    	String Result2="";
		    	
		    	Pattern pattern = Pattern.compile("<cbpId>(.*?)</cbpId>");
		    	Matcher matcher = pattern.matcher(outputString);
		        if( matcher.find())
		        	Result = matcher.group(1);
		        else
		        {
		        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
			    	Matcher matcherOk = patternOk.matcher(outputString);
			        if( matcherOk.find()) 
			        	Result = matcherOk.group(1);
		        }
		        
		        Pattern pattern2 = Pattern.compile("<contactId>(.*?)</contactId>");
		    	Matcher matcher2 = pattern2.matcher(outputString);
		        if( matcher2.find())
		        	Result2 = matcher2.group(1);
		        else
		        {
		        	Pattern patternOk2 = Pattern.compile("<faultstring>(.*?)</faultstring>");
			    	Matcher matcherOk2 = patternOk2.matcher(outputString);
			        if( matcherOk2.find()) 
			        	Result2 = matcherOk2.group(1);
		        }
		        
		        ResultadoHash.put("cbpId", Result);
		        ResultadoHash.put("contactId", Result2);
		        
		        System.out.println("CBP Creada: "+Result);
		        System.out.println("ID Contacto: "+Result2);
		        
		        amd.DataRowExcel("CBP", Result);
		        
			return ResultadoHash;
		}
		    
		public String CrearFA(int Ambiente,String TipoCliente, String cbpId, String ContacId, String AddressId, String Email) throws IOException {

				//Code to make a webservice HTTP request
				String responseString = "";
				String outputString = "";
				String wsURL="";
				if(Ambiente == 4)
					 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if(Ambiente == 8)
					 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if(Ambiente == 10)
					 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if (Ambiente ==12)
				    wsURL = "";
				else if(Ambiente == 2)/*PMX2*/
					 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if(Ambiente == 3)/*PMX3*/
					 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				
				
				String billFormat="BO";
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					billFormat="FA";
					
				}else if(TipoCliente.equalsIgnoreCase("PYME"))
				{
					billFormat="FA";
					
				}
				
				
				URL url = new URL(wsURL);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection)connection;
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://customerService.customer.ws.crm.crmimpl.com\">\r\n" + 
						"   <soapenv:Header/>\r\n" + 
						"   <soapenv:Body>\r\n" + 
						"      <cus:createBillingEntities>\r\n" + 
						"         <createBillingEntitiesRequest>\r\n" + 
						"            <addressId>"+AddressId+"</addressId>\r\n" + 
						"            <affiliateId>CLAROCHILE</affiliateId>\r\n" + 
						"            <barDetails>\r\n" + 
						"               <billFormat>"+billFormat+"</billFormat>\r\n" + 
						"               <billMedia>OL</billMedia>\r\n" + 
						"               <lob>WLS</lob>\r\n" + 
						"               <email>"+Email+"</email>\r\n" + 
						"               <smsNotificationNumber>56948150923</smsNotificationNumber>\r\n" + 
						"            </barDetails>\r\n" + 
						"            <cbpId>"+cbpId+"</cbpId>\r\n" + 
						"            <contactId>"+ContacId+"</contactId>\r\n" + 
						"         </createBillingEntitiesRequest>\r\n" + 
						"      </cus:createBillingEntities>\r\n" + 
						"   </soapenv:Body>\r\n" + 
						"</soapenv:Envelope>\r\n" + 
						"";			
				//Incluir una opcion para que no pegue el Request cuando no sea flujo completo por servicio
				amd.DataRowExcel("EstadoTrx", xmlInput);
				byte[] buffer = new byte[xmlInput.length()];
				buffer = xmlInput.getBytes();
				bout.write(buffer);
				byte[] b = bout.toByteArray();
				String SOAPAction = "";
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
				
				
				//Write the SOAP message formatted to the console.
				String formattedSOAPResponse = formatXMLFACliente(outputString); // Write a separate method to format the XML input.
				
				return formattedSOAPResponse;
				}
			
			 private String formatXMLFACliente(String outputString) {
			    	
			    	String Result="";
			    	Pattern pattern = Pattern.compile("<barId>(.*?)</barId>");
			    	Matcher matcher = pattern.matcher(outputString);
			        if( matcher.find())
			        	Result = matcher.group(1);
			        else
			        {
			        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
				    	Matcher matcherOk = patternOk.matcher(outputString);
				        if( matcherOk.find()) 
				        	Result = matcherOk.group(1);
			        }
			        
			        System.out.println("Cuenta Financiera: "+Result);
			        amd.DataRowExcel("FA", Result);
			        amd.DataRowExcel("GeneraContrato", outputString);
				return Result;
			}
			 /*OBSOLETO*/

			/*
			public void VentaEquipoPropio(int Ambiente, String CBP, String FA, String CodigoPlan, String NumeroCel, String Sim) throws IOException{

		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL="";
		if(Ambiente == 4)
			 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
		else if(Ambiente == 8)
			 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <prov:ProvideWirelessIVRRequest>\r\n" + 
				"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
				"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
				"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
				"         <prov:actionType>PR</prov:actionType>\r\n" + 
				"         <prov:reasonID>CREQ</prov:reasonID>\r\n" + 
				"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
				"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
				"         <prov:IMEI>123456789987654</prov:IMEI>\r\n" + 
				"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
				"         <prov:acquisitionType>OWN</prov:acquisitionType>\r\n" + 
				"         <prov:SIMSKU>7000437</prov:SIMSKU>\r\n" + 
				"         <prov:creditScore>5</prov:creditScore>\r\n" + 
				"         <prov:contractId>12345</prov:contractId>\r\n" + 
				"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
				"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
				"         <prov:onBehalfUser>230000727</prov:onBehalfUser>\r\n" + 
				"         <prov:equipmentMake>Segundo</prov:equipmentMake>\r\n" + 
				"         <prov:equipmentModel>Equipo</prov:equipmentModel>\r\n" + 
				"         <prov:user>FVMIVR</prov:user>\r\n" + 
				"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
				"      </prov:ProvideWirelessIVRRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>\r\n" + 
				"";
		amd.DataRowExcel("EstadoTrx", xmlInput);
		System.out.println(xmlInput);
		
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "";
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
		
		
		//Write the SOAP message formatted to the console.
		String formattedSOAPResponse = formatXMLVenta(outputString); // Write a separate method to format the XML input.
		
		/*
		 * Agrega el Response al archivo de Resultado
		 * */
			 /*
		 amd.DataRowExcel("Status", outputString);
		//return formattedSOAPResponse; 
	 }
	 */
			 /*
	 public void VentaEquipoCompraArriendo(int Ambiente, String TipoAdquisicion, String CBP, String FA, String CodigoPlan, String NumeroCel, String Sim, String Imsi) throws IOException{
		 
		//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="";
			if(Ambiente == 4)
				 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if(Ambiente == 8)
				 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if(Ambiente == 10)
				 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if (Ambiente ==12)
			    wsURL = "";
			
			String CompraArriendo="LSG";
			if(TipoAdquisicion.equalsIgnoreCase("Compra"))
				CompraArriendo="PUR";
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <prov:ProvideWirelessIVRRequest>\r\n" + 
					"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
					"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
					"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
					"         <prov:actionType>PR</prov:actionType>\r\n" + 
					"         <prov:reasonID>CREQ</prov:reasonID>\r\n" + 
					"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
					"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
					"         <prov:IMEI>"+Imsi+"</prov:IMEI>\r\n" + 
					"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
					"         <prov:acquisitionType>"+CompraArriendo+"</prov:acquisitionType>\r\n" + 
					"         <prov:equipmentSKU>70002567</prov:equipmentSKU>\r\n" + 
					"         <prov:SIMSKU>7000437</prov:SIMSKU>\r\n" + 
					"         <prov:creditScore>5</prov:creditScore>\r\n" + 
					"         <prov:contractId>12345</prov:contractId>\r\n" + 
					"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
					"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
					"         <prov:onBehalfUser>17281686K</prov:onBehalfUser>\r\n" + 
					"         <prov:equipmentCommitmentDuration>18</prov:equipmentCommitmentDuration>\r\n" + 
					"         <prov:user>FVMIVR</prov:user>\r\n" + 
					"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
					"      </prov:ProvideWirelessIVRRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>\r\n" + 
					"";
			amd.DataRowExcel("EstadoTrx", xmlInput);
			
			System.out.println(xmlInput);
			
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			
			
			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXMLVenta(outputString); // Write a separate method to format the XML input.
			/*
			 * Agrega el Response al archivo de Resultado
			 * */
			 /*
			 amd.DataRowExcel("Status", outputString);
			
			//return formattedSOAPResponse;		 
	 }
	 */
			 /*
	 public void VentaSinEquipo(int Ambiente, String TipoAdquisicion, String CBP, String FA, String CodigoPlan, String NumeroCel, String Sim, String Imsi, String Rut) throws IOException{
		 
			//Code to make a webservice HTTP request
				String responseString = "";
				String outputString = "";
				String wsURL="";
				if(Ambiente == 4)
					 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
				else if(Ambiente == 8)
					 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
				else if(Ambiente == 10)
					 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
				else if (Ambiente ==12)
				    wsURL = "";
				
				//String CompraArriendo="LSG";
				//if(TipoAdquisicion.equalsIgnoreCase("Compra"))
					//CompraArriendo="PUR";
				
				URL url = new URL(wsURL);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection)connection;
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				String xmlInput = "  <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
						"   <soapenv:Header/>\r\n" + 
						"   <soapenv:Body>\r\n" + 
						"      <prov:ProvideWirelessIVRRequest>\r\n" + 
						"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
						"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
						"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
						"         <prov:actionType>PR</prov:actionType>\r\n" + 
						"         <prov:reasonID>CREQ</prov:reasonID>\r\n" + 
						"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
						"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
						"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
						"         <prov:acquisitionType>OWN</prov:acquisitionType>\r\n" + 
						"         <prov:SIMSKU>7000450</prov:SIMSKU>\r\n" + 
						"         <prov:creditScore>5</prov:creditScore>\r\n" + 
						"         <prov:contractId>12345</prov:contractId>\r\n" + 
						"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
						"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
						"         <prov:onBehalfUser>164586650</prov:onBehalfUser>\r\n" + 
						"         <prov:equipmentMake>Segundo</prov:equipmentMake>\r\n" + 
						"         <prov:equipmentModel>Equipo</prov:equipmentModel>\r\n" + 
						"         <prov:user>FVMIVR</prov:user><!-- Puede ser FVMIVR o IVR-->\r\n" + 
						"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
						"      </prov:ProvideWirelessIVRRequest>\r\n" + 
						"   </soapenv:Body>\r\n" + 
						"</soapenv:Envelope>";
				amd.DataRowExcel("EstadoTrx", xmlInput);
				
				System.out.println(xmlInput);
				
				byte[] buffer = new byte[xmlInput.length()];
				buffer = xmlInput.getBytes();
				bout.write(buffer);
				byte[] b = bout.toByteArray();
				String SOAPAction = "";
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
				
				
				//Write the SOAP message formatted to the console.
				String formattedSOAPResponse = formatXMLVenta(outputString); // Write a separate method to format the XML input.
				
				//return formattedSOAPResponse;
		 }
*//*
	 private String formatXMLVenta(String outputString) {
	    	
		 System.out.println(outputString);
		 
	    	String Result="";
	    	Pattern pattern = Pattern.compile("<prov:orderId>(.*?)</prov:orderId>");
	    	Matcher matcher = pattern.matcher(outputString);
	        if( matcher.find())
	        	Result = matcher.group(1);
	        else
	        {
	        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
		    	Matcher matcherOk = patternOk.matcher(outputString);
		        if( matcherOk.find()) 
		        	Result = matcherOk.group(1);
	        }
	        
	        System.out.println(Result);
	        amd.DataRowExcel("Orden", Result+"A");
	        amd.DataRowExcel("GeneraContrato", outputString);
	        
		return Result;
	}*/
	 /*
	*/
	 
		public String ConsultaQDN(String Telefono) throws IOException {

			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="http://esb.clarochile.cl/integracion/oneamx/controladora/amdocs/proxy/queryHLR?wsdl";
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:quer=\"http://queryhlr.informationmanagement.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <quer:QueryHlrRequest>\r\n" + 
					"         <quer:subscriptionId>"+Telefono+"</quer:subscriptionId>\r\n" + 
					"         <quer:affiliateId/>\r\n" + 
					"         <quer:market/>\r\n" + 
					"         <quer:hlrId/>\r\n" + 
					"      </quer:QueryHlrRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>\r\n" + 
					"";
			
			System.out.println(xmlInput);
			
			amd.DataRowExcel("Orden", xmlInput);
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			
			System.out.println(outputString);
			
			
			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXMLConsultaQDN(outputString); // Write a separate method to format the XML input.
			
			return formattedSOAPResponse;
			}
		
		 private String formatXMLConsultaQDN(String outputString) {
		    	
		   Hashtable<String, String> ResultadoHash = 
                 new Hashtable<String, String>();
	    	
		    String Result="";
	    	String Estado="No Encontrado";
	    	int contador = 0;
	    	
	    	Pattern pattern = Pattern.compile("<hlrAttributeName>(.*?)</hlrAttributeName>");
	    	Matcher matcher = pattern.matcher(outputString);
	    	while( matcher.find())
		    {
		        Result = matcher.group(1);
		        System.out.println("Ojala funciones "+Result);
		        contador++;		        
		    }
	        if(contador>0)
	        	Estado="Encontrado";
	    	
	        System.out.println("Este valor obtengo contando "+contador);		        
	        
	        amd.DataRowExcel("CBP", Estado);
	        /*Para obtener el Response de descomenta la línea de abajo*/
	        amd.DataRowExcel("EstadoTrx", outputString);
		return Estado;
		}
		
	 public String AgregarEliminarServicio(Hashtable<String, String> Data, int Ambiente, String ChangeAction) throws IOException {
		 String Telefono     = Data.get("Telefono");		 
		 String Servicio     = Data.get("Tipo");
		 amd.DataRowExcel("FA", Telefono);
		 
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		
		String wsURL="";
		if(Ambiente == 4)
			 wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeServiceStatus?wsdl";
		else if(Ambiente == 8)
			 wsURL = "http://osbtestcln01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeServiceStatus?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://osbbuat.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeServiceStatus?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		else if(Ambiente == 2)/*PMX2*/
			 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeServiceStatus?wsdl";
		else if(Ambiente == 3)/*PMX3*/
			 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeServiceStatus?wsdl";
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		if(Servicio.equalsIgnoreCase("INTERNATIONAL_GROUP"))
		{
			System.out.println(Servicio);
			System.out.println("Paso 1 LDI");
			
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chan=\"http://changeservicestatus.selfmanagement.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <chan:ChangeServiceStatusRequest>\r\n" + 
					"         <chan:subscriberIdentifier>"+Telefono+"</chan:subscriberIdentifier>\r\n" + 
					"         <chan:serviceAction>\r\n" + 
					"            <chan:serviceCaption>"+Servicio+"</chan:serviceCaption>\r\n" + 
					"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
					"            <chan:serviceAttribute>\r\n" + 
					"               <chan:attributeName/>\r\n" + 
					"               <chan:attributeValue/>\r\n" + 
					"            </chan:serviceAttribute>\r\n" + 
					"         </chan:serviceAction>\r\n" + 
					"         <chan:serviceAction>\r\n" + 
					"            <chan:serviceCaption>LDI_VOICE_GROUP</chan:serviceCaption>\r\n" + 
					"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
					"            <chan:serviceAttribute>\r\n" + 
					"               <chan:attributeName/>\r\n" + 
					"               <chan:attributeValue/>\r\n" + 
					"            </chan:serviceAttribute>\r\n" + 
					"         </chan:serviceAction>\r\n" + 
					"         <chan:context>\r\n" + 
					"            <chan:tokenID/>\r\n" + 
					"            <chan:userID/>\r\n" + 
					"         </chan:context>\r\n" + 
					"      </chan:ChangeServiceStatusRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
			
			System.out.println(xmlInput);		
			
			amd.DataRowExcel("Status", xmlInput);
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
		}
		else if (Servicio.equalsIgnoreCase("ROAMING_GROUP"))
			{
			System.out.println(Servicio);
			System.out.println("Paso 2 ROAMING");
			
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chan=\"http://changeservicestatus.selfmanagement.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <chan:ChangeServiceStatusRequest>\r\n" + 
					"         <chan:subscriberIdentifier>"+Telefono+"</chan:subscriberIdentifier>\r\n" + 
					"         <chan:serviceAction>\r\n" + 
					"            <chan:serviceCaption>"+Servicio+"</chan:serviceCaption>\r\n" + 
					"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
					"            <chan:serviceAttribute>\r\n" + 
					"               <chan:attributeName/>\r\n" + 
					"               <chan:attributeValue/>\r\n" + 
					"            </chan:serviceAttribute>\r\n" + 
					"         </chan:serviceAction>\r\n" + 
					"         <chan:serviceAction>\r\n" + 
					"            <chan:serviceCaption>Roaming_Voice</chan:serviceCaption>\r\n" + 
					"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
					"            <chan:serviceAttribute>\r\n" + 
					"               <chan:attributeName/>\r\n" + 
					"               <chan:attributeValue/>\r\n" + 
					"            </chan:serviceAttribute>\r\n" + 
					"         </chan:serviceAction>\r\n" + 
					"         <chan:serviceAction>\r\n" + 
					"            <chan:serviceCaption>Roaming_GPRS</chan:serviceCaption>\r\n" + 
					"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
					"            <chan:serviceAttribute>\r\n" + 
					"               <chan:attributeName/>\r\n" + 
					"               <chan:attributeValue/>\r\n" + 
					"            </chan:serviceAttribute>\r\n" + 
					"         </chan:serviceAction>\r\n" + 
					"         <chan:serviceAction>\r\n" + 
					"            <chan:serviceCaption>Roaming_SMS</chan:serviceCaption>\r\n" + 
					"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
					"            <chan:serviceAttribute>\r\n" + 
					"               <chan:attributeName/>\r\n" + 
					"               <chan:attributeValue/>\r\n" + 
					"            </chan:serviceAttribute>\r\n" + 
					"         </chan:serviceAction>\r\n" + 
					"         <chan:context>\r\n" + 
					"            <chan:tokenID/>\r\n" + 
					"            <chan:userID/>\r\n" + 
					"         </chan:context>\r\n" + 
					"      </chan:ChangeServiceStatusRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
			
			System.out.println(xmlInput);		
			
			amd.DataRowExcel("Status", xmlInput);
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			}
		else{
			System.out.println(Servicio);
			System.out.println("Paso 3 OTRO");
			
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chan=\"http://changeservicestatus.selfmanagement.schema.amx.com\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <chan:ChangeServiceStatusRequest>\r\n" + 
				"         <chan:subscriberIdentifier>"+Telefono+"</chan:subscriberIdentifier>\r\n" + 
				"                <chan:serviceAction>\r\n" + 
				"            <chan:serviceCaption>"+Servicio+"</chan:serviceCaption>\r\n" + 
				"            <chan:serviceChangeAction>"+ChangeAction+"</chan:serviceChangeAction>\r\n" + 
				"            <chan:serviceAttribute>\r\n" + 
				"               <chan:attributeName/>\r\n" + 
				"               <chan:attributeValue/>\r\n" + 
				"            </chan:serviceAttribute>\r\n" + 
				"         </chan:serviceAction>\r\n" + 
				"           <chan:context>\r\n" + 
				"            <chan:tokenID/>\r\n" + 
				"            <chan:userID/>\r\n" + 
				"           </chan:context>\r\n" + 
				"      </chan:ChangeServiceStatusRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
			System.out.println(xmlInput);		
			
			amd.DataRowExcel("Status", xmlInput);
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			}
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
		
		System.out.println(outputString);
		
		
		//Write the SOAP message formatted to the console.
		String formattedSOAPResponse = formatXMLAgregarEliminarServicio(outputString); // Write a separate method to format the XML input.
		return formattedSOAPResponse;
		}
	 
 private String formatXMLAgregarEliminarServicio(String outputString) {
    	
	 Hashtable<String, String> ResultadoHash = 
             new Hashtable<String, String>();
    	
	    String Result="";
    	String Result2="Éxito";
    	
    	Pattern pattern = Pattern.compile("<orderId>(.*?)</orderId>");
    	Matcher matcher = pattern.matcher(outputString);
        if( matcher.find())
        	Result = matcher.group(1);
        else
        {
        	Result2="Error";
        	Pattern patternOk = Pattern.compile("<errorDescription>(.*?)</errorDescription>");
	    	Matcher matcherOk = patternOk.matcher(outputString);
	        if( matcherOk.find()) 
	        	Result = matcherOk.group(1);
        }
        System.out.println(Result);
    amd.DataRowExcel("CBP", Result2);
    amd.DataRowExcel("Ciclo", Result);
    amd.DataRowExcel("Orden", outputString);
	return Result;
	}
 
 /*
  * Codigo Nuevo para Portabilidad con equipo Propio, el Operador de Origen es Fijo. (13-05-2022)
  * */
 /*
 public void PortabilidadEquipoPropio(int Ambiente, String CBP, String FA, String CodigoPlan, String NumeroCel, String Sim,String IdProveedor) throws IOException{

	//Code to make a webservice HTTP request
	String responseString = "";
	String outputString = "";
	String wsURL="";
	if(Ambiente == 4)
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
	else if(Ambiente == 8)
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
	else if(Ambiente == 10)
		 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
	else if (Ambiente ==12)
	    wsURL = "";
	
	URL url = new URL(wsURL);
	URLConnection connection = url.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection)connection;
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <prov:ProvideWirelessIVRRequest>\r\n" + 
			"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
			"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
			"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
			"         <prov:actionType>PI</prov:actionType>\r\n" + 
			"         <prov:reasonID>CREQ</prov:reasonID>\r\n" + 
			"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
			"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
			"         <prov:IMEI>123456789987654</prov:IMEI>\r\n" + 
			"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
			"         <prov:acquisitionType>OWN</prov:acquisitionType>\r\n" + 
			"         <prov:externalProvider>"+IdProveedor+"</prov:externalProvider>\r\n"+
			"         <prov:SIMSKU>7000437</prov:SIMSKU>\r\n" + 
			"         <prov:creditScore>5</prov:creditScore>\r\n" + 
			"         <prov:contractId>12345</prov:contractId>\r\n" + 
			"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
			"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
			"         <prov:onBehalfUser>17281686K</prov:onBehalfUser>\r\n" + 
			"         <prov:equipmentMake>Segundo</prov:equipmentMake>\r\n" + 
			"         <prov:equipmentModel>Equipo</prov:equipmentModel>\r\n" + 
			"         <prov:user>FVMIVR</prov:user>\r\n" + 
			"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
			"         <prov:ceaseServiceCode>0</prov:ceaseServiceCode>\r\n"+
			"         <prov:validationType>Digital</prov:validationType>\r\n"+
			"         <prov:traceabilityCode></prov:traceabilityCode>\r\n"+
			"      </prov:ProvideWirelessIVRRequest>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>\r\n" + 
			"";
	amd.DataRowExcel("EstadoTrx", xmlInput);
	System.out.println(xmlInput);
	
	byte[] buffer = new byte[xmlInput.length()];
	buffer = xmlInput.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	String SOAPAction = "";
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
	//Write the SOAP message formatted to the console.
	String formattedSOAPResponse = formatXMLVenta(outputString); // Write a separate method to format the XML input.
	
	System.out.println(outputString);
	/*
	 * Agrega el Response al archivo de Resultado
	 * *//*
	 amd.DataRowExcel("Status", outputString);
	//return formattedSOAPResponse;
	 
 }	
 */
 /*
  * Codigo Nuevo para Portabilidad con equipo Propio, el Operador de Origen es Fijo. (13-05-2022)
  * */
 /*
 public void PortabilidadEquipoCompraArriendo(int Ambiente, String TipoAdquisicion, String CBP, String FA, String CodigoPlan, String NumeroCel, String Sim, String Imsi, String IdProveedor) throws IOException{
	 
	//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL="";
		if(Ambiente == 4)
			 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
		else if(Ambiente == 8)
			 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		
		String CompraArriendo="LSG";
		if(TipoAdquisicion.equalsIgnoreCase("Compra"))
			CompraArriendo="PUR";
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <prov:ProvideWirelessIVRRequest>\r\n" + 
				"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
				"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
				"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
				"         <prov:actionType>PI</prov:actionType>\r\n" + 
				"         <prov:reasonID>CREQ</prov:reasonID>\r\n" + 
				"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
				"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
				"         <prov:IMEI>"+Imsi+"</prov:IMEI>\r\n" + 
				"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
				"         <prov:acquisitionType>"+CompraArriendo+"</prov:acquisitionType>\r\n" + 
				"         <prov:externalProvider>"+IdProveedor+"</prov:externalProvider>\r\n"+
				"         <prov:equipmentSKU>70002567</prov:equipmentSKU>\r\n" + 
				"         <prov:SIMSKU>7000437</prov:SIMSKU>\r\n" + 
				"         <prov:creditScore>5</prov:creditScore>\r\n" + 
				"         <prov:contractId>12345</prov:contractId>\r\n" + 
				"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
				"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
				"         <prov:onBehalfUser>17281686K</prov:onBehalfUser>\r\n" + 
				"         <prov:equipmentCommitmentDuration>18</prov:equipmentCommitmentDuration>\r\n" + 
				"         <prov:user>FVMIVR</prov:user>\r\n" + 
				"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
				"         <prov:ceaseServiceCode>0</prov:ceaseServiceCode>\r\n"+
				"         <prov:validationType>Digital</prov:validationType>\r\n"+
				"         <prov:traceabilityCode></prov:traceabilityCode>\r\n"+
				"      </prov:ProvideWirelessIVRRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>\r\n" + 
				"";
		amd.DataRowExcel("EstadoTrx", xmlInput);
		
		System.out.println(xmlInput);
		
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "";
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
		
		
		//Write the SOAP message formatted to the console.
		String formattedSOAPResponse = formatXMLVenta(outputString); // Write a separate method to format the XML input.
		
		System.out.println(outputString);
		/*
		 * Agrega el Response al archivo de Resultado
		 * */
 /*
		 amd.DataRowExcel("Status", outputString);
		
		//return formattedSOAPResponse;		 
 }
 */
 
 //Agregando mas servicios
 
 public void CambioOferta(Hashtable<String, String> Data,String CBP,  int Ambiente) throws IOException {
		 String Telefono     = Data.get("Telefono");		 
		 String Plan     = Data.get("NombrePlan");
		 String Caso     = Data.get("Caso");
		 amd.DataRowExcel("FA", Telefono);
		 amd.DataRowExcel("Ciclo", Plan);
		 amd.DataRowExcel("Rut", Caso);
		 
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		
		String wsURL="";
		if(Ambiente == 4)
			 wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/replaceOffer?wsdl";
		else if(Ambiente == 8)
			 wsURL = "http://osbtestcln01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/replaceOffer?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://osbbuat.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/replaceOffer?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		else if(Ambiente == 2)/*PMX2*/
			 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/replaceOffer?wsdl";
		else if(Ambiente == 3)/*PMX3*/
			 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/replaceOffer?wsdl";
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rep=\"http://replaceoffer.offer.schema.amx.com\">\r\n" + 
	    		"   <soapenv:Header/>\r\n" + 
	    		"   <soapenv:Body>\r\n" + 
	    		"      <rep:ReplaceOfferRequest>\r\n" + 
	    		"         <rep:Sale_Channel>IV</rep:Sale_Channel>\r\n" + 
	    		"         <rep:cBP_ID>"+CBP+"</rep:cBP_ID>\r\n" + 
	    		"         <rep:credit_Score>5</rep:credit_Score>\r\n" + 
	    		"         <rep:mSISDN>"+Telefono+"</rep:mSISDN>\r\n" + 
	    		"         <rep:targetOffer>"+Plan+"</rep:targetOffer>\r\n" + 
	    		"         <!--Optional:-->\r\n" + 
	    		"      </rep:ReplaceOfferRequest>\r\n" + 
	    		"   </soapenv:Body>\r\n" + 
	    		"</soapenv:Envelope>";
		System.out.println(xmlInput);									
		amd.DataRowExcel("Orden", xmlInput);
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "";
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
		
		amd.DataRowExcel("Plan", outputString);
		System.out.println(outputString);
		
		//Write the SOAP message formatted to the console.
		formatXMLCambiOferta(outputString); // Write a separate method to format the XML input.
		
	}
 
 private void formatXMLCambiOferta(String outputString) {
 	
	   Hashtable<String, String> ResultadoHash = 
           new Hashtable<String, String>();
  	
	    String Result="";
   	String Result2="Éxito";
   	
   	Pattern pattern = Pattern.compile("<orderId>(.*?)</orderId>");
   	Matcher matcher = pattern.matcher(outputString);
       if( matcher.find())
       	Result = matcher.group(1);
       else
       {
       	Result2="Error";
       	Pattern patternOk = Pattern.compile("<errorDescription>(.*?)</errorDescription>");
	    	Matcher matcherOk = patternOk.matcher(outputString);
	        if( matcherOk.find()) 
	        	Result = matcherOk.group(1);
       }       
       System.out.println(Result);
      amd.DataRowExcel("Status", Result);
      amd.DataRowExcel("CBP", Result2);	
	}
 
 public void TraspasoTitularidad(Hashtable<String, String> Data, int Ambiente, String CBPOriginal, String CBPNueva) throws IOException {
	 String Telefono     = Data.get("Telefono");		 
	 String Servicio     = Data.get("Tipo");
	 amd.DataRowExcel("FA", Telefono);
	 
	//Code to make a webservice HTTP request
	String responseString = "";
	String outputString = "";
	
	String wsURL="";
	if(Ambiente == 4)
		 wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeOwnership?wsdl";
	else if(Ambiente == 8)
		 wsURL = "http://osbtestcln01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeOwnership?wsdl";
	else if(Ambiente == 10)
		 wsURL = "http://osbbuat.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeOwnership?wsdl";
	else if (Ambiente ==12)
	    wsURL = "";
	else if(Ambiente == 2)/*PMX2*/
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeOwnership?wsdl";
	else if(Ambiente == 3)/*PMX3*/
		 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeOwnership?wsdl";
	
	URL url = new URL(wsURL);
	URLConnection connection = url.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection)connection;
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chan=\"http://changeownership.management.schema.amx.com\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <chan:ChangeOwnershipRequest>\r\n" + 
			"         <chan:sourceCBPId>"+CBPOriginal+"</chan:sourceCBPId>\r\n" + 
			"         <chan:targetCBPId>"+CBPNueva+"</chan:targetCBPId>\r\n" + 
			"         <chan:salesChannelX9>IV</chan:salesChannelX9>\r\n" + 
			"         <chan:MSISDN>"+Telefono+"</chan:MSISDN>\r\n" + 
			"         <chan:salesPersonRUT>159661652</chan:salesPersonRUT>\r\n" + 
			"      </chan:ChangeOwnershipRequest>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>";
	
	System.out.println(xmlInput);
	amd.DataRowExcel("Orden", xmlInput);
	byte[] buffer = new byte[xmlInput.length()];
	buffer = xmlInput.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	String SOAPAction = "";
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
	
	amd.DataRowExcel("Plan", outputString);
	System.out.println(outputString);	
	//Write the SOAP message formatted to the console.
	formatXMLTraspasoTitularidad(outputString); // Write a separate method to format the XML input.
	}
 
 private void formatXMLTraspasoTitularidad(String outputString) {
	 	
	   Hashtable<String, String> ResultadoHash = 
         new Hashtable<String, String>();
	
	String Result="";
 	String Result2="Error";
 	String Result3="";
 	
 	
 	Pattern pattern = Pattern.compile("<chan:orderId>(.*?)</chan:orderId>");
 	Matcher matcher = pattern.matcher(outputString);
     if( matcher.find())
     {	 
     	Result = matcher.group(1);
     	Result2="Éxito";
     }
     	
     Pattern patternOk = Pattern.compile("<chan:errorDescription>(.*?)</chan:errorDescription>");
	 Matcher matcherOk = patternOk.matcher(outputString);
	 if(matcherOk.find()) 
	    Result3 = matcherOk.group(1);
            
	 System.out.println(Result);
    amd.DataRowExcel("Status", Result);
    amd.DataRowExcel("CBP", Result2);
    amd.DataRowExcel("Ciclo", Result3);
    
	}
 
 public Hashtable<String, String> IdentificarLlamente(Hashtable<String, String> Data, int Ambiente) throws IOException {
	String Telefono     = Data.get("Telefono");		 
	String Servicio     = Data.get("Tipo");
	
	try
	{
	//Code to make a webservice HTTP request
	String responseString = "";
	String outputString = "";
	
	String wsURL="";
	if(Ambiente == 4)
		 wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/identifyCaller?wsdl";
	else if(Ambiente == 8)
		 wsURL = "http://osbtestcln01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/identifyCaller?wsdl";
	else if(Ambiente == 10)
		 wsURL = "http://osbbuat.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/identifyCaller?wsdl";
	else if (Ambiente ==12)
	    wsURL = "";
	else if(Ambiente == 2)/*PMX2*/
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/identifyCaller?wsdl";
	else if(Ambiente == 3)/*PMX3*/
		 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/identifyCaller?wsdl";
	
	URL url = new URL(wsURL);
	URLConnection connection = url.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection)connection;
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:iden=\"http://identifycaller.customermanagement.schema.amx.com\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <iden:IdentifyCallerRequest>\r\n" + 
			"         <!--Optional:-->\r\n" + 
			"         <iden:ani>"+Telefono+"</iden:ani>\r\n" + 
			"      </iden:IdentifyCallerRequest>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>";
										
	byte[] buffer = new byte[xmlInput.length()];
	buffer = xmlInput.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	String SOAPAction = "";
	
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
	
	System.out.println(outputString);	
	//Write the SOAP message formatted to the console.
	Hashtable<String, String> Cliente = formatXMLIdentificarLlamente(outputString); // Write a separate method to format the XML input.
	return Cliente;
	}
	catch(Exception e)
	{
		System.out.println("Este es mi Error  "+e.toString());
	}
	return null;
 }
 private Hashtable<String, String> formatXMLIdentificarLlamente(String outputString) {
 	
	 Hashtable<String, String> ResultadoHash = 
             new Hashtable<String, String>();
    	
	    String Result="";
    	String Result2="";
    	
    	Pattern pattern = Pattern.compile("<iden:cbpId>(.*?)</iden:cbpId>");
    	Matcher matcher = pattern.matcher(outputString);
        if( matcher.find())
        	Result = matcher.group(1);
        else
        {
        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
	    	Matcher matcherOk = patternOk.matcher(outputString);
	        if( matcherOk.find()) 
	        	Result = matcherOk.group(1);
        }
        
        Pattern pattern2 = Pattern.compile("<iden:contactId>(.*?)</iden:contactId>");
    	Matcher matcher2 = pattern2.matcher(outputString);
        if( matcher2.find())
        	Result2 = matcher2.group(1);
        else
        {
        	Pattern patternOk2 = Pattern.compile("<faultstring>(.*?)</faultstring>");
	    	Matcher matcherOk2 = patternOk2.matcher(outputString);
	        if( matcherOk2.find()) 
	        	Result2 = matcherOk2.group(1);
        }
        
        ResultadoHash.put("cbpId", Result);
        ResultadoHash.put("contactId", Result2);
        
	return ResultadoHash;
}
 
 
 
 public void TerminoContrato(Hashtable<String, String> Data, int Ambiente) throws IOException {
	 String Telefono     = Data.get("Telefono");		 
	 String Servicio     = Data.get("Tipo");
	 amd.DataRowExcel("FA", Telefono);
	 
	//Code to make a webservice HTTP request
	String responseString = "";
	String outputString = "";
	
	String wsURL="";
	if(Ambiente == 4)
		 wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeSuscriberStatus?wsdl";
	else if(Ambiente == 8)
		 wsURL = "http://osbtestcln01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeSuscriberStatus?wsdl";
	else if(Ambiente == 10)
		 wsURL = "http://osbbuat.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeSuscriberStatus?wsdl";
	else if (Ambiente ==12)
	    wsURL = "";
	else if(Ambiente == 2)/*PMX2*/
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeSuscriberStatus?wsdl";
	else if(Ambiente == 3)/*PMX3*/
		 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/changeSuscriberStatus?wsdl";
	
	
	URL url = new URL(wsURL);
	URLConnection connection = url.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection)connection;
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	String xmlInput = "";
										
	amd.DataRowExcel("Status", xmlInput);
	byte[] buffer = new byte[xmlInput.length()];
	buffer = xmlInput.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	String SOAPAction = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
			"   <SOAP-ENV:Header/>\r\n" + 
			"   <S:Body>\r\n" + 
			"      <ns2:ChangeSubscriberStatusRequest xmlns:ns2=\"http://changesubscriberstatus.selfmanagement.schema.amx.com\" xmlns=\"http://commonsexceptions.schema.amx.com\">\r\n" + 
			"         <ns2:changeStatusAction>CE</ns2:changeStatusAction>\r\n" + 
			"         <ns2:reasonCode>NPDEAC</ns2:reasonCode>\r\n" + 
			"         <ns2:subscriberIdentifier>56946369995</ns2:subscriberIdentifier>\r\n" + 
			"         <ns2:outGoingOperatorType>0</ns2:outGoingOperatorType>\r\n" + 
			"         <ns2:ceaseServiceCode>1</ns2:ceaseServiceCode>\r\n" + 
			"      </ns2:ChangeSubscriberStatusRequest>\r\n" + 
			"   </S:Body>\r\n" + 
			"</S:Envelope>";
	
	
	System.out.println(xmlInput);
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
	
	System.out.println(outputString);	
	//Write the SOAP message formatted to the console.
	//String formattedSOAPResponse = formatXMLAgregarEliminarServicio(outputString); // Write a separate method to format the XML input.
	}
 
 public void ConsultaPassCorrecta(Hashtable<String, String> Data, int Ambiente) throws IOException {
	 String Telefono     = Data.get("Telefono");		 
	 String Servicio     = Data.get("Tipo");
	 amd.DataRowExcel("FA", Telefono);
	 
	//Code to make a webservice HTTP request
	String responseString = "";
	String outputString = "";
	
	String wsURL="";
	if(Ambiente == 4)
		 wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/amdocs/proxy/login?wsdl";
	else if(Ambiente == 8)
		 wsURL = "http://osbtestcln01.clarochile.cl:80/integracion/oneamx/controladora/amdocs/proxy/login?wsdl";
	else if(Ambiente == 10)
		 wsURL = "http://osbbuat.clarochile.cl:80/integracion/oneamx/controladora/amdocs/proxy/login?wsdl";
	else if (Ambiente ==12)
	    wsURL = "";
	else if(Ambiente == 2)/*PMX2*/
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/amdocs/proxy/login?wsdl";
	else if(Ambiente == 3)/*PMX3*/
		 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/amdocs/proxy/login?wsdl";
	
	
	URL url = new URL(wsURL);
	URLConnection connection = url.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection)connection;
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:log=\"http://login.management.schema.amx.com\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <log:LoginRequest>\r\n" + 
			"         <log:userName>17281686K</log:userName>\r\n" + 
			"         <log:password>qA202110*</log:password>\r\n" + 
			"         <log:market/>\r\n" + 
			"         <log:affiliate/>\r\n" + 
			"      </log:LoginRequest>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>";
	
	System.out.println(xmlInput);									
	amd.DataRowExcel("Status", xmlInput);
	byte[] buffer = new byte[xmlInput.length()];
	buffer = xmlInput.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	String SOAPAction = "";
	
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
	
	System.out.println(outputString);	
	//Write the SOAP message formatted to the console.
	//String formattedSOAPResponse = formatXMLAgregarEliminarServicio(outputString); // Write a separate method to format the XML input.
	}
 /*
 public void MigracionEquipoPropio(int Ambiente, String CBP, String FA, String CodigoPlan, String NumeroCel, String Sim) throws IOException{

	//Code to make a webservice HTTP request
	String responseString = "";
	String outputString = "";
	String wsURL="";
	if(Ambiente == 4)
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
	else if(Ambiente == 8)
		 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
	else if(Ambiente == 10)
		 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
	else if (Ambiente ==12)
	    wsURL = "";
	
	URL url = new URL(wsURL);
	URLConnection connection = url.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection)connection;
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <prov:ProvideWirelessIVRRequest>\r\n" + 
			"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
			"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
			"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
			"         <prov:actionType>PR</prov:actionType>\r\n" + 
			"         <prov:reasonID>PRMIG</prov:reasonID>\r\n" + 
			"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
			"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
			"         <prov:IMEI>123456789987654</prov:IMEI>\r\n" + 
			"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
			"         <prov:acquisitionType>OWN</prov:acquisitionType>\r\n" + 
			"         <prov:SIMSKU>7000437</prov:SIMSKU>\r\n" + 
			"         <prov:creditScore>5</prov:creditScore>\r\n" + 
			"         <prov:contractId>12345</prov:contractId>\r\n" + 
			"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
			"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
			"         <prov:onBehalfUser>230000727</prov:onBehalfUser>\r\n" + 
			"         <prov:equipmentMake>Segundo</prov:equipmentMake>\r\n" + 
			"         <prov:equipmentModel>Equipo</prov:equipmentModel>\r\n" + 
			"         <prov:user>FVMIVR</prov:user>\r\n" + 
			"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
			"      </prov:ProvideWirelessIVRRequest>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>\r\n" + 
			"";
	amd.DataRowExcel("EstadoTrx", xmlInput);
	System.out.println(xmlInput);
	
	byte[] buffer = new byte[xmlInput.length()];
	buffer = xmlInput.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	String SOAPAction = "";
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
	
	
	//Write the SOAP message formatted to the console.
	String formattedSOAPResponse = formatXMLVenta(outputString); // Write a separate method to format the XML input.
	System.out.println(outputString);
	/*
	 * Agrega el Response al archivo de Resultado
	 * *//*
	 amd.DataRowExcel("Status", outputString);
	//return formattedSOAPResponse; 
 }
 */
 public Hashtable<String, String> ConsultaDeuda(Hashtable<String, String> Data, int Ambiente) throws IOException {
		String CuentaFA = Data.get("NombrePlan");		 
				
		try
		{
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		
		String wsURL="";
		if(Ambiente == 4)
			wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/controladora/ivr/proxy/getFAOpenBalance?wsdl";
		else if(Ambiente == 8)
			wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/controladora/ivr/proxy/getFAOpenBalance?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/controladora/ivr/proxy/getFAOpenBalance?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		else if(Ambiente == 2)/*PMX2*/
			 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/controladora/ivr/proxy/getFAOpenBalance?wsdl";
		else if(Ambiente == 3)/*PMX3*/
			 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/controladora/ivr/proxy/getFAOpenBalance?wsdl";
		
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:get=\"http://getfaopenbalance.billinginquiry.schema.amx.com\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <get:GetFAOpenBalanceRequest>\r\n" + 
				"         <!--Optional:-->\r\n" + 
				"         <get:AccountIDInfoDt>\r\n" + 
				"            <get:accountId>"+CuentaFA+"</get:accountId>\r\n" + 
				"         </get:AccountIDInfoDt>\r\n" + 
				"      </get:GetFAOpenBalanceRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
											
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "";
		
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
		System.out.println(xmlInput);
		System.out.println(outputString);	
		//Write the SOAP message formatted to the console.
		Hashtable<String, String> Deuda = formatXMLConsultaDeuda(outputString); // Write a separate method to format the XML input.
		return Deuda;
		}
		catch(Exception e)
		{
			System.out.println("Este es mi Error  "+e.toString());
		}
		return null;
	 }
	 private Hashtable<String, String> formatXMLConsultaDeuda(String outputString) {
	 	
		 Hashtable<String, String> ResultadoHash = 
	             new Hashtable<String, String>();
	    	
		    String Result="";
	    	String Result1="";
	    	
	    	/* OBTIENE SALDO DE LA CUENTA */
	    	Pattern pattern = Pattern.compile("<get:aRBalance>(.*?)</get:aRBalance>");
	    	Matcher matcher = pattern.matcher(outputString);
	        if( matcher.find())
	        	Result = matcher.group(1);
	        else
	        {
	        	Pattern patternOk = Pattern.compile("<ns2:message>(.*?)</ns2:message>");
		    	Matcher matcherOk = patternOk.matcher(outputString);
		        if( matcherOk.find()) 
		        	Result = matcherOk.group(1);
	        }
	        /* OBTIENE NUMERO DE DOCUMENTO DE LA CUENTA */
	        Pattern pattern1 = Pattern.compile("<get:invoiceId>(.*?)</get:invoiceId>");
	    	Matcher matcher1 = pattern1.matcher(outputString);
	        if( matcher1.find())
	        	Result1 = matcher1.group(1);
	        else
	        {
	        	Pattern patternOk1 = Pattern.compile("<faultstring>(.*?)</faultstring>");
		    	Matcher matcherOk1 = patternOk1.matcher(outputString);
		        if( matcherOk1.find()) 
		        	Result1 = matcherOk1.group(1);
	        }
	        	        
	        ResultadoHash.put("ARBalance", Result);
	        ResultadoHash.put("InvoiceId", Result1);
	        
	        //System.out.println(Result);
	        //System.out.println(Result1);
	        
		return ResultadoHash;
		
		  
			
	}
 
	 public void PagoDeuda(String FA,String Saldo,String Boleta,int Ambiente) throws IOException {

		 amd.DataRowExcel("FA", FA);
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		
		String wsURL="";
		if(Ambiente == 4)
			 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/controladora/cashier/proxy/createPayment?wsdl";
		else if(Ambiente == 8)
			 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/controladora/cashier/proxy/createPayment?wsdl";
		else if(Ambiente == 10)
			 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/controladora/cashier/proxy/createPayment?wsdl";
		else if (Ambiente ==12)
		    wsURL = "";
		else if(Ambiente == 2)/*PMX2*/
			 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/controladora/cashier/proxy/createPayment?wsdl";
		else if(Ambiente == 3)/*PMX3*/
			 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/controladora/cashier/proxy/createPayment?wsdl";
		
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cre=\"http://createpayment.receivablesmanagement.schema.amx.com\">\\r\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <cre:CreatePaymentRequest>\r\n" + 
				"         <cre:paymentApplicationItemScreen>\r\n" + 
				"            <cre:accountCurrency>CLP</cre:accountCurrency>\r\n" + 
				"            <cre:accountId>"+FA+"</cre:accountId>\r\n" + 
				"            <cre:amount>"+Saldo+"</cre:amount>\r\n" + 
				"            <cre:currency>CLP</cre:currency>\r\n" + 
				"            <cre:invoiceId>"+Boleta+"</cre:invoiceId>\r\n" + 
				"         </cre:paymentApplicationItemScreen>\r\n" + 
				"         <cre:cashPaymentDetailsScreen>\r\n" + 
				"            <cre:accountCurrency>CLP</cre:accountCurrency>\r\n" + 
				"            <cre:accountId>"+FA+"</cre:accountId>\r\n" + 
				"            <cre:amount>"+Saldo+"</cre:amount>\r\n" + 
				"            <cre:currency>CLP</cre:currency>\r\n" + 
				"            <cre:depositChoice>N</cre:depositChoice>\r\n" + 
				"            <cre:depositDate>2022-09-02</cre:depositDate>\r\n" + 
				"            <cre:genericCustDt/>\r\n" + 
				"            <cre:paymentCustDt>\r\n" + 
				"               <cre:actualAmount>"+Saldo+"</cre:actualAmount>\r\n" + 
				"               <cre:paymentMethod>CA</cre:paymentMethod>\r\n" + 
				"            </cre:paymentCustDt>\r\n" + 
				"            <cre:paymentDetailsCustDt>\r\n" + 
				"               <cre:paymentSubMethod>EF</cre:paymentSubMethod>\r\n" + 
				"               <cre:paymentReceiptNumber>52426101</cre:paymentReceiptNumber>\r\n" + 
				"            </cre:paymentDetailsCustDt>\r\n" + 
				"            <cre:paymentId/>\r\n" + 
				"            <cre:paymentMethod>CA</cre:paymentMethod>\r\n" + 
				"            <cre:paymentSourceId>PP000306</cre:paymentSourceId>\r\n" + 
				"            <cre:paymentSourceType>CLR</cre:paymentSourceType>\r\n" + 
				"         </cre:cashPaymentDetailsScreen>\r\n" + 
				"      </cre:CreatePaymentRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		System.out.println(xmlInput);
		amd.DataRowExcel("Sim", xmlInput);
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "";
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
		
		amd.DataRowExcel("Imsi", outputString);
		System.out.println(outputString);	
		//Write the SOAP message formatted to the console.
		formatXMLPagoDeuda(outputString); // Write a separate method to format the XML input.
		}
	 
	 private void formatXMLPagoDeuda(String outputString) {
		 	
		   Hashtable<String, String> ResultadoHash = 
	         new Hashtable<String, String>();
		
		String Result="";
	 	String Result2="Error";
	 	String Result3="";
	 	
	 	
	 	Pattern pattern = Pattern.compile("<cre:ccPymConfirmationNo>(.*?)</cre:ccPymConfirmationNo>");
	 	Matcher matcher = pattern.matcher(outputString);
	     if( matcher.find())
	     {	 
	     	Result = matcher.group(1);
	     	Result2="Éxito";
	     }
	     	
	     Pattern patternOk = Pattern.compile("<com:errorCode>(.*?)</com:errorCode>");
		 Matcher matcherOk = patternOk.matcher(outputString);
		 if(matcherOk.find()) 
		   Result3 = matcherOk.group(1);
	            
			System.out.println(Result);
		    amd.DataRowExcel("Plan", Result);
		    amd.DataRowExcel("Status", Result2);
		    amd.DataRowExcel("Error", Result3);
		    
		}	 
	 
		/*
		 *CONSULTA CONTACTID POR RUT 
		 * */
	   public String ConsultaPersona(int Ambiente, String Rut) throws IOException {
		 	
		 	System.out.println("Ambiente: "+Ambiente);
		 	System.out.println("Rut: "+Rut);
			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="";
			if(Ambiente == 4)
				 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
			else if(Ambiente == 8)
				 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
			else if(Ambiente == 10)
				 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
			else if (Ambiente ==12)
			    wsURL = "";
			else if(Ambiente == 2)/*PMX2*/
				 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
			else if(Ambiente == 3)/*PMX3*/
				 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/crm/controladora/proxy/ContactProviderService?wsdl";
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			/*String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://contactService.contact.ws.crm.crmimpl.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <con:retrieveContactDetails>\r\n" + 
					"         <contactRequest>\r\n" + 
					"            <identifierInfo>\r\n" + 
					"               <identifierNumber>"+Rut+"</identifierNumber>\r\n" + 
					"               <identifierType>RUT</identifierType>\r\n" + 
					"               <isPrimary>true</isPrimary>\r\n" + 
					"            </identifierInfo>\r\n" + 
					"         </contactRequest>\r\n" + 
					"      </con:retrieveContactDetails>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
			*/
			
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://contactService.contact.ws.crm.crmimpl.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <con:getCustomerEntitiesByIdentifier>\r\n" + 
					"         <identifierInfo>\r\n" + 
					"            <identifierNumber>"+Rut+"</identifierNumber>\r\n" + 
					"            <identifierType>RUT</identifierType>\r\n" + 
					"            <isPrimary/>\r\n" + 
					"         </identifierInfo>\r\n" + 
					"      </con:getCustomerEntitiesByIdentifier>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
			
			
			
			System.out.println(xmlInput);
			//amd.DataRowExcel("Sim", xmlInput);
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			//amd.DataRowExcel("Imsi", outputString);
			System.out.println(outputString);	
			
			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXMLConsultaPersona(outputString); // Write a separate method to format the XML input.
			
			return formattedSOAPResponse;
			}
		
		 private String formatXMLConsultaPersona(String outputString) {
		    	
		    	String Result="";
		    	Pattern pattern = Pattern.compile("<contactId>(.*?)</contactId>");
		    	Matcher matcher = pattern.matcher(outputString);
		        if( matcher.find())
		        	Result = matcher.group(1);
		        else
		        {
		        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
			    	Matcher matcherOk = patternOk.matcher(outputString);
			        if( matcherOk.find()) 
			        	Result = matcherOk.group(1);
		        }
		        System.out.println("Contacto"+Result);
		        amd.DataRowExcel("ContactId", Result);
		        
			return Result;
			
		}

		 public Hashtable<String, String> CrearCBP2(int Ambiente,String TipoCliente,String Rut,String ContacId,String AddressId,String Ciclo,String Modo) throws IOException {

				//Code to make a webservice HTTP request
				String responseString = "";
				String outputString = "";
				String wsURL="";
				if(Ambiente == 4)
					 wsURL = "http://osbtest.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if(Ambiente == 8)
					 wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if(Ambiente == 10)
					 wsURL = "http://osbbuat.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if (Ambiente ==12)
				    wsURL = "";
				else if(Ambiente == 2)/*PMX2*/
					 wsURL = "http://esbonetest01.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				else if(Ambiente == 3)/*PMX3*/
					 wsURL = "http://esbonetest03.clarochile.cl/integracion/oneamx/crm/controladora/proxy/CustomerProviderService?wsdl";
				
				
				String Id = "<contactId>"+ContacId+"</contactId>";
				String customerSubType="<customerSubType>NNVO</customerSubType>";
				String customerType="<customerType>N</customerType>";
				String Mode=Modo;
				
				if(TipoCliente.equalsIgnoreCase("Empresas"))
				{
					Id= "<orgId>"+ContacId+"</orgId>";
					customerSubType="<customerSubType>ENRM</customerSubType>";
					customerType= "<customerType>E</customerType>";
					
				}
				else if (TipoCliente.equalsIgnoreCase("PYME"))
				{
					Id= "<orgId>"+ContacId+"</orgId>";
					customerSubType="<customerSubType>PNVO</customerSubType>";
					customerType= "<customerType>P</customerType>";
				}
					
				
				
				URL url = new URL(wsURL);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection)connection;
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://customerService.customer.ws.crm.crmimpl.com\">\r\n" + 
						"   <soapenv:Header/>\r\n" + 
						"   <soapenv:Body>\r\n" + 
						"      <cus:createCustomerBillingProfile>\r\n" + 
						"         <request>\r\n" + 
						"            <addressId>"+AddressId+"</addressId>\r\n" + 
						"            <billCycle>"+Ciclo+"</billCycle>\r\n" + 
						"            <billFrequency>1</billFrequency>\r\n" + 
						"            "+customerSubType+"\r\n" + 
						"            "+customerType+"\r\n" + 
						"            <identifierInfo>\r\n" + 
						"               <identifierNumber>"+Rut+"</identifierNumber>\r\n" + 
						"               <identifierType>RUT</identifierType>\r\n" + 
						"               <isPrimary>true</isPrimary>\r\n" + 
						"            </identifierInfo>\r\n" + 
						"            <mode>"+Mode+"</mode>\r\n" + 
						"            <!--<orgId>ORG1000006219</orgId>-->\r\n" + 
						"            "+Id+"\r\n" + 
						"         </request>\r\n" + 
						"      </cus:createCustomerBillingProfile>\r\n" + 
						"   </soapenv:Body>\r\n" + 
						"</soapenv:Envelope>\r\n" + 
						"";							
				
				amd.DataRowExcel("EstadoTrx", xmlInput);
				System.out.println(xmlInput);
				
				byte[] buffer = new byte[xmlInput.length()];
				buffer = xmlInput.getBytes();
				bout.write(buffer);
				byte[] b = bout.toByteArray();
				String SOAPAction = "";
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
				
				
				//Write the SOAP message formatted to the console.
				Hashtable<String, String> formattedSOAPResponse = formatXMLCBPCliente(outputString); // Write a separate method to format the XML input.
				
				return formattedSOAPResponse;
				}
			
			 private Hashtable<String, String> formatXMLCBPCliente2(String outputString) {
			    	
				 Hashtable<String, String> ResultadoHash = 
		                 new Hashtable<String, String>();
			    	
				    String Result="";
			    	String Result2="";
			    	
			    	Pattern pattern = Pattern.compile("<cbpId>(.*?)</cbpId>");
			    	Matcher matcher = pattern.matcher(outputString);
			        if( matcher.find())
			        	Result = matcher.group(1);
			        else
			        {
			        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
				    	Matcher matcherOk = patternOk.matcher(outputString);
				        if( matcherOk.find()) 
				        	Result = matcherOk.group(1);
			        }
			        
			        Pattern pattern2 = Pattern.compile("<contactId>(.*?)</contactId>");
			    	Matcher matcher2 = pattern2.matcher(outputString);
			        if( matcher2.find())
			        	Result2 = matcher2.group(1);
			        else
			        {
			        	Pattern patternOk2 = Pattern.compile("<faultstring>(.*?)</faultstring>");
				    	Matcher matcherOk2 = patternOk2.matcher(outputString);
				        if( matcherOk2.find()) 
				        	Result2 = matcherOk2.group(1);
			        }
			        
			        ResultadoHash.put("cbpId", Result);
			        ResultadoHash.put("contactId", Result2);
			        
			        System.out.println("CBP Creada: "+Result);
			        System.out.println("ID Contacto: "+Result2);
			        
			        amd.DataRowExcel("CBP", Result);
			        
				return ResultadoHash;
			}
 
		 public void ProvideWirelessIVR(int Ambiente,String CBP,String FA,String CodigoPlan,String NumeroCel,String Sim,String OrderType,String ReasonID,String Provider,String AdquisicionType,String EquipoSKU,String Marca, String Modelo,String Imei,String Contrato) throws IOException{

			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL="";
			if(Ambiente == 4)
				 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if(Ambiente == 8)
				 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if(Ambiente == 10)
				 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if (Ambiente ==12)
			    wsURL = "";
			else if(Ambiente == 2)/*PMX2*/
				 wsURL = "http://esbonetest01.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			else if(Ambiente == 3)/*PMX3*/
				 wsURL = "http://esbonetest03.clarochile.cl:80/integracion/oneamx/controladora/ivr/proxy/provideWirelessIVR?wsdl";
			
			
			String SKUEquipment= "<prov:equipmentSKU>"+EquipoSKU+"</prov:equipmentSKU>";
			String DuracionContrato = "<prov:equipmentCommitmentDuration>"+Contrato+"</prov:equipmentCommitmentDuration>";
			String SerieEquipo = "<prov:IMEI>"+Imei+"</prov:IMEI>";
			//Tipo Orden
			//String ActionType = "<prov:actionType>"+OrderType+"</prov:actionType>";
			//String ReasonCode = "<prov:reasonID>"+ReasonID+"</prov:reasonID>";
			//String ExProvider = "<prov:reasonID>"+Provider+"</prov:reasonID>";
			//Tipo Adquisicion
			//String Adquisicion = "<prov:reasonID>"+AdquisicionType+"</prov:reasonID>";
			//String SKUEquipo = "<prov:reasonID>"+EquipoSKU+"</prov:reasonID>";
			//String MarcaEquipo = "<prov:reasonID>"+Marca+"</prov:reasonID>";
			//String ModeloEquipo = "<prov:reasonID>"+Modelo+"</prov:reasonID>";
			//String SerieEquipo = "<prov:reasonID>"+Imei+"</prov:reasonID>";
			//String DuracionContrato = "<prov:equipmentCommitmentDuration>"+Contrato+"</prov:equipmentCommitmentDuration>";
			
			if(AdquisicionType.equalsIgnoreCase("Propio"))
			{
				SKUEquipment= "<prov:equipmentSKU>"+EquipoSKU+"</prov:equipmentSKU>";
				DuracionContrato= "<prov:equipmentCommitmentDuration></prov:equipmentCommitmentDuration>";
				SerieEquipo = "<prov:IMEI>"+Imei+"</prov:IMEI>";
			}
			else if(AdquisicionType.equalsIgnoreCase("Sin Equipo"))
			{
				SKUEquipment= "<prov:equipmentSKU></prov:equipmentSKU>";
				DuracionContrato= "<prov:equipmentCommitmentDuration></prov:equipmentCommitmentDuration>";
				SerieEquipo = "<prov:IMEI></prov:IMEI>";
			}
			else
			{
				SKUEquipment= "<prov:equipmentSKU>"+EquipoSKU+"</prov:equipmentSKU>";
				DuracionContrato= "<prov:equipmentCommitmentDuration>"+Contrato+"</prov:equipmentCommitmentDuration>";
				SerieEquipo = "<prov:IMEI>"+Imei+"</prov:IMEI>";
			}

			
			
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prov=\"http://providewirelessivr.wireless.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <prov:ProvideWirelessIVRRequest>\r\n" + 
					"         <prov:customerId>"+CBP+"</prov:customerId>\r\n" + 
					"         <prov:FAID>"+FA+"</prov:FAID>\r\n" + 
					"         <prov:offerPlanID>"+CodigoPlan+"</prov:offerPlanID>\r\n" + 
					"         <prov:actionType>"+OrderType+"</prov:actionType>\r\n" + 
					"         <prov:reasonID>"+ReasonID+"</prov:reasonID>\r\n" + 
					"         <prov:reasonText>Caso ejecutado por Automatizacion QA</prov:reasonText>\r\n" + 
					"         <prov:MSISDN>"+NumeroCel+"</prov:MSISDN>\r\n" + 
					"         "+SerieEquipo+"\r\n" + 
					"         <prov:ICCID>"+Sim+"</prov:ICCID>\r\n" + 
					"         <prov:acquisitionType>"+AdquisicionType+"</prov:acquisitionType>\r\n" + 
					"         "+SKUEquipment+"\r\n" + 
					"         <prov:externalProvider>"+Provider+"</prov:externalProvider>\r\n" + 
					"         <prov:SIMSKU>7000437</prov:SIMSKU>\r\n" + 
					"         <prov:creditScore>5</prov:creditScore>\r\n" + 
					"         <prov:contractId>12345</prov:contractId>\r\n" + 
					"         <prov:percentageSubsidy>0</prov:percentageSubsidy>\r\n" + 
					"         <prov:numberOfInstallments>1</prov:numberOfInstallments>\r\n" + 
					"         <prov:onBehalfUser></prov:onBehalfUser>\r\n" + 
					"         <prov:equipmentMake>"+Marca+"</prov:equipmentMake>\r\n" + 
					"         <prov:equipmentModel>"+Modelo+"</prov:equipmentModel>\r\n" + 
					"         "+DuracionContrato+"\r\n" + 
					"         <prov:user>FVMIVR</prov:user>\r\n" + 
					"         <prov:cobroCargoInicial>1</prov:cobroCargoInicial>\r\n" + 
					"      </prov:ProvideWirelessIVRRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
			amd.DataRowExcel("EstadoTrx", xmlInput);
			System.out.println(xmlInput);
			
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "";
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
			
			
			//Write the SOAP message formatted to the console.
			String formattedSOAPResponse = formatXMLProvideWirelessIVR(outputString); // Write a separate method to format the XML input.
			
			/*
			 * Agrega el Response al archivo de Resultado
			 * */
			 amd.DataRowExcel("Status", outputString);
			//return formattedSOAPResponse; 
		 }
		 private String formatXMLProvideWirelessIVR(String outputString) {
		    	
			 System.out.println(outputString);
			 
		    	String Result="";
		    	Pattern pattern = Pattern.compile("<prov:orderId>(.*?)</prov:orderId>");
		    	Matcher matcher = pattern.matcher(outputString);
		        if( matcher.find())
		        	Result = matcher.group(1);
		        else
		        {
		        	Pattern patternOk = Pattern.compile("<faultstring>(.*?)</faultstring>");
			    	Matcher matcherOk = patternOk.matcher(outputString);
			        if( matcherOk.find()) 
			        	Result = matcherOk.group(1);
		        }
		        
		        System.out.println(Result);
		        amd.DataRowExcel("Orden", Result+"A");
		        amd.DataRowExcel("GeneraContrato", outputString);
		        
			return Result;
		}
}
