package by.epamtc.kalimylin.controller.util;

/**
 * Contains all page path in use by controller layer
 */
public final class PagePath {
 
	public static final String ABOUT_PAGE = "/WEB-INF/jsp/info/aboutPage.jsp";
	public static final String ALL_DRIVERS = 
			"/WEB-INF/jsp/workPage/logist/allDrivers.jsp";
	public static final String ALL_ORDERS = 
			"/WEB-INF/jsp/workPage/logist/allOrders.jsp";
	public static final String ALL_SHIPPERS =
			"/WEB-INF/jsp/workPage/logist/allShippers.jsp";
	public static final String ALL_TRIP = 
			"/WEB-INF/jsp/workPage/logist/allTrips.jsp";
	public static final String ALL_USERS = 
			"/WEB-INF/jsp/workPage/admin/allUsers.jsp";
	public static final String CONTACT_PAGE = 
			"/WEB-INF/jsp/info/contactPage.jsp";
	public static final String CREATE_REQUIREMENT = 
			"/WEB-INF/jsp/workPage/mechanic/createRequirement.jsp";
	public static final String CREATE_VEHICLE = 
			"/WEB-INF/jsp/workPage/mechanic/createVehicle.jsp";
	public static final String CREATE_VEHICLE_TYPE = 
			"/WEB-INF/jsp/workPage/mechanic/createVehicleType.jsp";
	public static final String DRIVER_VEHICLE = 
			"/WEB-INF/jsp/workPage/logist/driverVehicle.jsp";
	public static final String ERROR_PAGE = "/WEB-INF/jsp/error/500.jsp";
	public static final String FINISH_TRIP_PAGE = 
			"/WEB-INF/jsp/workPage/driver/finishTripPage.jsp";
	public static final String GO_TO_ALL_ORDERS = 
			"controller?command=ALL_ORDERS&message=";
	public static final String GO_TO_ALL_USERS = 
			"controller?command=ALL_USERS&message=";
	public static final String GO_TO_LOG_IN_PAGE = 
			"controller?command=LOG_IN_PAGE&message=";
	public static final String GO_TO_MOTORPOOL = 
			"controller?command=MOTORPOOL&message=";
	public static final String GO_TO_NEW_TRIP = 
			"controller?command=NEW_TRIP&message=";
	public static final String GO_TO_NEW_VEHICLE = 
			"controller?command=NEW_VEHICLE&message=";
	public static final String GO_TO_UPDATE_USER_DATA = 
			"controller?command=UPDATE_USER_DATA_PAGE&message=";
	public static final String GO_TO_WELCOME_USER = 
			"controller?command=WELCOME_USER&message=";
	public static final String GO_TO_WORKSPACE = 
			"controller?command=WORKSPACE&message=";
	public static final String INDEX = "index.jsp";
	public static final String LOG_IN_PAGE = "/WEB-INF/jsp/logInPage.jsp";
	public static final String MOTORPOOL = 
			"/WEB-INF/jsp/workPage/mechanic/motorpool.jsp";
	public static final String NEW_ORDER = 
			"/WEB-INF/jsp/workPage/logist/newOrder.jsp";
	public static final String NEW_SHIPPER = 
			"/WEB-INF/jsp/workPage/logist/newShipper.jsp";
	public static final String NEW_TRIP = 
			"/WEB-INF/jsp/workPage/logist/newTrip.jsp";
	public static final String REGISTRATION_PAGE = 
			"/WEB-INF/jsp/registrationPage.jsp";
	public static final String TRIP = "/WEB-INF/jsp/workPage/logist/trip.jsp";
	public static final String TRIP_PAGE = 
			"/WEB-INF/jsp/workPage/driver/tripPage.jsp";
	public static final String UPDATE_USER_DATA = 
			"/WEB-INF/jsp/updateUserDataPage.jsp";
	public static final String UPDATE_VEHICLE = 
			"/WEB-INF/jsp/workPage/mechanic/updateVehicle.jsp";
	public static final String USER_DATA = 
			"/WEB-INF/jsp/workPage/admin/userData.jsp";
	public static final String WELCOME_PAGE = "/WEB-INF/jsp/welcomePage.jsp";
	public static final String WORK_PAGE_ADMIN = 
			"/WEB-INF/jsp/workPage/admin/workPage.jsp";
	public static final String WORK_PAGE_DRIVER = 
			"/WEB-INF/jsp/workPage/driver/workPage.jsp";
	public static final String WORK_PAGE_GUEST = 
			"/WEB-INF/jsp/workPage/guest.jsp";
	public static final String WORK_PAGE_LOGIST = 
			"/WEB-INF/jsp/workPage/logist/workPage.jsp";
	public static final String WORK_PAGE_MECHANIC = 
			"/WEB-INF/jsp/workPage/mechanic/workPage.jsp";
	
	private PagePath() {
	}
}
