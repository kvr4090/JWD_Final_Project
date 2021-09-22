package by.epamtc.kalimylin.controller.command.impl.processing.user;

import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
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
 * The command registers new user, then redirect to welcome page.
 */
public class RegistrationUserCommand implements Command{
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		
		User user = formUser(request);
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog = formMessage(user);
		String url = getURL(request);
		ExceptionHelper handler;
		boolean isRegistrate = false;

		page.append(PagePath.GO_TO_WELCOME_USER);

		try {
			isRegistrate = service.registration(user, url);
			
			page.append(ConditionHelper.updateLine(isRegistrate));
			page.append(REGISTRATION);
			messageToLog.append(ConditionHelper.updateLine(isRegistrate));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		} finally {
			user = null;
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form user from request.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @return {@link User} with data from request
	 */
	private User formUser(HttpServletRequest request) {
		
		User user = new User();		
		user.setLogin(request.getParameter(LOGIN));
		user.setCorrectHash(request.getParameter(PASSWORD));
		user.setEmail(request.getParameter(EMAIL));
		user.setPhone(request.getParameter(PHONE));
		
		return user;
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param user  new user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User user) {
		
		StringBuilder message = new StringBuilder()	
				.append(SEPARATOR_COMMA)
				.append(LOGIN_SPACE)
				.append(user.getLogin())
				.append(REGISTRATION)
				.append(PHONE)
				.append(user.getPhone())
				.append(EMAIL)
				.append(user.getEmail());	
		return message;
	}
	
	/**
	 * Form URL from request.
	 * For tests on localhost.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @return {@link String} URL from request
	 */
	private String getURL(HttpServletRequest request) {
		
		String serverName = request.getServerName();
		StringBuilder url;
		
		if (serverName.equals(LOCALHOST)) {
			url = new StringBuilder()	
					.append(request.getScheme()).append("://")
					.append(request.getServerName()).append(":")
					.append(request.getServerPort())
					.append(request.getRequestURI());	
			return url.toString();
		} else {
			return request.getRequestURL().toString();
		}
	}
}
