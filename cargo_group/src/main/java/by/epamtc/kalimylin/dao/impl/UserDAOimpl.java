package by.epamtc.kalimylin.dao.impl;

import static by.epamtc.kalimylin.dao.ColumnName.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.dao.UserDAO;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * The class implements UserDAO interface methods
 */
public class UserDAOimpl implements UserDAO {
	
	private static final ConnectionPool connectionPool =
			ConnectionPool.getInstance();
	
	private static final String SQL_ADD_USER = 
			"INSERT INTO users(roles_id, login, correct_hash, email, phone, "
			+ "is_active) VALUES(?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE_USER_PASSWORD = 
			"UPDATE users SET correct_hash=? WHERE id=?";
	
	private static final String SQL_UPDATE_ROLE_STATUS = 
			"UPDATE users SET roles_id=?, is_active=? WHERE id=?";
	
	private static final String SQL_ADD_USER_PERSONAL_DATA = 
			"INSERT INTO personal_data(users_id, name, surname, birthdate, "
			+ "post, passport_number, passport_identification_number, "
			+ "passport_date_of_expiry, passport_date_of_issue, "
			+ "passport_authority, drive_license, recruitment_date, "
			+ "termination_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_FIND_USER_PERSONAL_DATA = 
			"SELECT * FROM personal_data WHERE users_id=?";
	
	private static final String SQL_FIND_USER_BY_VEHICLE_ID = 
			"SELECT * FROM users JOIN roles ON users.roles_id = roles.id "
			+ "AND vehicles_id=?";
	
	private static final String SQL_FIND_USER_BY_ID = 
			"SELECT * FROM users JOIN roles ON users.roles_id = roles.id "
			+ "AND users.id=?";
	
	private static final String SQL_FIND_USER_BY_LOGIN =
			"SELECT users.id, vehicles_id, login, correct_hash, email, phone, "
			+ "is_active, type FROM users JOIN roles "
			+ "ON users.roles_id = roles.id AND login=?";
	
	private static final String SQL_FIND_ALL_USERS = 
			"SELECT users.id, vehicles_id, login, email, phone, is_active, type"
			+ " FROM users JOIN roles ON users.roles_id = roles.id";
	
	private static final String SQL_FIND_USER_BY_EMAIL = 
			"SELECT users.id, vehicles_id, login, correct_hash, email, phone, "
			+ "is_active, type FROM users "
			+ "JOIN roles ON users.roles_id = roles.id AND email=?";
	
	private static final String SQL_FIND_ID_ROLE = 
			"SELECT id FROM roles WHERE type=?";
	
	private static final String SQL_UPDATE_USER_DATA = 
			"UPDATE users SET roles_id=?, login=?, correct_hash=?, email=?, "
			+ "phone=?, is_active=?, vehicles_id=? WHERE login=?";
	
	private static final String SQL_UPDATE_USER_DATA_NO_VEHICLE = 
			"UPDATE users SET roles_id=?, login=?, correct_hash=?, email=?, "
			+ "phone=?, is_active=?, vehicles_id=NULL WHERE login=?";
	
	private static final String SQL_UPDATE_USER_PERSONAL_DATA = 
			"UPDATE personal_data SET users_id=?, name=?,surname=?,birthdate=?,"
			+ " post=?, passport_number=?, passport_identification_number=?, "
			+ "passport_date_of_expiry=?, passport_date_of_issue=?, "
			+ "passport_authority=?, drive_license=?, recruitment_date=?, "
			+ "termination_date=? WHERE users_id=?";

	private static final String SQL_FIND_ALL_PERSONAL_DATA = 
			"SELECT * FROM personal_data";
	
	private static final String SQL_FIND_MATCH_DATA_BY_USER_DATA = 
			"SELECT * FROM users WHERE login=? OR email=? OR phone=?";
	
	private static final String SQL_FIND_MATCHES_PASSPORT = 
			"SELECT users_id FROM personal_data WHERE passport_number=? "
			+ "OR passport_identification_number=?";
	
	@Override
	public boolean addUser(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean accountAdded = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_ADD_USER);
			preparedStatement = 
					fillPreparedStatementUser(preparedStatement, user);
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				accountAdded = true;
			}
		} catch (SQLException e) {	
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return accountAdded;
	}
	
	@Override
	public boolean addPersonalData(PersonalData personalData) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean dataUpdated = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_ADD_USER_PERSONAL_DATA);
			preparedStatement = fillPreparedStatementPersonalData(
					preparedStatement, personalData);
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				dataUpdated = true;
			}
		} catch (SQLException e) {	
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return dataUpdated;
	}
	
	@Override
	public User findUserByLogin(AuthenticationData authenticationData) 
			throws DAOException {		
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			preparedStatement.setString(1, authenticationData.getLogin());
		
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				user = createUser(resultSet);
				user.setCorrectHash(resultSet.getString(CORRECT_HASH));
			}	
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return user;
	}
	
	@Override
	public User findUserByEmail(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User resultUser = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			preparedStatement.setString(1, user.getEmail());
		
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				resultUser = createUser(resultSet);
				resultUser.setCorrectHash(resultSet.getString(CORRECT_HASH));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return resultUser;
	}
	
	@Override
	public List<User> findSimilarUser(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		User tempUser;
		List<User> resultList = new ArrayList<>();
		
		try {
			preparedStatement = connection.prepareStatement(
					SQL_FIND_MATCH_DATA_BY_USER_DATA);
			
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPhone());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				if (user.getId() == resultSet.getInt(ID)) {
					continue;								
				}
				tempUser = new User();
				tempUser.setLogin(resultSet.getString(LOGIN));
				tempUser.setEmail(resultSet.getString(EMAIL));
				tempUser.setPhone(resultSet.getString(PHONE));
				resultList.add(tempUser);			
			}
		} catch (SQLException e) {
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return resultList;
	}
	
	@Override
	public PersonalData findPersonalDataByUserId(PersonalData data) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PersonalData userData = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_USER_PERSONAL_DATA);
			
			preparedStatement.setInt(1, data.getUserId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				userData = createPersonalData(resultSet);
			}		
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return userData;
	}
	
	@Override
	public boolean updateUser(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean userUpdated = false;
		String sqlQuery = SQL_UPDATE_USER_DATA;
		
		try {
			
			if (user.getVehicleId() == 0) {
				sqlQuery = SQL_UPDATE_USER_DATA_NO_VEHICLE;
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement = 
						fillPreparedStatementUser(preparedStatement, user);
				preparedStatement.setString(7, user.getLogin());
			} else {
				preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement = 
						fillPreparedStatementUser(preparedStatement, user);
				preparedStatement.setInt(7, user.getVehicleId());
				preparedStatement.setString(8, user.getLogin());
			}						
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				userUpdated = true;
			}		
		} catch (SQLException e) {	
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return userUpdated;
	}
	
	@Override
	public boolean updatePersonalData(PersonalData userData) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean dataUpdated = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_UPDATE_USER_PERSONAL_DATA);
			preparedStatement = fillPreparedStatementPersonalData(
					preparedStatement, userData);
			preparedStatement.setInt(14, userData.getUserId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				dataUpdated = true;
			}
		} catch (SQLException e) {	
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return dataUpdated;
	}
	
	@Override
	public List<User> findAllUsers() throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		List<User> resultList = new ArrayList<User>();
		User tempUser = null;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {			
				tempUser = createUser(resultSet);
				resultList.add(tempUser);			
			}
		} catch (SQLException e) {	
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return resultList;
	}

	@Override
	public List<PersonalData> findAllPersonalData() throws DAOException {				
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		List<PersonalData> resultList = new ArrayList<>();
		PersonalData tempData = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_ALL_PERSONAL_DATA);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {			
				tempData = createPersonalData(resultSet);
				resultList.add(tempData);			
			}
		} catch (SQLException e) {	
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return resultList;
	}

	@Override
	public User findUserByVehicleId(Vehicle vehicle) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		User tempUser = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_USER_BY_VEHICLE_ID);			
			preparedStatement.setInt(1, vehicle.getId());	
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {			
				return null;	
			}
			
			tempUser = createUser(resultSet);
			
		} catch (SQLException e) {
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return tempUser;
	}

	@Override
	public User findUserById(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		User tempUser = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_USER_BY_ID);			
			preparedStatement.setInt(1, user.getId());	
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {			
				return null;	
			}		
			tempUser = createUser(resultSet);
			tempUser.setCorrectHash(resultSet.getString(CORRECT_HASH));
		} catch (SQLException e) {
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return tempUser;
	}

	@Override
	public boolean updateUserPassword(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean passwordUpdated = false;
		
		try {
			preparedStatement =
					connection.prepareStatement(SQL_UPDATE_USER_PASSWORD);
			preparedStatement.setString(1, user.getCorrectHash());
			preparedStatement.setInt(2, user.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				passwordUpdated = true;
			}		
		} catch (SQLException e) {	
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return passwordUpdated;
	}

	@Override
	public boolean updateUserRoleStatus(User user) throws DAOException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean userUpdated = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_UPDATE_ROLE_STATUS);
			preparedStatement.setInt(1, findIdRole(user.getRole()));
			preparedStatement.setBoolean(2, user.getIsActive());
			preparedStatement.setInt(3, user.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				userUpdated = true;
			}		
		} catch (SQLException e) {	
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return userUpdated;
	}

	@Override
	public int matchesPersonalData(PersonalData personalData) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		int counter = 0;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_MATCHES_PASSPORT);
			preparedStatement.setString(1, personalData.getPassportNumber());
			preparedStatement.setString(
					2, personalData.getPassportIdentificationNumber());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if (resultSet.getInt(USERS_ID) != personalData.getUserId()) {
					counter++;
				}
			}
		} catch (SQLException e) {	
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return counter;
	}
	
	/**
	 * Find id role
	 * 
	 * @param role  role for which looking for id
	 * @return {@code Integer} role id
	 * @throws SQLException {@link SQLException}
	 */
	private int findIdRole(Role role) throws SQLException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = 0;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_FIND_ID_ROLE);
			
			preparedStatement.setString(1, role.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				id = resultSet.getInt(ID);
			}
		} finally {
			connectionPool.releaseResources(connection, preparedStatement,
					resultSet);
		}
		return id;
	}
	
	/**
	 * Form {@link User} from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new User
	 * @return {@link User} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private User createUser(ResultSet resultSet) throws SQLException {
		
		User user = new User();
		
		user.setId(resultSet.getInt(ID));
		user.setRole(Role.valueOf(resultSet.getString(TYPE).toUpperCase()));
		user.setVehicleId(resultSet.getInt(VEHICLES_ID));
		user.setLogin(resultSet.getString(LOGIN));
		user.setEmail(resultSet.getString(EMAIL));
		user.setPhone(resultSet.getString(PHONE));
		user.setIsActive(resultSet.getBoolean(IS_ACTIVE));
				
		return user;	
	}
	
	/**
	 * Form {@link PersonalData} from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new personalData
	 * @return {@link PersonalData} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private PersonalData createPersonalData(ResultSet resultSet) 
			throws SQLException {
		
		PersonalData personalData = new PersonalData();
		
		personalData.setUserId(resultSet.getInt(USERS_ID));
		personalData.setName(resultSet.getString(NAME));
		personalData.setSurname(resultSet.getString(SURNAME));
		personalData.setBirthdate(resultSet.getDate(BIRTHDATE).toLocalDate());
		personalData.setPost(resultSet.getString(POST));
		personalData.setPassportNumber(resultSet.getString(PASSPORT_NUMBER));
		personalData.setPassportIdentificationNumber(
				resultSet.getString(PASSPORT_IDENTIFICATION_NUMBER));
		personalData.setPassportDateOfExpiry(
				resultSet.getDate(PASSPORT_DATE_EXPIRY).toLocalDate());
		personalData.setPassportDateOfIssue(
				resultSet.getDate(PASSPORT_DATE_ISSUE).toLocalDate());
		personalData.setPassportAuthority(
				resultSet.getString(PASSPORT_AUTHORITY));
		personalData.setDriveLicense(resultSet.getString(DRIVE_LICENSE));
		personalData.setRecruitmentDate(
				resultSet.getDate(RECRUITMENT_DATE).toLocalDate());
		
		if (resultSet.getDate(TERMINATION_DATE) != null) {
			personalData.setTerminationDate(
					resultSet.getDate(TERMINATION_DATE).toLocalDate());
		}
		return personalData;	
	}
	
	/**
	 * Fill {@link PreparedStatement} date from {@link User}
	 * 
	 * @param preparedStatement preparedStatement
	 * @param user  source for data
	 * @return {@link PreparedStatement} with data
	 * @throws DAOException {@link DAOException}
	 * @throws SQLException {@link SQLException}
	 */
	private PreparedStatement fillPreparedStatementUser(
			PreparedStatement preparedStatement, User user) 
					throws DAOException, SQLException {
		
		int idRole = findIdRole(user.getRole());
		preparedStatement.setInt(1, idRole);
		preparedStatement.setString(2, user.getLogin());
		preparedStatement.setString(3, user.getCorrectHash());
		preparedStatement.setString(4, user.getEmail());
		preparedStatement.setString(5, user.getPhone());
		preparedStatement.setBoolean(6, user.getIsActive());
		
		return preparedStatement;
	}
	
	/**
	 * Fill {@link PreparedStatement} date from {@link PersonalData}
	 * 
	 * @param preparedStatement  preparedStatement
	 * @param personalData  source for data
	 * @return {@link PreparedStatement} with data
	 * @throws SQLException {@link SQLException}
	 */
	private PreparedStatement fillPreparedStatementPersonalData(
			PreparedStatement preparedStatement, PersonalData personalData) 
					throws SQLException {
		
		preparedStatement.setInt(1, personalData.getUserId());
		preparedStatement.setString(2, personalData.getName());
		preparedStatement.setString(3, personalData.getSurname());
		preparedStatement.setDate(4, Date.valueOf(personalData.getBirthdate()));
		preparedStatement.setString(5, personalData.getPost());
		preparedStatement.setString(6, personalData.getPassportNumber());
		preparedStatement.setString(
				7, personalData.getPassportIdentificationNumber());
		preparedStatement.setDate(
				8, Date.valueOf(personalData.getPassportDateOfExpiry()));
		preparedStatement.setDate(
				9, Date.valueOf(personalData.getPassportDateOfIssue()));
		preparedStatement.setString(10, personalData.getPassportAuthority());
		preparedStatement.setString(11, personalData.getDriveLicense());
		preparedStatement.setDate(
				12, Date.valueOf(personalData.getRecruitmentDate()));
		
		if (personalData.getTerminationDate() != null) {
			preparedStatement.setDate(
					13, Date.valueOf(personalData.getTerminationDate()));				
		} else {
			preparedStatement.setDate(13, null);
		}	
		return preparedStatement;
	}
}