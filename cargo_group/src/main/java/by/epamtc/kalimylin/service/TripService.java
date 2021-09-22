package by.epamtc.kalimylin.service;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.dao.OrderDAO;
import by.epamtc.kalimylin.dao.TripDAO;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * TripService interface, contains interfaces for working with Trip
 */
public interface TripService {
	
	/**
	 * Add new {@link Trip}
	 * {@link TripDAO#addTrip(Trip)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip  trip for add
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean addTrip(Trip trip) throws ServiceException;

	/**
	 * Form trip by orders id from listId
	 * Update list orders, which include in trip. Update trip for driver.
	 * Uses driver's id, finds vehicle for trip, then finds parameter of 
	 * vehicleType this vehicle and compare total sum parameters all orders,
	 * which are part of the trip like weight, volume, pallets quantity with 
	 * parameters of vehicle type. If total sum more than parameters vehicle 
	 * type, throw ServiceException with message, that trip is overload. 
	 * Set for orders parameter trip id, by input value trip's id.
	 * Also update table, where enroll trips id and users id.(trips_has_users).
	 * The cases of dimensions requiring the examination of the geometry of the 
	 * body (height, length, width) are not taken into account.
	 * Linear dimensions of the body are listed for reference.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip  trip for processing
	 * @param listId  {@link List} of {@link Integer} with id for processing. 
	 * Contains at first position logist's id, who form this trip, 
	 * at secondary position driver's id, executor of this trip.
	 * Next positions contains orders id, which are part of the trip.
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean processingTrip(Trip trip, List<Integer> listId)
			throws ServiceException;
	
	/**
	 * Finds the nearest {@link Trip} for the user.
	 * Find all trips for user {@link TripDAO#findTripByUser(User)}, 
	 * then sort by date and return first trip.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param user  user for add
	 * @return {@link Trip}
	 * @throws ServiceException {@link ServiceException}
	 */
	Trip findCurrentTrip(User user) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Trip}
	 * {@link TripDAO#findAvailableTrip()}
	 * 
	 * @return {@link List} of {@link Trip}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Trip> findAvailableTrip() throws ServiceException;
	
	/**
	 * Update trip finish data with the current date.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip  trip for finish
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean finishTrip(Trip trip) throws ServiceException;
	
	/**
	 * Delete selected trip.
	 * First check whether or not the trip has started
	 * Second check {@link TripDAO#updateWorkListBeforeDeleteTrip(Trip)}
	 * Third check {@link OrderDAO#updateOrderBeforeDeleteTrip(Trip)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip  trip for delete
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean deleteTrip(Trip trip) throws ServiceException;

}
