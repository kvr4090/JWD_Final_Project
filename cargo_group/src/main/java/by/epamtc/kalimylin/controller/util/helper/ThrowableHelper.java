package by.epamtc.kalimylin.controller.util.helper;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.util.PagePath;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The class catch and processing all possible exceptions and errors
 * for the stable operation of the application. Log Level WARN.
 * Redirect to error page.
 */
public class ThrowableHelper extends HttpServlet {

	private static final long serialVersionUID = 119285071282271331L;
	private static final Logger logger = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Throwable throwable = 
				(Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		
		if (throwable != null) {
			logger.log(Level.WARN, formMessage(throwable).toString());
		}		
		req.getRequestDispatcher(PagePath.ERROR_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	/**
	 * Form message for logger, uses current throwable.
	 * 
	 * @param throwable  current throwable 
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(Throwable throwable) {
		
		StringBuilder messageToLog = new StringBuilder()
				.append(Message.EXCEPTION_OCCURRED)
				.append(throwable);
		return messageToLog;
	}
	
}
