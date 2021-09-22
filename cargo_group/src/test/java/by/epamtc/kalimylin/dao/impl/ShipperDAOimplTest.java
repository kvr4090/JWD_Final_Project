package by.epamtc.kalimylin.dao.impl;

import by.epamtc.kalimylin.bean.executor.Shipper;
import by.epamtc.kalimylin.dao.exception.DAOException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ShipperDAOimplTest {

	private static final ShipperDAOimpl shipperDAOimpl = new ShipperDAOimpl();
	private Shipper shipper;

	@BeforeSuite
	public void beforeSuite() {
		
		shipper = fillShipper();
	}

	@AfterSuite
	public void afterSuite() {
		
		shipper = null;
	}


	@Test(groups = "init")
	public void successfulAddShipperTest() {
		
		try {
			boolean result = shipperDAOimpl.addShipper(shipper);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}  
	}
	
	@Test(expectedExceptions = DAOException.class,
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddShipperTest() throws DAOException {
		
		shipperDAOimpl.addShipper(shipper);
	}

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindAllShippersTest() {
		
		try {
			int result = shipperDAOimpl.findAllShippers().size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindShipperByNameTest() {
		
		try {
			assertNotNull(shipperDAOimpl.findShipperByName(shipper));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedFindShipperByNameTest() {
		
		Shipper invalidShipper = fillShipper();
		invalidShipper.setName("fake name");
		
		try {
			assertNull(shipperDAOimpl.findShipperByName(invalidShipper));
			invalidShipper = null;
		} catch (DAOException e) {
			fail();
		}
	}
	
	public Shipper fillShipper() {
		
		Shipper shipper = new Shipper();
		shipper.setName("Cargo Group");
		shipper.setContactPersonName("Valiantsin");
		shipper.setContactPersonSurname("Kalimylin");
		shipper.setContactPhone("375445869423");
		shipper.setEmail("kvr4090@mail.ru");
		shipper.setNote("Some info about company");
		
		return shipper;
	}
}
