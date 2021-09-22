package by.epamtc.kalimylin.controller.command.impl.processing.driver;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill session attributes and forward to finish trip page.
 * If empty trip, will add message to request, that nothing to finish.
 */
public class FinishTripPageCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		TripService tripService = provider.getTripService();		
		VehicleService vehicleService = provider.getVehicleService();
		
		String page = PagePath.WELCOME_PAGE;
		User driver = (User) request.getSession().getAttribute(USER);
		Trip trip = new Trip(); 
		StringBuilder messageToLog = formMessage(driver, trip);
		ExceptionHelper handler;
		Vehicle vehicle = new Vehicle();
		Vehicle trailer = new Vehicle();

		try {
			trip = tripService.findCurrentTrip(driver);
			
			if (trip == null) {
				request.setAttribute(MESSAGE, EMPTY_TRIP_TO_FINISH);
				messageToLog.append(EMPTY_TRIP_TO_FINISH);
			} else {
				vehicle.setId(trip.getVehicleId());
				vehicle = vehicleService.findVehicleById(vehicle);
				trailer.setId(vehicle.getTrailerId());
				trailer = vehicleService.findVehicleById(trailer);
				
				request.getSession().setAttribute(TRIP, trip);
				request.getSession().setAttribute(VEHICLE, vehicle);
				request.getSession().setAttribute(TRAILER, trailer);
				
				page = PagePath.FINISH_TRIP_PAGE;
				messageToLog = formMessage(driver, trip);
			}
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
	 * @param driver  current user
	 * @param trip  trip to finish
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User driver, Trip trip) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(driver.getId())
				.append(GO_TO)
				.append(FINISH_TRIP)
				.append(WITH_ID)
				.append(trip.getId());	
		return message;
	}
}
