package by.epamtc.kalimylin.service.util;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.service.exception.ServiceException;

import static org.testng.Assert.fail;

public class MailSenderTest {

    @Test(timeOut = 10000)
    public void successfulSendEmailTest() {
    	try {
			MailSender.sendEmail("kvr4090@mail.ru", "", "");
		} catch (ServiceException e) {
			fail();
		}
    }
  
    @Test(expectedExceptions = ServiceException.class,
    		expectedExceptionsMessageRegExp = MessageService.MESSAGE_SEND_FAILED)
    public void failedSendEmailTest() throws ServiceException {
    	MailSender.sendEmail("", "", "");
    }
}
