package com.appspot.demo.server.paypal.service;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MailerService {
	private static Logger logger = Logger.getLogger(MailerService.class.getName());
	
	private String fromEmail;
	
	public MailerService(String fromEmail){
		this.fromEmail = fromEmail;
	}
	
	public void sendMail(String recipient, String text, String subject){
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.fromEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			
			
		}
		catch(AddressException ex){
			logger.warning(ex.getMessage());
			
		}
		catch(MessagingException ex){
			logger.warning(ex.getMessage());
		}
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

}
