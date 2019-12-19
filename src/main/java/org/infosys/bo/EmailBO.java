package org.infosys.bo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.infosys.vo.common.User;
import org.springframework.stereotype.Component;

@Component
public class EmailBO {

	public boolean sendMail(User user)
	{
		boolean isSent = false;
		final String username = "rmdmohan27@gmail.com";
		final String password = "javaj2ee123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("UMLEngineering@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(user.getEmail()));
			message.setSubject("Welcome To UMLEngineering");
			message.setText("Dear "+user.getName() +", "
				+ "\n\n Your One Time Password is "+ user.getOneTimePassword());

			Transport.send(message);
			isSent = true;
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return isSent;
	}
}
