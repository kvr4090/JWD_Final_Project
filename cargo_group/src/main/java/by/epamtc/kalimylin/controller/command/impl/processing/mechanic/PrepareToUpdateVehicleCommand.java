package by.epamtc.kalimylin.controller.command.impl.processing.mechanic;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
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
 * The command fill request attribute current vehicle, 
 * current primary vehicleType, current secondary vehicleType,
 * list secondary vehicle, list primary VehicleTypes, list secondaryVehicleTypes
 * and then forward to page, where user can fill form to update vehicle.
 * When prepare to update vehicle truck, truck is primary vehicle,
 * trailer is secondary vehicle.
 * When prepare to update vehicle trailer, truck is secondary vehicle,
 * trailer is primary vehicle.
 * VehicleTypes and lists distribution is similar vehicle.
 */
public class PrepareToUpdateVehicleCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService vehicleService = provider.getVehicleService();
		
		User mechanic = (User) request.getSession().getAttribute(USER);
		String page = PagePath.UPDATE_VEHICLE;
		StringBuilder messageToLog = formMessage(mechanic);
		String currentLicensePlate = request.getParameter(VEHICLE);
		Vehicle currentVehicle = new Vehicle();
		ExceptionHelper handler;
		
		String currentPrimaryVehicleType = null;
		String currentSecondaryVehicleType = null;
		List<Vehicle> secondaryVehicleList = null;
		List<VehicleType> primaryVehicleTypeList = null;
		List<VehicleType> secondaryVehicleTypeList = null;
		List<List<VehicleType>> availableType = null;
		List<String> roadTrainComposition = null;
		
		currentVehicle.setLicensePlate(currentLicensePlate);
		
		try {
			
			currentVehicle = 
					vehicleService.findVehicleByLicensePlate(currentVehicle);
			
			roadTrainComposition = 
					vehicleService.findRoadTrainComposition(currentVehicle);
			
			currentPrimaryVehicleType = roadTrainComposition.get(0);
			currentSecondaryVehicleType = roadTrainComposition.get(1);
			
			secondaryVehicleList = vehicleService.findSecondaryVehicle(
					currentPrimaryVehicleType);		
			
			availableType = vehicleService.separateVehicleType(
					currentPrimaryVehicleType);		
			
			primaryVehicleTypeList = availableType.get(0);							
			secondaryVehicleTypeList = availableType.get(1);
						
			request.setAttribute(CURRENT_VEHICLE, currentVehicle);
			request.setAttribute(
					CURRENT_PRIMARY_VEHICLE_TYPE, currentPrimaryVehicleType);			
			request.setAttribute(CURRENT_SECONDARY_VEHICLE_TYPE,
					currentSecondaryVehicleType);			
			request.setAttribute(SECONDARY_VEHICLE, secondaryVehicleList);
			request.setAttribute(PRIMARY_VEHICLE_TYPE, primaryVehicleTypeList);
			request.setAttribute(
					SECONDARY_VEHICLE_TYPE, secondaryVehicleTypeList);
			
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
				.append(UPDATE)
				.append(VEHICLE_ID_SPACE);
		return message;
	}
}
