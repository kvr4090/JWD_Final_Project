package by.epamtc.kalimylin.service.impl;

import static by.epamtc.kalimylin.service.util.MessageService.*;
import static by.epamtc.kalimylin.service.util.validation.InputDataValidator.*;

import java.util.List;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.dao.DAOProvider;
import by.epamtc.kalimylin.dao.UserDAO;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.util.EmailCrypt;
import by.epamtc.kalimylin.service.util.MailSender;
import by.epamtc.kalimylin.service.util.PasswordProcessing;
import by.epamtc.kalimylin.service.util.validation.VariableValidator;
import by.epamtc.kalimylin.service.exception.CannotPerformOperationException;
import by.epamtc.kalimylin.service.exception.InvalidHashException;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * UserService interface, contains interfaces for working with User
 */
public class UserServiceImpl implements UserService {
		
	@Override
	public User logIn(AuthenticationData data) throws ServiceException {
		
		checkAuthData(data);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		User user = null;
		boolean isActualAuthData = false;
		
		try {
			user = userDAO.findUserByLogin(data);
	
			isActualAuthData = identificationUser(user) 
							&& authenticationUser(data, user) 
							&& checkUserForBlock(user);
			
			if (!isActualAuthData) {
				user = null;
			}			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {
			data = null;
			
			if (user != null) {
				user.setCorrectHash(null);
			}
		}
		return user;
	}
	
	/**
	 * Checks user for null
	 * 
	 * @param user  user for check
	 * @return boolean result of processing
	 */
	private boolean identificationUser(User user) {
		
		return user != null;
	}
	
	/**
	 * Verify passwords authentication data and user
	 * 
	 * @param data  data for verify
	 * @param user  user for verify
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean authenticationUser(AuthenticationData data, User user) 
			throws ServiceException {
		
		boolean resultAuthentication = false;
		String correctHash = user.getCorrectHash();
		String password = data.getPassword();
	
		try {
			resultAuthentication = PasswordProcessing.verifyPassword(
					password, correctHash);					
		} catch (CannotPerformOperationException | InvalidHashException e) {
			throw new ServiceException(ERROR);																	
		} finally {
			correctHash = null;
			password = null;
		}		
		return resultAuthentication;
	}
	
	/**
	 * Checks for blocking user's account. If account is block,
	 * throws {@link ServiceException} with message, that account is blocking.
	 * 
	 * @param user  user for check
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean checkUserForBlock(User user) throws ServiceException {
		
		if (!user.getIsActive()) {
			throw new ServiceException(INVALID_ENTRY);
		}		
		return true;
	}

	@Override
	public boolean registration(User user, String serverUrl) 
			throws ServiceException { 					
		
		checkUser(user);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();

		findSimilarUserData(user, false);
		user = emailProcessing(user, serverUrl);	
		user = passwordProcessing(user);
		user.setRole(Role.GUEST);

		try {
			return userDAO.addUser(user);			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {
			user = null;
		}
	}

	@Override
	public boolean updateUser(User user, String serverUrl) 
			throws ServiceException {
		
		checkUser(user);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		findSimilarUserData(user, true);
		user = emailProcessing(user, serverUrl);	
		user = passwordProcessing(user);
		
		try {
			return userDAO.updateUser(user);			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {
			user = null;
		}	
	}
	
	/**
	 * Find similar user data. 
	 * Checks similar login, if isUpdate = true.
	 * Also checks email and phone.
	 * If find accounts with similar data, throws {@link ServiceException} 
	 * with appropriate message (similar login, similar email, similar phone).
	 * 
	 * @param user  user for compare 
	 * @param isUpdate  When updating user data, no login matching is required, 
	 * so the value is set true. And when add user data, login matching is 
	 * required, so the value is set false.
	 * @throws ServiceException {@link ServiceException}
	 */
	private void findSimilarUserData(User user, boolean isUpdate) 
			throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		List<User> similarUser;
		
		try {			
			similarUser = userDAO.findSimilarUser(user);
			
			for (User tempUser : similarUser) {
				
				if (user.getLogin().equals(tempUser.getLogin()) && !isUpdate) {
					throw new ServiceException(SIMILAR_LOGIN);
				}
				
				if (user.getEmail().equals(tempUser.getEmail())) {
					throw new ServiceException(SIMILAR_EMAIL);
				}

				if (user.getPhone().equals(tempUser.getPhone())) {
					throw new ServiceException(SIMILAR_PHONE);
				}
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {
			similarUser = null;
		}
	}
	
	/**
	 * Ñonverts the password to hash
	 * 
	 * @param user  user contains password for processing
	 * @return {@link User} with hash, without password
	 * @throws ServiceException {@link ServiceException}
	 */
	private User passwordProcessing (User user) throws ServiceException {
		
		String correctHash = null;
		
		try {
			correctHash = PasswordProcessing.createHash(user.getCorrectHash());
			user.setCorrectHash(correctHash);
		} catch (CannotPerformOperationException e) {
			throw new ServiceException(e);
		} finally {
			correctHash = null;
		}		
		return user;
	}
	
	/**
	 * Forms message to confirm email and send to user's email.
	 * Blocks user's account.
	 * If user has no email,  message is not sent and account not blocked.
	 * The email address is stored encrypted until the user confirms it. 
	 * Encrypted email uses as a token in a message to confirm email.
	 * {@link EmailCrypt#encodeEmailAdress(String)}
	 * 
	 * @param user  user for processing
	 * @param serverUrl  contains URL address, which forwards to server, 
	 * when user confirm email
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private User emailProcessing(User user, String serverUrl) 
			throws ServiceException {
		
		boolean isAvailableEmail = 
				VariableValidator.isCorrectEmail(user.getEmail());
		String cryptEmail;
		
		if (isAvailableEmail) {
			sendLinkToConfimrEmail(user, serverUrl);
			cryptEmail = EmailCrypt.encodeEmailAdress(user.getEmail());
			user.setEmail(cryptEmail);
			user.setIsActive(false);
		} else {
			user.setIsActive(true);
			user.setEmail(null);
		}
		return user;
	}
	
	/**
	 * Forms message to confirm email and send to user's email.
	 * 
	 * @param user  user for processing
	 * @param url  contains URL address, which forwards to server, 
	 * when user confirm email
	 * @throws ServiceException {@link ServiceException}
	 */
	private void sendLinkToConfimrEmail(User user, String url) 
			throws ServiceException {
		
		String message = messageLinkConfirmEmail(user, url);
		MailSender.sendEmail(user.getEmail(), CONFIRM_EMAIL_SUBJECT, message);	
	}
	
	/**
	 * Forms message to confirm email
	 * 
	 * @param user  user for processing
	 * @param url  contains URL address, which forwards to server, 
	 * when user confirm email
	 * @return {@link String} with message
	 * @throws ServiceException {@link ServiceException}
	 */
	private String messageLinkConfirmEmail(User user, String url)
			throws ServiceException {
		
		String token = EmailCrypt.encodeEmailAdress(user.getEmail());
		StringBuilder message = new StringBuilder();
		message.append(CONFIRM_EMAIL).append(url).append(COMMAND)
				.append(token).append(PARAM_EMAIL).append(user.getEmail()) ;				
		
		return message.toString();
	}
	
	@Override
	public boolean updateUserPassword(User user) throws ServiceException {
		
		checkId(user.getId());
		checkPassword(user.getCorrectHash());
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		user = passwordProcessing(user);
		
		try {
			return userDAO.updateUserPassword(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
		
	@Override
	public boolean updatePersonalData(PersonalData personalData) 
			throws ServiceException {
		
		checkPersonalData(personalData);
		checkSimilarPassportData(personalData);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
				
		try {
			
			PersonalData  data = userDAO.findPersonalDataByUserId(personalData); 
			
			if (data != null) {
				return userDAO.updatePersonalData(personalData);
			} else {
				return userDAO.addPersonalData(personalData);
			}		
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Checks looking for similar passport data.
	 * Checked parameters is passport number, passport identification number.
	 * If find accounts with similar passport data, 
	 * throws {@link ServiceException} with appropriate message passport data
	 * already in use.
	 * 
	 * @param personalData personalData to check
	 * @throws ServiceException {@link ServiceException}
	 */
	private void checkSimilarPassportData(PersonalData personalData)
			throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		try {
			int countMatches = userDAO.matchesPersonalData(personalData);
			
			if (countMatches != 0) {
				throw new ServiceException(PASSPORT_ALREADY_IN_USE);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
	}
	
	@Override
	public boolean confirmEmail(String email, String token)
			throws ServiceException {
		
		boolean compareResult = compareEmailToken(email, token);
		
		if (!VariableValidator.isCorrectEmail(email)
				&& !VariableValidator.isCorrectToken(token)) {
			return false;
		}
		
		if (!compareResult) {
			return false;
		}
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();	
		
		User user = new User();
		user.setEmail(token);
	
		try {
			user = userDAO.findUserByEmail(user);
			
			if (user == null) {
				throw new ServiceException(EMAIL_ALREADY_CONFIRM);
			}
			user.setEmail(email);
			return activateAccount(user);	
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Compare input email with input token
	 *  
	 * @param email  email address for compare
	 * @param token  token for compare
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean compareEmailToken(String email, String token)
			throws ServiceException {
		
		return EmailCrypt.encodeEmailAdress(email).equals(token);		
	}
	
	/**
	 * Activate user's account
	 * Set parameter isActive true
	 * 
	 * @param user  user for activate
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean activateAccount(User user) throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
	
		try {		
			user.setIsActive(true);		
			return userDAO.updateUser(user);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> findAllUserToShow() throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		List<User> resultList = null;
		
		try {
			resultList = userDAO.findAllUsers();
			
			for (User user : resultList) {
				user = prepareUserToShow(user);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return resultList;
	}
	/**
	 * Sets null to next user's parameters:
	 * Correct hash, role, email, isActive
	 * 
	 * @param user  user to update parameters
	 * @return {@link User} with updated parameters
	 */
	private User prepareUserToShow(User user) {
		
		user.setCorrectHash(null);
		user.setRole(null);
		user.setEmail(null);
		user.setIsActive(false);
		
		return user;
	}

	@Override
	public List<PersonalData> findAllPersonalDataToShow()
			throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		List<PersonalData> resultList = null;
		
		try {
			resultList = userDAO.findAllPersonalData();
			
			for (PersonalData personalData : resultList) {
				personalData = prepareDatatoShow(personalData);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
		return resultList;
	}
	/**
	 * Sets null to next personalData's parameters:
	 * birthdate, passportAuthority, passportDateOfExpiry, passportDateOfIssue,
	 * passportIdentificationNumber, passportNumber, post, recruitmentDate.
	 * 
	 * @param data  data to update parameters
	 * @return {@link PersonalData} with updated parameters
	 */
	private PersonalData prepareDatatoShow(PersonalData data) {
		
		data.setBirthdate(null);
		data.setPassportAuthority(null);
		data.setPassportDateOfExpiry(null);
		data.setPassportDateOfIssue(null);
		data.setPassportIdentificationNumber(null);
		data.setPassportNumber(null);
		data.setPost(null);
		data.setRecruitmentDate(null);
		
		return data;
	}

	@Override
	public User findUserbyVehicleId(Vehicle vehicle) throws ServiceException {
		
		checkVehicle(vehicle);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		try {
			return userDAO.findUserByVehicleId(vehicle);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public boolean updateUserVehicleId(User user) throws ServiceException {
		
		checkId(user.getId());
		checkId(user.getVehicleId());
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		User temp = findUserById(user);
		temp.setVehicleId(user.getVehicleId());
		
		try {
			return userDAO.updateUser(temp);			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {
			user = null;
			temp = null;
		}
	}

	@Override
	public User findUserById(User user) throws ServiceException {
		
		checkId(user.getId());
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
				
		try {
			return userDAO.findUserById(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public List<User> findAllUser() throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		try {
			return userDAO.findAllUsers();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public PersonalData findPersonalDataByUserId(User user) 
			throws ServiceException {
		
		checkId(user.getId());
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		try {
			PersonalData data = new PersonalData();
			data.setUserId(user.getId());
			return userDAO.findPersonalDataByUserId(data);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}
	
	@Override
	public boolean updateUserRoleStatus(User user) throws ServiceException {
		
		checkId(user.getId());
		checkRole(user.getRole());
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserDAO();
		
		try {
			return userDAO.updateUserRoleStatus(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
