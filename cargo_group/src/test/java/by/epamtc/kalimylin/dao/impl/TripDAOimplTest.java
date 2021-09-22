package by.epamtc.kalimylin.dao.impl;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.dao.exception.DAOException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import static org.testng.Assert.*;

import java.time.LocalDate;

public class TripDAOimplTest {
	
	private static final TripDAOimpl tripDAOimpl = new TripDAOimpl();
	private Trip trip;
	private User user;
	
	@BeforeSuite
	public void beforeSuite() {
		
		trip = fillTrip();
		user = fillUser();
	}

	@AfterSuite
	public void afterSuite() {

		user = null;
		trip = null;
	}


	@Test(groups = "init")
	public void successfulAddTripTest() {
		
		try {
			boolean result = tripDAOimpl.addTrip(trip);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(expectedExceptions = DAOException.class, 
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddTripTest() throws DAOException {
		
		Trip fakeTrip = fillTrip();
		fakeTrip.setVehicleId(0);
		
		try {
			tripDAOimpl.addTrip(fakeTrip);
		} finally {
			fakeTrip = null;
		}	
	}
	

	@Test(dependsOnGroups = {"init", "successful", "failed"}, 
			dependsOnMethods = {"successfulUpdateWorkListBeforeDeleteTripTest"})
 	public void successfulDeleteTripTest() {
		
		try {
			boolean result = tripDAOimpl.deleteTrip(trip);
			assertEquals(result, true);
			tripDAOimpl.addTrip(trip);
		} catch (DAOException e) {
			fail();
		}  
	}
	
	@Test(dependsOnMethods = {"successfulDeleteTripTest"})
 	public void failedDeleteTripTest() {
		
		Trip fakeTrip = fillTrip();
		fakeTrip.setId(0);
		
		try {
			boolean result = tripDAOimpl.deleteTrip(fakeTrip);
			assertEquals(result, false);
			fakeTrip = null;
		} catch (DAOException e) {
			fail();
		}  
	}
	
	

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void findAvailableTripTest() {
		
		try {
			int result = tripDAOimpl.findAvailableTrip().size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(groups = "init", dependsOnMethods = {"successfulAddTripTest"})
  	public void successfulFindTripTest() {
		
		try {
			int id = tripDAOimpl.findTripByParams(trip).getId();
			trip.setId(id);
			assertNotNull(tripDAOimpl.findTripByParams(trip));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedFindTripTest() {
		
		Trip fakeTrip = fillTrip();
		fakeTrip.setVehicleId(0);
		
		try {
			assertNull(tripDAOimpl.findTripByParams(fakeTrip));
			fakeTrip = null;
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(dependsOnGroups = {"init"},
			dependsOnMethods = {"successfulUpdateWorkListForUserTest"},
			groups = "successful")
	public void findTripByUserTest() {
		
		try {
			int result = tripDAOimpl.findTripByUser(user).size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedfindTripByUserTest() {
		
		User fakeUser = fillUser();
		fakeUser.setId(0);
		
		try {
			int result = tripDAOimpl.findTripByUser(fakeUser).size();
			assertEquals(result, 0);
			fakeUser = null;
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulUpdateTripTest() {
		
		try {
			trip.setFinishDate(LocalDate.now());
			boolean result = tripDAOimpl.updateTrip(trip);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedUpdateTripTest() {

		Trip fakeTrip = fillTrip();
		fakeTrip.setId(0);

		try {
			fakeTrip.setFinishDate(LocalDate.now());
			boolean result = tripDAOimpl.updateTrip(fakeTrip);
			assertEquals(result, false);
			fakeTrip = null;
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(dependsOnGroups = {"init", "successful"})
	public void successfulUpdateWorkListBeforeDeleteTripTest() {
		
		try {
			boolean result = tripDAOimpl.updateWorkListBeforeDeleteTrip(trip);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnMethods = {"successfulUpdateWorkListBeforeDeleteTripTest"})
	public void failedUpdateWorkListBeforeDeleteTripTest() {
		
		Trip fakeTrip = fillTrip();
		fakeTrip.setId(0);
		
		try {
			boolean result = tripDAOimpl.updateWorkListBeforeDeleteTrip(fakeTrip);
			assertEquals(result, false);
			fakeTrip = null;
		} catch (DAOException e) {
			fail();
		}
	}
	

	@Test(groups = "init", dependsOnMethods = {"successfulFindTripTest"})
	public void successfulUpdateWorkListForUserTest() {
		
		try {
			boolean result = tripDAOimpl.updateWorkListForUser(user, trip);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(expectedExceptions = DAOException.class,
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedUpdateWorkListForUserTest() throws DAOException {
		
		User fakeUser = fillUser();
		Trip fakeTrip = fillTrip();
		
		fakeTrip.setId(0);
		fakeUser.setId(0);
		
		try {
			tripDAOimpl.updateWorkListForUser(fakeUser, fakeTrip);
		} finally {
			fakeTrip = null;
			fakeUser = null;
		}
	}
	
	public Trip fillTrip() {
		
		Trip trip = new Trip();
		trip.setVehicleId(1);
		trip.setStartDate(LocalDate.now());
		trip.setNote("default trip note");
		
		return trip;
	}
	
	public User fillUser() {
		
		User user = new User();
		user.setId(1);
		
		return user;
	}
}
