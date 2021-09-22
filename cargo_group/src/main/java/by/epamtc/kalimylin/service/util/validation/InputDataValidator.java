package by.epamtc.kalimylin.service.util.validation;

import static by.epamtc.kalimylin.service.util.MessageService.INCORRECT_DATA;
import static by.epamtc.kalimylin.service.util.validation.BeanValidator.*;
import static by.epamtc.kalimylin.service.util.validation.VariableValidator.*;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.service.exception.InvalidInputDataServiceException;

/**
 * The class contains different methods, which checks to correct values in 
 * parameters in objects or variables.
 * Uses for validate {@link VariableValidator} and {@link BeanValidator}
 * When incorrect values in the input data, 
 * throws {@link InvalidInputDataServiceException}
 */
public class InputDataValidator {

	public static void checkId(int id) throws InvalidInputDataServiceException {
		
		if (!TechValidator.isCorrectId(id)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}
	
	public static void checkListOrdersId(List<Order> listOrder) 
			throws InvalidInputDataServiceException {
		
		for (Order order : listOrder) {
			checkId(order.getId());
		}
	}

	public static void checkOrder(Order order) 
			throws InvalidInputDataServiceException {
		
		if (!isCorrectOrder(order)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}		
	}
	
	public static void checkRequirement(Requirement requirement) 
			throws InvalidInputDataServiceException {
		
		if (!isCorrectRequirement(requirement)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}

	public static void checkShipper(Shipper shipper) 
			throws InvalidInputDataServiceException {
		
		if (!isCorrectShipper(shipper)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}

	public static void checkTrip(Trip trip) 
			throws InvalidInputDataServiceException {
		
		if (!isCoorectTrip(trip)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}

	public static void checkUser(User user) 
			throws InvalidInputDataServiceException {
		
		if((user.getEmail() == null) || (user.getEmail().isBlank())) {
			if(!isCorrectUserWithoutEmail(user)) {
				throw new InvalidInputDataServiceException(INCORRECT_DATA);
			}
		} else {
			if(!isCorrectEmail(user.getEmail())) {
				throw new InvalidInputDataServiceException(INCORRECT_DATA);
			}
		}	
	}
	
	public static void checkAuthData(AuthenticationData authData) 
			throws InvalidInputDataServiceException {
		
		if (!isCorrectAuthenticationData(authData)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}

	public static void checkPersonalData(PersonalData data)
			throws InvalidInputDataServiceException {
		
		if (!isCorrectPersonalData(data)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}
	
	public static void checkVehicle(Vehicle vehicle) 
			throws InvalidInputDataServiceException {
		
		if (!isCorrectVehicle(vehicle)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	} 

	public static void checkRole(Role role) 
			throws InvalidInputDataServiceException {
		
		if (!VariableValidator.isCorrectRole(role)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}

	public static void checkPassword(String password) 
			throws InvalidInputDataServiceException {
		
		if (!VariableValidator.isCorrectPassword(password)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
	}
}
