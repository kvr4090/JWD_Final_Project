package by.epamtc.kalimylin.controller.command.impl.processing.mechanic;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute list vehicle types
 * and then forward to page, where user can fill form to create new vehicle.
 */
public class NewVehicleCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService service = provider.getVehicleService();
		
		User mechanic = (User) request.getSession().getAttribute(USER);
		String page = PagePath.CREATE_VEHICLE;
		StringBuilder messageToLog = formMessage(mechanic);
		List<VehicleType> list;
		ExceptionHelper handler;
		
		try {
			list = service.findAllVehicleType();
			request.setAttribute(VEHICLE_TYPE, list);
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
	 * @param mechanic  current user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User mechanic) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(mechanic.getId())
				.append(GO_TO)
				.append(NEW_VEHICLE_SPACE);		
		return message;
	}
}
