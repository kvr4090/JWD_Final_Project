package by.epamtc.kalimylin.service.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * The Class contains util method to encrypt email
 */
public class EmailCrypt {
	
	/**
	 * Encrypts email.
	 * Encrypted mail is used as a token to validate mail and is used as 
	 * a link element. String must not contain incorrect characters.
	 * The result contains only numbers and letters.
	 * 
	 * @param emailAdress  email to encrypts
	 * @return {@link String} as a token
	 * @throws ServiceException {@link ServiceException}
	 */
	public static String encodeEmailAdress(String emailAdress) 
			throws ServiceException {
		
		MessageDigest messageDigest;
		
		try {
			messageDigest = MessageDigest.getInstance(MessageService.ALGORITHM);
			messageDigest.update(emailAdress.getBytes(StandardCharsets.UTF_8));
		    byte[] digest = messageDigest.digest();
		    return String.format(
		    		MessageService.FORMAT, new BigInteger(1, digest));
		    
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException(e);
		}	    
	}
}
