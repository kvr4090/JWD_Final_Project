package by.epamtc.kalimylin.controller.command.impl.go_to.logist;

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

/**
 * The command forward to page, where fill data to create new shipper
 */
public class GoToNewShipperCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute(USER);
		logger.log(Level.INFO, formMessage(user).toString());
		
		request.getRequestDispatcher(
				PagePath.NEW_SHIPPER).forward(request, response);
	}
	
	/**
	 * Form message for logger, uses current user and current command. 
	 * 
	 * @param user  current user
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User user) {
				
		StringBuilder message = new StringBuilder()
				.append(Message.LOG_INTRO)
				.append(user.getId())
				.append(Message.GO_TO_NEW_SHIPPER_PAGE);		
		return message;
	}
}
