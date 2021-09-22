package by.epamtc.kalimylin.service.util.validation;

import static by.epamtc.kalimylin.service.util.validation.VariableValidator.*;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class VariableValidatorTest {
  
  	@Test(dataProvider = "driveLicense")
  	public void isCorrectDriveLicenseTest(String value, boolean expectedResult) {
  		boolean result = isCorrectDriveLicense(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] driveLicense() {
  		return new Object[][] {
  				{ "AC", false },
  				{ "AE", false },
  				{ "E", true },
  				{ "C", true },
  				{ "CE", true },
  				{ "c", false },
  				{ "e", false },
  				{ "ce", false },
  				{ "m; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  		};
  	}
  		
  	@Test(dataProvider = "email")
  	public void isCorrectEmailTest(String value, boolean expectedResult) {
  		boolean result = isCorrectEmail(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] email() {
  		return new Object[][] {
  				{ "q@q.q", true },
  				{ "qqq.qq", false },
  				{ "mail.qwe@", false },
  				{ "email@mail.ru", true },
  				{ "longEmailFor30signsPossibleYet@gmail.ru", true },
  				{ "longEmailFor31signNotPossibleYe@gmail.ru", false },
  				{ "<script>alert(1@2.3)</script>", false },
  				{ "m@; DROP TABLE users", false },
  				{ "%c", false },
  		};
  	}
  	
  	@Test(dataProvider = "letterLine")
  	public void isCorrectLetterLineTest(String value, boolean expectedResult) {
  		boolean result = isCorrectLetterLine(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] letterLine() {
  		return new Object[][] {
  				{ "q", true },
  				{ "maximum sing in line", true },
  				{ "!@#$%^&*()_+", false },
  				{ "ЙЦУКЕН ФЫВАПР", true },
  				{ "123 qwe йцу 123", false },
  				{ "<script>alert('xss');</script>", false },
  				{ "Usual letter line", true },
  				{ "1c", false },
  				{ "m; DROP TABLE users", false },
  		};
  	}

  	@Test(dataProvider = "licensePlate")
  	public void isCorrectLicensePlateTest(String value, boolean expectedResult) {
  		boolean result = isCorrectLicensePlate(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] licensePlate() {
  		return new Object[][] {
  				{ "1234AA1", true },
  				{ "1234aa1", false },
  				{ "1234AAA1", false },
  				{ "1234AA0", false },
  				{ "!@#$%^&*()_+", false },
  				{ "1234ЯЯ1", false },
  				{ "1234AA8", false },
  				{ "<script>alert('xss');</script>", false },
  				{ "AA12344", false },
  				{ "12AA343", false },
  				{ "AA51234", false },
  				{ "m; DROP TABLE users", false },
  		};
  	}
  	
  	@Test(dataProvider = "login")
  	public void isCorrectLoginTest(String value, boolean expectedResult) {
  		boolean result = isCorrectLogin(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] login() {
  		return new Object[][] {
  				{ "admin", true },
  				{ "admin!", false },
  				{ "1hags asd", false },
  				{ "Super_login_maXxSize", true },
  				{ "!@#$%^&*()_+", false },
  				{ "S1234AAAqwe", true },
  				{ "m; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  				{ "mini", true },
  		};
  	}
  	
  	@Test(dataProvider = "longLetterLine")
  	public void isCorrectLongLetterLineTest(String value, boolean expectedResult) {
  		boolean result = isCorrectLongLetterLine(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] longLetterLine() {
  		return new Object[][] {
  				{ "", false },
  				{ "no", false },
  				{ "short", true },
  				{ "A normal long sentence, 50 characters long. Cool .", true },
  				{ "!@#$%^&*()_+", false },
  				{ "Обычное длинное предложение, длинной 50 символов .", true },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  				{ "min", true },
  		};
  	}

  	@Test(dataProvider = "note")
  	public void isCorrectNoteTest(String value, boolean expectedResult) {
  		boolean result = VariableValidator.isCorrectNote(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] note() {
  		return new Object[][] {
  				{ "", true },
  				{ "A normal long sentence, 500 characters long. Cool."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool ."
  						+ "A normal long sentence, 50 characters long. Cool .", true },
  				{ "!@#$%^&*()_+<>:\"][", false },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  		};
  	}

  	@Test(dataProvider = "passportIdentificationNumber")
  	public void isCorrectPassportIdentificationNumberTest(
  			String value, boolean expectedResult) {
  		boolean result = isCorrectPassportIdentificationNumber(value);
  		assertEquals(result, expectedResult);  
  	}
  	
  	@DataProvider
  	public Object[][] passportIdentificationNumber() {
  		return new Object[][] {
  				{ "1234567A12OAAO", false },
  				{ "1234567a123aa1", false },
  				{ "1234567A123AA1", true },
  				{ "!@#$%^&*()_+", false },
  				{ "0987654Q000ZX9", true },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  				{ "12345678Q12HG0", false },
  		};
  	}

  	@Test(dataProvider = "passportNumber")
  	public void isCorrectPassportNumberTest(String value, boolean expectedResult) {
  		boolean result = isCorrectPassportNumber(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] passportNumber() {
  		return new Object[][] {
  				{ "AA123456O", false },
  				{ "aa1234560", false },
  				{ "AA1234560", true },
  				{ "!@#$%^&*()_+", false },
  				{ "XX0987654", true },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  		};
  	}

  	@Test(dataProvider = "password")
  	public void isCorrectPasswordTest(String value, boolean expectedResult) {
  		boolean result = isCorrectPassword(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] password() {
  		return new Object[][] {
  				{ "1234567890", false },
  				{ "qwerty123", false },
  				{ "Qq!%Tjj3ashdq%$hfdg@", true },
  				{ "!@#$%^&*()_+", false },
  				{ "Q1q#qw", true },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  		};
  	}

  	@Test(dataProvider = "phone")
 	public void isCorrectPhoneTest(String value, boolean expectedResult) {
  		boolean result = isCorrectPhone(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] phone() {
  		return new Object[][] {
  				{ "(044)1231122", false },
  				{ "+375 33 123 1122", false },
  				{ "80291231122", true },
  				{ "+375-29-123-23-32", false },
  				{ "375291231122", true },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  		};
  	}
  	

  	@Test(dataProvider = "role")
  	public void isCorrectRoleTestString(String value, boolean expectedResult) {
  		boolean result = isCorrectRole(value);
  		assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
  	public Object[][] role() {
  		return new Object[][] {
  				{ "ADMIN", true },
  				{ "ADIMN", false },
  				{ "DRIVER", true },
  				{ "123qwe", false },
  				{ "LoGiSt", false },
  				{ "note; DROP TABLE users", false },
  				{ "<script>alert('xss');</script>", false },
  		};
  	}
}
