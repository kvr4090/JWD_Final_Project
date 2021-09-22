package by.epamtc.kalimylin.controller.command.impl.processing.user;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command confirm email, then redirect to welcome page.
 */
public class ConfirmEmailCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		UserService userService = serviceProvider.getUserService();
		
		StringBuilder page = new StringBuilder();
		String token = request.getParameter(TOKEN);
		String email = request.getParameter(EMAIL);
		StringBuilder messageToLog = formMessage(email);
		ExceptionHelper handler;
	
		boolean activateResult = false;
		
		page.append(PagePath.GO_TO_WELCOME_USER);
		
		try {
			activateResult = userService.confirmEmail(email, token);
			messageToLog.append(ConditionHelper.updateLine(activateResult));
			page.append(ConditionHelper.updateLine(activateResult));
			page.append(CONFIRM);
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());
	}

	/**
	 * Form message for logger, uses current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param email  users email to confirm
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(String email) {
		
		StringBuilder message = new StringBuilder()	
				.append(SEPARATOR_COMMA)
				.append(CONFIRM)
				.append(EMAIL_SPACE)
				.append(email);		
		return message;
	}
}
