package defPackage;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailConnector {
	
	//All the information to connect gmail
	private String d_email = "macsclassroommail@gmail.com"; //email of the sender
	private String d_uname = "macs"; //name of the sender
	private String d_password = "itisnotwrong"; //password of the sender
	private String d_host = "smtp.gmail.com";
	private String d_port  = "465";
	private String m_to; //receiver email
	private String m_subject; //subject of the mail
	private String m_text;//text of the mail
	
	/**
	 * Constructor:
	 * @param email_to - where are you sending the mail;
	 * @param subject - subject of the mail;
	 * @param test - text of the mail;
	 */
	public MailConnector(String email_to, String subject, String test){
		this.m_to = email_to;
		this.m_subject = subject;
		this.m_text = test;
	}
	
	/**
	 * This method makes properties;
	 * All ready to make Message and send it with Transport object;
	 * @return
	 */
	private Properties makeProperties(){
		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		return props;
	}
	
	/**
	 * This method send the mail to the given email;
	 */
	public void sendMail(){
		Properties props = makeProperties();
		
		SMTPAuthenticator auth = new SMTPAuthenticator();
	    Session session = Session.getInstance(props, auth);
	    session.setDebug(true);

	    MimeMessage msg = new MimeMessage(session);
	    try {
	        msg.setSubject(m_subject);
	        msg.setFrom(new InternetAddress(d_email));
	        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
	        msg.setText(m_text);

	        Transport transport = session.getTransport("smtp");
	        transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();

	    } catch (AddressException e) {
	    	e.printStackTrace();
	           
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	           
	    }
	}
	
	/**
	 * This is the inner class;
	 * Authenticator - here are the information for sender, needed to make Massage;
	 */
	class SMTPAuthenticator extends Authenticator {
		
	
		public PasswordAuthentication getPasswordAuthentication () {
			return new PasswordAuthentication( d_email,  d_password );
		}
	}
	
	public static void main(String[] args) {
		
		new MailConnector("gkhos15@freeuni.edu.ge", " amanac tu gaweda", " mere rara vqna").sendMail();
		
	}
	

	
}
