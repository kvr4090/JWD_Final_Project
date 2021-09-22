package by.epamtc.kalimylin.service.impl;

import static by.epamtc.kalimylin.service.util.MessageService.*;
import static by.epamtc.kalimylin.service.util.validation.InputDataValidator.*;
import static by.epamtc.kalimylin.service.util.validation.VariableValidator.*;

import java.util.ArrayList;
import java.util.List;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.DAOProvider;
import by.epamtc.kalimylin.dao.VehicleDAO;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.RequirementService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.InvalidInputDataServiceException;
import by.epamtc.kalimylin.service.exception.ServiceException;
import by.epamtc.kalimylin.service.util.validation.BeanValidator;

/**
 * VehicleService interface, contains interfaces for working with Vehicle
 */
public class VehicleServiceImpl implements VehicleService {
	
	@Override
	public boolean addVehicle(Vehicle vehicle) throws ServiceException {
		
		checkVehicle(vehicle);
		checkSimilarVehicle(vehicle);
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		try {		
			return vehicleDAO.addVehicle(vehicle);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Vehicle findVehicleByLicensePlate(Vehicle vehicle) 
			throws ServiceException {
		
		boolean checkPlate = isCorrectLicensePlate(vehicle.getLicensePlate());
		
		if (!checkPlate) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		try {		
			return vehicleDAO.findVehicleByLicensePlate(vehicle);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Vehicle findVehicleById(Vehicle vehicle) throws ServiceException {
		
		checkId(vehicle.getId());
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		try {		
			return vehicleDAO.findVehicleById(vehicle);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Vehicle> findAllVehicle() throws ServiceException {

		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		try {		
			return vehicleDAO.findAllVehicle();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	
	@Override
	public boolean updateVehicle(Vehicle vehicle) throws ServiceException {
		
		checkVehicle(vehicle);
		checkSimilarVehicle(vehicle);
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		boolean isUpdate = false;
	
		try {				
			isUpdate = updateSecondaryVehicleInRoadTrain(vehicle);
			
			if (isUpdate) {
				isUpdate = vehicleDAO.updateVehicle(vehicle);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}	
		return isUpdate;
	}
	/**
	 * Checks for similar license plate, if its find, 
	 * throws {@link ServiceException} with appropriate message, that this 
	 * license plate already in use. 
	 * 
	 * @param vehicle  original vehicle
	 * @throws ServiceException {@link ServiceException}
	 */
	private void checkSimilarVehicle(Vehicle vehicle) throws ServiceException {
		
		Vehicle currentVehicle = findVehicleById(vehicle);
		Vehicle similarVehicle = findVehicleByLicensePlate(vehicle);
		boolean condition;
		
		if (similarVehicle != null) {
			condition = (currentVehicle == null) 
					|| (currentVehicle.getId() != similarVehicle.getId());
			
			if (condition) {
				throw new ServiceException(LICENSE_PLATE_ALREADY_IN_USE);
			}
		} 
	}
	
	/**
	 * Update secondary vehicle trailer's id in road train
	 * Four cases:
	 * when update id to same id. No updates, return true (0 to 0 or 1 to 1)
	 * when update id to 0. Updates secondary vehicle trailer id to 0 (1 to 0)
	 * when update id from 0. Updates by input value id, so updates new 
	 * secondary vehicle (0 to 1)
	 * when update id from old value to new value. Updates old secondary vehicle
	 * updates new secondary vehicle. (1 to 2)
	 * 
	 * @param vehicle  primary vehicle
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 * @throws DAOException {@link DAOException}
	 */
	private boolean updateSecondaryVehicleInRoadTrain(Vehicle vehicle) 
			throws ServiceException, DAOException {

		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		boolean resultPrimary = false;
		boolean resultNextSecondary = true;
		boolean resultSecondary = false;
		
		Vehicle primaryVehicle = findVehicleById(vehicle);
		Vehicle secondaryVehicle = new Vehicle();
		secondaryVehicle.setId(primaryVehicle.getTrailerId());
		
		if (primaryVehicle.getTrailerId() != 0) {
			secondaryVehicle = findVehicleById(secondaryVehicle);
		}

		boolean condition = 
				primaryVehicle.getTrailerId() == vehicle.getTrailerId();
		
		if ((condition && primaryVehicle.getTrailerId() == 0) || condition) {
			return true; //	0->0 or 1->1
		}
		
		if (vehicle.getTrailerId() == 0) {
			secondaryVehicle.setTrailerId(0); // 1->0														
		} else {
			if (primaryVehicle.getTrailerId() == 0) { // 0->1
				secondaryVehicle.setId(vehicle.getTrailerId());
				secondaryVehicle = findVehicleById(secondaryVehicle);
				secondaryVehicle.setTrailerId(vehicle.getId());														
			} else {
				secondaryVehicle.setTrailerId(0); // 1->2
				Vehicle nextSecondaryVehicle = new Vehicle();
				nextSecondaryVehicle.setId(vehicle.getTrailerId());
				nextSecondaryVehicle = findVehicleById(nextSecondaryVehicle);	
				nextSecondaryVehicle.setTrailerId(vehicle.getId());
				resultNextSecondary = 
						vehicleDAO.updateVehicle(nextSecondaryVehicle);																
			}		
		}
		resultPrimary = vehicleDAO.updateVehicle(primaryVehicle);
		resultSecondary = vehicleDAO.updateVehicle(secondaryVehicle);
		
		return resultPrimary && resultSecondary && resultNextSecondary;
	}

	@Override
	public boolean addVehicleType(VehicleType vehicleType) 
			throws ServiceException {
		
		String type = replaceToLocale(vehicleType.getType());
		vehicleType.setType(type);

		boolean isCorrectVehicleType = 
				BeanValidator.isCorrectVehicleType(vehicleType);
		
		if (!isCorrectVehicleType) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		vehicleType.setType(formType(vehicleType));
		
		try {		
			return vehicleDAO.addVehicleType(vehicleType);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * Form short describe vehicle.
	 * Describe contains vehicle body and parameter values
	 * 
	 * @param vehicleType type as data source
	 * @return {@link String} with describe
	 * @throws ServiceException {@link ServiceException}
	 */
	private String formType(VehicleType vehicleType) throws ServiceException {
		
		ServiceProvider providerService = ServiceProvider.getInstance();
		RequirementService serviceRequirement = 
				providerService.getRequirementService();
		
		String requirementParam = serviceRequirement.formTypeById(vehicleType);
		String type = vehicleType.getType();

		return type + requirementParam;	
	}
	/**
	 * Replace parameters with russian locale
	 * 
	 * @param type  string for replace
	 * @return {@link String} in russian locale
	 */
	private String replaceToLocale(String type) {
		type = type.replaceAll("truck", TRUCK);
		type = type.replaceAll("trailer", TRAILER);
		type = type.replaceAll("tractor", TRACTOR);
		type = type.replaceAll("tent", TENT);
		type = type.replaceAll("ref", REFRIGERATOR);
		type = type.replaceAll("isot", ISOTHERMIC);		
		return type;		
	}
	
	@Override
	public List<VehicleType> findAllVehicleType() throws ServiceException {
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		try {		
			return vehicleDAO.findVehicleType();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean finishTrip(List<Vehicle> listVehicle, Trip trip) 
			throws ServiceException {
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		OrderService orderService = serviceProvider.getOrderService();
		boolean result = true;
		boolean temp = false;
		
		List<Order> listOrder = orderService.findOrderByTrip(trip);
		int distanseWorked = calculateOdometer(listOrder);
		
		for (Vehicle vehicle : listVehicle) {
			checkVehicle(vehicle);
			int totalOdometer = vehicle.getOdometer() + distanseWorked;
			vehicle.setOdometer(totalOdometer);
			temp = updateVehicle(vehicle);
			result = result && temp;
		}
		return result;
	}
	/**
	 * Calculates total distance, uses each order's distance from list orders.
	 * 
	 * @param list  list with orders to calculate total distance
	 * @return {@code Integer} total sum
	 */
	private int calculateOdometer(List<Order> list) {
		
		int summ = 0;
		
		for (Order order : list) {
			summ += order.getDistance();
		}
		return summ;
	}

	@Override
	public List<Vehicle> findSecondaryVehicle(String typeVehicle) 
			throws ServiceException {
		
		if(!isCorrectVehicleType(typeVehicle)) {
			throw new InvalidInputDataServiceException(INCORRECT_DATA);
		}
		
		boolean condition = typeVehicle.contains(TRAILER);
		
		if (condition) {
			return findPair(TRUCK);
		}		
		return findPair(TRAILER);
	}
	/**
	 * Return {@link List} of objects {@link Vehicle}
	 * Search for a list of vehicles suitable for a current vehicleType.
	 * For tractor search trailers, for trailer search tractors. 
	 * 
	 * @param typeVehicle  vehicle type for searching
	 * @return {@link List} of {@link Vehicle}
	 * @throws ServiceException {@link ServiceException}
	 */
	private List<Vehicle> findPair(String typeVehicle) throws ServiceException {
		
		List<Vehicle> resultListVehicle = new ArrayList<>();
		List<Vehicle> listVehicle = findAllVehicle();
		List<VehicleType> listType = findAllVehicleType();

		for (Vehicle vehicle : listVehicle) {
			
			if (!vehicle.getIsAvailable()) {
				continue;
			}
			
			for (VehicleType vehicleType : listType) {
				
				boolean temp = ((vehicle.getTypeId() == vehicleType.getId())
							&& (vehicleType.getType().contains(typeVehicle)))
							&& (vehicle.getTrailerId() == 0);
				
				if (temp) {
					resultListVehicle.add(vehicle);		
				} 
			}
		}
		return resultListVehicle;
	}

	@Override
	public List<List<VehicleType>> separateVehicleType(String vehicleType) 
			throws ServiceException {
		
		List<VehicleType> listType = findAllVehicleType();
		List<VehicleType> trailerType = new ArrayList<>();
		List<VehicleType> truckType = new ArrayList<>();
		List<List<VehicleType>> resultList = new ArrayList<>();
		
		for (VehicleType type : listType) {
			boolean isTrailer = type.getType().contains(TRAILER);
			
			if (isTrailer) {
				trailerType.add(type);
			} else {
				truckType.add(type);
			}
		}
		
		if (vehicleType.contains(TRUCK)) {
			resultList.add(truckType);
			resultList.add(trailerType);
		} else {
			resultList.add(trailerType);
			resultList.add(truckType);
		}
		
		return resultList;
	}

	@Override
	public List<String> findRoadTrainComposition(Vehicle vehicle) 
			throws ServiceException {
		
		checkVehicle(vehicle);
		
		String currentPrimaryVehicleType = null;
		String currentSecondaryVehicleType = null;
		
		List<VehicleType> listVehicleType = findAllVehicleType();
		currentPrimaryVehicleType = 
				findVehicleTypeById(vehicle.getTypeId(), listVehicleType);
		Vehicle currentSecondaryVehicle = new Vehicle();
		currentSecondaryVehicle.setId(vehicle.getTrailerId());
		currentSecondaryVehicle = findVehicleById(currentSecondaryVehicle);	
		
		if (vehicle.getTrailerId() != 0) {
			currentSecondaryVehicleType = findVehicleTypeById(
					currentSecondaryVehicle.getTypeId(), listVehicleType);
		}
		
		List<String> result = new ArrayList<>();
		result.add(currentPrimaryVehicleType);
		result.add(currentSecondaryVehicleType);
		
		return result;
	}
	/**
	 * Find type by id from input list of VehicleTypes
	 * 
	 * @param vehicleTypeId  id for search
	 * @param list  search list
	 * @return {@link String}
	 */
	private String findVehicleTypeById(int vehicleTypeId, 
			List<VehicleType> list) {
		
		String result = null;
		
		for (VehicleType vehicleType : list) {
			
			if (vehicleTypeId == vehicleType.getId()) {
				result = vehicleType.getType();
			}
		}
		return result;
	}
	

	@Override
	public Vehicle findWagonTruck(Trip trip) throws ServiceException {
		
		checkId(trip.getVehicleId());
		
		Vehicle primaryVehicle = new Vehicle();
		primaryVehicle.setId(trip.getVehicleId());
		primaryVehicle = findVehicleById(primaryVehicle);
		
		if (primaryVehicle.getTrailerId() == 0) {
			return primaryVehicle;
		}
		
		Vehicle trailer = new Vehicle();
		trailer.setId(primaryVehicle.getTrailerId());
		trailer = findVehicleById(trailer);
		
		return trailer;
	}

	@Override
	public boolean updateVehicleBeforeStartTrip(Vehicle truck) 
			throws ServiceException {
		
		Vehicle trailer;
		truck = findVehicleById(truck);
		boolean isUpdateTruck = setValuesBeforeStartTrip(truck);
		boolean isUpdateTrailer = true;
		
		if (truck.getTrailerId() != 0) {
			trailer = new Vehicle();
			trailer.setId(truck.getTrailerId());
			trailer = findVehicleById(trailer);
			isUpdateTrailer = setValuesBeforeStartTrip(trailer);
		}
		return isUpdateTruck && isUpdateTrailer;
	}
	/**
	 * Update vehicle before start trip.
	 * Set message that vehicle in trip to parameter note
	 * Set status unavailable.
	 * 
	 * @param vehicle  vehicle for update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean setValuesBeforeStartTrip(Vehicle vehicle) 
			throws ServiceException {
		
		DAOProvider provider = DAOProvider.getInstance();
		VehicleDAO vehicleDAO = provider.getVehicleDAO();
		
		vehicle.setNote(VEHICLE_IN_TRIP);
		vehicle.setIsAvailable(false);
		
		try {
			return vehicleDAO.updateVehicle(vehicle);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}	
	} 
}
