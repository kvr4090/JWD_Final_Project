package by.epamtc.kalimylin.service.util.validation;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The class contains different methods, which checks to correct values in 
 * parameters in objects or variables.
 * Uses specified parameter values for comparison.
 * Returns boolean result of compare 
 */
public class TechValidator {
	
	private static final BigDecimal MAX_PRICE_BY_ORDER = 
			BigDecimal.valueOf(200000);
	private static final BigDecimal MIN_PRICE_BY_ORDER = BigDecimal.valueOf(1);
	private static final int MAX_ROUTE_DISTANCE =  15000;
	private static final float MAX_WEIGHT =  50;
	private static final float MAX_VOLUME =  120;
	private static final int MAX_PALLETS_QUANTITY = 66;
	private static final float MAX_LENGTH = 20;
	private static final float MAX_WIDTH = 5;
	private static final float MAX_HEIGHT = 5;
	private static final int MAX_ODOMETER = 9999999;
	private static final int MAX_ID = 10000;
	private static final int OLD_YEAR = 1960;
	private static final int MAX_YEAR = 2030;
	
	private static boolean isPositive(int value) {
		return value >= 0;
	}
	
	private static boolean isPositive(float value) {
		return value >= 0.0;
	}
	
	public static boolean isCorrectId(int id) {
		return (isPositive(id)) && (id < MAX_ID);
	}

	public static boolean isCorrectPrice(BigDecimal price) {
		return (price.compareTo(MAX_PRICE_BY_ORDER) == -1)
				&& (price.compareTo(MIN_PRICE_BY_ORDER) == 1);
	}
	
	public static boolean isCorrectRouteDistance(int distance) {
		return (distance < MAX_ROUTE_DISTANCE) && (isPositive(distance));
	}
	
	public static boolean isCorrectWeight(float weight) {
		return (weight < MAX_WEIGHT) && (isPositive(weight));
	}
	
	public static boolean isCorrectVolume(float volume) {
		return (volume <= MAX_VOLUME) && (isPositive(volume));
	}
	
	public static boolean isCorrectPalletsQuantity(int quantity) {
		return (quantity <= MAX_PALLETS_QUANTITY) && (isPositive(quantity));
	}
	
	public static boolean isCorrectLength(float length) {
		return (length < MAX_LENGTH) && (isPositive(length));
	}
	
	public static boolean isCorrectWidth(float width) {
		return (width < MAX_WIDTH) && (isPositive(width));
	}
	
	public static boolean isCorrectHeight(float height) {
		return (height < MAX_HEIGHT) && (isPositive(height));
	}
	
	public static boolean isCorrectOdometer(int odometer) {
		return (odometer <= MAX_ODOMETER) && (isPositive(odometer));
	}
	
	public static boolean isCorrectTomorrowDate(LocalDate date) {	
		return currentDate().isBefore(date);
	}
	
	public static boolean isCorrectTripStartDate(LocalDate startDate) {
		return (startDate.isAfter(LocalDate.now()) 
				|| startDate.isEqual(LocalDate.now()))
				&& (startDate.getYear() < MAX_YEAR);
	}
	
	public static boolean isCorrectTerminationDate(LocalDate terminationDate) {
		return (terminationDate.isBefore(currentDate()) 
				&& terminationDate.getYear() > OLD_YEAR)
				|| (isCurrentDate(terminationDate));
	}
	
	public static boolean isCorrectBirthdate(LocalDate date) {
		return ((currentDate().getYear() - 18) >= date.getYear()) 
				&& (date.getYear() > OLD_YEAR);
	}
	
	public static boolean isCurrentDate(LocalDate date) {
		return currentDate().getDayOfYear() == date.getDayOfYear();
	}
	
	public static boolean isCorrectLastYear(LocalDate date) {
		return (date.isBefore(currentDate()))
				&& (date.getYear() > OLD_YEAR);
	}
	
	private static LocalDate currentDate() {
		return LocalDate.now();
	}
}
