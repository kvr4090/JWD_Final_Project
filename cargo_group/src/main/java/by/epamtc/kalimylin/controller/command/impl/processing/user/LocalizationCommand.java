package by.epamtc.kalimylin.controller.command.impl.processing.user;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;
import java.util.Stack;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * The command takes selected locale and set to previous request, then redirect
 * previous request with new locale.
 * Previous request takes from session attribute request log.
 */
public class LocalizationCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER);
        String locale = request.getParameter(LOCALE);
        request.getSession().setAttribute(LOCALE, locale);
        logger.log(Level.INFO, formMessage(user).toString());
        
        @SuppressWarnings("unchecked")
		Stack<String> requestLog = 
		(Stack<String>) session.getAttribute(REQUEST_LOG);
        response.sendRedirect(requestLog.peek());	
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
		
		String userId = Message.GUEST;
		
		if (user != null) {
			userId = String.valueOf(user.getId());
		}
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(userId)
				.append(CHANGE_LOCALE);
		return message;
	}
}
