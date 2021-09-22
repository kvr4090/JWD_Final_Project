package by.epamtc.kalimylin.dao.impl;

import static by.epamtc.kalimylin.dao.ColumnName.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.controller.util.Message;
import by.epamtc.kalimylin.dao.OrderDAO;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * The class implements OrderDAO interface methods
 */
public class OrderDAOimpl implements OrderDAO{

	private static final ConnectionPool connectionPool = 
			ConnectionPool.getInstance();
	
	private static final String SQL_ADD_ORDER = 
			"INSERT INTO orders(users_id, shippers_id, requirements_id, date, "
			+ "price, route_start_point, route_end_point, route_distance, note)"
			+ " VALUES(?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_FIND_ORDER_BY_TRIP = 
			"SELECT id, users_id, shippers_id, requirements_id, trips_id, date,"
			+ " price, route_start_point, route_end_point, route_distance, note"
			+ " FROM orders WHERE trips_id=?";
	
	private static final String SQL_FIND_AVAILABLE_ORDER = 
			"SELECT * FROM orders WHERE trips_id IS NULL";

	private static final String SQL_UPDATE_ORDER_BEFORE_DELETE_TRIP = 
			"UPDATE orders SET trips_id=NULL WHERE trips_id=?";
	
	private static final String SQL_DELETE_ORDER = 
			"DELETE FROM orders WHERE id=?";	
	
	private static final String SQL_FIND_ORDER_BY_ID = 
			"SELECT * FROM orders WHERE id=?";
	
	private static final String SQL_UPDATE_ORDER_TRIP_ID = 
			"UPDATE orders SET trips_id=? WHERE id=? AND trips_id IS NULL";
	
	@Override
	public boolean addOrder(Order order) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean orderAdded = false;

		try {
			preparedStatement = connection.prepareStatement(SQL_ADD_ORDER);
			preparedStatement = fillStatement(preparedStatement, order);
						
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != 0) {
				orderAdded = true;
			}		
		} catch (SQLException e) {	
			throw new DAOException(e);
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
		return orderAdded;
	}
	
	@Override
	public List<Order> findOrderByTrip(Trip trip) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		List<Order> resultList = new ArrayList<Order>();
		Order tempOrder = null;

		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_ORDER_BY_TRIP);
			preparedStatement.setInt(1, trip.getId());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {		
				tempOrder = createOrder(resultSet);
				resultList.add(tempOrder);					
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
	public List<Order> findAvailableOrder() throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		List<Order> resultList =  new ArrayList<Order>();
		Order tempOrder = null;

		try {
			preparedStatement = 
					connection.prepareStatement(SQL_FIND_AVAILABLE_ORDER);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {		
				tempOrder = createOrder(resultSet);
				resultList.add(tempOrder);					
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
	public boolean updateOrderBeforeDeleteTrip(Trip trip) throws DAOException {  
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		boolean updateOrder = false;
		
		try {			
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					SQL_UPDATE_ORDER_BEFORE_DELETE_TRIP);		
			preparedStatement.setInt(1, trip.getId());
			
			int requiredRowCount = findOrderByTrip(trip).size();		
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount != requiredRowCount) {
				
				connection.rollback();																
				return false;
			}			
			updateOrder = true;
			connection.commit();		
		} catch (SQLException e) {
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}	
		return updateOrder;
	}
	
	@Override
	public List<Order> findOrdersById(List<Order> listOrder) 
			throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		List<Order> resultList = new ArrayList<Order>();
		Order tempOrder = null;

		try {		
			for (Order order : listOrder) {
				
				preparedStatement = 
						connection.prepareStatement(SQL_FIND_ORDER_BY_ID);
				preparedStatement.setInt(1, order.getId());
				
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {		
					tempOrder = createOrder(resultSet);
					resultList.add(tempOrder);					
				}
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
	public boolean updateListOrdersByTripId(List<Order> listOrders, Trip trip) 
			throws DAOException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		int counter = 0;
		
		try {
			connection.setAutoCommit(false);
			
			for (Order order : listOrders) {
				
				preparedStatement = 
						connection.prepareStatement(SQL_UPDATE_ORDER_TRIP_ID);
				preparedStatement.setInt(1, trip.getId());
				preparedStatement.setInt(2, order.getId());
				
				int rowCount = preparedStatement.executeUpdate();
				
				if (rowCount != 0) {
					counter++;
				}	
			}
			
			if (counter != listOrders.size()) {
				connection.rollback();
				throw new DAOException(Message.FAILED + Message.UPDATE);
			}			
			connection.commit();
			return true;
			
		} catch (SQLException e) {
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
	}

	@Override
	public boolean deleteListOrder(List<Order> listOrder) throws DAOException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		int counter = 0;
		
		try {
			connection.setAutoCommit(false);
			
			for (Order order : listOrder) {
				
				preparedStatement = 
						connection.prepareStatement(SQL_DELETE_ORDER);
				preparedStatement.setInt(1, order.getId());				
				int rowCount = preparedStatement.executeUpdate();
				
				if (rowCount != 0) {
					counter++;
				}	
			}
			
			if (counter != listOrder.size()) {
				connection.rollback();
				throw new DAOException(Message.FAILED + Message.DELETE);
			}		
			connection.commit();
			return true;
			
		} catch (SQLException e) {			
			throw new DAOException(e);		
		} finally {
			connectionPool.releaseResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Form order from {@link ResultSet}
	 * 
	 * @param resultSet  input resultSet with data for new order
	 * @return {@link Order} with data from resultSet
	 * @throws SQLException {@link SQLException}
	 */
	private Order createOrder(ResultSet resultSet) throws SQLException {
		
		Order order = new Order();
		
		order.setId(resultSet.getInt(ID));
		order.setUserId(resultSet.getInt(USERS_ID));
		order.setShipperId(resultSet.getInt(SHIPPERS_ID));
		order.setRequirementId(resultSet.getInt(REQUIREMENTS_ID));
		order.setTripId(resultSet.getInt(TRIPS_ID));
		order.setDate(resultSet.getDate(DATE).toLocalDate());
		order.setPrice(resultSet.getBigDecimal(PRICE));
		order.setRouteStartPoint(resultSet.getString(ROUTE_START_POINT));
		order.setRouteEndPoint(resultSet.getString(ROUTE_END_POINT));
		order.setDistance(resultSet.getInt(ROUTE_DISTANCE));
		order.setNote(resultSet.getString(NOTE));
		
		return order;
	}
	
	/**
	 * Fill {@link PreparedStatement} date from {@link Order}
	 * 
	 * @param preparedStatement  preparedStatement
	 * @param order  source for data
	 * @return {@link PreparedStatement} with data
	 * @throws SQLException {@link SQLException}
	 */
	private PreparedStatement fillStatement(PreparedStatement preparedStatement,
			Order order) throws SQLException {
		
		preparedStatement.setInt(1, order.getUserId());
		preparedStatement.setInt(2, order.getShipperId());
		preparedStatement.setInt(3, order.getRequirementId());
		preparedStatement.setDate(4, Date.valueOf(order.getDate()));
		preparedStatement.setBigDecimal(5, order.getPrice());
		preparedStatement.setString(6, order.getRouteStartPoint());
		preparedStatement.setString(7, order.getRouteEndPoint());
		preparedStatement.setInt(8, order.getDistance());
		preparedStatement.setString(9, order.getNote());
		
		return preparedStatement;	
	}
}
