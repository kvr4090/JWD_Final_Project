package by.epamtc.kalimylin.service.impl;

import static by.epamtc.kalimylin.service.util.validation.InputDataValidator.*;
import static by.epamtc.kalimylin.service.util.MessageService.*;

import java.util.List;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.DAOProvider;
import by.epamtc.kalimylin.dao.RequirementDAO;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.RequirementService;
import by.epamtc.kalimylin.service.exception.ServiceException;

public class RequirementServiceImpl implements RequirementService {
		
	@Override
	public boolean addRequirement(Requirement requirement) 
			throws ServiceException {

		checkRequirement(requirement);
		
		DAOProvider provider = DAOProvider.getInstance();	
		RequirementDAO requirementDAO = provider.getRequirementDAO();
				
		try {	
			return requirementDAO.addRequirement(requirement);
		
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Requirement> findAllRequirement() throws ServiceException {
		
		DAOProvider provider = DAOProvider.getInstance();	
		RequirementDAO requirementDAO = provider.getRequirementDAO();
		
		try {		
			return requirementDAO.findRequirement();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public String formTypeById(VehicleType type) throws ServiceException {
		
		checkId(type.getRequirementId());
		
		DAOProvider provider = DAOProvider.getInstance();	
		RequirementDAO requirementDAO = provider.getRequirementDAO();
		
		try {
			Requirement tempRequirement = new Requirement();
			tempRequirement.setId(type.getRequirementId());
			tempRequirement = 
					requirementDAO.findRequirementById(tempRequirement);
			
			return formType(tempRequirement);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * From describe requirement type
	 * 
	 * @param requirement  requirement for describe
	 * @return {@link String} with requirement's describe
	 */
	private String formType(Requirement requirement) {
		
		StringBuilder type = new StringBuilder()
			.append(requirement.getWeight())
			.append(SEPARATOR_WEIGHT)
			.append(requirement.getVolume())
			.append(SEPARATOR_VOLUME)
			.append(requirement.getPalletsQuantity())
			.append(SEPARATOR_PALLETS)
			.append(requirement.getMaxLength())
			.append(SEPARATOR_LENGTH)
			.append(requirement.getMaxWidth())
			.append(SEPARATOR_WIDTH)
			.append(requirement.getMaxHeight())
			.append(SEPARATOR_HEIGHT);	
		return type.toString();
	}

	@Override
	public Requirement findRequirementById(Requirement requirement) 
			throws ServiceException {
		
		checkId(requirement.getId());
		
		DAOProvider provider = DAOProvider.getInstance();	
		RequirementDAO requirementDAO = provider.getRequirementDAO();
		
		try {
			return requirementDAO.findRequirementById(requirement);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Requirement findRequirementByVehicle(Vehicle vehicle) 
			throws ServiceException {
		
		checkId(vehicle.getTypeId());
		
		DAOProvider provider = DAOProvider.getInstance();	
		RequirementDAO requirementDAO = provider.getRequirementDAO();
		
		try {
			return requirementDAO.findRequirementByVehicle(vehicle);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
