package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.*;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command add new order and then redirect to workspace.
 */
public class AddOrderCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		OrderService service = provider.getOrderService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		StringBuilder page = new StringBuilder();
		Order order = new Order();
		StringBuilder messageToLog = formMessage(logist, order);
		ExceptionHelper handler;
		
		page.append(PagePath.GO_TO_WORKSPACE);
		
		try {
			order.setUserId(logist.getId());
			order = formOrder(request, order);
			messageToLog = formMessage(logist, order);
			boolean isAdd = service.addOrder(order);
			
			page.append(ConditionHelper.updateLine(isAdd));
			page.append(ADD);
			messageToLog.append(ConditionHelper.updateLine(isAdd));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form order from request.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param order  new order
	 * @return {@link Order} with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid 
	 * input data
	 */
	private Order formOrder(HttpServletRequest request, Order order)
			throws ParseVariableException {
		
		order.setShipperId(parseInt(request.getParameter(SHIPPER)));
		order.setRequirementId(parseInt(request.getParameter(REQUIREMENT)));
		order.setDate(LocalDate.now());
		order.setPrice(parseBigDecimal(request.getParameter(PRICE)));
		order.setRouteStartPoint(request.getParameter(START_POINT));
		order.setRouteEndPoint(request.getParameter(FINISH_POINT));
		order.setDistance(parseInt(request.getParameter(DISTANCE)));
		order.setNote(request.getParameter(NOTE));
		
		return order;	
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param logist  current user
	 * @param order  new order
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, Order order) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(ADD)
				.append(ORDER_ID)
				.append(order.toString());	
		return message;
	}
}
