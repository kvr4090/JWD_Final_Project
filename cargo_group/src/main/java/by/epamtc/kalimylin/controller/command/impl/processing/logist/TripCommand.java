package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.ParseVariable;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.ShipperService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute list shippers, list orders and then
 * forward to page, where user can find trip details.
 */
public class TripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		OrderService orderService = provider.getOrderService();
		ShipperService shipperService = provider.getShipperService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		String page = PagePath.TRIP;
		StringBuilder messageToLog = formMessage(logist);
		Trip trip = new Trip();
		ExceptionHelper handler;
				
		List<Order> listOrder;
		List<Shipper> listShippers;
		
		try {
			trip.setId(ParseVariable.parseInt(request.getParameter(TRIP)));
			listOrder = orderService.findOrderByTrip(trip);
			listShippers = shipperService.findAllShippers();
			
			request.setAttribute(LIST_ORDER, listOrder);
			request.setAttribute(LIST_SHIPPER, listShippers);
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
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
				.append(TRIP_INFO);		
		return message;
	}
}
