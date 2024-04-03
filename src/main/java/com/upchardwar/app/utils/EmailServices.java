package com.upchardwar.app.utils;

import java.util.Properties;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.status.AppConstant;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServices {
	
	public String generateOtp() {
		Random r=new Random();
		int rn = r.nextInt(9000)+1000;
		
		return String.valueOf(rn);
	}

	public Boolean sendEmail(String otp, String email) {
		//variable for gmail
		          
		        boolean f=false;
		        
				String host= AppConstant.HOST_GMAIL;;
				
				String  from=AppConstant.FROM_WHICH_GMAIL;
				
				String subject=AppConstant.FOR_EMAIL_VALIDATION;
				//get the system properties
				Properties properties=System.getProperties();
				
				
				
			
				//setting imp information to properties object
				
				//host set
				
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.socketFactory.host", "465");
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.auth","true");
				
				//Step 1: to get session object
				
			Session session=	Session.getInstance(properties, new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						// TODO Auto-generated method stub
						return new PasswordAuthentication(from, "dgno knnf fbmu tcsn");
					}
					
				});
				session.setDebug(true);
				//step 2 : compose the message [text , multimedia]
				
				MimeMessage m=new MimeMessage(session);
				
				//from
				try {
					m.setFrom(from);
					
					
					m.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					
					
					m.setSubject(subject);
					
					m.setText(otp);
					
					Transport.send(m);
  					
					f=true;
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return f;
			}

	public Boolean sendEmail(String otp, String email, String subject, String body) {
	    boolean success = false;

	    String host = AppConstant.HOST_GMAIL;
	    String from = AppConstant.FROM_WHICH_GMAIL;

	    Properties properties = System.getProperties();
	    properties.put("mail.smtp.host", host);
	    properties.put("mail.smtp.socketFactory.host", "465");
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.auth", "true");

	    Session session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(from, "dgno knnf fbmu tcsn");
	        }
	    });

	    session.setDebug(true);

	    MimeMessage m = new MimeMessage(session);

	    try {
	        m.setFrom(from);
	        m.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        m.setSubject(subject);
	        m.setText(body);
	        Transport.send(m);

	        success = true;
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }

	    return success;
	}

}
