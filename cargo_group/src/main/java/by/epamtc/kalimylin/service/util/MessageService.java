package by.epamtc.kalimylin.service.util;

/**
 * Contains all messages in use by service layer
 */
public final class MessageService {
	
	public static final String ALGORITHM = "SHA-256";
	public static final String ALREADY_IN_USE = "already_in_use";
	public static final String ASK_US = " ask us about: ";
	public static final String CLIENT = "Client ";
	public static final String CLIENT_EMAIL = ". Send answer to this email: ";
	public static final String CLIENT_MESSAGE = ", he wrote this message: ";
	public static final String COMMAND = "?command=ACTIVATE_ACCOUNT&token=";
	public static final String CONFIRM_EMAIL = 
			"Welcome user! To activate the account, pass by reference. ";
	public static final String CONFIRM_EMAIL_SUBJECT = "Email confirmation.";
	public static final String DATE_ALREADY_IN_USE = "date_already_in_use";
	public static final String DECODING_FAILED = 
			"Base64 decoding of pbkdf2 output failed.";
	public static final String DEFAULT_CLIENT_NAME = "DefaultClient";
	public static final String DEFAULT_PHONE_NUMBER = "000000000000";
	public static final String EMAIL_ALREADY_CONFIRM = "email_already_confirm";
	public static final String ERROR = "error";
	public static final String FAILED_ADD = "failed.add";
	public static final String FAILED_DELETE = "failed.delete";
	public static final String FAILED_DELETE_ORDER_IN_USE = 
			"failed_delete_order_in_use";
	public static final String FORMAT = "%064x";
	public static final String INCONSISTENCY_DATA = "inconsistency_data";
	public static final String INCORRECT_DATA = "incorrect_data";
	public static final String INVALID_ENTRY = "invalid_entry";
	public static final String INVALID_HASH_ALGORITHM = 
			"Hash algorithm not supported.";
	public static final String INVALID_HASH_LENGTH = 
			"Hash length doesn't match stored hash length.";
	public static final String INVALID_KEY = "Invalid key spec";
	public static final String INVALID_MAIL_PROREPTY = 
			"Invalid mail properties. Please, check mail property file "
			+ "or parameters in file!";
	public static final String ISOTHERMIC = "изот";
	public static final String ITERATION_FAILED = 
			"Invalid number of iterations. Must be >= 1.";
	public static final String LICENSE_PLATE_ALREADY_IN_USE = 
			"license_plate_already_in_use";
	public static final String MAIL_PROPERTIES = "resources/mail";
	public static final String MAIL_USER_NAME = "mail.user.name";
	public static final String MESSAGE_SEND_FAILED = "message_send_failed";
	public static final String MISSING_FIELDS = 
			"Fields are missing from the password hash.";
	public static final String OVERLOAD_TRIP = "overload_trip";
	public static final String PARAM_EMAIL = "&email=";
	public static final String PARSE_HASH_FAILED = 
			"Could not parse the hash size as an integer.";
	public static final String PARSE_ITERATION_FAILED = 
			"Could not parse the iteration count as an integer.";
	public static final String PASSPORT_ALREADY_IN_USE = 
			"passport_already_in_use";
	public static final String REFRIGERATOR = "реф";
	public static final String SALT_FAILED = "Base64 decoding of salt failed.";
	public static final String SEPARATOR_HEIGHT = "в";
	public static final String SEPARATOR_LENGTH = "д ";
	public static final String SEPARATOR_PALLETS = "п ";
	public static final String SEPARATOR_VOLUME = "м3 ";
	public static final String SEPARATOR_WEIGHT = "т ";
	public static final String SEPARATOR_WIDTH = "ш ";
	public static final String SIMILAR_EMAIL = "similar_email";
	public static final String SIMILAR_LOGIN = "similar_login";
	public static final String SIMILAR_PHONE = "similar_phone";
	public static final String TENT = "тент";
	public static final String TRACTOR = "Тягач";
	public static final String TRAILER = "Прицеп";
	public static final String TRIP_STARTED = "trip_started";
	public static final String TRUCK = "Автомобиль";
	public static final String UNSUPPORTED_HASH_TYPE = "Unsupported hash type.";
	public static final String VEHICLE_IN_TRIP = "Vehicle in trip";
	
	private MessageService() {		
	}
}	
