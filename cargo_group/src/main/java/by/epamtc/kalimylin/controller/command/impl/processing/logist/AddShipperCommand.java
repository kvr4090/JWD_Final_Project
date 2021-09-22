package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.ShipperService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command add new shipper and then redirect to workspace.
 */
public class AddShipperCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		ShipperService service = provider.getShipperService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog;
		ExceptionHelper handler;
		Shipper shipper = formShipper(request);
		boolean isAdd;
		
		page.append(PagePath.GO_TO_WORKSPACE);
		messageToLog = formMessage(logist, shipper);
		
		try {
			isAdd = service.addShipper(shipper);
			
			page.append(ConditionHelper.updateLine(isAdd));
			page.append(ADD);
			messageToLog.append(ConditionHelper.updateLine(isAdd));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form shipper from request.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @return {@link Shipper} with data from request
	 */
	private Shipper formShipper(HttpServletRequest request) {
		
		Shipper shipper = new Shipper();
		
		shipper.setName(request.getParameter(COMPANY));
		shipper.setEmail(request.getParameter(EMAIL));
		shipper.setContactPersonName(request.getParameter(NAME));
		shipper.setContactPersonSurname(request.getParameter(SURNAME));
		shipper.setContactPhone(request.getParameter(PHONE));
		shipper.setNote(request.getParameter(NOTE));
		
		return shipper;
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param logist  current user
	 * @param shipper  new shipper
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, Shipper shipper) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(ADD)
				.append(SHIPPER_ID)
				.append(shipper.toString());		
		return message;
	}
}
