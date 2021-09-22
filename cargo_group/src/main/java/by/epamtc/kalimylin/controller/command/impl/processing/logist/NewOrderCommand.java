package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.RequirementService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.ShipperService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute list shippers, list requirements and then
 * forward to page, where user can fill form to create new shipper.
 */
public class NewOrderCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		ShipperService shipperService = provider.getShipperService();
		RequirementService requirementService = 
				provider.getRequirementService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		String page = PagePath.NEW_ORDER;
		StringBuilder messageToLog = formMessage(logist);
		ExceptionHelper handler;
		
		try {
			List<Shipper> listShipper = shipperService.findAllShippers();
			List<Requirement> listRequirement =
					requirementService.findAllRequirement();
			
			request.setAttribute(LIST_SHIPPER, listShipper);
			request.setAttribute(LIST_REQUIREMENT, listRequirement);
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
				.append(GO_TO)
				.append(NEW_ORDER);	
		return message;
	}
}
