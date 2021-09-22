package by.epamtc.kalimylin.controller.command;

/**
 * Available commands name
 */
public enum ParameterName {
	
	/* default command name */	
	REGISTRATION_USER,	
	WELCOME_USER,		
	REGISTRATION_PAGE,
	LOG_IN_PAGE,		
	CONTACT_PAGE,
	ABOUT_PAGE,				
	LOG_IN,					
	ACTIVATE_ACCOUNT,
	SEND_QUESTION,
	LOCALIZATION,
	BACK,
	
	/* admin command name */ 
	ALL_USERS,
	UPDATE_USER,
	USER_DATA,
	UPDATE_PASSWORD,
	
	/* logist command name */
	NEW_ORDER,
	NEW_SHIPPER,
	ADD_SHIPPER,
	ADD_ORDER,
	ALL_SHIPPERS,
	ALL_DRIVERS,
	DRIVER_VEHICLE,
	CHANGE_DRIVER_TRUCK,
	ADD_TRIP,
	NEW_TRIP,
	ALL_TRIP,
	TRIP,
	DELETE_TRIP,
	DELETE_ORDER,
	ALL_ORDERS,
	
	/* driver command name */
	FINISH_TRIP_PAGE,
	TRIP_PAGE,
	FINISH_TRIP,
	START_TRIP,
	
	/* mechanic command name */	
	MOTORPOOL,
	UPDATE_VEHICLE,
	PREPARE_TO_UPDATE_VEHICLE,
	NEW_VEHICLE,
	ADD_NEW_VEHICLE,
	NEW_VEHICLE_TYPE,
	ADD_NEW_VEHICLE_TYPE,
	
	/* logist and mechanic command name */		
	ADD_NEW_REQUIREMENT,				
	NEW_REQUIREMENT,
	
	/* all roles command name */		
	WORKSPACE,
	LOG_OUT,
	UPDATE_USER_DATA_PAGE,  
	UPDATE_USER_DATA	
}
