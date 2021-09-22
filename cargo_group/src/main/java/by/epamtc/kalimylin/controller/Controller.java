package by.epamtc.kalimylin.controller;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.helper.ControllerHelper.*;
import static by.epamtc.kalimylin.controller.command.ParameterName.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Stack;

import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.command.CommandProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for requests from client and responds for application management.
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 935819503014406672L;
	private final CommandProvider provider = new CommandProvider();
	
	@Override
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String currentCommandName = request.getParameter(COMMAND);
		Command command = null;
		String requestLine = request.getQueryString();
		
		checkLocale(session);
		
		if (currentCommandName != null) {
			command = provider.getCommand(currentCommandName);
		}
		
		command.execute(request, response);
		setPrevRequest(requestLine, session);
	}

	@Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);	
	}
	
	/**
	 * Set default or leave current locale to session attribute
	 * 
	 * @param session  {@link HttpSession}
	 * @return session with default locale, if session attribute locale = null.
	 */
	private HttpSession checkLocale(HttpSession session) {
		
		if (session.getAttribute(LOCALE) == null) {
            session.setAttribute(LOCALE, Locale.getDefault());
        }		
		return session;
	}
	
	/**
	 * Adds entries to user request log.
	 * Ignore command log_out, localization, back
	 * Write clear request without any parameters like "message=".
	 * Ignore similar request, compare current request with previous.
	 * If new request different than previous, new request will add 
	 * to request log, then request log set as attribute to current session. 
	 * 
	 * @param reqLine  contains current request query
	 * @param session  {@link HttpSession}
	 * @return {@link HttpSession} with updated request log
	 */
	@SuppressWarnings("unchecked")
	private HttpSession setPrevRequest(String reqLine, HttpSession session) {

		boolean conditionAddReq = (reqLine != null) 
				&& (!reqLine.contains(LOG_OUT.toString().toLowerCase()))
				&& (!reqLine.contains(LOCALIZATION.toString().toLowerCase()))
				&& (!reqLine.contains(BACK.toString().toLowerCase()));
		Stack<String> requestLog;
		String tempRequest;
		
		if (conditionAddReq) {
			requestLog = (Stack<String>) session.getAttribute(REQUEST_LOG);
			requestLog = checkLog(requestLog);
			tempRequest = clearRequestLine(CONTROLLER_PARAM + reqLine);
			
			if (!requestLog.peek().equals(tempRequest)) {
				requestLog.push(tempRequest);
				session.setAttribute(REQUEST_LOG, requestLog);
			}
		}		
		return session;
	}
}
