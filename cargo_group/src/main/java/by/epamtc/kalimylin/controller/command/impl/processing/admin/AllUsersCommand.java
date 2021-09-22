package by.epamtc.kalimylin.controller.command.impl.processing.admin;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command fill request attribute listUsers and forward to page,
 * where user can find information about all users.
 */
public class AllUsersCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
				
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		
		String page = PagePath.ALL_USERS;
		User admin = (User) request.getSession().getAttribute(USER);
		StringBuilder messageToLog = formMessage(admin);
		ExceptionHelper handler;
		
		try {
			List<User> listUsers = service.findAllUser();
			request.setAttribute(LIST_USERS, listUsers);
			logger.log(Level.INFO, messageToLog);
			
		} catch (ServiceException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			request.setAttribute(MESSAGE, handler.getAttributeValue());
		}	
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * 
	 * @param admin  current user
	 * @return {@link StringBuilder} message for logger 
	 */
	private StringBuilder formMessage(User admin) {
		
		StringBuilder message = new StringBuilder()
				.append(Message.LOG_INTRO)
				.append(admin.getId())
				.append(Message.GO_TO_ALL_USERS_PAGE);	
		return message;
	}
}
