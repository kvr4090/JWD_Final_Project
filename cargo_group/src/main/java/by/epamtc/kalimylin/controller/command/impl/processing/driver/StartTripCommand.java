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
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.ParseVariable;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command start trip and forward to workspace.
 */
public class StartTripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService service = provider.getVehicleService();
		
		User driver = (User) request.getSession().getAttribute(USER);
		Trip trip = new Trip();
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog = formMessage(driver, trip);
		ExceptionHelper handler;
		Vehicle truck = new Vehicle();
		boolean isStarted;

		truck.setId(driver.getVehicleId());
		page.append(PagePath.GO_TO_WORKSPACE);
		
		try {
			trip.setId(ParseVariable.parseInt(request.getParameter(TRIP)));
			messageToLog = formMessage(driver, trip);
			isStarted = service.updateVehicleBeforeStartTrip(truck);
			
			page.append(ConditionHelper.updateLine(isStarted));
			page.append(START_TRIP);
			messageToLog.append(ConditionHelper.updateLine(isStarted));
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
	 * @param driver  current user
	 * @param trip  trip to finish
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User driver, Trip trip) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(driver.getId())
				.append(START_TRIP)
				.append(WITH_ID)
				.append(trip.getId());	
		return message;
	}
}
