package by.epamtc.kalimylin.dao;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * OrderDAO interface, contains interfaces for working with Orders, CRUD
 */
public interface OrderDAO {
	
	/**
	 * Enroll new {@link Order} in data base. If it was not possible to enroll
	 * a new order, throws exception.
	 * 
	 * @param order  order for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addOrder(Order order) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Order}, or return empty list, if there are no records 
	 * in the database with the specified order id.
	 * Never return <code>null</code>
	 * 
	 * @param listOrders  list with orders id
	 * @return {@link List} of {@link Order}
	 * @throws DAOException {@link DAOException}
	 */
	List<Order> findOrdersById(List<Order> listOrders) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Order}, or return empty list, if there are no records 
	 * in the database with the specified id trip. 
	 * Never return <code>null</code>
	 * 
	 * @param trip  selected trip
	 * @return {@link List} of {@link Order}
	 * @throws DAOException {@link DAOException}
	 */
	List<Order> findOrderByTrip(Trip trip) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Order}, which not include in the any trip, or return empty list, 
	 * if there are no records in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link Order}
	 * @throws DAOException {@link DAOException}
	 */
	List<Order> findAvailableOrder() throws DAOException;
	
	/**
	 * Execute the SQL statement and update order data in database.
	 * List orders included in trip, available to plan again.
	 * In each order from list, parameter trip id deleted.
	 * If it was not possible to update the order, or id not exists,
	 * the changes are not sent and throws exception.
	 * 
	 * @param trip  selected trip
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateOrderBeforeDeleteTrip(Trip trip) throws DAOException;
	
	/**
	 * Execute the SQL statement and update order data in database. 
	 * If it was not possible to update the order, or order id, or trip id
	 * not exists, the changes are not sent and throws exception. 
	 * Set order's tripId to value of selected trip.
	 * 
	 * @param listOrders  list orders to update
	 * @param trip  selected trip
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException} if the number of updated row 
	 * does not match 
	 * the size of list order.
	 */
	boolean updateListOrdersByTripId(List<Order> listOrders, Trip trip)
			throws DAOException;
	
	/**
	 * Delete selected list orders from database.
	 * If try to delete order with id, which not exists, the changes 
	 * are not sent and throws exception.
	 * 
	 * @param listOrder  list orders for delete
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean deleteListOrder(List<Order> listOrder) throws DAOException;
}
