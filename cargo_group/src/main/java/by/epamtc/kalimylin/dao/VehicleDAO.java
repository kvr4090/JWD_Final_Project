package by.epamtc.kalimylin.dao;

import java.util.List;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * UserDAO interface, contains interfaces for working with Vehicle, CRUD
 */
public interface VehicleDAO {
	
	/**
	 * Enroll new {@link Vehicle} in data base. 
	 * If it was not possible to enroll a new vehicle, throws exception.
	 * 
	 * @param vehicle  vehicle for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addVehicle(Vehicle vehicle) throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Vehicle}
	 * 
	 * @param vehicle  vehicle, which contains params for search
	 * @return {@link Vehicle}, if vehicle's id not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	Vehicle findVehicleById(Vehicle vehicle) throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Vehicle}
	 * 
	 * @param vehicle  vehicle, which contains params for search
	 * @return {@link Vehicle}, if vehicle's license plate not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	Vehicle findVehicleByLicensePlate(Vehicle vehicle) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Vehicle}, return empty list, if there are no records 
	 * in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link Vehicle}
	 * @throws DAOException {@link DAOException}
	 */
	List<Vehicle> findAllVehicle() throws DAOException;
	
	/**
	 * Execute the SQL statement and update vehicle data in database.
	 * If it was not possible to update the vehicle, or id not exists,
	 * throws exception.
	 * 
	 * @param vehicle  vehicle for update
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean updateVehicle(Vehicle vehicle) throws DAOException;
	
	/**
	 * Enroll new {@link VehicleType} in data base. 
	 * If it was not possible to enroll a new vehicleType, throws exception.
	 * 
	 * @param vehicleType  vehicleType for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addVehicleType(VehicleType vehicleType) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link VehicleType}, return empty list, if there are no records 
	 * in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link VehicleType}
	 * @throws DAOException {@link DAOException}
	 */
	List<VehicleType> findVehicleType() throws DAOException;
}
