package by.epamtc.kalimylin.controller.command.impl.processing.admin;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.ParseVariable;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command update user password and redirect to all users page.
 */
public class UpdateUserPasswordCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		
		StringBuilder page = new StringBuilder();
		ExceptionHelper handler;
		User user = new User();
		User admin = (User) request.getSession().getAttribute(USER);
		StringBuilder messageToLog = formMessage(admin, user);
		
		page.append(PagePath.GO_TO_ALL_USERS);
		
		try {
			user.setId(ParseVariable.parseInt(request.getParameter(USER)));
			user.setCorrectHash(request.getParameter(PASSWORD));
			messageToLog = formMessage(admin, user);
			boolean isUpdate = service.updateUserPassword(user);
			
			page.append(ConditionHelper.updateLine(isUpdate));
			page.append(UPDATE);
			messageToLog.append(ConditionHelper.updateLine(isUpdate));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());	
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command
	 * 
	 * @param admin  current user
	 * @param user  user changing password
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User admin, User user) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(admin.getId())
				.append(CHANGE)
				.append(LOG_INTRO)
				.append(user.getId())
				.append(PASSWORD_SPACE);
		return message;		
	}
}
