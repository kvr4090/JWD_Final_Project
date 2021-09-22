package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.*;
import static by.epamtc.kalimylin.controller.util.helper.ConditionHelper.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command delete trip and then redirect to workspace.
 */
public class DeleteTripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		TripService service = provider.getTripService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		StringBuilder page = new StringBuilder();
		Trip trip = new Trip();
		StringBuilder messageToLog = formMessage(logist, trip);
		ExceptionHelper handler;
		boolean isSuccessDeleteTrip;
		
		page.append(PagePath.GO_TO_WORKSPACE);
				
		try {
			trip.setId(parseInt(request.getParameter(TRIP)));
			trip.setVehicleId(parseInt(request.getParameter(VEHICLE)));
			messageToLog = formMessage(logist, trip);
			isSuccessDeleteTrip = service.deleteTrip(trip);
			
			page.append(updateLine(isSuccessDeleteTrip));
			page.append(DELETE);
			messageToLog.append(updateLine(isSuccessDeleteTrip));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param logist  current user
	 * @param trip  trip for delete
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, Trip trip) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(DELETE)
				.append(TRIP_ID_SPACE)
				.append(trip.getId());
		return message;
	}
}
