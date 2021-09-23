package by.epamtc.kalimylin.controller.util.helper;

import static by.epamtc.kalimylin.controller.util.Message.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for processing Exception.
 * At first call constructor with parameters, to initialize objects.
 * For processing exception, need to call method log().
 * For update page path use updatePage(StringBuilder pagePath).
 * For update attribute value use getAttributeValue().
 */
public class ExceptionHelper {
	
	private static final Logger loggerForHandler = LogManager.getLogger();
	private Logger logger;
	private Exception exception;
	private StringBuilder message;
	private StringBuilder value;
	
	/**
	 * Constructor
	 * 
	 * @param logger  A logger to log the events of the called class  
	 * @param e  Exception for logging
	 * @param message  main message to log 
	 */
	public ExceptionHelper(Logger logger, Exception e, StringBuilder message) {
		this.logger = logger;
		this.exception = e;
		this.message = message;
	}
	/**
	 * Use input logger to log exception.
	 * If there is an exception cause, log ERROR level for logger
	 * Parameter value append ERROR and messageToLog append cause.
	 * It there is no cause, log INFO level for logger.
	 * Parameter value append message from exception and messageToLog append
	 * message from exception too.
	 * At first position to log message, add exception cause or 
	 * exception message, then append main message.
	 */
	public void log() {
		
		checkLogger();
		checkException();
		checkMessage();
		
		StringBuilder messageToLog = new StringBuilder();
		value = new StringBuilder();
		
		if (exception.getCause() != null) {
			value.append(ERROR);
			messageToLog.append(exception.getCause())
						.append(message);
			logger.log(Level.ERROR, messageToLog.toString());
			
		} else {
			value.append(exception.getMessage());
			messageToLog.append(exception.getMessage())
						.append(message);
			logger.log(Level.INFO, messageToLog.toString());
		}
	}
	
	/**
	 * Before update input page path by value, checks the call to the log() 
	 * method.
	 * If the log() method was not called, the result will not be correct.
	 * 
	 * @param pagePath  input page path for update
	 * @return {@link StringBuilder} page path with append value
	 */
	public StringBuilder updatePage(StringBuilder pagePath) {
		checkCallLogMethod();
		return pagePath.append(value);
	}
	
	/**
	 * Before return the correct result, checks the call to the log() method.
	 * If the log() method was not called, the result will not be correct. 
	 * 
	 * @return {@link String} value
	 */
	public String getAttributeValue() {
		checkCallLogMethod();
		return value.toString();
	}
	
	/**
	 * If the user does not call the log() method,
	 * the current method calls it itself. User error insurance
	 */
	private void checkCallLogMethod() {
		
		if (value == null) {
			log();
		}
	}
	
	/**
	 * Application cannot work without logging events.
	 */
	private void checkLogger() {
		
		if (logger == null) {
			Class<?> source = org.slf4j.helpers.Util.getCallingClass();
			loggerForHandler.log(Level.FATAL, formMessage(source).toString());
			throw new RuntimeException(EMPTY_LOGGER);
		}
	}
	
	/**
	 * Create and set to parameter default Exception with warning message.
	 */
	private void checkException() {
		
		if (exception == null) {
			exception = new Exception(EMPTY_EXCEPTION);
		}
	}
	
	/**
	 * Set to parameter stub message.
	 */
	private void checkMessage() {
		
		if (message == null) {
			message = new StringBuilder().append(STUB_MESSAGE); 
		}		
	}
	
	/**
	 * Form message for logger, uses calling class.
	 * 
	 * @param source  calling Class
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(Class<?> source) {
		
		StringBuilder message = new StringBuilder()	
				.append(source)
				.append(TRY_TO)
				.append(PROCESSING_WITHOUT_LOGGER)
				.append(exception);
		return message;
	}
}
