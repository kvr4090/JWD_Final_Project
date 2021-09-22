package by.epamtc.kalimylin.controller.util;

import static by.epamtc.kalimylin.controller.util.Message.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;

/**
 * Parse different variables, uses default classes variable parsers
 */
public class ParseVariable {
	
	/**
	 * Parses the string argument as a signed decimal integer 
	 * {@link Integer#parseInt(String)}
	 * 
	 * @param number  number for parse
	 * @return {@code Integer}
	 * @throws ParseVariableException {@link ParseVariableException} if the 
	 * string does not contain a parsable value.
	 */
	public static int parseInt(String number) 
			throws ParseVariableException {
		
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			throw new ParseVariableException(INCORRECT_DATA);
		}
	}
	
	/**
	 * Parses the string argument as a float
	 * Round to two decimal places.
	 * {@link Float#parseFloat(String)}
	 * 
	 * @param number  number for parse
	 * @return {@code Float} 
	 * @throws ParseVariableException {@link ParseVariableException} 
	 * if the string does not contain a parsable value.
	 */
	public static float parseFloat(String number) 
			throws ParseVariableException {
		
		float num;
		float tmp;
		int pow = 10;
		
		try {
			num = Float.parseFloat(number);	        
		} catch (NumberFormatException e) {
			throw new ParseVariableException(INCORRECT_DATA);
		}
		
        for (int i = 1; i < 2; i++) {
            pow *= 10;
        }
        tmp = num * pow;
        
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
	}
	
	/**
	 * Obtains an instance of {@code LocalDate} from a text string 
	 * such as {@code 2007-12-03}.
	 * {@link LocalDate#parse(CharSequence)}
	 * 
	 * @param date  date for parse
	 * @return {@link LocalDate} 
	 * @throws ParseVariableException {@link ParseVariableException} if the 
	 * string does not contain a parsable value.
	 */
	public static LocalDate parseLocalDate(String date) 
			throws ParseVariableException {
		
		try {
			return LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			throw new ParseVariableException(INCORRECT_DATE);
		}
	}
	
	/**
	 * Obtains an instance of {@code LocalDate} from a year, month and day.
	 * such as {@code 2007-12-03}.
	 * {@link LocalDate#of(int, int, int)}
	 * 
	 * @param year  year
	 * @param month  month 
	 * @param dayOfMonth  dayOfMonth 
	 * @return {@link LocalDate} 
	 * @throws ParseVariableException {@link ParseVariableException} if the 
	 * string does not contain a parsable value.
	 */
	public static LocalDate parseLocalDate(int year, int month, int dayOfMonth) 
			throws ParseVariableException {
		
		try {
			return LocalDate.of(year, month, dayOfMonth);
		} catch (DateTimeParseException e) {
			throw new ParseVariableException(INCORRECT_DATE);
		}
	}
	
	/**
	 * Obtains an instance of enum Role
	 * 
	 * @param role  the string to be parsed.
	 * @return {@link Role}
	 * @throws ParseVariableException {@link ParseVariableException} if the 
	 * string does not contain a parsable value.
	 */
	public static Role parseRole(String role) 
			throws ParseVariableException {
		
		try {
			return Role.valueOf(role);
		} catch (IllegalArgumentException e) {
			throw new ParseVariableException(INCORRECT_DATA);
		}
	}
	
	/**
	 * Obtains an instance of {@link BigDecimal}.
	 * String length not exceeding 8 characters.
	 * 
	 * @param value  the string to be parsed.
	 * @return {@link BigDecimal}
	 * @throws ParseVariableException {@link ParseVariableException} if the 
	 * string does not contain a parsable value.
	 */
	public static BigDecimal parseBigDecimal(String value) 
			throws ParseVariableException {
		
		if (value.length() > 8) {
			throw new ParseVariableException(INCORRECT_DATA);
		}
		
		try {
			return new BigDecimal(value, MathContext.DECIMAL32);
		} catch (NumberFormatException e) {
			throw new ParseVariableException(INCORRECT_DATA);
		}
	}
}
