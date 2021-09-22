package by.epamtc.kalimylin.dao.util;

/**
 * Contains all messages in use by DAO layer
 */
public final class MessageDAO {
	
	public static final String CLOSE_CLOSED_CONNECTION = 
			"Try to close closed connection";
	public static final String CONNECTION_ADDED = 
			"connection added to freeConnection: ";
	public static final String CREATED_INSTANSE = "created instanse";
	public static final String FAILED_CREATE_CONNECTION = 
			"coudn't create connection to data base: ";
	public static final String FAILED_CREATE_POOL = 
			"connections pool don't created ";
	public static final String GAVE_CONNECTION = "Gave connection ";
	public static final String I_E_DESTROY_POOL = 
			"InterruptedException in method destroyPool ";
	public static final String I_E_GET_CONNECTION = 
			"InterruptedException in method getConnection ";
	public static final String RELEASE_CONNECTION = "release connection ";
	public static final String RESULT_SET_CLOSE_ERROR = 
			"ResultSet close error!";
	public static final String SQLEXC_DEREGISTER_DRIVERS = 
			"SQLException in method deregisterDrivers ";
	public static final String SQLEXC_DESTROY_POOL = 
			"SQLException in method destroyPool ";
	public static final String STATEMENT_CLOSE_ERROR = "Statement close error!";
	
	private MessageDAO() {
	}
}
