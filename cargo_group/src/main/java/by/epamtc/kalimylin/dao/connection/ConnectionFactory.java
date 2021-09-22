package by.epamtc.kalimylin.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class connection factory
 * Initialize database property in static block,
 * can throw {@link ExceptionInInitializerError}, if occurred severe problem
 * with database property values or database property file.
 */
class ConnectionFactory {
	
	private static final Logger logger = LogManager.getLogger();
	
	private static final String ERROR_LOAD_PROPS = 
			"An exception occurred, when loading database property file: ";
	private static final String PROPERTY_DATABASE = "resources/db";
	private static final String PROPERTY_URL = "db.url";
	private static final String PROPERTY_PASSWORD = "db.password";
	private static final String PROPERTY_USER = "db.user";
	private static final String PROPERTY_DRIVER = "db.driver";
	private static final String DATABASE_URL;
	private static final String DATABASE_PASSWORD;
	private static final String DATABASE_USER;
	private static final String DATABASE_DRIVER;

	static {
	
		try {	
			ResourceBundle databasePropsBundle = 
					ResourceBundle.getBundle(PROPERTY_DATABASE);

			DATABASE_URL = databasePropsBundle.getString(PROPERTY_URL);
			DATABASE_PASSWORD = 
					databasePropsBundle.getString(PROPERTY_PASSWORD);
			DATABASE_USER = databasePropsBundle.getString(PROPERTY_USER);
			DATABASE_DRIVER = databasePropsBundle.getString(PROPERTY_DRIVER);
			Class.forName(DATABASE_DRIVER);

		} catch (MissingResourceException | ClassNotFoundException e) {			
			logger.log(Level.FATAL, ERROR_LOAD_PROPS + e.getMessage());
			throw new RuntimeException(ERROR_LOAD_PROPS + e.getMessage());
		}
	}

	private ConnectionFactory() {}
	
	/**
	 * @return {@link Connection} new connection to database
	 * @throws SQLException {@link SQLException}
	 */
	static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, 
				DATABASE_USER, DATABASE_PASSWORD);
	}
}
