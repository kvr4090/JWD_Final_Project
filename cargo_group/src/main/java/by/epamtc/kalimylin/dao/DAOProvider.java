package by.epamtc.kalimylin.dao;

import by.epamtc.kalimylin.dao.impl.OrderDAOimpl;
import by.epamtc.kalimylin.dao.impl.RequirementDAOimpl;
import by.epamtc.kalimylin.dao.impl.ShipperDAOimpl;
import by.epamtc.kalimylin.dao.impl.TripDAOimpl;
import by.epamtc.kalimylin.dao.impl.UserDAOimpl;
import by.epamtc.kalimylin.dao.impl.VehicleDAOimpl;

/**
 * A factory that offers to get links to an object, 
 * whose class implements the required DAO interface.
 */
public class DAOProvider {
	
	private static final DAOProvider instance = new DAOProvider();
	
	private final OrderDAO orderDAO = new OrderDAOimpl();
	private final RequirementDAO requirementDAO = new RequirementDAOimpl();
	private final ShipperDAO shipperDAO = new ShipperDAOimpl();
	private final TripDAO tripDAO = new TripDAOimpl();
	private final UserDAO userDAO = new UserDAOimpl();
	private final VehicleDAO vehicleDAO = new VehicleDAOimpl();
	
	private DAOProvider() {}
	
	/**
	 * @return {@link DAOProvider} class instance
	 */
	public static DAOProvider getInstance() {
		return instance;
	}
	
	/**
	 * @return {@link OrderDAO} reference to an object, whose class implements 
	 * the OrderDAO interface
	 */
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
	
	/**
	 * @return {@link RequirementDAO} reference to an object, 
	 * whose class implements the RequirementDAO interface
	 */
	public RequirementDAO getRequirementDAO() {
		return requirementDAO;
	}
	
	/**
	 * @return {@link ShipperDAO} reference to an object, whose class implements 
	 * the ShipperDAO interface
	 */
	public ShipperDAO getShipperDAO() {
		return shipperDAO;
	}
	
	/**
	 * @return {@link TripDAO} reference to an object, whose class implements 
	 * the TripDAO interface
	 */
	public TripDAO getTripDAO() {
		return tripDAO;
	}
	
	/**
	 * @return {@link UserDAO} reference to an object, whose class implements 
	 * the UserDAO interface
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	/**
	 * @return {@link VehicleDAO} reference to an object, whose class implements 
	 * the VehicleDAO interface
	 */
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}
	
}