package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.Vehicle;
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
 * The command fill request attribute list vehicles, list vehicle types,
 * driver id, driver truck, driver trailer and then forward to page, 
 * where user can find information about drivers vehicle with details.
 */
public class DriverVehicleCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(); 

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService service = provider.getVehicleService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		String page = PagePath.DRIVER_VEHICLE;
		List<Vehicle> listVehicle = new ArrayList<>();
		List<VehicleType> listType = new ArrayList<>();
		String driverVehicleLicensePlate = request.getParameter(VEHICLE);
		Vehicle primaryVehicle = new Vehicle();
		Vehicle secondaryVehicle = new Vehicle();
		StringBuilder messageToLog = formMessage(logist, primaryVehicle);
		ExceptionHelper handler;

		primaryVehicle.setLicensePlate(driverVehicleLicensePlate);
		
		try {
			if (driverVehicleLicensePlate != null) {
				primaryVehicle = 
						service.findVehicleByLicensePlate(primaryVehicle);
				secondaryVehicle.setId(primaryVehicle.getTrailerId());
				secondaryVehicle = service.findVehicleById(primaryVehicle);
			}
			messageToLog = formMessage(logist, primaryVehicle);
			listVehicle = service.findAllVehicle();
			listType = service.findAllVehicleType();
			
			request.setAttribute(USER_ID, request.getParameter(USER_ID));
			request.setAttribute(TRUCK, primaryVehicle);
			request.setAttribute(TRAILER, secondaryVehicle);
			request.setAttribute(LIST_VEHICLE, listVehicle);
			request.setAttribute(LIST_VEHICLE_TYPE, listType);
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
	 * @param vehicle  selected vehicle
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, Vehicle vehicle) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(FIND)
				.append(DRIVER_VEHICLE)
				.append(vehicle.getId());
		return message;
	}
}
