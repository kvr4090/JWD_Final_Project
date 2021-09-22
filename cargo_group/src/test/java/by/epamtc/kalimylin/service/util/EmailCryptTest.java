package by.epamtc.kalimylin.service.util;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.service.exception.ServiceException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.DataProvider;

public class EmailCryptTest {
	
    @Test(dataProvider = "email")
    public void encodeEmailAdressTest(String email, int expectedResult) {
    	try {
    		int result = EmailCrypt.encodeEmailAdress(email).length();
			assertEquals(result, expectedResult);
		} catch (ServiceException e) {
			fail();
		}
    }
    
    @DataProvider
    public Object[][] email() {
    	return new Object[][] {
    			{ "1", 64 },
    			{ "mail@gmail.com", 64 },
    			{ "CargoGroupMailService@gmail.com", 64 },
    			{ "!@#$%^&*()_", 64 },
    			{ "QWERTYUIOP", 64 },
    			{ "…÷” ≈Õ√ÿŸ«’⁄", 64 },
    			{ "Q!W@E#R$T%Y^U&I*O(P)", 64 },
    			{ "…!÷”π ;≈%Õ:√?ÿ*Ÿ(«)’_⁄+", 64 },
    			{ "maximum_count_sign_maximum_count_maximum_count_sig", 64 }
    	};
    }
}
