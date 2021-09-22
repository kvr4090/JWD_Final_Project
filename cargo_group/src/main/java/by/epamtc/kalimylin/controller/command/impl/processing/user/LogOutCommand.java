package by.epamtc.kalimylin.controller.command.impl.processing.user;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.USER;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.util.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * The command invalidate current session, then redirect to welcome page.
 */
public class LogOutCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER);
		logger.log(Level.INFO, formMessage(user).toString());
		
        if (session != null) {
        	session.invalidate();
        }
        response.sendRedirect(PagePath.GO_TO_WELCOME_USER);
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param user  current user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User user) {
		
		StringBuilder message = new StringBuilder()	
				.append(Message.LOG_INTRO)
				.append(user.getId())
				.append(Message.LOG_OUT);	
		return message;
	}
}
