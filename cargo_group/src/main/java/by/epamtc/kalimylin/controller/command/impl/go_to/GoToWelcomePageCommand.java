package by.epamtc.kalimylin.controller.command.impl.go_to;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.AttributeAndParameter;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.util.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * The command forward to welcome page
 */
public class GoToWelcomePageCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();		
		User user = (User) session.getAttribute(AttributeAndParameter.USER);
		logger.log(Level.INFO, formMessage(user).toString());
		
		request.getRequestDispatcher(
				PagePath.WELCOME_PAGE).forward(request, response);
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
				.append(Message.LOG_INTRO)
				.append(userId)
				.append(Message.GO_TO_WELCOME_PAGE);	
		return message;		
	}
}
