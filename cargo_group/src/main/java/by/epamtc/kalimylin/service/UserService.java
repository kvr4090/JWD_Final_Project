package by.epamtc.kalimylin.service;

import java.util.List;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.dao.UserDAO;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.service.exception.ServiceException;
import by.epamtc.kalimylin.service.util.EmailCrypt;
import by.epamtc.kalimylin.service.util.PasswordProcessing;

/**
 * UserService interface, contains interfaces for working with User
 */
public interface UserService {
	
	/**
	 * Return {@link User}
	 * First check identification User. Search account with this login. 
	 * Return {@code <code>null</code>} if account not exists.
	 * Second check authentication User. Compares passwords authenticationData
	 * with founds account. 
	 * {@link PasswordProcessing#verifyPassword(String, String)}
	 * Third check for blocking user's account. If account is block,
	 * throws {@link ServiceException} with message, that account is blocking.
	 * Finally object reference authenticationData set 
	 * {@code <code>null</code>}.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param authenticationData  contains login and password to log in
	 * @return {@link User} without parameter correct hash, return 
	 * {@code <code>null</code>} if invalid login or password.
	 * @throws ServiceException {@link ServiceException}
	 */
	User logIn(AuthenticationData authenticationData) throws ServiceException;
	
	/**
	 * Add new {@link User}.
	 * First check looking for similar login, phone, email. 
	 * If find accounts with similar data, throws {@link ServiceException} 
	 * with appropriate message (similar login, similar email, similar phone).
	 * Next step, forms message to confirm email and send to user's email.
	 * The user account is blocked until he confirms his email.
	 * If user has no email,  message is not sent and account not blocked.
	 * The email address is stored encrypted until the user confirms it. 
	 * Encrypted email uses as a token in a message to confirm email.
	 * {@link EmailCrypt#encodeEmailAdress(String)}
	 * Password saves encrypted, uses util method 
	 * {@link PasswordProcessing#createHash(String)}
	 * Set guest role for user.
	 * Finally, object's reference to list with similar users and user 
	 * for registration set null.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param user  user for registration
	 * @param serverUrl  contains URL address, which forwards to server, 
	 * when user confirm email
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean registration(User user, String serverUrl) throws ServiceException;
	
	/**
	 * Update user data. 
	 * First check looking for similar login, phone, email. 
	 * If find accounts with similar data, throws {@link ServiceException} 
	 * with appropriate message (similar login, similar email, similar phone).
	 * Next step, forms message to confirm email and send to user's email.
	 * The user account is blocked until he confirms his email.
	 * If user has no email,  message is not sent and account not blocked.
	 * The email address is stored encrypted, until the user confirms it. 
	 * Encrypted email uses as a token in a message to confirm email.
	 * {@link EmailCrypt#encodeEmailAdress(String)}
	 * Password saves encrypted, uses util method 
	 * {@link PasswordProcessing#createHash(String)}
	 * Finally, object's reference to list with similar users and user 
	 * for registration set null.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param user  contains data to update
	 * @param serverUrl  contains URL address, which forwards to server, 
	 * when user confirm email
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateUser(User user, String serverUrl) throws ServiceException;
	
	/**
	 * Update vehicle id in user data.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param user  user to update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateUserVehicleId(User user) throws ServiceException;
	
	/**
	 * Update personalData data.
	 * First check looking for similar passport data.
	 * Checked parameters is passport number, passport identification number.
	 * If find accounts with similar passport data, 
	 * throws {@link ServiceException} with appropriate message passport data
	 * already in use.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param personalData  personalData to update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updatePersonalData(PersonalData personalData)
			throws ServiceException;
	/**
	 * Confirms users email.
	 * Compare email address and token. If user try to confirm email again,
	 * throws {@link ServiceException} with appropriate message, that email
	 * already confirm.
	 * If the token-email comparison result is positive, locking is removed 
	 * from the user account.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param email  email address for confirm
	 * @param token  users token for compare with original token
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean confirmEmail(String email, String token) throws ServiceException;
	
	/**
	 * Return {@link User}
	 * {@link UserDAO#findUserByVehicleId(Vehicle)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicle  vehicle, which contains id for search
	 * @return {@link User} without parameter correct hash, return 
	 * {@code <code>null</code>} if invalid vehicle id
	 * @throws ServiceException {@link ServiceException}
	 */
	User findUserbyVehicleId(Vehicle vehicle) throws ServiceException;
	
	/**
	 * Return {@link User} {@link UserDAO#findUserById(User)}
	 * 
	 * @param user  contains data for search
	 * @return {@link User}
	 * @throws ServiceException {@link ServiceException}
	 */
	User findUserById(User user) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link User}
	 * {@link UserDAO#findAllUsers()}
	 * Before sending user data out of server, important data that is not 
	 * needed is deleted. Set null to correctHash, Role, Email. 
	 * Set false to user status.
	 * 
	 * @return {@link List} of {@link User}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<User> findAllUserToShow() throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link PersonalData}
	 * {@link UserDAO#findAllPersonalData()}
	 * Before sending personalData data, important data that is not needed 
	 * is deleted. Set null to all passport data, post, birthdate,
	 * recruitmentDate.
	 * 
	 * @return {@link List} of {@link PersonalData}
	 * @throws ServiceException {@link ServiceException}
	 */	
	List<PersonalData> findAllPersonalDataToShow() throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link User}
	 * {@link UserDAO#findAllUsers()}
	 * 
	 * @return {@link List} of {@link User}, users data without parameter
	 * correct hash. 
	 * @throws ServiceException {@link ServiceException}
	 */
	List<User> findAllUser() throws ServiceException;
	
	/**
	 * Return {@link PersonalData}
	 * {@link UserDAO#findPersonalDataByUserId(PersonalData)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param user  user, which contains id for search
	 * @return {@link PersonalData}
	 * @throws ServiceException {@link ServiceException}
	 */
	PersonalData findPersonalDataByUserId(User user) throws ServiceException;
	
	/**
	 * Update users role and status. 
	 * {@link UserDAO#updateUserRoleStatus(User)}
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param user  user, which contains data for update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateUserRoleStatus(User user) throws ServiceException;
	
	/**
	 * Update user password. 
	 * {@link UserDAO#updateUserPassword(User)}
	 * Encrypts user password and updates user's data
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param user  user, which contains data for update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateUserPassword(User user) throws ServiceException;
}
