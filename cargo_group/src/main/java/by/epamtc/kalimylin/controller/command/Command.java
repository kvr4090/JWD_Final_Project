package by.epamtc.kalimylin.controller.command;

import java.io.IOException;

import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The interface for different commands
 */
public interface Command {
	/**
	 * This method use different methods from Service layer to processing 
	 * current request, also add result of execute to message for logger. 
	 * Form page path for request.
	 * Uses {@link ExceptionHelper} to processing {@link ServletException},
	 * {@link ParseVariableException}
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response  {@link HttpServletResponse}
	 * @throws ServletException {@link ServletException}
	 * @throws IOException {@link IOException}
	 */
	public void execute (HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException;
}
