package by.epamtc.kalimylin.dao.exception;

/**
 * Signals that occurred severe problem with database.
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 5189322577517829467L;

	public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
