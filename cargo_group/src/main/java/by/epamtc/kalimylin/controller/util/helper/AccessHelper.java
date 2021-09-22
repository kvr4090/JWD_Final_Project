package by.epamtc.kalimylin.controller.util.helper;

import static by.epamtc.kalimylin.controller.command.ParameterName.*;

import java.util.ArrayList;
import java.util.HashMap;

import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.ParameterName;

/**
 * Class for control access to different command names.
 * Use hashMap for storage available pairs
 * (Role, {@link ArrayList} of {@link ParameterName} available command names)
 * Personal hashMap for each role.
 * Values initialize in static block.
 */
public class AccessHelper {
	
	private static final ArrayList<ParameterName> defaultCommand = 
			new ArrayList<>();
	
	private static final ArrayList<ParameterName> adminCommand = 
			new ArrayList<>();
	
	private static final ArrayList<ParameterName> logistCommand = 
			new ArrayList<>();
	
	private static final ArrayList<ParameterName> mechanicCommand = 
			new ArrayList<>();
	
	private static final ArrayList<ParameterName> driverCommand = 
			new ArrayList<>();
	
	private static final ArrayList<ParameterName> allRoleCommand = 
			new ArrayList<>();
	
	private static final HashMap<Role, ArrayList<ParameterName>> commandMap = 
			new HashMap<Role, ArrayList<ParameterName>>();
	
	static {
		defaultCommand.add(REGISTRATION_USER);
		defaultCommand.add(WELCOME_USER);
		defaultCommand.add(REGISTRATION_PAGE);
		defaultCommand.add(LOG_IN_PAGE);
		defaultCommand.add(CONTACT_PAGE);
		defaultCommand.add(ABOUT_PAGE);
		defaultCommand.add(LOG_IN);
		defaultCommand.add(LOG_OUT);
		defaultCommand.add(ACTIVATE_ACCOUNT);
		defaultCommand.add(SEND_QUESTION);
		defaultCommand.add(LOCALIZATION);
		defaultCommand.add(BACK);
		
		adminCommand.add(ALL_USERS);
		adminCommand.add(UPDATE_USER);
		adminCommand.add(USER_DATA);
		adminCommand.add(UPDATE_PASSWORD);
		
		logistCommand.add(NEW_ORDER);
		logistCommand.add(NEW_SHIPPER);
		logistCommand.add(ADD_SHIPPER);
		logistCommand.add(ADD_ORDER);
		logistCommand.add(ALL_SHIPPERS);
		logistCommand.add(ALL_DRIVERS);
		logistCommand.add(DRIVER_VEHICLE);
		logistCommand.add(CHANGE_DRIVER_TRUCK);
		logistCommand.add(ADD_TRIP);
		logistCommand.add(NEW_TRIP);
		logistCommand.add(ALL_TRIP);
		logistCommand.add(TRIP);
		logistCommand.add(DELETE_TRIP);
		logistCommand.add(DELETE_ORDER);
		logistCommand.add(ALL_ORDERS);
		logistCommand.add(ADD_NEW_REQUIREMENT);
		logistCommand.add(NEW_REQUIREMENT);
		
		driverCommand.add(FINISH_TRIP_PAGE);
		driverCommand.add(TRIP_PAGE);
		driverCommand.add(FINISH_TRIP);
		driverCommand.add(START_TRIP);
		
		mechanicCommand.add(MOTORPOOL);
		mechanicCommand.add(UPDATE_VEHICLE);
		mechanicCommand.add(PREPARE_TO_UPDATE_VEHICLE);
		mechanicCommand.add(NEW_VEHICLE);
		mechanicCommand.add(ADD_NEW_VEHICLE);
		mechanicCommand.add(NEW_VEHICLE_TYPE);
		mechanicCommand.add(ADD_NEW_VEHICLE_TYPE);
		mechanicCommand.add(ADD_NEW_REQUIREMENT);
		mechanicCommand.add(NEW_REQUIREMENT);
		
		allRoleCommand.add(WORKSPACE);
		allRoleCommand.add(LOG_OUT);
		allRoleCommand.add(UPDATE_USER_DATA_PAGE);
		allRoleCommand.add(UPDATE_USER_DATA);
		
		commandMap.put(Role.ADMIN, adminCommand);
		commandMap.put(Role.DRIVER, driverCommand);
		commandMap.put(Role.LOGIST, logistCommand);
		commandMap.put(Role.MECHANIC, mechanicCommand);
		commandMap.put(Role.GUEST, allRoleCommand);	
	}

	private AccessHelper() {
	}
	
	/**
	 * Take role from user and check, if a command is in the list 
	 * of available commands for this role.
	 * If the command is in lists, that are not available for this role,
	 * access denied
	 * 
	 * @param user  user for check
	 * @param parameterName  command name for check
	 * @return boolean result of the verification:
	 * true - access granted 
	 * false - access denied
	 */
	public static boolean checkAccess(User user, ParameterName parameterName) {
		
		if (user == null) {		
			return defaultCommand.contains(parameterName);
			
		} else {
			ArrayList<ParameterName> availableCommand = 
					commandMap.get(user.getRole());
			
			return defaultCommand.contains(parameterName)
					|| allRoleCommand.contains(parameterName)
					|| availableCommand.contains(parameterName);
		}
	}
}
