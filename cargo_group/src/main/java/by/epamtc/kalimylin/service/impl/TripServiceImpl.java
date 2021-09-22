package by.epamtc.kalimylin.service.impl;

import static by.epamtc.kalimylin.service.util.validation.InputDataValidator.*;
import static by.epamtc.kalimylin.service.util.MessageService.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.dao.DAOProvider;
import by.epamtc.kalimylin.dao.TripDAO;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.OrderService;
import by.epamtc.kalimylin.service.RequirementService;
import by.epamtc.kalimylin.service.ServiceProvider;
import by.epamtc.kalimylin.service.TripService;
import by.epamtc.kalimylin.service.UserService;
import by.epamtc.kalimylin.service.VehicleService;
import by.epamtc.kalimylin.service.exception.InvalidInputDataServiceException;
import by.epamtc.kalimylin.service.exception.ServiceException;
import by.epamtc.kalimylin.service.util.validation.TechValidator;

/**
 * TripService interface, contains interfaces for working with Trip
 */
public class TripServiceImpl implements TripService {

	@Override
	public boolean addTrip(Trip trip) throws ServiceException {
		
		checkTrip(trip);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO = daoProvider.getTripDAO();
		
		try {	
			return tripDAO.addTrip(trip);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Trip> findAvailableTrip() throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO = daoProvider.getTripDAO();
		
		try {	
			return tripDAO.findAvailableTrip();
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Update trip data.
	 * {@link TripDAO#updateTrip(Trip)}
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip trip for update
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean updateTrip(Trip trip) throws ServiceException {
		
		checkTrip(trip);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO = daoProvider.getTripDAO();
		
		try {	
			return tripDAO.updateTrip(trip);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteTrip(Trip trip) throws ServiceException { 		
		
		checkId(trip.getId());
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		OrderService orderService = serviceProvider.getOrderService();
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO= daoProvider.getTripDAO();

		boolean resultUpdateOrder = false;
		boolean resultUpdateWorkList = false;
		
		checkIsStartTrip(trip);
		
		try {
			
			resultUpdateWorkList = tripDAO.updateWorkListBeforeDeleteTrip(trip);
			resultUpdateOrder = orderService.updateOrdersBeforeDeleteTrip(trip);
			
			if (!resultUpdateOrder && !resultUpdateWorkList) {		
				throw new ServiceException(FAILED_DELETE);
			}
			return tripDAO.deleteTrip(trip);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}	
	}
	
	/**
	 * Checks trip status. If trip already start, throws 
	 * {@link ServiceException} with appropriate message, that trip was start.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param trip trip for check
	 * @throws ServiceException {@link ServiceException}
	 */
	private void checkIsStartTrip(Trip trip) throws ServiceException {
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		VehicleService vehicleService = serviceProvider.getVehicleService();
		
		Vehicle vehicle = new Vehicle();
		vehicle.setId(trip.getVehicleId());
		vehicle = vehicleService.findVehicleById(vehicle);
		
		if (vehicle.getNote().contains(VEHICLE_IN_TRIP)) {
			throw new ServiceException(TRIP_STARTED);
		}
		
	}
	
	/**
	 * Returns a list of {@link Requirement} for each order from the input list.
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param listOrders list with orders
	 * @return {@link List} of {@link Requirement}
	 * @throws ServiceException {@link ServiceException}
	 */	
	private List<Requirement> selectRequirementsFromOrders(
			List<Order> listOrders) throws ServiceException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		RequirementService requirementService = 
				provider.getRequirementService();

		List<Requirement> list = new ArrayList<Requirement>();
		Requirement tempRequirement = new Requirement();
		
		for (Order order : listOrders) {
			tempRequirement.setId( order.getRequirementId());
			tempRequirement = 
					requirementService.findRequirementById(tempRequirement);
			list.add(tempRequirement);
		}
		return list;
	}
	
	/**
	 * Returns {@link Requirement} which contains total sum parameters 
	 * from the input list. Parameters for sum: weight, volume, pallet quantity
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param listRequirementOrders list with requirement for sum
	 * @return {@link Requirement}
	 */
	private Requirement summParameteres(
			List<Requirement> listRequirementOrders) {
		
		Requirement resultRequirement = new Requirement();
		
		float weight = 0;
		float volume = 0;
		int palletsQuantity = 0;

		for(Requirement temp : listRequirementOrders) {
			weight += temp.getWeight();
			volume += temp.getVolume();
			palletsQuantity += temp.getPalletsQuantity();
		}
		
		resultRequirement.setWeight(weight);
		resultRequirement.setVolume(volume);
		resultRequirement.setPalletsQuantity(palletsQuantity);
		
		return resultRequirement;
	}
	
	@Override
	public Trip findCurrentTrip(User user) throws ServiceException {
		
		checkId(user.getId());
		
		List<Trip> list = findTripByUser(user);

		if (list == null || list.isEmpty()) {
			return null;
		}
		
		Collections.sort(list, new Comparator<Trip>() {
			  public int compare(Trip o1, Trip o2) {
			      return o1.getStartDate().compareTo(o2.getStartDate());
			  }
			});
		
		return list.get(0);
	}
	
	/**
	 * Find trip by user id
	 * {@link TripDAO#findTripByUser(User)}
	 * 
	 * @param user  user, which contains id for search
	 * @return {@link List} of {@link Trip}
	 * @throws ServiceException {@link ServiceException}
	 */
	private List<Trip> findTripByUser(User user) throws ServiceException {
		
		checkUser(user);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO = daoProvider.getTripDAO();
		
		try {						
			return tripDAO.findTripByUser(user);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean finishTrip(Trip trip) throws ServiceException {
		
		trip.setFinishDate(LocalDate.now());
		checkTrip(trip);
		return updateTrip(trip);
		}

	@Override
	public boolean processingTrip(Trip trip, List<Integer> mixedListId) 
			throws ServiceException { 
		
		checkTrip(trip);
		checkIsCorrectListId(mixedListId);

		trip = updateDocuments(trip, mixedListId);
		
		return updateExecutors(trip, mixedListId);
	}
	
	/**
	 * Add trip and update orders data from this trip.
	 * Checks is available form trip uses current vehicle and current total sum
	 * requirement's parameters
	 * Checks if free date to start trip. if the date uses another trip, throws
	 * {@link ServiceException} with appropriate message, that date already 
	 * in use.
	 * If failed to add trip, or update orders, throws {@link ServiceException}
	 * with appropriate message, that failed add trip.
	 * 
	 * @param trip  trip, for add
	 * @param mixedListId  list, contains id for processing
	 * @return {@link Trip} which added
	 * @throws ServiceException {@link ServiceException}
	 */
	private Trip updateDocuments(Trip trip, List<Integer> mixedListId) 
			throws ServiceException {
		
		boolean isAddTrip;
		boolean isUpdateOrders;
		
		trip = AddVehicleIdByDriverId(trip, mixedListId.get(1));
		
		checkFormTrip(trip, mixedListId);			
		isFreeDateForTrip(trip);
		
		isAddTrip = addTrip(trip); 			
		trip = findNewTrip(trip);
		
		isUpdateOrders = updateListOrders(formListOrderById(mixedListId), trip);
		
		if (!isUpdateOrders && !isAddTrip) {
			deleteTrip(trip);
			throw new ServiceException(FAILED_ADD);
		}
		return trip;
	}
	
	/**
	 * Updates trip's creator and executor
	 * {@link TripDAO#updateWorkListForUser(User, Trip)} for each
	 * 
	 * @param trip  trip, which id add to table
	 * @param mixedListId  contains logist's id at first position, driver's id
	 * at second position.
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */	
	private boolean updateExecutors(Trip trip, List<Integer> mixedListId) 
			throws ServiceException {
		
		User logist = new User();
		User driver = new User();
	
		logist.setId(mixedListId.get(0));
		driver.setId(mixedListId.get(1));
		
		boolean setTripForDriver = setTripForUser(driver, trip);
		boolean setTripForLogist = setTripForUser(logist, trip);
		
		return setTripForDriver && setTripForLogist;
	}
	
	/**
	 * Add data about trip to user's work table.
	 * {@link TripDAO#updateWorkListForUser(User, Trip)}
	 * 
	 * @param user  user, which form(logist), or execute(driver) trip
	 * @param trip  trip, which id add to table
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean setTripForUser(User user, Trip trip) 
			throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO = daoProvider.getTripDAO();
		
		try {
			return tripDAO.updateWorkListForUser(user, trip);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * Update order data.
	 * {@link OrderService#updateListOrdersTripId(List, Trip)}
	 *   
	 * @param listOrders  list orders to update
	 * @param trip  selected trip
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean updateListOrders(List<Order> listOrders, Trip trip) 
			throws ServiceException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		OrderService orderService = provider.getOrderService();
		
		return orderService.updateListOrdersTripId(listOrders, trip);
	}
	
	/**
	 * Checks if free date to start trip. if the date uses another trip, throws
	 * {@link ServiceException} with appropriate message, that date already 
	 * in use.
	 * 
	 * @param trip  trip for add
	 * @throws ServiceException {@link ServiceException}
	 */
	private void isFreeDateForTrip(Trip trip) throws ServiceException {
				
		if (findNewTrip(trip) != null) {
			throw new ServiceException(DATE_ALREADY_IN_USE);
		}	
	}
	
	/**
	 * Finds trip by vehicle id and start date.
	 * {@link TripDAO#findTripByParams(Trip)}
	 *  
	 * @param trip  trip, which contains data for search
	 * @return {@link Trip}
	 * @throws ServiceException {@link ServiceException}
	 */
	private Trip findNewTrip(Trip trip) throws ServiceException {
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		TripDAO tripDAO = daoProvider.getTripDAO();
		
		try {
			return tripDAO.findTripByParams(trip);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Forms {@link List} of {@link Order}, uses list of order's id 
	 * 
	 * @param mixedListId  list contains orders id since third position
	 * @return {@link List} of {@link Order}
	 * @throws ServiceException {@link ServiceException}
	 */
	private List<Order> formListOrderById(List<Integer> mixedListId) 
			throws ServiceException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		OrderService orderService = provider.getOrderService();
		List<Order> listOrders = new ArrayList<>();
		
		for (int i = 2; i < mixedListId.size(); i++) {
			Order tempOrder = new Order();
			tempOrder.setId(mixedListId.get(i));
			listOrders.add(tempOrder);
		}
		return orderService.findOrdersById(listOrders);
	}
	
	/**
	 * Update trip's parameter vehicle id, uses driver's id of this trip.
	 * 
	 * @param trip  trip for update
	 * @param driverId  driver's id
	 * @return {@link Trip} with updated parameter vehicle id
	 * @throws ServiceException {@link ServiceException}
	 */
	private Trip AddVehicleIdByDriverId(Trip trip, int driverId) 
			throws ServiceException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService service = provider.getUserService();
		
		User driver = new User();
		driver.setId(driverId);
		driver = service.findUserById(driver);
		int vehicleId = driver.getVehicleId();	
		trip.setVehicleId(vehicleId);
		
		return trip;
	}
	
	/**
	 * Finds parameter of vehicleType input vehicle and compare total sum 
	 * parameters all orders, which are part of the trip like weight, volume, 
	 * pallet quantity with parameters of vehicle type. If total sum more than 
	 * parameters vehicle type, throws {@link ServiceException} with message, 
	 * that trip is overload. 
	 * 
	 * @param trip  trip for checks
	 * @param mixedListId  list contains id for processing 
	 * @throws ServiceException {@link ServiceException}
	 */
	private void checkFormTrip(Trip trip, List<Integer> mixedListId) 
			throws ServiceException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		VehicleService service = provider.getVehicleService();
		
		Vehicle wagon = service.findWagonTruck(trip);
		List<Order> listOrders = formListOrderById(mixedListId);
		
		 boolean isForm = isFormTrip(wagon, listOrders);
		 
		 if (!isForm) {
			 throw new ServiceException(OVERLOAD_TRIP);
		}
	}
	
	/**
	 * Compare total sum parameters all orders from input list, like weight, 
	 * volume, pallet quantity with parameters of vehicle type
	 * 
	 * @param vehicle  vehicle for compare with total sum parameters
	 * @param listOrders  list orders to sum and compare
	 * @return boolean result of processing
	 * @throws ServiceException {@link ServiceException}
	 */
	private boolean isFormTrip(Vehicle vehicle, List<Order> listOrders) 
			throws ServiceException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		RequirementService requirementService = 
				provider.getRequirementService();		
		Requirement totalRequirement =
				summParameteres(selectRequirementsFromOrders(listOrders));
		Requirement vehicleRequirement = 
				requirementService.findRequirementByVehicle(vehicle);
	
		return compareRequirement(vehicleRequirement, totalRequirement); 	
	}
	
	/**
	 * Compare requirements
	 * 
	 * @param vehicleReq  requirement for compare
	 * @param totalReq  requirement for compare
	 * @return boolean result of processing
	 */
	private boolean compareRequirement(Requirement vehicleReq, 
			Requirement totalReq) {
		
		boolean compareWeight = vehicleReq.getWeight() >= totalReq.getWeight();
		boolean compareVolume = vehicleReq.getVolume() >= totalReq.getVolume();
		boolean comparePalletsQuantity = (vehicleReq.getPalletsQuantity()) 
				>= (totalReq.getPalletsQuantity());
		
		return compareWeight && compareVolume && comparePalletsQuantity;
	}
	
	/**
	 * Checks is correct id in list of id	
	 * When incorrect values in the input data, throws exception.
	 * 
	 * @param listId  list of id to check
	 * @throws InvalidInputDataServiceException 
	 * {@link InvalidInputDataServiceException}
	 */
	private void checkIsCorrectListId(List<Integer> listId) 
			throws ServiceException {
		
		for (Integer id : listId) {
			if (!TechValidator.isCorrectId(id)) {
				throw new InvalidInputDataServiceException(INCORRECT_DATA);
			}
		}
	}
}
