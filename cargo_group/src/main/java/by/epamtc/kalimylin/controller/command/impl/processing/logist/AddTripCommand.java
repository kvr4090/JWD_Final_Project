package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.AttributeAndParameter;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command add new trip and redirect to workspace.
 */
public class AddTripCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		TripService tripService = provider.getTripService();	
		
		User logist = (User) request.getSession().getAttribute(USER);
		Trip currentTrip = new Trip();
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog = formMessage(logist, currentTrip);
		ExceptionHelper handler;
		List<Integer> mixListId = new ArrayList<>();
		
		Enumeration<String> params = request.getParameterNames();
		String note = request.getParameter(NOTE);
		String tripDate = request.getParameter(AttributeAndParameter.DATE);
		int driverId;
		boolean isProcessing;
		
		mixListId.add(logist.getId());
		currentTrip.setNote(note);
		page.append(PagePath.GO_TO_WORKSPACE);
		
		try {
			currentTrip.setStartDate(parseLocalDate(tripDate));
			
			driverId = parseInt(request.getParameter(DRIVER_ID));
			mixListId.add(driverId);
			
			messageToLog = formMessage(logist, currentTrip);
			
			mixListId.addAll(formListIdFromParams(params, request));
			
			isProcessing = tripService.processingTrip(currentTrip, mixListId);
			
			page.append(ConditionHelper.updateLine(isProcessing));
			page.append(ADD);
			messageToLog.append(ConditionHelper.updateLine(isProcessing));
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
	 * @param trip  new trip
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, Trip trip) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(ADD)
				.append(TRIP_ID_SPACE)
				.append(trip.toString());	
		return message;
	}
	
	/**
	 * Form list orders id, uses Enumeration {@code String} parameters names
	 * from request. Parse only parameters which contains "order". 
	 * 
	 * @param params  contains orders id
	 * @param request  {@link HttpServletRequest}
	 * @return {@link List} of {@link Integer} with orders id
	 * @throws ParseVariableException {@link ParseVariableException} if invalid
	 * input data in parameters
	 */
	private List<Integer> formListIdFromParams(Enumeration<String> params, 
			HttpServletRequest request) throws ParseVariableException {
		
		List<Integer> mixListId = new ArrayList<>();
		int tempOrderId;
		
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
																					
			if (param.contains(AttributeAndParameter.ORDER)) {				
				tempOrderId = parseInt(request.getParameter(param));				
				mixListId.add(tempOrderId);
			}
		}	
		return mixListId;
	}	
}
