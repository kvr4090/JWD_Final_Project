package by.epamtc.kalimylin.controller.command.impl.processing.driver;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command finish trip and redirect to workspace.
 */
public class FinishTripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		TripService tripService = provider.getTripService();
		VehicleService vehicleService = provider.getVehicleService();

		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog;
		ExceptionHelper handler;
		User driver = (User) request.getSession().getAttribute(USER);
		Trip trip = (Trip) request.getSession().getAttribute(TRIP);
		Vehicle truck = setParamTruck(request);		
		boolean isFinish = false;
		List<Vehicle> listVehicle = new ArrayList<Vehicle>();
		
		listVehicle.add(truck);
		page.append(PagePath.GO_TO_WORKSPACE);
		messageToLog = formMessage(driver, trip);
		
		if (truck.getTrailerId() != 0) {
			Vehicle trailer = setParamTrailer(request);
			listVehicle.add(trailer);
		}
		
		try {
			isFinish = vehicleService.finishTrip(listVehicle, trip) 
					   && tripService.finishTrip(trip);

			page.append(ConditionHelper.updateLine(isFinish));
			page.append(FINISH_TRIP);
			messageToLog.append(ConditionHelper.updateLine(isFinish));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());		
    }
	
	/**
	 * Set parameters to truck from request
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @return {@link Vehicle} truck with parameters from request
	 */
	private Vehicle setParamTruck(HttpServletRequest request) {
		
		Vehicle truck = (Vehicle) request.getSession().getAttribute(VEHICLE);
		boolean isAvailableTruck = 
				Boolean.valueOf(request.getParameter(SELECTOR_TRUCK));
		String messageTruck = request.getParameter(MESSAGE_TRUCK);
		truck.setIsAvailable(isAvailableTruck);
		truck.setNote(messageTruck);

		return truck;
	}
	
	/**
	 * Set parameters to trailer from request
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @return {@link Vehicle} trailer with parameters from request
	 */
	private Vehicle setParamTrailer(HttpServletRequest request) {
		
		Vehicle trailer = (Vehicle) request.getSession().getAttribute(TRAILER);
		boolean isAvailableTrailer = 
				Boolean.valueOf(request.getParameter(SELECTOR_TRAILER));
		String messageTrailer = request.getParameter(MESSAGE_TRAILER);
		trailer.setIsAvailable(isAvailableTrailer);
		trailer.setNote(messageTrailer);

		return trailer;
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
				.append(FINISH_TRIP)
				.append(WITH_ID)
				.append(trip.getId());	
		return message;
	}
}
