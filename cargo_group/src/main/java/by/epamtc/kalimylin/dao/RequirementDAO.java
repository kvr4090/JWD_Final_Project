package by.epamtc.kalimylin.dao;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.dao.exception.DAOException;

/**
 * RequirementDAO interface, contains interfaces for working with 
 * Requirements, CRUD
 */
public interface RequirementDAO {
	
	/**
	 * Enroll new {@link Requirement} in data base. 
	 * If it was not possible to enroll a new requirement, throws exception.
	 * 
	 * @param requirement  requirement for add
	 * @return boolean result of processing
	 * @throws DAOException {@link DAOException}
	 */
	boolean addRequirement(Requirement requirement) throws DAOException;
	
	/**
	 * Execute the SQL statement and return {@link List} of objects 
	 * {@link Requirement}, return empty list, if there are no records 
	 * in the database.
	 * Never return <code>null</code>
	 * 
	 * @return {@link List} of {@link Requirement}
	 * @throws DAOException {@link DAOException}
	 */
	List<Requirement> findRequirement() throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Requirement}
	 * 
	 * @param requirement  requirement, which contains id for search
	 * @return {@link Requirement}, if requirement's id not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	Requirement findRequirementById(Requirement requirement) 
			throws DAOException;
	
	/**
	 * Execute the SQL statement and return object {@link Requirement}
	 * 
	 * @param vehicle  vehicle, which contains id for search
	 * @return {@link Requirement}, if vehicle's type id not exists,
	 * return <code>null</code>
	 * @throws DAOException {@link DAOException}
	 */
	Requirement findRequirementByVehicle(Vehicle vehicle) throws DAOException;	
}