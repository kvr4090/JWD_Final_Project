package by.epamtc.kalimylin.controller.command.impl.processing.user;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command log in visitor then redirect:
 * to welcome page, if authentication valid,
 * to log in page, if authentication invalid.
 * When command catch Service exception, then redirect to log in page
 */
public class AuthorizationUserCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
			
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog;
		ExceptionHelper handler;
		AuthenticationData authenticationData = new AuthenticationData();
		User user;
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);

		authenticationData.setLogin(login);
		messageToLog = formMessage(authenticationData);
		authenticationData.setPassword(password);
		
		try {
			user = service.logIn(authenticationData);
			
			if (user != null) {
				request.getSession().setAttribute(USER, user);
				page.append(PagePath.GO_TO_WELCOME_USER);
			} else {
				page.append(PagePath.GO_TO_LOG_IN_PAGE)
					.append(INVALID_DATA);
				messageToLog = formMessage(authenticationData);
				messageToLog.append(SEPARATOR_SPACE).append(INVALID_DATA);
			}
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {
			page.append(PagePath.GO_TO_LOG_IN_PAGE);
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);		
		} finally {
			login = null;
			password = null;
			authenticationData = null;
			user = null;
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form message for logger, uses current visitor data and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param data  current visitor authentication data
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(AuthenticationData data) {
		
		StringBuilder message = new StringBuilder()
				.append(SEPARATOR_COMMA)
				.append(LOGIN_SPACE)
				.append(data.getLogin())
				.append(LOG_IN)
				.append(PASSWORD_SPACE)
				.append(data.getPassword());		
		return message;
	}
}
