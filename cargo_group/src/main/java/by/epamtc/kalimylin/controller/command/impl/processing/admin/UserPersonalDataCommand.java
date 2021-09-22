package by.epamtc.kalimylin.controller.command.impl.processing.admin;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.ParseVariable;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request by user personal data and forward to user data page.
 */
public class UserPersonalDataCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		
		User admin = (User) request.getSession().getAttribute(USER);
		User user = new User();
		PersonalData userData;
		ExceptionHelper handler;
		String page = PagePath.USER_DATA;
		StringBuilder messageToLog = formMessage(admin, user);
		
		try {
			user.setId(ParseVariable.parseInt(request.getParameter(USER)));
			user = service.findUserById(user);
			messageToLog = formMessage(admin, user);
			userData = service.findPersonalDataByUserId(user);
			Role[] roles = Role.values();
	
			request.setAttribute(ROLES, roles);
			request.setAttribute(USER, user);
			request.setAttribute(PERSONAL_DATA, userData);
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			request.setAttribute(MESSAGE, handler.getAttributeValue());
		}
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param admin  current user
	 * @param user  user, which data view
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User admin, User user) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(admin.getId())
				.append(FIND)
				.append(LOG_INTRO)
				.append(user.getId())
				.append(PERSONAL_DATA_SPACE);
		return message;
	}
}
