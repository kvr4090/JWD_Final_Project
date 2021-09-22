package by.epamtc.kalimylin.controller.command.impl.processing.mechanic;

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
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command update vehicle and then redirect to motorpool.
 */
public class UpdateVehicleCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService service = provider.getVehicleService();
		
		User mechanic = (User) request.getSession().getAttribute(USER);
		Vehicle vehicle = new Vehicle();
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog = formMessage(mechanic, vehicle);
		ExceptionHelper handler;
		boolean isUpdate = false;
		
		page.append(PagePath.GO_TO_MOTORPOOL);
				                                                
		try {
			vehicle.setId(parseInt(request.getParameter(ID)));
			messageToLog = formMessage(mechanic, vehicle);
			vehicle = service.findVehicleById(vehicle);			
			vehicle = fillVehicle(request, vehicle);
			
			isUpdate = service.updateVehicle(vehicle);
			
			page.append(ConditionHelper.updateLine(isUpdate));
			page.append(UPDATE);
			messageToLog.append(ConditionHelper.updateLine(isUpdate));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Fill vehicle data from request.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param vehicle  vehicle for update data
	 * @return {@link Vehicle} with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid
	 * input data in parameters
	 */
	private Vehicle fillVehicle(HttpServletRequest request, Vehicle vehicle)
			throws ParseVariableException {
		
		vehicle.setLicensePlate(request.getParameter(LICENSE_PLATE));
		vehicle.setOdometer(parseInt(request.getParameter(ODOMETER)));
		vehicle.setIsAvailable(Boolean.valueOf(
				request.getParameter(CONDITION)));
		vehicle.setColor(request.getParameter(COLOR));
		vehicle.setTrailerId(parseInt(request.getParameter(HOOK_UP)));													
		vehicle.setTypeId(parseInt(request.getParameter(VEHICLE_TYPE)));
		vehicle.setNote(request.getParameter(NOTE));
		
		return vehicle;
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param mechanic  current user
	 * @param vehicle  current vehicle 
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User mechanic, Vehicle vehicle) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(mechanic.getId())
				.append(UPDATE)
				.append(VEHICLE_ID_SPACE)
				.append(vehicle.getId());	
		return message;
	}
}
