package by.epamtc.kalimylin.service;

import by.epamtc.kalimylin.service.impl.OrderServiceImpl;
import by.epamtc.kalimylin.service.impl.RequirementServiceImpl;
import by.epamtc.kalimylin.service.impl.ShipperServiceImpl;
import by.epamtc.kalimylin.service.impl.TripServiceImpl;
import by.epamtc.kalimylin.service.impl.UserServiceImpl;
import by.epamtc.kalimylin.service.impl.VehicleServiceImpl;

/**
 * A factory that offers to get links to an object, 
 * whose class implements the required Service interface.
 */
public class ServiceProvider {

	private static final ServiceProvider instance = new ServiceProvider();

	private final UserService userService = new UserServiceImpl();
	private final OrderService orderService = new OrderServiceImpl();
	private final RequirementService requirementService = 
			new RequirementServiceImpl();
	private final ShipperService shipperService = new ShipperServiceImpl();
	private final TripService tripService = new TripServiceImpl();
	private final VehicleService vehicleService = new VehicleServiceImpl();
	 
	private ServiceProvider() {}
	
	/**
	 * @return {@link ServiceProvider} class instance
	 */
	public static ServiceProvider getInstance() {
	    return instance;
	}
	
	/**
	 * @return {@link UserService} reference to an object, 
	 * whose class implements the UserService interface
	 */
	public UserService getUserService() {		
	    return userService;
	}
	
	/**
	 * @return {@link OrderService} reference to an object, 
	 * whose class implements the OrderService interface
	 */
	public OrderService getOrderService() {
		return orderService;
	}
	
	/**
	 * @return {@link RequirementService} reference to an object, 
	 * whose class implements the RequirementService interface
	 */
	public RequirementService getRequirementService() {
		return requirementService;
	}
	
	/**
	 * @return {@link ShipperService} reference to an object, 
	 * whose class implements the ShipperService interface
	 */
	public ShipperService getShipperService() {
		return shipperService;
	}
	
	/**
	 * @return {@link TripService} reference to an object, 
	 * whose class implements the TripService interface
	 */
	public TripService getTripService() {
		return tripService;
	}
	
	/**
	 * @return {@link VehicleService} reference to an object, 
	 * whose class implements the VehicleService interface
	 */
	public VehicleService getVehicleService() {
		return vehicleService;
	}
}
