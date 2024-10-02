package Core.Soap;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Soap {
	public String someMethod() throws IOException {

		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL = "http://osbtestcln01.clarochile.cl/integracion/oneamx/controladora/proxy/retrieveUserDetails?wsdl";
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ret='http://retrieveuserdetails.userservices.schema.amx.com'>\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <ret:RetrieveUserDetailsRequest>\r\n" + 
				"         <!--Optional:-->\r\n" + 
				"         <ret:loginName>17281686K</ret:loginName>\r\n" + 
				"      </ret:RetrieveUserDetailsRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";

		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "http://retrieveuserdetails.userservices.amx.com/RetrieveUserDetails";
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
		
		public String PagoPorCaja() throws IOException {
            
			String FA="100034655";
			String Orden="241745";
			String Monto ="259899.57";
			String invoiceId="100223104";
			
			
			
			//Code to make a webservice HTTP request
			String responseString = "";
			String outputString = "";
			String wsURL = "http://osbtest.clarochile.cl:80/integracion/oneamx/controladora/cashier/proxy/createPayment?wsdl";
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cre=\"http://createpayment.receivablesmanagement.schema.amx.com\">\r\n" + 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <cre:CreatePaymentRequest>\r\n" + 
					"         <cre:paymentApplicationItemScreen>\r\n" + 
					"            <cre:accountCurrency>CLP</cre:accountCurrency>\r\n" + 
					"            <cre:accountId>"+FA+"</cre:accountId> <!-- Este corresponde al número de la FA-->\r\n" + 
					"            <cre:amount>"+Monto+"</cre:amount> <!-- Este corresponde al monto a pagar que se obtiene del servicio getFAOpenBalance-->\r\n" + 
					"            <cre:currency>CLP</cre:currency>\r\n" + 
					"            <cre:invoiceId>"+invoiceId+"</cre:invoiceId><!-- Este corresponde al número del invoiceId, que se obtiene del servicio getFAOpenBalance-->\r\n" + 
					"         </cre:paymentApplicationItemScreen>\r\n" + 
					"         <cre:cashPaymentDetailsScreen>\r\n" + 
					"            <cre:accountCurrency>CLP</cre:accountCurrency>\r\n" + 
					"            <cre:accountId>"+FA+"</cre:accountId><!-- Este corresponde al número de la FA-->\r\n" + 
					"            <cre:amount>"+Monto+"</cre:amount><!-- Este corresponde al monto a pagar que se obtiene del servicio getFAOpenBalance-->\r\n" + 
					"            <cre:currency>CLP</cre:currency>\r\n" + 
					"            <cre:depositChoice>N</cre:depositChoice>\r\n" + 
					"            <cre:depositDate>2021-10-14</cre:depositDate><!-- Actualizar a la fecha actual -->\r\n" + 
					"            <cre:genericCustDt/>\r\n" + 
					"            <cre:paymentCustDt>\r\n" + 
					"               <cre:actualAmount>"+Monto+"</cre:actualAmount><!-- Este corresponde al monto a pagar que se obtiene del servicio getFAOpenBalance-->\r\n" + 
					"               <cre:orderId>"+Orden+"</cre:orderId><!-- Este corresponde al número de orden que requiere el pago-->\r\n" + 
					"               <cre:paymentMethod>CA</cre:paymentMethod>\r\n" + 
					"            </cre:paymentCustDt>\r\n" + 
					"            <cre:paymentDetailsCustDt>\r\n" + 
					"               <cre:orderId>"+Orden+"</cre:orderId><!-- Este corresponde al número de orden que requiere el pago-->\r\n" + 
					"               <cre:paymentReferenceId/>\r\n" + 
					"               <cre:paymentSubMethod>EF</cre:paymentSubMethod>\r\n" + 
					"               <cre:paymentReceiptNumber>52426141</cre:paymentReceiptNumber><!-- Corresponde a un número corelativo, se debe sumar 1 por cada vez que se ejecute el servicio-->\r\n" + 
					"            </cre:paymentDetailsCustDt>\r\n" + 
					"            <cre:paymentMethod>CA</cre:paymentMethod>\r\n" + 
					"            <cre:paymentSourceId>PP000306</cre:paymentSourceId>\r\n" + 
					"            <cre:paymentSourceType>CLR</cre:paymentSourceType>\r\n" + 
					"         </cre:cashPaymentDetailsScreen>\r\n" + 
					"      </cre:CreatePaymentRequest>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";

			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "http://createpayment.receivablesmanagement.customer.billing.neg.cont.claro.cl/CreatePayment";
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
			System.out.println(outputString);
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
