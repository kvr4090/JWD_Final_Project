package by.epamtc.kalimylin.service;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.RequirementDAO;
import by.epamtc.kalimylin.service.exception.ServiceException;

/**
 * RequirementService interface, contains interfaces for working with 
 * Requirements
 */
public interface RequirementService {
	
	/**
	 * Add new {@link Requirement}. 
	 * {@link RequirementDAO#addRequirement(Requirement)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param requirement  requirement for add
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	boolean addRequirement(Requirement requirement) throws ServiceException;
	
	/**
	 * Return {@link List} of objects {@link Requirement}
	 * {@link RequirementDAO#findRequirement()}
	 * 
	 * @return {@link List} of {@link Requirement}
	 * @throws ServiceException {@link ServiceException}
	 */
	List<Requirement> findAllRequirement() throws ServiceException;
	
	/**
	 * Return {@link String} of describe {@link VehicleType}
	 * {@link RequirementDAO#findRequirementById(Requirement)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param type  vehicle type for describe
	 * @return {@link String} with describe
	 * @throws ServiceException {@link ServiceException}
	 */
	String formTypeById(VehicleType type) throws ServiceException;
	
	/**
	 * Return {@link Requirement}
	 * {@link RequirementDAO#findRequirementById(Requirement)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param requirement  requirement, which contains id for search
	 * @return {@link Requirement}
	 * @throws ServiceException {@link ServiceException}
	 */
	Requirement findRequirementById(Requirement requirement) 
			throws ServiceException;
	
	/**
	 * Return {@link Requirement}
	 * {@link RequirementDAO#findRequirementByVehicle(Vehicle)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param vehicle  vehicle, which contains id for search
	 * @return {@link Requirement}
	 * @throws ServiceException {@link ServiceException}
	 */
	Requirement findRequirementByVehicle(Vehicle vehicle) 
			throws ServiceException;

}
