package by.epamtc.kalimylin.service.util;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * The Class contains util method to send email
 */
public class MailSender {
	
	private static final Logger logger = LogManager.getLogger();
	private static final Properties properties = new Properties();
	
	private static final String PROPERTIES = "resources/mail";
	private static final String USER_NAME = "mail.user.name";
	private static final String USER_PASSWORD = "mail.user.password";
	private static final String MAIL_FROM = "mail.from";
	private static final String MAIL_HOST = "mail.smtp.host";
	private static final String MAIL_PORT = "mail.smtp.port";
	private static final String STARTTLS = "mail.smtp.starttls.enable";
	private static final String MAIL_AUTH = "mail.smtp.auth";
	private static final String CONNECT_TIMEOUT = "mail.smtp.connectiontimeout";
	private static final String MAIL_TIMEOUT = "mail.smtp.timeout";
	private static ExceptionHelper handler;
	
	static {
		
		try {
			ResourceBundle mailProps = ResourceBundle.getBundle(PROPERTIES);
			
			properties.setProperty(MAIL_HOST, mailProps.getString(MAIL_HOST));
			properties.setProperty(MAIL_AUTH, mailProps.getString(MAIL_AUTH));
			properties.setProperty(MAIL_PORT, mailProps.getString(MAIL_PORT));
			properties.setProperty(MAIL_HOST, mailProps.getString(MAIL_HOST));
			properties.setProperty(STARTTLS, mailProps.getString(STARTTLS));
			properties.setProperty(
					CONNECT_TIMEOUT, mailProps.getString(CONNECT_TIMEOUT));
			properties.setProperty(
					MAIL_TIMEOUT, mailProps.getString(MAIL_TIMEOUT));
			properties.setProperty(USER_NAME, mailProps.getString(USER_NAME));
			properties.setProperty(
					USER_PASSWORD, mailProps.getString(USER_PASSWORD));
			properties.setProperty(MAIL_FROM, mailProps.getString(MAIL_FROM));
			
		} catch (MissingResourceException e) {
			handler = new ExceptionHelper(logger, e, 
					new StringBuilder()
					.append(MessageService.INVALID_MAIL_PROREPTY));
			handler.log();		
		}
	}
	/**
	 * Sends email.
	 * When incorrect values in the input data, throws exception.
	 * When there are technical problems with sending a letter,throws exception.
	 * 
	 * @param emailTo  email recipient
	 * @param emailSubject  email header
	 * @param emailMessage  message to send
	 * @throws ServiceException {@link ServiceException}
	 */
	public static void sendEmail(String emailTo, String emailSubject, 
			String emailMessage) throws ServiceException {
		
		Session session = Session.getInstance(properties,
		        new javax.mail.Authenticator() {
		          protected PasswordAuthentication getPasswordAuthentication() {
		               return new PasswordAuthentication(
		            		   properties.getProperty(USER_NAME),
		            		   properties.getProperty(USER_PASSWORD));
			   }
		         });
		try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(
            		properties.getProperty(MAIL_FROM)));
            message.setRecipients(Message.RecipientType.TO,
            		InternetAddress.parse(emailTo));
            message.setSubject(emailSubject);
            message.setText(emailMessage);

            Transport.send(message);
        } catch (MessagingException e) {
        	throw new ServiceException(MessageService.MESSAGE_SEND_FAILED);
        }	
	}	
}
