package by.epamtc.kalimylin.controller.command.impl.processing.driver;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request and forward to trip page.
 */
public class TripPageCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		TripService tripService = provider.getTripService();
		OrderService orderService = provider.getOrderService();
		VehicleService vehicleService = provider.getVehicleService();
		
		User driver = (User) request.getSession().getAttribute(USER);
		Trip trip = new Trip();
		String page = PagePath.TRIP_PAGE;
		ExceptionHelper handler;
		StringBuilder messageToLog = formMessage(driver, trip);
		List<Order> listOrder = null;
		Vehicle truck = new Vehicle();
		Vehicle trailer = new Vehicle();
		
		try {
			trip = tripService.findCurrentTrip(driver);
			request.setAttribute(TRIP, trip);
		
			if (trip != null) {
				truck.setId(trip.getVehicleId());
				messageToLog = formMessage(driver, trip);
				
				listOrder = orderService.findOrderByTrip(trip);
				truck = vehicleService.findVehicleById(truck);
				trailer.setId(truck.getTrailerId());
				trailer = vehicleService.findVehicleById(trailer);
				
				request.setAttribute(LIST_ORDER, listOrder);
				request.setAttribute(VEHICLE, truck);
				request.setAttribute(TRAILER, trailer);	
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
				.append(FIND)
				.append(TRIP_ID_SPACE)
				.append(trip.getId());		
		return message;
	}
}
