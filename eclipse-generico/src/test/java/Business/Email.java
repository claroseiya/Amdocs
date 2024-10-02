package Business;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;





public class Email {
	
	public void EnviarEmail() {
		
	
	final String username = "Mauricio.pizarro@clarochile.cl";
	final String password = "";

	
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	
	props.put("mail.smtp.host", "smtp.office365.com");
	props.put("mail.smtp.port", "25");
	props.put("mail.smtp.ssl.trust", "smtp.office365.com");
	
	Session session = Session.getInstance(props,
	  new javax.mail.Authenticator() {
	      protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	      }
	  });

	
	try {

	      // Define message
	      MimeMessage message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(username));
	      message.setSubject("Prueba");
	      message.addRecipient(Message.RecipientType.TO,new InternetAddress("mpizarrogonzalez@gmail.com"));
	      message.setText("Hola soy un roboc dislexico");
	      // Envia el mensaje
	      Transport.send(message);
	} catch (Exception e) {
		System.out.println("Eror Mail"+e.getMessage());
		System.out.println("Eror Mail"+e);
	}

	}
	
	 public void EnviarEmail2() throws AddressException,
	            MessagingException {
		    
		    final String username = "ClaroSeiya@gmail.com";
			final String password = "Pass1010";

		 
	        // sets SMTP server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.secure", "tsl");
	// *** BEGIN CHANGE
	        properties.put("mail.smtp.user", username);

	        // creates a new session, no Authenticator (will connect() later)
	        Session session = Session.getDefaultInstance(properties);
	// *** END CHANGE

	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);

	        msg.setFrom(new InternetAddress(username));
	        InternetAddress[] toAddresses = { new InternetAddress("mauricio.pizarro@clarochile.cl") };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject("Hola");
	        msg.setSentDate(new Date());
	        // set plain text message
	        msg.setText("Este es mi mensaje");

	        try
	        {
	        	// *** BEGIN CHANGE
		        // sends the e-mail
		        Transport t = session.getTransport("smtp");
		        t.connect(username, password);
		        t.sendMessage(msg, msg.getAllRecipients());
		        t.close();
		// *** END CHANGE

	        	
	        }
	        catch(Exception e)
	        {
	        	System.out.println("error"+e.getMessage());
	        }
	
	    }
	
}
