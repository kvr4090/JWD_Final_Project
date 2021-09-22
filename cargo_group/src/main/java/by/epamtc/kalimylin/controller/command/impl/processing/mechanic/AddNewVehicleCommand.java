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
 * The command add new vehicle and then redirect to workspace.
 */
public class AddNewVehicleCommand implements Command {
	
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
		boolean isAdd;
		
		page.append(PagePath.GO_TO_WORKSPACE);
		
		try {
			vehicle = formVehicle(request, vehicle);
			messageToLog = formMessage(mechanic, vehicle);
			isAdd = service.addVehicle(vehicle);
			
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
	 * Form vehicle from request.
	 * YearOfIssue is LocalDate and requires month and day of month.
	 * Vehicle registration certificate contains only year of issue, 
	 * so use default values:
	 * Default month 01 (January) 
	 * Default day of month 01
	 * Set trailer id = 0 by default
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param vehicle  vehicle for update data
	 * @return {@link Vehicle} with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid
	 * input data in parameters
	 */
	private Vehicle formVehicle(HttpServletRequest request, Vehicle vehicle)
			throws ParseVariableException {

		vehicle.setLicensePlate(request.getParameter(LICENSE_PLATE));
		vehicle.setOdometer(parseInt(request.getParameter(ODOMETER)));
		vehicle.setColor(request.getParameter(COLOR));
		vehicle.setBrand(request.getParameter(BRAND));
		vehicle.setModel(request.getParameter(MODEL));
		vehicle.setYearOfIssue(parseLocalDate(
				parseInt(request.getParameter(YEAR)), 1, 1));
		vehicle.setDriveLicense(request.getParameter(DRIVE_LICENSE));
		vehicle.setTypeId(parseInt(request.getParameter(VEHICLE_TYPE)));
		vehicle.setIsAvailable(Boolean.valueOf(
				request.getParameter(CONDITION)));
		vehicle.setNote(request.getParameter(NOTE));
		vehicle.setTrailerId(0);
		
		return vehicle;
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param mechanic  current user
	 * @param vehicle  new vehicle
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User mechanic, Vehicle vehicle) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(mechanic.getId())
				.append(ADD)
				.append(VEHICLE_ID_SPACE)
				.append(vehicle.getLicensePlate());	
		return message;
	}
}
