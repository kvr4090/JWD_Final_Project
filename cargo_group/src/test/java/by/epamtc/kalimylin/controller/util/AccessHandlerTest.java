package by.epamtc.kalimylin.controller.util;

import static by.epamtc.kalimylin.controller.command.ParameterName.*;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.controller.command.ParameterName;
import by.epamtc.kalimylin.controller.util.helper.AccessHelper;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;

public class AccessHandlerTest {
	
	User defaultUser;
	User guest;
	User driver;
	User mechanic;
	User logist;
	User admin;

	@BeforeClass
	public void beforeClass() {
		defaultUser = null;
		guest = new User();
		guest.setRole(Role.GUEST);
		driver = new User();
		driver.setRole(Role.DRIVER);
		mechanic = new User();
		mechanic.setRole(Role.MECHANIC);
		logist = new User();
		logist.setRole(Role.LOGIST);
		admin = new User();
		admin.setRole(Role.ADMIN);
	}

	@AfterClass
	public void afterClass() {
		guest = null;
		driver = null;
		mechanic = null;
		logist = null;
		admin = null;
	}

	@Test(dataProvider = "defaultCommand")
	public void defaultCheckAccessTest(ParameterName command, boolean expectedResult) {
		boolean result = AccessHelper.checkAccess(defaultUser, command);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
	public Object[][] defaultCommand() {
		return new Object[][] {
				{ ABOUT_PAGE, true },
				{ UPDATE_USER, false },
				{ ADD_SHIPPER, false },
				{ TRIP_PAGE, false },
				{ NEW_VEHICLE, false },
				{ UPDATE_USER_DATA_PAGE, false },
		};
	}
	
	@Test(dataProvider = "guestCommand")
	public void guestCheckAccessTest(ParameterName command, boolean expectedResult) {
		boolean result = AccessHelper.checkAccess(guest, command);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
	public Object[][] guestCommand() {
		return new Object[][] {
				{ ABOUT_PAGE, true },
				{ UPDATE_USER, false },
				{ ADD_SHIPPER, false },
				{ TRIP_PAGE, false },
				{ NEW_VEHICLE, false },
				{ UPDATE_USER_DATA_PAGE, true },
		};
	}
	
	@Test(dataProvider = "driverCommand")
	public void driverCheckAccessTest(ParameterName command, boolean expectedResult) {
		boolean result = AccessHelper.checkAccess(driver, command);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
	public Object[][] driverCommand() {
		return new Object[][] {
				{ ABOUT_PAGE, true },
				{ UPDATE_USER, false },
				{ ADD_SHIPPER, false },
				{ TRIP_PAGE, true },
				{ NEW_VEHICLE, false },
				{ UPDATE_USER_DATA_PAGE, true },
		};
	}
	
	@Test(dataProvider = "mechanicCommand")
	public void mechanicCheckAccessTest(ParameterName command, boolean expectedResult) {
		boolean result = AccessHelper.checkAccess(mechanic, command);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
	public Object[][] mechanicCommand() {
		return new Object[][] {
				{ ABOUT_PAGE, true },
				{ UPDATE_USER, false },
				{ ADD_SHIPPER, false },
				{ TRIP_PAGE, false },
				{ NEW_VEHICLE, true },
				{ UPDATE_USER_DATA_PAGE, true },
		};
	}
	
	@Test(dataProvider = "logistCommand")
	public void logistCheckAccessTest(ParameterName command, boolean expectedResult) {
		boolean result = AccessHelper.checkAccess(logist, command);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
	public Object[][] logistCommand() {
		return new Object[][] {
				{ ABOUT_PAGE, true },
				{ UPDATE_USER, false },
				{ ADD_SHIPPER, true },
				{ TRIP_PAGE, false },
				{ NEW_VEHICLE, false },
				{ UPDATE_USER_DATA_PAGE, true },
		};
	}
	
	@Test(dataProvider = "adminCommand")
	public void adminCheckAccessTest(ParameterName command, boolean expectedResult) {
		boolean result = AccessHelper.checkAccess(admin, command);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
	public Object[][] adminCommand() {
		return new Object[][] {
				{ ABOUT_PAGE, true },
				{ UPDATE_USER, true },
				{ ADD_SHIPPER, false },
				{ TRIP_PAGE, false },
				{ NEW_VEHICLE, false },
				{ UPDATE_USER_DATA_PAGE, true },
		};
	}
}
