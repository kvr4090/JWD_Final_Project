package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute list users, list vehicles,
 * list personal data and forward to page, where user can find information 
 * about all drivers and their trucks.
 */
public class AllDriversCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();
		VehicleService vehicleService = provider.getVehicleService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		String page = PagePath.ALL_DRIVERS;
		ExceptionHelper handler;
		StringBuilder messageToLog = formMessage(logist);
		List<PersonalData> listPersonalData = new ArrayList<>();
		List<User> listUsers = new ArrayList<>();
		List<Vehicle> listVehicles = new ArrayList<>();
			
		try {
			listVehicles = vehicleService.findAllVehicle();
			listUsers = userService.findAllUserToShow();
			listPersonalData = userService.findAllPersonalDataToShow();
			
			request.setAttribute(LIST_VEHICLE, listVehicles);
			request.setAttribute(LIST_USERS, listUsers);
			request.setAttribute(LIST_PERSONAL_DATA, listPersonalData);
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
				.append(LOG_INTRO).append(logist.getId())
				.append(FIND).append(ALL_DRIVERS);
		
		return message;
	}
}
