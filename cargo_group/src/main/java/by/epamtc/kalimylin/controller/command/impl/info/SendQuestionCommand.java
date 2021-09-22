package by.epamtc.kalimylin.controller.command.impl.info;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.ShipperService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command send question from client by email and redirect to welcome page.
 */
public class SendQuestionCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		ShipperService service = serviceProvider.getShipperService();
		
		StringBuilder page = new StringBuilder();
		Shipper shipper = formShipper(request);
		StringBuilder messageToLog = formMessage(shipper);	
		ExceptionHelper handler;
				
		page.append(PagePath.GO_TO_WELCOME_USER);
		
		try {
			service.sendQuestionFromClient(shipper);
			page.append(SUCCESSFUL)
				.append(SEND);	
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {	
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}	
		response.sendRedirect(page.toString());	
	}
	
	/**
	 * Form message for logger, uses current user and current command. 
	 * 
	 * @param client  current user, who send a email, entity shipper 
	 * more suitable, than entity user.
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(Shipper client) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(client.getContactPersonName())
				.append(SEPARATOR_COMMA)
				.append(client.getEmail())
				.append(SEPARATOR_COMMA)
				.append(SEND)
				.append(client.getContactPersonSurname());		
		return message;
	}
	
	/**
	 * Form shipper, from request. Entity shipper more suitable, 
	 * than entity user. Use shipper surname for save message subject, cause no
	 * suitable variables in entity.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @return {@link Shipper}
	 */
	private Shipper formShipper(HttpServletRequest request) {
		
		Shipper shipper = new Shipper();
		
		shipper.setContactPersonName(request.getParameter(NAME));
		shipper.setEmail(request.getParameter(EMAIL));
		shipper.setNote(request.getParameter(MESSAGE));
		shipper.setContactPersonSurname(request.getParameter(SUBJECT));
		
		return shipper;		
	}
}
	