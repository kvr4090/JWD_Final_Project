package by.epamtc.kalimylin.dao.impl;

import static by.epamtc.kalimylin.dao.ColumnName.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.VehicleDAO;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * The class implements VehicleDAO interface methods
 */
public class VehicleDAOimpl implements VehicleDAO {			
	
	private static final ConnectionPool connectionPool = 
			ConnectionPool.getInstance();
	
	private static final String SQL_ADD_VEHICLE = 
			"INSERT INTO vehicles(vehicle_types_id, trailer_id, license_plate, "
			+ "odometer, is_available, drive_license, model, brand, "
			+ "year_of_issue, color, note) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_FIND_VEHICLE_BY_LICENSE_PLATE = 
			"SELECT * FROM vehicles WHERE license_plate=?";
	
	private static final String SQL_FIND_VEHICLE_BY_ID = 
			"SELECT * FROM vehicles WHERE id=?";
	
	private static final String SQL_FIND_ALL_VEHICLE = "SELECT * FROM vehicles";
	
	private static final String SQL_UPDATE_VEHICLE = 
			"UPDATE vehicles SET vehicle_types_id=?, trailer_id=?, "
			+ "license_plate=?, odometer=?, is_available=?, drive_license=?, "
			+ "model=?, brand=?, year_of_issue=?, color=?, note=? WHERE id=?";
	
	private static final String SQL_ADD_VEHICLE_TYPE = 
			"INSERT INTO vehicle_types(requirements_id, type, is_available) "
			+ "VALUES(?,?,?)";
	
	private static final String SQL_FIND_VEHICLE_TYPE = 
			"SELECT * FROM vehicle_types WHERE is_available=true";
	
	@Override
	public boolean addVehicle(Vehicle vehicle) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean vehicleAdded = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_ADD_VEHICLE);
			preparedStatement = fillStatement(preparedStatement, vehicle);
	
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				vehicleAdded = true;
			}
		} catch (SQLException e) {		
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return vehicleAdded;
	}
	
	@Override
	public Vehicle findVehicleById(Vehicle vehicle) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Vehicle tempVehicle = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_VEHICLE_BY_ID);
			
			preparedStatement.setInt(1, vehicle.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {			
				return null;	
			}
			tempVehicle = createVehicle(resultSet);
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		} 	
		return tempVehicle;
	}
	
	@Override
	public Vehicle findVehicleByLicensePlate(Vehicle vehicle) 
			throws DAOException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Vehicle tempVehicle = null;
		
		try {
			preparedStatement = connection.prepareStatement(
					SQL_FIND_VEHICLE_BY_LICENSE_PLATE);
			
			preparedStatement.setString(1, vehicle.getLicensePlate());
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {			
				return null;	
			}
			tempVehicle = createVehicle(resultSet);			
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		} 	
		return tempVehicle;
	}

	@Override
	public List<Vehicle> findAllVehicle() throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Vehicle tempVehicle = null;
		List<Vehicle> resultList = new ArrayList<Vehicle>();
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_ALL_VEHICLE);
																						
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {				
				tempVehicle = createVehicle(resultSet);
				resultList.add(tempVehicle);		
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
	public boolean updateVehicle(Vehicle vehicle) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean updateVehicle = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_UPDATE_VEHICLE);
			preparedStatement = fillStatement(preparedStatement, vehicle);
			preparedStatement.setInt(12, vehicle.getId());

			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				updateVehicle = true;
			}			
		} catch (SQLException e) {		
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return updateVehicle;
	}
		
	@Override
	public boolean addVehicleType(VehicleType vehicleType) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean vehicleTypeAdded = false;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_ADD_VEHICLE_TYPE);
			
			preparedStatement.setInt(1, vehicleType.getRequirementId());
			preparedStatement.setString(2, vehicleType.getType());
			preparedStatement.setBoolean(3, vehicleType.getIsAvailable());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				vehicleTypeAdded = true;
			}	
		} catch (SQLException e) {
			throw new DAOException(e);			
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return vehicleTypeAdded;
	}

	@Override
	public List<VehicleType> findVehicleType() throws DAOException {  									
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<VehicleType> list = new ArrayList<VehicleType>();
		VehicleType temp = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_VEHICLE_TYPE);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				temp = new VehicleType();
				temp.setId(resultSet.getInt(ID));
				temp.setRequirementId(resultSet.getInt(REQUIREMENTS_ID));
				temp.setType(resultSet.getString(TYPE));
				temp.setIsAvailable(resultSet.getBoolean(IS_AVAILABLE));
				list.add(temp);
			}
		} catch (SQLException e) {
			throw new DAOException(e);	
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		} 
		return list;
	}
	
	/**
	 * Fill {@link PreparedStatement} date from {@link Vehicle}
	 * 
	 * @param preparedStatement  preparedStatement
	 * @param vehicle  source for data
	 * @return {@link PreparedStatement} with data
	 * @throws SQLException {@link SQLException}
	 */
	private PreparedStatement fillStatement(PreparedStatement preparedStatement,
			Vehicle vehicle) throws SQLException {
		
		preparedStatement.setInt(1, vehicle.getTypeId());
		preparedStatement.setInt(2, vehicle.getTrailerId());
		preparedStatement.setString(3, vehicle.getLicensePlate());
		preparedStatement.setInt(4, vehicle.getOdometer());
		preparedStatement.setBoolean(5, vehicle.getIsAvailable());
		preparedStatement.setString(6, vehicle.getDriveLicense());
		preparedStatement.setString(7, vehicle.getModel());
		preparedStatement.setString(8, vehicle.getBrand());
		preparedStatement.setDate(9, Date.valueOf(vehicle.getYearOfIssue()));
		preparedStatement.setString(10, vehicle.getColor());
		preparedStatement.setString(11, vehicle.getNote());
		
		return preparedStatement;	
	}
	
	/**
	 * Form {@link Vehicle} from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new vehicle
	 * @return {@link Vehicle} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private Vehicle createVehicle(ResultSet resultSet) throws SQLException {
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.setId(resultSet.getInt(ID));
		vehicle.setTypeId(resultSet.getInt(VEHICLE_TYPES_ID));
		vehicle.setTrailerId(resultSet.getInt(TRAILER_ID));
		vehicle.setLicensePlate(resultSet.getString(LICENSE_PLATE));
		vehicle.setOdometer(resultSet.getInt(ODOMETER));
		vehicle.setIsAvailable(resultSet.getBoolean(IS_AVAILABLE));
		vehicle.setDriveLicense(resultSet.getString(DRIVE_LICENSE));
		vehicle.setModel(resultSet.getString(MODEL));
		vehicle.setBrand(resultSet.getString(BRAND));
		vehicle.setYearOfIssue(resultSet.getDate(YEAR_OF_ISSUE).toLocalDate());
		vehicle.setColor(resultSet.getString(COLOR));
		vehicle.setNote(resultSet.getString(NOTE));
		
		return vehicle;
	}
}