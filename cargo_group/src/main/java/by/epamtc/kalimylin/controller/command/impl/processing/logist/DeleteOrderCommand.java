package by.epamtc.kalimylin.controller.command.impl.processing.logist;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.parseInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.AttributeAndParameter;
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
 * The command delete list selected orders and then redirect to all orders page.
 * When try to delete empty list orders, append message, that empty list.
 */
public class DeleteOrderCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		OrderService service = provider.getOrderService();
		
		User logist = (User) request.getSession().getAttribute(USER);
		StringBuilder page = new StringBuilder();
		List<Order> listOrders = new ArrayList<>();
		Enumeration<String> params = request.getParameterNames();
		StringBuilder messageToLog = formMessage(logist, listOrders);
		ExceptionHelper handler;
		Order tempOrder;
		boolean isDelete;
		
		page.append(PagePath.GO_TO_ALL_ORDERS);

		try {		
			while (params.hasMoreElements()) {
				String param = (String) params.nextElement();
																						
				if (param.contains(AttributeAndParameter.ORDER)) {
					tempOrder = new Order();
					tempOrder.setId(parseInt(request.getParameter(param)));
					listOrders.add(tempOrder);
				}
			}
			
			if (listOrders.size() != 0) {
				messageToLog = formMessage(logist, listOrders);
				isDelete = service.deleteListOrder(listOrders);
				
				page.append(ConditionHelper.updateLine(isDelete));
				page.append(DELETE);
				messageToLog.append(ConditionHelper.updateLine(isDelete));
			} else {
				page.append(EMPTY_LIST);
			}		
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {	
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);
		}		
		response.sendRedirect(page.toString());	
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param logist  current user
	 * @param listOrders  list orders for delete
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User logist, List<Order> listOrders) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(logist.getId())
				.append(DELETE)
				.append(ORDER_ID)
				.append(listOrders.toString());
		return message;
	}
}
