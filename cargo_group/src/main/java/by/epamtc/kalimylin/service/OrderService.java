package by.epamtc.kalimylin.service;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.dao.OrderDAO;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * OrderService interface, contains interfaces for working with Order
 */
public interface OrderService {
	
	/**
	 * Add new {@link Order}.
	 * {@link OrderDAO#addOrder(Order)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param order  order for add
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean addOrder(Order order) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Order}
	 * {@link OrderDAO#findOrdersById(List)}
	 * If finds not all orders from list, throws {@link ServiceException} with
	 * appropriate message inconsistency data.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param listOrder  list with orders id
	 * @return {@link List} of {@link Order}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Order> findOrdersById(List<Order> listOrder) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Order}
	 * {@link OrderDAO#findOrderByTrip(Trip)}
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param trip  selected trip
	 * @return {@link List} of {@link Order}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Order> findOrderByTrip(Trip trip) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Order}
	 * {@link OrderDAO#findAvailableOrder()}
	 * 
	 * @return {@link List} of {@link Order}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Order> findAvailableOrder() throws ServiceException;
	
	/**
	 * Update orders data. 
	 * {@link OrderDAO#deleteListOrder(List)}
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param listOrder  list orders for delete
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean deleteListOrder(List<Order> listOrder) throws ServiceException;
	
	/**
	 * Update order data. 
	 * {@link OrderDAO#updateListOrdersByTripId(List, Trip)}
	 * If any order from list include in trip, throws {@link ServiceException}
	 * with appropriate message, that order in use.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param listOrders  list orders to update
	 * @param trip  selected trip
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateListOrdersTripId(List<Order> listOrders, Trip trip)
			throws ServiceException;
	
	/**
	 * Update order data.
	 * {@link OrderDAO#updateOrderBeforeDeleteTrip(Trip)}
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param trip  selected trip
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateOrdersBeforeDeleteTrip(Trip trip) throws ServiceException;
}
