package by.epamtc.kalimylin.controller.command.impl.processing.mechanic;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.Message.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.*;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.helper.ConditionHelper;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.RequirementService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command add new requirement and then redirect to workspace.
 */
public class AddRequirementCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		RequirementService requirementService = 
				provider.getRequirementService();
		
		User user = (User) request.getSession().getAttribute(USER);
		Requirement requirement = new Requirement();
		StringBuilder page = new StringBuilder();
		StringBuilder messageToLog = formMessage(user, requirement);
		ExceptionHelper handler;
		boolean isAdd;
		
		page.append(PagePath.GO_TO_WORKSPACE);
		
		try {
			requirement = fillRequirement(request, requirement);
			messageToLog = formMessage(user, requirement);
			isAdd = requirementService.addRequirement(requirement);
			
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
	 * Form requirement from request.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param requirement  requirement for update
	 * @return {@link VehicleType} with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid
	 * input data in parameters
	 */
	private Requirement fillRequirement(HttpServletRequest request,
			Requirement requirement) throws ParseVariableException {
		        
        requirement.setWeight(parseFloat(request.getParameter(WEIGHT)));
        requirement.setVolume(parseFloat(request.getParameter(VOLUME)));
        requirement.setPalletsQuantity(
        		parseInt(request.getParameter(PALLETS_QUANTITY)));
        requirement.setMaxLength(parseFloat(request.getParameter(LENGTH)));
        requirement.setMaxWidth(parseFloat(request.getParameter(WIDTH)));
        requirement.setMaxHeight(parseFloat(request.getParameter(HEIGHT)));
		
		return requirement;	
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param user  current user
	 * @param requirement  current requirement 
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User user, Requirement requirement) {
		
		StringBuilder message = new StringBuilder()	
				.append(LOG_INTRO)
				.append(user.getId())
				.append(ADD)
				.append(REQUIREMENT_SPACE)
				.append(requirement.toString());
		return message;
	}
}
