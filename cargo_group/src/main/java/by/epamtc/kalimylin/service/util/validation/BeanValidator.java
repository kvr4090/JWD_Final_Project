package by.epamtc.kalimylin.service.util.validation;

import static by.epamtc.kalimylin.service.util.validation.VariableValidator.*;
import static by.epamtc.kalimylin.service.util.validation.TechValidator.*;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.service.util.MessageService;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;

/**
 * The class contains different methods, which checks to correct values in 
 * parameters in objects of entity classes.
 * Uses for validate {@link VariableValidator} and {@link TechValidator}
 */
public class BeanValidator {
	
	/**
	 * Checks {@link AuthenticationData}
	 * 
	 * @param data  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectAuthenticationData(AuthenticationData data) {
		
		return isNotNull(data) 
				&& isCorrectLogin(data.getLogin()) 
				&& isCorrectPassword(data.getPassword());							
	}
	
	/**
	 * Checks {@link VehicleType}
	 * 
	 * @param type  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectVehicleType(VehicleType type) {
		
		boolean invalidCondition = 
				type.getType().contains(MessageService.TRAILER)
				&& type.getType().contains(MessageService.TRACTOR);
		
		return isNotNull(type) 
				&& isCorrectLongLetterLine(type.getType())
				&& !invalidCondition;
	}
	
	/**
	 * Checks {@link User}
	 * 
	 * @param user  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectUserWithoutEmail(User user) {

		return isNotNull(user) 
				&& isCorrectLogin(user.getLogin())
				&& isCorrectPhone(user.getPhone());
	}
	/**
	 * Checks {@link PersonalData}
	 * 
	 * @param data  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectPersonalData(PersonalData data) {
		
		boolean isCorrectTerminationDate = true;
		
		if (data.getTerminationDate() != null) {
			isCorrectTerminationDate = isCorrectTerminationDate(
					data.getTerminationDate());
		}
		
		return isNotNull(data)
				&& isCorrectLetterLine(data.getName())
				&& isCorrectLetterLine(data.getSurname())
				&& isCorrectLetterLine(data.getPost())
				&& isCorrectPassportNumber(data.getPassportNumber())
				&& isCorrectPassportIdentificationNumber(
						data.getPassportIdentificationNumber())
				&& isCorrectLongLetterLine(data.getPassportAuthority())
				&& isCorrectDriveLicense(data.getDriveLicense())
				&& isCorrectLastYear(data.getPassportDateOfIssue())
				&& isCorrectTomorrowDate(data.getPassportDateOfExpiry())
				&& isCorrectBirthdate(data.getBirthdate())
				&& isCorrectLastYear(data.getRecruitmentDate())
				&& isCorrectTerminationDate;
	}
	
	/**
	 * Checks {@link Order}
	 * 
	 * @param order  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectOrder(Order order) {

		return isNotNull(order) 
				&& isCorrectLongLetterLine(order.getRouteStartPoint()) 
				&& isCorrectLongLetterLine(order.getRouteEndPoint())
				&& isCorrectNote(order.getNote()) 
				&& isCorrectPrice(order.getPrice())
				&& isCorrectRouteDistance(order.getDistance())
				&& TechValidator.isCurrentDate(order.getDate());
	}
	
	/**
	 * Checks {@link Shipper}
	 * 
	 * @param shipper  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectShipper(Shipper shipper) {
		
		return isNotNull(shipper)
				&& isCorrectLetterLine(shipper.getName())
				&& isCorrectEmail(shipper.getEmail())
				&& isCorrectLongLetterLine(shipper.getContactPersonName()) 
				&& isCorrectLongLetterLine(shipper.getContactPersonSurname())
				&& isCorrectPhone(shipper.getContactPhone())
				&& isCorrectNote(shipper.getNote());
	}
	
	/**
	 * Checks {@link Requirement}
	 * 
	 * @param requirement  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectRequirement(Requirement requirement) {
		
		return isNotNull(requirement)
				&& isCorrectWeight(requirement.getWeight()) 
				&& isCorrectVolume(requirement.getVolume()) 
				&& isCorrectPalletsQuantity(requirement.getPalletsQuantity()) 
				&& isCorrectLength(requirement.getMaxLength())
				&& isCorrectWidth(requirement.getMaxWidth())
				&& isCorrectHeight(requirement.getMaxHeight());
	}
	
	/**
	 * Checks {@link Vehicle}
	 * 
	 * @param vehicle  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCorrectVehicle(Vehicle vehicle) {

		return isNotNull(vehicle)
				&& isCorrectLicensePlate(vehicle.getLicensePlate())
				&& isCorrectDriveLicense(vehicle.getDriveLicense())
				&& isCorrectLetterLine(vehicle.getModel())
				&& isCorrectLetterLine(vehicle.getBrand())
				&& isCorrectLetterLine(vehicle.getColor())
				&& isCorrectNote(vehicle.getNote())
				&& isCorrectLastYear(vehicle.getYearOfIssue())
				&& isCorrectOdometer(vehicle.getOdometer());
	}
	
	/**
	 * Checks {@link Trip}
	 * 
	 * @param trip  Entity for validate
	 * @return boolean result of validate 
	 */
	public static boolean isCoorectTrip(Trip trip) {
		
		boolean isCorrectFinishDate = true;
		
		if (trip.getFinishDate() != null) {
			isCorrectFinishDate = isCurrentDate(trip.getFinishDate());
		}
		
		return isNotNull(trip)
				&& isCorrectNote(trip.getNote())
				&& isCorrectTripStartDate(trip.getStartDate())
				&& isCorrectFinishDate;
	}
}
