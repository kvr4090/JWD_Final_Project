package by.epamtc.kalimylin.controller.command.impl.processing.mechanic;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.VehicleType;
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
 * The command add new vehicleType and then redirect to workspace.
 */
public class AddNewVehicleTypeCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService vehicleService = provider.getVehicleService();
		
		User mechanic = (User) request.getSession().getAttribute(USER);
		VehicleType type = new VehicleType();
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog = formMessage(mechanic, type);
		ExceptionHelper handler;
		boolean isAdd;
		
		page.append(PagePath.GO_TO_WORKSPACE);

		try {
			type = createVehicleType(request, type);
			messageToLog = formMessage(mechanic, type);
			isAdd = vehicleService.addVehicleType(type);
			
			page.append(ConditionHelper.updateLine(isAdd));
			page.append(ADD);
			messageToLog.append(ConditionHelper.updateLine(isAdd));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}			
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form vehicleType from request.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param vehicleType  vehicleType for update
	 * @return {@link VehicleType} with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid
	 * input data in parameters
	 */
	private VehicleType createVehicleType(HttpServletRequest request,
			VehicleType vehicleType) throws ParseVariableException {
		
		StringBuilder type = new StringBuilder();
		
		type.append(request.getParameter(CHASSIS))
			.append(SEPARATOR_SPACE)
			.append(request.getParameter(VEHICLE_TYPE))
			.append(SEPARATOR_SPACE);
		
		vehicleType.setRequirementId(
				ParseVariable.parseInt(request.getParameter(REQUIREMENT)));
		vehicleType.setIsAvailable(true);
		vehicleType.setType(type.toString());

		return vehicleType;	
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param mechanic  current user
	 * @param type  current vehicleType 
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User mechanic, VehicleType type) {
		
		StringBuilder message = new StringBuilder() 
				.append(LOG_INTRO)
				.append(mechanic.getId())
				.append(ADD)
				.append(VEHICLE_TYPE_SPACE)
				.append(type.getType());
		return message;
	}
}
