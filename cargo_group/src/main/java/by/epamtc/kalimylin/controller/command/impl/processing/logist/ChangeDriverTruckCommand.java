package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command changes the truck from the selected user 
 * and then redirect to workspace.
 * When try to update a truck with a same truck, command just write to log, that
 * it was a unnecessary update truck.
 */
public class ChangeDriverTruckCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider;
		UserService service;
		
		User logist = (User) request.getSession().getAttribute(USER);
		User driver = new User();
		StringBuilder page = new StringBuilder();
		ExceptionHelper handler;
		StringBuilder messageToLog = formMessage(logist, driver, 0);
		Vehicle truck = new Vehicle();
		int newVehicleId = 0;
		boolean isUpdate;
		
		page.append(PagePath.GO_TO_WORKSPACE);
		
		try {			
			truck.setId(parseInt(request.getParameter(ID)));
			newVehicleId = parseInt(request.getParameter(NEW_VEHICLE));

			if (truck.getId() != newVehicleId) {
				
				provider = ServiceProvider.getInstance();
				service = provider.getUserService();
				
				driver.setId(parseInt(request.getParameter(USER_ID)));
				driver.setVehicleId(newVehicleId);
				
				messageToLog = formMessage(logist, driver, newVehicleId);
				isUpdate = service.updateUserVehicleId(driver);
				
				page.append(ConditionHelper.updateLine(isUpdate));
				page.append(UPDATE);
				messageToLog.append(ConditionHelper.updateLine(isUpdate));
				
			} else {
				messageToLog.append(UNNECESSARY_UPDATE);
			}
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
	 * @param driver  selected driver
	 * @param newTruckId  id from selected new truck for user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, User driver, 
			int newTruckId) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(CHANGE)
				.append(LOG_INTRO)
				.append(driver.getId())
				.append(TRUCK_ID_SPACE)
				.append(driver.getVehicleId())
				.append(TO)
				.append(TRUCK_ID_SPACE)
				.append(newTruckId);
		return message;
	}
}
