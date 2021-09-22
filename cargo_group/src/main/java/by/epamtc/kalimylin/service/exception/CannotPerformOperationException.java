package by.epamtc.kalimylin.service.exception;

/**
 * If this exception is thrown, it means something is wrong with the platform 
 * your code is running on, and it's not safe to create a hash. 
 * For example, if your system's random number generator doesn't work properly, 
 * this kind of exception will be thrown.
 */
public class CannotPerformOperationException extends Exception {

	private static final long serialVersionUID = 5017311747387831680L;

	public CannotPerformOperationException() {
    }

    public CannotPerformOperationException(String message) {
        super(message);
    }

    public CannotPerformOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotPerformOperationException(Throwable cause) {
        super(cause);
    }
}