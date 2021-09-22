package by.epamtc.kalimylin.service.util.validation;

import org.apache.commons.lang3.EnumUtils;

import by.epamtc.kalimylin.bean.executor.user.Role;

/**
 * The class contains different methods, which checks to correct values in 
 * string.
 * Uses checks to null
 * Uses specified regular expression values for matches.
 * Returns boolean result of checking.
 */
public class VariableValidator {
	
	private static final String REGEXP_LETTER_LINE = "^[A-zÀ-ÿ0-9\\s]{1,20}$";
	
	private static final String REGEXP_LONG_LETTER_LINE = 
			"^[a-zA-Zà-ÿÀ-ß0-9\\s\\,\\.]{3,50}$";
	
	private static final String REGEXP_VEHICLE_TYPE = 
			"^[a-zA-Zà-ÿÀ-ß0-9\\s\\,\\.]{3,80}$";
	
	private static final String REGEXP_NOTE = 
			"^[a-zA-Zà-ÿÀ-ß0-9\\s\\,\\.\\:]{0,500}";
	
	private static final String REGEXP_EMAIL = 
			"[^\\s<>]{1,30}@[^\\s<>]{1,10}\\.[^\\s<>]{1,10}";
	
	private static final String REGEXP_LOGIN = "^[a-zA-Z][a-zA-Z0-9-_]{3,19}$";
	
	private static final String REGEXP_PASSWORD = 
			"^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])"
			+ "[0-9a-zA-Z!@#$%^&*]{6,20}$";
	
	private static final String REGEXP_PHONE = "^[0-9]{11,12}$";	
	private static final String REGEXP_DRIVE_LICENSE = "^[CE]{0,2}$";
	
	private static final String REGEXP_LICENSE_PLATE = 
			"^([\\d]{4})([A-Z]{2})([1-7])$"; 
	
	private static final String REGEXP_PASSPORT_NUMBER = 
			"^([A-Z]{2})([\\d]{7})$";
	
	private static final String REGEXP_IDENTIFICATION_PASSPORT_NUMBER = 
			"^([\\d]{7})([A-Z])([\\d]{3})([A-Z]{2})([0-9])$";
	
	private static final String REGEXP_TOKEN = "^[a-z0-9]{64}$";
	
	public static boolean isNotNull(Object object) {
		return object != null;
	}
	
	public static boolean isCorrectToken(String token) {
		return isNotNull(token) && token.matches(REGEXP_TOKEN);
	}
			
	public static boolean isCorrectLogin(String login) {
		return isNotNull(login) && login.matches(REGEXP_LOGIN);
	}
	
	public static boolean isCorrectPassword(String password) {		
		return isNotNull(password) && password.matches(REGEXP_PASSWORD);
	}
	
	public static boolean isCorrectRole(String role) {
		return isNotNull(role) && EnumUtils.isValidEnum(Role.class, role);
	}
	
	public static boolean isCorrectRole(Role role) {
		return isNotNull(role);		
	}

	public static boolean isCorrectLetterLine(String line) {
		return isNotNull(line) && line.matches(REGEXP_LETTER_LINE);
	}
	
	public static boolean isCorrectVehicleType(String type) {
		return isNotNull(type) && type.matches(REGEXP_VEHICLE_TYPE);
	}
	
	public static boolean isCorrectLongLetterLine(String line) {
		return isNotNull(line) && line.matches(REGEXP_LONG_LETTER_LINE);
	}
	
	public static boolean isCorrectNote(String note) {
		return isNotNull(note) && note.matches(REGEXP_NOTE);
	}

	public static boolean isCorrectEmail(String email) {
		return isNotNull(email) 
				&& email.matches(REGEXP_EMAIL)
				&& email.length() < 41;
	}

	public static boolean isCorrectPhone(String phone) {
		return isNotNull(phone) && phone.matches(REGEXP_PHONE);
	}

	public static boolean isCorrectDriveLicense(String driveLicense) {
		return isNotNull(driveLicense) 
				&& driveLicense.matches(REGEXP_DRIVE_LICENSE);
	}
	
	public static boolean isCorrectLicensePlate(String licensePlate) {
		return isNotNull(licensePlate) 
				&& licensePlate.matches(REGEXP_LICENSE_PLATE);
	}
	
	public static boolean isCorrectPassportNumber(String passportNumber) {
		return isNotNull(passportNumber) 
				&& passportNumber.matches(REGEXP_PASSPORT_NUMBER);
	}
	
	public static boolean isCorrectPassportIdentificationNumber(
			String passportNumber) {
		return isNotNull(passportNumber) && passportNumber.matches(
				REGEXP_IDENTIFICATION_PASSPORT_NUMBER);
	}
}
