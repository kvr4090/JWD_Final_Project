package by.epamtc.kalimylin.controller.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.ParameterName;
import by.epamtc.kalimylin.controller.util.AttributeAndParameter;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.AccessHelper;

/**
 * The filter determines whether the user can execute the current request.
 */
public class AuthorizationFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void doFilter(ServletRequest servletRequest, 
			ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        StringBuilder page = new StringBuilder();
        String commandName = 
        		request.getParameter(AttributeAndParameter.COMMAND);
        ParameterName parameterName = null;
             
        try {
        	if (commandName != null) {
            	parameterName = 
            			ParameterName.valueOf(commandName.toUpperCase());
    		}
		} catch (IllegalArgumentException e) {
			/* throws invalid command */ 
		}
        
        if (parameterName != null) {
        	
        	HttpSession session = request.getSession();
			User user = (User) session.getAttribute(AttributeAndParameter.USER);

			if (AccessHelper.checkAccess(user, parameterName)) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				logger.log(Level.WARN, formMessage(user, parameterName));
				response.sendRedirect(
						page.append(PagePath.GO_TO_WELCOME_USER)
							.append(Message.ERROR).toString());
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}			
	}
	
	/**
	 * Form message for logger, uses current user and current parameterName.
	 * 
	 * @param user  current user
	 * @param parameterName  current command name
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User user, ParameterName parameterName) {
		
		StringBuilder message = new StringBuilder();
		int userId = 0;
		
		if (user != null) {
			userId = user.getId();
		}
			
		message.append(Message.LOG_INTRO)
			   .append(userId)
			   .append(Message.INVALID_ACCESS)
			   .append(parameterName);	
		return message;
	}
}
