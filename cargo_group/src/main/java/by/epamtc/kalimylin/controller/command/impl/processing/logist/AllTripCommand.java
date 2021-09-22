package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute list trips, list users, list personal 
 * data, list vehicles and then forward to page, where user can find information 
 * about all trips with details.
 */
public class AllTripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		TripService service = provider.getTripService();
		UserService userService = provider.getUserService();
		VehicleService vehicleService = provider.getVehicleService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		String page = PagePath.ALL_TRIP;
		StringBuilder messageToLog = formMessage(logist);
		ExceptionHelper handler;
		
		List<Trip> listTrip;
		List<PersonalData> listPersonalData;
		List<User> listUser;
		List<Vehicle> listVehicle;
		
		try {
			listTrip = service.findAvailableTrip();
			listPersonalData = userService.findAllPersonalDataToShow();
			listUser = userService.findAllUserToShow();
			listVehicle = vehicleService.findAllVehicle();
			
			request.setAttribute(LIST_TRIP, listTrip);
			request.setAttribute(LIST_USERS, listUser);
			request.setAttribute(LIST_PERSONAL_DATA, listPersonalData);
			request.setAttribute(LIST_VEHICLE, listVehicle);
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
				.append(FIND)
				.append(ALL_TRIPS);	
		return message;
	}
}
