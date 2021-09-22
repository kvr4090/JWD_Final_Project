package by.epamtc.kalimylin.controller.command.impl.processing.user;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.USER;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.util.helper.WorkspaceHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command forward to work page based on user's role.
 */
public class WorkPageCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute(USER);
		String page = WorkspaceHelper.getWorkSpace(user.getRole());
		String messageToLog = formMessage(user).toString();
		logger.log(Level.INFO, messageToLog);
		
		request.getRequestDispatcher(page).forward(request, response);
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
				.append(Message.GO_TO_WORKPAGE);	
		return message;
	}
}
