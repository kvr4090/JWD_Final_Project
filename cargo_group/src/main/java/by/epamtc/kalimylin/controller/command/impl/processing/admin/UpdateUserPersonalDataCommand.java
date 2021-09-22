package by.epamtc.kalimylin.controller.command.impl.processing.admin;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;
import static by.epamtc.kalimylin.controller.util.ParseVariable.*;
import static by.epamtc.kalimylin.controller.util.helper.ConditionHelper.*;
import static by.epamtc.kalimylin.controller.util.Message.*;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.Command;
import by.epamtc.kalimylin.controller.exception.ParseVariableException;
import by.epamtc.kalimylin.controller.util.PagePath;
import by.epamtc.kalimylin.controller.util.ParseVariable;
import by.epamtc.kalimylin.controller.util.helper.ExceptionHelper;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The command update user personal data and redirect to all users page.
 */
public class UpdateUserPersonalDataCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		
		User admin = (User) request.getSession().getAttribute(USER);
		StringBuilder page = new StringBuilder();
		ExceptionHelper handler;
		PersonalData personalData = new PersonalData();
		User user = new User();
		StringBuilder messageToLog = formMessage(admin, user, personalData);
		boolean isUpdateData;
		boolean isUpdateRoleStatus;
		
		page.append(PagePath.GO_TO_ALL_USERS);
		
		try {
			user = formUser(request, user);
			personalData = formPersonalData(request, personalData);
			messageToLog = formMessage(admin, user, personalData);
					
			isUpdateData = service.updatePersonalData(personalData);
			isUpdateRoleStatus = service.updateUserRoleStatus(user);
			
			page.append(updateLine(isUpdateData && isUpdateRoleStatus));
			page.append(UPDATE);
			messageToLog.append(updateLine(isUpdateData && isUpdateRoleStatus));
			logger.log(Level.INFO, messageToLog.toString());
			
		} catch (ServiceException | ParseVariableException e) {
			handler = new ExceptionHelper(logger, e, messageToLog);
			handler.log();
			page = handler.updatePage(page);	
		}
		response.sendRedirect(page.toString());
	}
	
	/**
	 * Form personal data from request
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param data  new personal data
	 * @return {@link PersonalData} data with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid 
	 * input data
	 */
	private PersonalData formPersonalData(HttpServletRequest request, 
			PersonalData data) throws ParseVariableException {

		data.setName(request.getParameter(NAME));
		data.setSurname(request.getParameter(SURNAME));
		data.setPost(request.getParameter(POST));
		data.setDriveLicense(request.getParameter(DRIVE_LICENSE));
		data.setPassportNumber(request.getParameter(PASSPORT_NUMBER));
		data.setPassportIdentificationNumber(
				request.getParameter(PASSPORT_IDENTIFICATION_NUMBER));	
		data.setPassportAuthority(request.getParameter(PASSPORT_AUTHORITY));
		data.setUserId(parseInt(request.getParameter(USER)));			
		data.setBirthdate(parseLocalDate(request.getParameter(BIRTHDATE)));	
		data.setPassportDateOfExpiry(parseLocalDate(
				request.getParameter(PASSPORT_DATE_EXPIRY)));	
		data.setPassportDateOfIssue(parseLocalDate(
				request.getParameter(PASSPORT_DATE_ISSUE)));
		data.setRecruitmentDate(parseLocalDate(
				request.getParameter(RECRUITMENT_DATE)));
		
		if (!StringUtils.isBlank(request.getParameter(TERMINATION_DATE))) {
			data.setTerminationDate(parseLocalDate(
					request.getParameter(TERMINATION_DATE)));	
		}
		return data; 
	}
	
	/**
	 * Form user from request
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param user  user changing data
	 * @return {@link User} with data from request
	 * @throws ParseVariableException {@link ParseVariableException} if invalid 
	 * input data
	 */
	private User formUser(HttpServletRequest request, User user)
			throws ParseVariableException {
		
		user.setId(ParseVariable.parseInt(request.getParameter(USER)));
		user.setIsActive(Boolean.valueOf(request.getParameter(IS_ACTIVE)));
		user.setRole(ParseVariable.parseRole(request.getParameter(ROLE)));
		
		return user;
	}
	
	/**
	 * Form message for logger, uses current user and current command.
	 * Will update in execute method by result execute command.
	 * 
	 * @param admin  current user
	 * @param user  user changing data
	 * @param data  new data to update
	 * @return {@link StringBuilder} message for logger
	 */
	private StringBuilder formMessage(User admin, User user, 
			PersonalData data) {
		
		StringBuilder message = new StringBuilder()
				.append(LOG_INTRO)
				.append(admin.getId())
				.append(CHANGE)
				.append(LOG_INTRO)
				.append(user.getId())
				.append(PERSONAL_DATA_SPACE)
				.append(data.toString());	
		return message;
	}
}
