package by.epamtc.kalimylin.service;

import java.util.List;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.dao.ShipperDAO;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * ShipperService interface, contains interfaces for working with Shipper
 */
public interface ShipperService {
	
	/**
	 * Add new {@link Shipper}.
	 * {@link ShipperDAO#addShipper(Shipper)}
	 * Check similar shipper's name, when found, throw {@link ServiceException}
	 * with appropriate message, that name already in use.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param shipper  shipper for add
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean addShipper (Shipper shipper) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Shipper}
	 * {@link ShipperDAO#findAllShippers()}
	 * 
	 * @return {@link List} of {@link Shipper}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Shipper> findAllShippers() throws ServiceException;
	
	/**
	 * Send message from visitor {@link Shipper} to company email. 
	 * 
	 * @param shipper client, which ask the question
	 * @throws ServiceException {@link ServiceException}
	 */
	void sendQuestionFromClient(Shipper shipper) throws ServiceException;
}
