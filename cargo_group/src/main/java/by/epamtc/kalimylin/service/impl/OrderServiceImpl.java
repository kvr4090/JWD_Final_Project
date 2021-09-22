package by.epamtc.kalimylin.service.impl;

import static by.epamtc.kalimylin.service.util.MessageService.*;
import static by.epamtc.kalimylin.service.util.validation.InputDataValidator.*;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.dao.DAOProvider;
import by.epamtc.kalimylin.dao.OrderDAO;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * The class implements OrderService interface methods
 */
public class OrderServiceImpl implements OrderService {
			
	@Override
	public boolean addOrder(Order order) throws ServiceException {

		checkOrder(order);
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();

		try {
			return orderDAO.addOrder(order);
		
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Order> findOrderByTrip(Trip trip) throws ServiceException {
		
		checkId(trip.getId());
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();
		
		try {	
			return orderDAO.findOrderByTrip(trip);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Order> findAvailableOrder() throws ServiceException {
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();
		
		try {		
			return orderDAO.findAvailableOrder();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Order> findOrdersById(List<Order> listOrders)
			throws ServiceException {		
		
		checkListOrdersId(listOrders);
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();
		
		try {		
			List<Order> resultList = orderDAO.findOrdersById(listOrders);
	
			if (listOrders.size() != resultList.size()) {
				throw new ServiceException(INCONSISTENCY_DATA);							
			}		
			return resultList;
	
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean updateListOrdersTripId(List<Order> listOrders, Trip trip) 
			throws ServiceException {
		
		checkListOrdersId(listOrders);
		checkId(trip.getId());
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();

		try {
			return orderDAO.updateListOrdersByTripId(listOrders, trip);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean updateOrdersBeforeDeleteTrip(Trip trip) 
			throws ServiceException {
		
		checkId(trip.getId());
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();
		
		try {
			return orderDAO.updateOrderBeforeDeleteTrip(trip);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteListOrder(List<Order> listOrders) 
			throws ServiceException {

		List<Order> tempListOrder = findOrdersById(listOrders);
		
		for (Order order : tempListOrder) {		
			if (order.getTripId() != 0) {
				throw new ServiceException(FAILED_DELETE_ORDER_IN_USE);											
			}
		}
		
		DAOProvider provider = DAOProvider.getInstance();
		OrderDAO orderDAO = provider.getOrderDAO();
		
		try {
			return orderDAO.deleteListOrder(tempListOrder);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
