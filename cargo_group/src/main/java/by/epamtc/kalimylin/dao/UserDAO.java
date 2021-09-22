package by.epamtc.kalimylin.dao;

import java.util.List;

import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * UserDAO interface, contains interfaces for working with User, CRUD
 */
public interface UserDAO {
	
	/**
	 * Enroll new {@link User} in data base. 
	 * If it was not possible to enroll a new user, throws exception.
	 * 
	 * @param user  user for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addUser(User user) throws DAOException;
	
	/**
	 * Enroll new {@link PersonalData} in data base. 
	 * If it was not possible to enroll a new personalData, throws exception.
	 * 
	 * @param personalData  personalData for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addPersonalData(PersonalData personalData) throws DAOException;					
	
	/**
	 * Execute the SQL statement and return object {@link User}
	 * 
	 * @param authenticationData  authenticationData, which contains 
	 * parameter for search.
	 * @return {@link User}, If authenticationData's login not exists,
	 *  return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	User findUserByLogin(AuthenticationData authenticationData) 
			throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Trip}
	 * 
	 * @param user  user, which contains parameter for search
	 * @return {@link User}, If authenticationData's email not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	User findUserByEmail(User user) throws DAOException;		
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link User}, return empty list, if there are no users with similar 
	 * parameters in the database.
	 * Checked parameters is login, email, phone.
	 * Never return <code>null</code>
	 * 
	 * @param user  original user
	 * @return {@link List} of {@link User}, contains a list of users, 
	 * each has at least one same parameter with the specified user
	 * @throws DAOException {@link DAOException}
	 */
	List<User> findSimilarUser(User user) throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link User}
	 * 
	 * @param vehicle  vehicle, which contains parameter for search
	 * @return {@link User} without parameter correct hash, 
	 * if vehicle's id not exists, return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	User findUserByVehicleId(Vehicle vehicle) throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Trip}
	 * 
	 * @param user  user, which contains parameter for search
	 * @return {@link User}, if user's id not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	User findUserById(User user) throws DAOException;
 	
	/**
	 * Execute the SQL statement and return object {@link PersonalData}
	 * 
	 * @param data  data, which contains parameter for search
	 * @return {@link PersonalData}, if user's id not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	PersonalData findPersonalDataByUserId(PersonalData data) 
			throws DAOException;					
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link User} without parameter correct hash, return empty list, 
	 * if there are no records in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link User}
	 * @throws DAOException {@link DAOException}
	 */
	List<User> findAllUsers() throws DAOException;
	
	/**
	 * Execute the SQL statement and update user data in database.
	 * If it was not possible to update the user, or id not exists,
	 * throws exception. Handles users with vehicle id and without vehicle id.
	 * 
	 * @param user  user for update
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateUser(User user) throws DAOException;									
	
	/**
	 * Execute the SQL statement and update personalData data in database.
	 * If it was not possible to update the personalData, or id not exists,
	 * throws exception.
	 * 
	 * @param personalData  personalData for update
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updatePersonalData(PersonalData personalData) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link PersonalData}, return empty list, if there are no records 
	 * in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link PersonalData}
	 * @throws DAOException {@link DAOException}
	 */
	List<PersonalData> findAllPersonalData() throws DAOException;
	
	/**
	 * Execute the SQL statement and update user in database.
	 * If it was not possible to update password, or user id not exists,
	 * throws exception.
	 * 
	 * @param user  user with password for update
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateUserPassword(User user) throws DAOException;
	
	/**
	 * Execute the SQL statement and update user in database.
	 * If it was not possible to update the trip, or id not exists,
	 * throws exception.
	 * 
	 * @param user  user with parameters for update
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateUserRoleStatus(User user) throws DAOException;
	
	/**
	 * Execute the SQL statement and return count of matches objects with
	 * similar personalData. Return 0, if there are no personalData with similar 
	 * parameters in the database.
	 * Checked parameters is PassportNumber, PassportIdentificationNumber.
	 * 
	 * @param personalData  personalData, which contains parameters for search
	 * @return <code>int</code> count of matches
	 * @throws DAOException {@link DAOException}
	 */
	int matchesPersonalData(PersonalData personalData) throws DAOException;
	
}