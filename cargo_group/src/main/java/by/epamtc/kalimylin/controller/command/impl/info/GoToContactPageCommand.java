package by.epamtc.kalimylin.controller.command.impl.info;

import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.USER;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command forward to page, where user can find company contacts.
 */
public class GoToContactPageCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute(USER);
		logger.log(Level.INFO, formMessage(user).toString());
		
		request.getRequestDispatcher(
				PagePath.CONTACT_PAGE).forward(request, response);
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * For log visitor action userId = "guest", cause in session, user = null;
	 * User witch have role GUEST, have active user in session, will log by id.  
	 * 
	 * @param user  current user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User user) {
		
		String userId = GUEST;
		
		if (user != null) {
			userId = String.valueOf(user.getId());
		}
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(userId)
				.append(GO_TO_CONTACT_PAGE);
		return message;		
	}
}
