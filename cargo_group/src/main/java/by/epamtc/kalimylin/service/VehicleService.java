package by.epamtc.kalimylin.service;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.VehicleDAO;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * VehicleService interface, contains interfaces for working with Vehicle
 */
public interface VehicleService {
	
	/**
	 * Add new {@link Vehicle}
	 * {@link VehicleDAO#addVehicle(Vehicle)}
	 * Checks for similar license plate, if its find, 
	 * throws {@link ServiceException} with appropriate message, that this 
	 * license plate already in use. 
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicle  vehicle for add
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean addVehicle(Vehicle vehicle) throws ServiceException;
	
	/**
	 * Return {@link Vehicle}
	 * {@link VehicleDAO#findVehicleByLicensePlate(Vehicle)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicle  vehicle, which contains license plate for search
	 * @return {@link Vehicle}
	 * @throws ServiceException {@link ServiceException}
	 */
	Vehicle findVehicleByLicensePlate(Vehicle vehicle) throws ServiceException;
	
	/**
	 * Update vehicle before start trip. 
	 * Change status vehicle to unavailable and and to note message, that
	 * vehicle in trip.
	 * If vehicle have a trailer, also updates trailer.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param truck  truck, which goes to trip and needs to update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateVehicleBeforeStartTrip(Vehicle truck) throws ServiceException;
	
	/**
	 * Return {@link Vehicle}
	 * {@link VehicleDAO#findVehicleById(Vehicle)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicle  vehicle, which contains id for search
	 * @return {@link Vehicle}
	 * @throws ServiceException {@link ServiceException}
	 */
	Vehicle findVehicleById(Vehicle vehicle) throws ServiceException;

	/**
	 * Finish the trip.
	 * Calculates total odometer. Sum distance of each order in trip.
	 * Vehicle updates according to driver’s report. Updates condition, odometer
	 * and note.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param list  list contains vehicles which executed trip. Tractor with 
	 * trailer, or solo vehicle.
	 * @param trip  selected trip for finishing
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */	
	boolean finishTrip(List<Vehicle> list, Trip trip) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Vehicle}
	 * {@link VehicleDAO#findAllVehicle()}
	 * 
	 * @return {@link List} of {@link Vehicle}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Vehicle> findAllVehicle() throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Vehicle}
	 * Search for a list of vehicles suitable for a secondary vehicle.
	 * Uses input vehicle type as a primary vehicle type.
	 * For tractor search trailers, for trailer search tractors.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicleType  uses this type as a primary vehicle type
	 * @return {@link List} of {@link Vehicle}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Vehicle> findSecondaryVehicle(String vehicleType) 
			throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link String}
	 * Forms a list, with description type of input vehicle type.
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param vehicle  uses this vehicle's type as a primary vehicle type
	 * @return {@link List} of {@link String} first position contains describe
	 * primary vehicle type, also add describe secondary vehicle type, 
	 * if vehicle have a trailer.
	 * @throws ServiceException {@link ServiceException}
	 */
	List<String> findRoadTrainComposition(Vehicle vehicle) 
			throws ServiceException;
	
	/**
	 * Return {@link List} of {@link List} of objects {@link VehicleType}
	 * Separates a list of all vehicle types into two parts, depending on 
	 * the input type.
	 * Uses input vehicle type as a primary vehicle type.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicleType  uses this type, as a primary vehicleType
	 * @return {@link List} of {@link List} of objects {@link VehicleType}
	 * First position contains list of all vehicleTypes, similar with 
	 * primary vehicleType.
	 * Second position contains list of all vehicleTypes, suitable for use 
	 * as a secondary vehicleType.
	 * Example:
	 * For type tractor, first position contains similar types for this 
	 * vehicleType. Second position contains trailer vehicleTypes.
	 * @throws ServiceException {@link ServiceException}
	 */
	List<List<VehicleType>> separateVehicleType(String vehicleType) 
			throws ServiceException;	
	
	/**
	 * Update vehicle data. 
	 * {@link VehicleDAO#updateVehicle(Vehicle)}
	 * Checks for similar license plate, if its find, 
	 * throws {@link ServiceException} with appropriate message, that this 
	 * license plate already in use.  
	 * When incorrect values in the input data, throws exception. 
	 * 
	 * @param vehicle  vehicle to update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean updateVehicle(Vehicle vehicle) throws ServiceException;
	
	/**
	 * Add new {@link VehicleType}
	 * {@link VehicleDAO#addVehicleType(VehicleType)}
	 * Is expected the application will use Russian Community, so vehicles type
	 * save in russian language.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicleType  vehicleType for add
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean addVehicleType(VehicleType vehicleType) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link VehicleType}
	 * {@link VehicleDAO#findVehicleType()}
	 * 
	 * @return {@link List} of {@link VehicleType}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<VehicleType> findAllVehicleType() throws ServiceException;
	
	/**
	 * Return {@link Vehicle}
	 * Finds vehicle, which will use for delivery cargo.
	 * The tractor type will not be able to carry the goods, so method finds
	 * trailer, which hook up to this tractor.
	 * If vehicle is solo truck, method returns this truck.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip  trip, which contains vehicle id
	 * @return {@link Vehicle}, which carrying the goods
	 * @throws ServiceException {@link ServiceException}
	 */
	Vehicle findWagonTruck(Trip trip) throws ServiceException;	
}
