package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.RequirementService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.ShipperService;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute list shippers, list orders, list vehicles,
 * list vehicle types, list users, list personal data, list requirements
 * and then forward to page, where user can fill form to create new trip.
 */
public class NewTripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();
		VehicleService vehicleService = provider.getVehicleService();
		OrderService orderService = provider.getOrderService();
		ShipperService shipperService = provider.getShipperService();
		RequirementService requirementService = 
				provider.getRequirementService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		String page = PagePath.NEW_TRIP;
		StringBuilder messageToLog = formMessage(logist);
		ExceptionHelper handler;
		
		List<PersonalData> listPersonalData;
		List<User> listUsers;
		List<Vehicle> listVehicles;
		List<VehicleType> listVehicleTypes;
		List<Order> listOrders;
		List<Shipper> listShippers;
		List<Requirement> listRequirements;
	
		try {
			listPersonalData = userService.findAllPersonalDataToShow();
			listUsers = userService.findAllUserToShow();
			listVehicles = vehicleService.findAllVehicle();
			listVehicleTypes = vehicleService.findAllVehicleType();
			listOrders = orderService.findAvailableOrder();
			listShippers = shipperService.findAllShippers();
			listRequirements = requirementService.findAllRequirement(); 
	
			request.setAttribute(LIST_USERS, listUsers);
			request.setAttribute(LIST_PERSONAL_DATA, listPersonalData);
			request.setAttribute(LIST_VEHICLE, listVehicles);
			request.setAttribute(LIST_VEHICLE_TYPE, listVehicleTypes);
			request.setAttribute(LIST_ORDER, listOrders);
			request.setAttribute(LIST_SHIPPER, listShippers);
			request.setAttribute(LIST_REQUIREMENT, listRequirements);
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			request.setAttribute(MESSAGE, handler.getAttributeValue());
		}
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param logist  current user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(GO_TO)
				.append(NEW_TRIP);	
		return message;
	}
}
