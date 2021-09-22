package by.epamtc.kalimylin.controller.command;

import static by.epamtc.kalimylin.controller.command.ParameterName.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.controller.command.impl.go_to.GoToBackCommand;
import by.epamtc.kalimylin.controller.command.impl.go_to.GoToWelcomePageCommand;
import by.epamtc.kalimylin.controller.command.impl.processing.driver.*;
import by.epamtc.kalimylin.controller.command.impl.go_to.logist.GoToNewShipperCommand;
import by.epamtc.kalimylin.controller.command.impl.go_to.mechanic.GoToNewRequirementCommand;
import by.epamtc.kalimylin.controller.command.impl.go_to.user.*;
import by.epamtc.kalimylin.controller.command.impl.processing.user.WorkPageCommand;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.command.impl.info.*;
import by.epamtc.kalimylin.controller.command.impl.processing.admin.*;
import by.epamtc.kalimylin.controller.command.impl.processing.driver.*;
import by.epamtc.kalimylin.controller.command.impl.processing.mechanic.*;
import by.epamtc.kalimylin.controller.command.impl.processing.user.*;

/**
 * Provider for different commands
 * Defines command
 */
public class CommandProvider {
	
	private static final Logger logger = LogManager.getLogger();
	private final int MAX_LENGTH_COMMAND = 30;
	
	private Map<ParameterName, Command> commands = 
			new HashMap<ParameterName, Command>();
	
	/**
	 * Initialize available commands:
	 * default commands
	 * command for driver
	 * command for mechanic
	 * command for logist
	 * command for admin
	 * command for all roles
	 * command for logist and mechanic
	 */
	public CommandProvider() {		
		 /* default commands */		
		commands.put(BACK, new GoToBackCommand());
		commands.put(WELCOME_USER, new GoToWelcomePageCommand());
		commands.put(LOG_IN, new AuthorizationUserCommand());
		commands.put(REGISTRATION_USER, new RegistrationUserCommand());
		commands.put(REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(ACTIVATE_ACCOUNT, new ConfirmEmailCommand());
		commands.put(LOG_IN_PAGE, new GoToLogInPageCommand());
		commands.put(CONTACT_PAGE, new GoToContactPageCommand());
		commands.put(SEND_QUESTION, new SendQuestionCommand());
		commands.put(ABOUT_PAGE, new GoToAboutPageCommand());
		commands.put(UPDATE_USER_DATA_PAGE, new GoToUpdateUserDataCommand());
		commands.put(UPDATE_USER_DATA, new UpdateUserDataCommand());
		commands.put(LOCALIZATION, new LocalizationCommand());
		 /* command for driver */
		commands.put(START_TRIP, new StartTripCommand());
		commands.put(TRIP_PAGE, new TripPageCommand());
		commands.put(FINISH_TRIP_PAGE, new FinishTripPageCommand());	
		commands.put(FINISH_TRIP, new FinishTripCommand());
		 /* command for mechanic */		
		commands.put(MOTORPOOL, new MotorpoolCommand());
		commands.put(PREPARE_TO_UPDATE_VEHICLE, 
				new PrepareToUpdateVehicleCommand());
		commands.put(UPDATE_VEHICLE, new UpdateVehicleCommand());
		commands.put(ADD_NEW_VEHICLE, new AddNewVehicleCommand());
		commands.put(ADD_NEW_VEHICLE_TYPE, new AddNewVehicleTypeCommand());
		commands.put(NEW_VEHICLE, new NewVehicleCommand());
		commands.put(NEW_VEHICLE_TYPE, new NewVehicleTypeCommand());
		 /* command for logist */
		commands.put(NEW_SHIPPER, new GoToNewShipperCommand());
		commands.put(ADD_SHIPPER, new AddShipperCommand());
		commands.put(NEW_ORDER, new NewOrderCommand());
		commands.put(ADD_ORDER, new AddOrderCommand());
		commands.put(ALL_SHIPPERS, new AllShippersCommand());
		commands.put(ALL_DRIVERS, new AllDriversCommand());
		commands.put(DRIVER_VEHICLE, new DriverVehicleCommand());
		commands.put(CHANGE_DRIVER_TRUCK, new ChangeDriverTruckCommand());
		commands.put(NEW_TRIP, new NewTripCommand());
		commands.put(ADD_TRIP, new AddTripCommand());
		commands.put(ALL_TRIP, new AllTripCommand());
		commands.put(TRIP, new TripCommand());
		commands.put(DELETE_TRIP, new DeleteTripCommand());
		commands.put(DELETE_ORDER, new DeleteOrderCommand());
		commands.put(ALL_ORDERS, new AllOrdersCommand());
		 /* command for admin */
		commands.put(ALL_USERS, new AllUsersCommand());
		commands.put(UPDATE_USER, new UpdateUserPersonalDataCommand());
		commands.put(USER_DATA, new UserPersonalDataCommand());
		commands.put(UPDATE_PASSWORD, new UpdateUserPasswordCommand());
		 /* command for all roles */
		commands.put(WORKSPACE, new WorkPageCommand());
		commands.put(LOG_OUT, new LogOutCommand());
		 /* command for logist and mechanic */
		commands.put(NEW_REQUIREMENT, new GoToNewRequirementCommand());
		commands.put(ADD_NEW_REQUIREMENT, new AddRequirementCommand());
	}
	
	/**
	 * Processing input string for valid command.
	 * Default command is welcome_user.
	 * Can catch IllegalArgumentException, NullPointerException
	 * if invalid input data.
	 * 
	 * @param commandName  input command name,
	 * need to check correct string length
	 * @return {@link Command} available valid command
	 */
	public Command getCommand(String commandName) {
		
		Command command = commands.get(WELCOME_USER);
		
		try {
			if (commandName.length() < MAX_LENGTH_COMMAND) {
				command = commands.get(valueOf(commandName.toUpperCase()));
	            logger.log(Level.INFO, Message.REQUEST_COMMAND + commandName);			
			}            
        } catch (IllegalArgumentException | NullPointerException e) {
        	logger.log(Level.WARN, Message.UNKNOWN_COMMAND + commandName);
        }
		return command;
	}	
}
