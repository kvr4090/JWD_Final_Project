package by.epamtc.kalimylin.controller.command.impl.go_to.user;

import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command forward to page, where user can log in.
 * Authorized users cannot use this command. 
 * This command is for guests only
 */
public class GoToLogInPageCommand implements Command {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.log(Level.INFO, formMessage().toString());	
		request.getRequestDispatcher(
				PagePath.LOG_IN_PAGE).forward(request, response);
	}
	
	/**
	 * Form message for logger, uses current command.
	 * 
	 * @return {@link StringBuilder} message for logger 
	 */
	private StringBuilder formMessage() {
				
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(GUEST)
				.append(GO_TO_LOG_IN_PAGE);
		return message;		
	}
}
