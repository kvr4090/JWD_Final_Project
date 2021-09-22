package by.epamtc.kalimylin.service.exception;

/**
 * The correctHash you gave was somehow corrupted. 
 * Note that some ways of corrupting a hash are impossible to detect, 
 * and their only symptom will be that VerifyPassword() will return false 
 * even though the correct password was given. 
 * So InvalidHashException is not guaranteed to be thrown if a hash has been 
 * changed, but if it is thrown then you can be sure that the hash was changed.
 */
public class InvalidHashException extends Exception {

	private static final long serialVersionUID = -4597390415312461125L;

	public InvalidHashException() {
    }

    public InvalidHashException(String message) {
        super(message);
    }

    public InvalidHashException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHashException(Throwable cause) {
        super(cause);
    }
}