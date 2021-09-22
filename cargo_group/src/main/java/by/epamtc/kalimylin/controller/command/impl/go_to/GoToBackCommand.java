package by.epamtc.kalimylin.controller.command.impl.go_to;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;

import java.io.IOException;
import java.util.Stack;

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
 * The command redirects to the previous request.
 * If request log almost empty, command redirect to start page.
 * This case occurs if you use the back button on the page and then use 
 * the back button in the browser to return to the previous page. 
 * Repeat this trick several times and request log will become empty 
 * and app catch EmptyStackException.
 */
public class GoToBackCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER);
		@SuppressWarnings("unchecked")
		Stack<String> requestLog = 
		(Stack<String>) session.getAttribute(REQUEST_LOG);
		String page;
		
		if (requestLog.size() < 2) {
			page = PagePath.GO_TO_WELCOME_USER;
		} else {
			requestLog.pop();
			page = requestLog.pop();
		}
		logger.log(Level.INFO, formMessage(user));
		response.sendRedirect(page);
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
				.append(Message.GO_BACK);		
		return message;		
	}
}
