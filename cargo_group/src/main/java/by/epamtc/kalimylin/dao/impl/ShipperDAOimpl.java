package by.epamtc.kalimylin.dao.impl;

import static by.epamtc.kalimylin.dao.ColumnName.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.dao.ShipperDAO;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * The class implements ShipperDAO interface methods
 */
public class ShipperDAOimpl implements ShipperDAO {
	
	private static final ConnectionPool connectionPool =
			ConnectionPool.getInstance();
	
	private static final String SQL_ADD_SHIPPER = 
			"INSERT INTO shippers(name, email, contact_person_name, "
			+ "contact_person_surname, contact_phone, note)"
			+ " VALUES(?,?,?,?,?,?)";
	
	private static final String SQL_FIND_SHIPPER_BY_NAME =
			"SELECT * FROM shippers WHERE name=?";
	
	private static final String SQL_FIND_ALL_SHIPPERS = 
			"SELECT * FROM shippers";
	
	@Override
	public boolean addShipper(Shipper shipper) throws DAOException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean shipperAdded = false;
		
		try {
			preparedStatement = connection.prepareStatement(SQL_ADD_SHIPPER);
			
			preparedStatement.setString(1, shipper.getName());
			preparedStatement.setString(2, shipper.getEmail());
			preparedStatement.setString(3, shipper.getContactPersonName());
			preparedStatement.setString(4, shipper.getContactPersonSurname());
			preparedStatement.setString(5, shipper.getContactPhone());
			preparedStatement.setString(6, shipper.getNote());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				shipperAdded = true;
			}			
		} catch (SQLException e) {		
			throw new DAOException(e);			
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return shipperAdded;
	}

	@Override
	public Shipper findShipperByName(Shipper shipper) throws DAOException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Shipper resultShipper = null;
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_SHIPPER_BY_NAME);
			
			preparedStatement.setString(1, shipper.getName());
			resultSet = preparedStatement.executeQuery();
			
			while (!resultSet.next()) {
				return null;
			}
			resultShipper = createShipper(resultSet);
		} catch (SQLException e) {
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement, 
					resultSet);
		}
		return resultShipper;
	}

	@Override
	public List<Shipper> findAllShippers() throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Shipper shipper = null;
		List<Shipper> list = new ArrayList<>();
		
		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_ALL_SHIPPERS);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				shipper = createShipper(resultSet);
				list.add(shipper);
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
	 * Form {@link Shipper} from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new shipper
	 * @return {@link Shipper} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private Shipper createShipper(ResultSet resultSet) throws SQLException {
		
		Shipper shipper = new Shipper();
		
		shipper.setId(resultSet.getInt(ID));
		shipper.setName(resultSet.getString(NAME));
		shipper.setEmail(resultSet.getString(EMAIL));
		shipper.setContactPersonName(resultSet.getString(CONTACT_PERSON_NAME));
		shipper.setContactPersonSurname(
				resultSet.getString(CONTACT_PERSON_SURNAME));
		shipper.setContactPhone(resultSet.getString(CONTACT_PHONE));
		shipper.setNote(resultSet.getString(NOTE));
		
		return shipper;		
	}
}
