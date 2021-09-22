package by.epamtc.kalimylin.service.exception;

/**
 * If this exception is thrown, it means some data not validated with validator,
 * and have incorrect values.
 */
public class InvalidInputDataServiceException extends ServiceException {
	
	private static final long serialVersionUID = 1142955666744873739L;

	public InvalidInputDataServiceException() {
    }

    public InvalidInputDataServiceException(String message) {
        super(message);
    }

    public InvalidInputDataServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputDataServiceException(Throwable cause) {
        super(cause);
    }

}
