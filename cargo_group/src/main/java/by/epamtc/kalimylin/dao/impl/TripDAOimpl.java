package by.epamtc.kalimylin.dao.impl;

import static by.epamtc.kalimylin.dao.ColumnName.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.dao.TripDAO;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * The class implements TripDAO interface methods
 */
public class TripDAOimpl implements TripDAO {
	
	private static final ConnectionPool connectionPool = 
			ConnectionPool.getInstance();
	
	private static final String SQL_ADD_TRIP = 
			"INSERT INTO trips(vehicles_id, start_date, note) VALUES(?,?,?)";

	private static final String SQL_FIND_TRIP_BY_USER = 
			"SELECT trips.id, trips.vehicles_id, trips.start_date, trips.note " 
			+ "FROM trips_has_users JOIN trips "
			+ "ON trips_has_users.trips_id = trips.id AND users_id=? "
			+ "AND finish_date IS NULL";
	
	private static final String SQL_FIND_AVAILABLE_TRIP = 
			"SELECT * FROM trips WHERE finish_date IS NULL";
	
	private static final String SQL_UPDATE_TRIP = 
			"UPDATE trips SET vehicles_id=?, finish_date=?, note=? WHERE id=?";
	
	private static final String SQL_DELETE_TRIP = 
			"DELETE FROM trips WHERE id=?";
	
	private static final String SQL_FIND_TRIP = 
			"SELECT * FROM trips WHERE vehicles_id=? AND start_date=?";
	
	private static final String SQL_ADD_TRIP_TO_WORK_LIST = 
			"INSERT INTO trips_has_users(trips_id, users_id) VALUES(?,?)";
	
	private static final String SQL_DELETE_NOTE_IN_WORK_LIST = 
			"DELETE FROM trips_has_users WHERE trips_id=?";
	
	@Override
	public boolean addTrip(Trip trip) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean tripAdded = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_ADD_TRIP);
			
			preparedStatement.setInt(1, trip.getVehicleId());
			preparedStatement.setDate(2, Date.valueOf(trip.getStartDate()));
			preparedStatement.setString(3, trip.getNote());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				tripAdded = true;
			}			
		} catch (SQLException e) {			
			throw new DAOException(e);			
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return tripAdded;
	}
	
	@Override
	public List<Trip> findTripByUser(User user) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Trip tempTrip = null;
		List<Trip> resultList = new ArrayList<Trip>();
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_TRIP_BY_USER);
			
			preparedStatement.setInt(1, user.getId());
			
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
				tempTrip = createTrip(resultSet);
				resultList.add(tempTrip);
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
	public List<Trip> findAvailableTrip() throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Trip tempTrip = null;
		List<Trip> resultList = new ArrayList<Trip>();
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_AVAILABLE_TRIP);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				tempTrip = createTrip(resultSet);
				resultList.add(tempTrip);
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
	public boolean updateTrip(Trip trip) throws DAOException {					
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean updateTrip = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_UPDATE_TRIP);
		
			preparedStatement.setInt(1, trip.getVehicleId());
			preparedStatement.setDate(2, Date.valueOf(trip.getFinishDate()));
			preparedStatement.setString(3, trip.getNote());
			preparedStatement.setInt(4, trip.getId());

			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				updateTrip = true;
			}		
		} catch (SQLException e) {	
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return updateTrip;
	}

	@Override
	public boolean deleteTrip(Trip trip) throws DAOException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean tripDeleted = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_DELETE_TRIP);
			
			preparedStatement.setInt(1, trip.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				tripDeleted = true;
			}			
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return tripDeleted;
	}

	@Override
	public Trip findTripByParams(Trip trip) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Trip tempTrip = null;

		try {
			preparedStatement = connection.prepareStatement(SQL_FIND_TRIP);
			
			preparedStatement.setInt(1, trip.getVehicleId());
			preparedStatement.setDate(2, Date.valueOf(trip.getStartDate()));
			
			resultSet = preparedStatement.executeQuery();
		
			while (!resultSet.next()) {
				return null;
			}
			tempTrip = createTrip(resultSet);
			
		} catch (SQLException e) {		
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return tempTrip;	
	}

	@Override
	public boolean updateWorkListForUser(User user, Trip trip) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean workListUpdated = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_ADD_TRIP_TO_WORK_LIST);
			
			preparedStatement.setInt(1, trip.getId());
			preparedStatement.setInt(2, user.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				workListUpdated = true;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DAOException(Message.LOGIST_IN_TRIP);			
		} catch (SQLException e) {
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return workListUpdated;
	}

	@Override
	public boolean updateWorkListBeforeDeleteTrip(Trip trip) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean workListUpdated = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_DELETE_NOTE_IN_WORK_LIST);
			
			preparedStatement.setInt(1, trip.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				workListUpdated = true;
			}		
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return workListUpdated;
	}
	
	/**
	 * Form {@link Trip} from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new trip
	 * @return {@link Trip} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private Trip createTrip(ResultSet resultSet) throws SQLException {
		
		Trip trip = new Trip();
		
		trip.setId(resultSet.getInt(ID));
		trip.setVehicleId(resultSet.getInt(VEHICLES_ID));
		trip.setStartDate(resultSet.getDate(START_DATE).toLocalDate());
		trip.setNote(resultSet.getString(NOTE));
				
		return trip;
	}
}
