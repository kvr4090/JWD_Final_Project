package by.epamtc.kalimylin.dao;

import java.util.List;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * ShipperDAO interface, contains interfaces for working with Shippers, CRUD
 */
public interface ShipperDAO {
	
	/**
	 * Enroll new {@link Shipper} in data base. 
	 * If it was not possible to enroll a new shipper, throws exception.
	 * 
	 * @param shipper  shipper for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addShipper(Shipper shipper) throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Shipper}
	 * 
	 * @param shipper  shipper, which contains name for search
	 * @return {@link Shipper}, if shipper's name not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	Shipper findShipperByName(Shipper shipper) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Shipper}, return empty list, if there are no records 
	 * in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link Shipper}
	 * @throws DAOException {@link DAOException}
	 */
	List<Shipper> findAllShippers() throws DAOException;
}
