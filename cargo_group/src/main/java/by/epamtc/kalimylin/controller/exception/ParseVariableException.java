package by.epamtc.kalimylin.controller.exception;

/**
 * Signals that some data cannot be matched to the required type.
 */
public class ParseVariableException extends Exception {

	private static final long serialVersionUID = 78120298558082833L;
	
	public ParseVariableException() {
    }

    public ParseVariableException(String message) {
        super(message);
    }

    public ParseVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseVariableException(Throwable cause) {
        super(cause);
    }

}
