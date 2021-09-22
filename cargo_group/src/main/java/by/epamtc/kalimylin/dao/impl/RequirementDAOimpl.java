package by.epamtc.kalimylin.dao.impl;

import static by.epamtc.kalimylin.dao.ColumnName.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.dao.RequirementDAO;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * The class implements RequirementDAO interface methods
 */
public class RequirementDAOimpl implements RequirementDAO {
	
	private static final ConnectionPool connectionPool = 
			ConnectionPool.getInstance();

	private static final String SQL_ADD_REQUIREMENT = 
			"INSERT INTO requirements(weight, volume, pallets_quantity,"
			+ " length, width, height) VALUES(?,?,?,?,?,?)";
	
	private static final String SQL_FIND_ALL_REQUIREMENT =
			"SELECT * FROM requirements";
	
	private static final String SQL_FIND_REQUIREMENT_BY_ID = 
			"SELECT * FROM requirements WHERE id=?";
	
	private static final String SQL_FIND_REQUIREMENT_BY_VEHICLE = 
			"SELECT * FROM requirements JOIN vehicle_types "
			+ "ON requirements.id = vehicle_types.requirements_id "
			+ "AND vehicle_types.id=?";

	@Override
	public boolean addRequirement(Requirement requirement) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean requirementAdded = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_ADD_REQUIREMENT);
			
			preparedStatement.setFloat(1, requirement.getWeight());
			preparedStatement.setFloat(2, requirement.getVolume());
			preparedStatement.setInt(3, requirement.getPalletsQuantity());
			preparedStatement.setFloat(4, requirement.getMaxLength());
			preparedStatement.setFloat(5, requirement.getMaxWidth());
			preparedStatement.setFloat(6, requirement.getMaxHeight());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				requirementAdded = true;
			}		
		} catch (SQLException e) {			
			throw new DAOException(e);			
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return requirementAdded;
	}

	@Override
	public List<Requirement> findRequirement() throws DAOException {
	
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		List<Requirement> resultList = new ArrayList<Requirement>();
		Requirement requirement = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_ALL_REQUIREMENT);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {			
				requirement = createRequirement(resultSet);			
				resultList.add(requirement);
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
	public Requirement findRequirementById(Requirement requirement) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		Requirement result = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_REQUIREMENT_BY_ID);
			preparedStatement.setInt(1, requirement.getId());
			
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {			
				return null;	
			}			
			result = createRequirement(resultSet);
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return result;
	}

	@Override
	public Requirement findRequirementByVehicle(Vehicle vehicle) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		Requirement requirement = null;
		
		try {
			preparedStatement = connection.prepareStatement(
					SQL_FIND_REQUIREMENT_BY_VEHICLE);
			preparedStatement.setInt(1, vehicle.getTypeId());
			
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {			
				return null;	
			}			
			requirement = createRequirement(resultSet);
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return requirement;
	}
	
	/**
	 * Form {{@link Requirement} from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new requirement
	 * @return {@link Requirement} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private Requirement createRequirement(ResultSet resultSet) 
			throws SQLException {
		
		Requirement requirement = new Requirement();
		
		requirement.setId(resultSet.getInt(ID));
		requirement.setWeight(resultSet.getFloat(WEIGHT));
		requirement.setVolume(resultSet.getFloat(VOLUME));
		requirement.setPalletsQuantity(resultSet.getInt(PALLETS_QUANTITY));
		requirement.setMaxLength(resultSet.getFloat(LENGTH));
		requirement.setMaxWidth(resultSet.getFloat(WIDTH));
		requirement.setMaxHeight(resultSet.getFloat(HEIGHT));
		
		return requirement;
	}
}
