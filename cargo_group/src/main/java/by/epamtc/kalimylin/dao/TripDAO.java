package by.epamtc.kalimylin.dao;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * TripDAO interface, contains interfaces for working with Trip, CRUD
 */
public interface TripDAO {
	
	/**
	 * Enroll new {@link Trip} in data base. 
	 * If it was not possible to enroll a new shipper, throws exception.
	 * 
	 * @param trip  trip for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addTrip(Trip trip) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Trip}, return empty list, if there are no records 
	 * in the database.
	 * Never return <code>null</code>
	 * 
	 * @param user  user, which contains id for search
	 * @return {@link List} of {@link Trip}
	 * @throws DAOException {@link DAOException}
	 */
	List<Trip> findTripByUser(User user) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Trip} which have finish date = <code>null</code>, 
	 * return empty list, if there are no records in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link Trip}
	 * @throws DAOException {@link DAOException}
	 */
	List<Trip> findAvailableTrip() throws DAOException;
	
	/**
	 * Execute the SQL statement and update trip data in database.
	 * If it was not possible to update the trip, or id not exists,
	 * throws exception.
	 * 
	 * @param trip  trip for update
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateTrip(Trip trip) throws DAOException;
	
	/**
	 * Delete selected trip from database.
	 * If try to delete trip with id, which not exists, throws exception.
	 * 
	 * @param trip  trip for delete
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean deleteTrip(Trip trip) throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Trip}
	 * In search uses values of start date and vehicle id.
	 * 
	 * @param trip  trip, which contains params for search
	 * @return {@link Trip}, if trip's vehicle id not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	Trip findTripByParams(Trip trip) throws DAOException;
	
	/**
	 * Execute the SQL statement and update work table (trips_has_users),
	 * in database.
	 * If it was not possible to update table, or id not exists,
	 * throws exception.
	 * 
	 * @param user  user, which form(logist), or execute(driver) trip
	 * @param trip  trip, which id add to table
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateWorkListForUser(User user, Trip trip) throws DAOException;
	
	/**
	 * Delete selected trip's id from work table(trips_has_users) in database.
	 * If try to delete with trip's id, which not exists, throws exception.
	 * 
	 * @param trip  rows with  this trip's id will be delete from table.  
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateWorkListBeforeDeleteTrip(Trip trip) throws DAOException;
}
