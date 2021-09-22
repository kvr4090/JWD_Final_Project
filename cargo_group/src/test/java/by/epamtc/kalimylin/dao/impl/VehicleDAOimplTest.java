package by.epamtc.kalimylin.dao.impl;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.exception.DAOException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;

import static org.testng.Assert.*;

import java.time.LocalDate;

public class VehicleDAOimplTest {
	
	private static final VehicleDAOimpl vehicleDAOimpl = new VehicleDAOimpl();
	private Vehicle vehicle;
	private VehicleType vehicleType;

	@BeforeSuite
	public void beforeSuite() {
		
		vehicle = fillVehicle();
		vehicleType = fillVehicleType();
	}

	@AfterSuite
	public void afterSuite() {
		
		vehicle = null;
		vehicleType = null;
	}

	@Test(groups = "init", dependsOnMethods = {"findVehicleTypeTest"})
	public void successfulAddVehicleTest() {
		
		vehicle.setTypeId(vehicleType.getId());
		
		try {
			assertNotNull(vehicleDAOimpl.addVehicle(vehicle));
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(expectedExceptions = DAOException.class, 
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddVehicleTest() throws DAOException {
		
		vehicleDAOimpl.addVehicle(vehicle);
	}

	@Test(groups = "init")
    public void successfulAddVehicleTypeTest() {
		
		try {
			assertNotNull(vehicleDAOimpl.addVehicleType(vehicleType));
		} catch (DAOException e) {
			fail();
		}
    }
	
	@Test(expectedExceptions = DAOException.class, 
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddVehicleTypeTest() throws DAOException {
		
		VehicleType invalidType = fillVehicleType();
		invalidType.setRequirementId(0);
		
		try {			
			vehicleDAOimpl.addVehicleType(invalidType);
		} finally {
			invalidType = null;
		}
	}

    @Test(groups = "successful", dependsOnGroups = {"init"})
    public void findAllVehicleTest() {
    	
    	try {
			int result = vehicleDAOimpl.findAllVehicle().size();
			assertEquals(result, 2);
		} catch (DAOException e) {
			fail();
		} 
    }

    @Test(groups = "successful", dependsOnGroups = {"init"})
    public void successfulFindVehicleByIdTest() {
    	
    	try {
			assertNotNull(vehicleDAOimpl.findVehicleById(vehicle));
		} catch (DAOException e) {
			fail();
		}
   
    }
    
    @Test(groups = "failed", dependsOnGroups = {"init", "successful"})
    public void failedFindVehicleByIdTest() {
    	
    	Vehicle invalidVehicle = fillVehicle();
    	invalidVehicle.setId(0);
    	
    	try {
			assertNull(vehicleDAOimpl.findVehicleById(invalidVehicle));
			invalidVehicle = null;
		} catch (DAOException e) {
			fail();
		}
   
    }

    @Test(groups = "init", dependsOnMethods = {"successfulAddVehicleTest"})
    public void successfulFindVehicleByLicensePlateTest() {
    	
    	try {
    		vehicle.setId(vehicleDAOimpl.findVehicleByLicensePlate(vehicle).getId());
			assertNotNull(vehicleDAOimpl.findVehicleByLicensePlate(vehicle));
		} catch (DAOException e) {
			fail();
		}
    }
    
    @Test(groups = "failed", dependsOnGroups = {"init", "successful"})
    public void failedFindVehicleByLicensePlateTest() {
    	
    	Vehicle invalidVehicle = fillVehicle();
    	invalidVehicle.setLicensePlate("fakePlate");;
    	
    	try {
			assertNull(vehicleDAOimpl.findVehicleByLicensePlate(invalidVehicle));
			invalidVehicle = null;
		} catch (DAOException e) {
			fail();
		}
    }

    @Test(groups = "init", dependsOnMethods = {"successfulAddVehicleTypeTest"})
    public void findVehicleTypeTest() {
    	
    	try {
    		vehicleType.setId(2);
			int result = vehicleDAOimpl.findVehicleType().size();
			assertEquals(result, 2);
		} catch (DAOException e) {
			fail();
		}
    }

    @Test(groups = "successful", dependsOnGroups = {"init"})
    public void successfulUpdateVehicleTest() {
    	
    	try {
			boolean result = vehicleDAOimpl.updateVehicle(vehicle);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		} 
    }
    
    @Test(groups = "failed", dependsOnGroups = {"init", "successful"})
    public void failedUpdateVehicleTest() {
    	
    	Vehicle invalidVehicle = fillVehicle();
    	invalidVehicle.setId(0);
    	
    	try {
			boolean result = vehicleDAOimpl.updateVehicle(invalidVehicle);
			assertEquals(result, false);
			invalidVehicle = null;
		} catch (DAOException e) {
			fail();
		} 
    }
    
    public Vehicle fillVehicle() {
    	
    	Vehicle vehicle = new Vehicle();
    	vehicle.setBrand("Volvo");
    	vehicle.setColor("grey");
    	vehicle.setDriveLicense("C");
    	vehicle.setIsAvailable(true);
    	vehicle.setLicensePlate("1234AA6");
    	vehicle.setModel("FH");
    	vehicle.setNote("new truck");
    	vehicle.setOdometer(5);
    	vehicle.setYearOfIssue(LocalDate.of(2021, 01, 01));
    	vehicle.setTrailerId(0);
    	
    	return vehicle;
    }
    
    public VehicleType fillVehicleType() {
    	
    	VehicleType vehicleType = new VehicleType();
    	vehicleType = new VehicleType();
    	vehicleType.setIsAvailable(true);
    	vehicleType.setType("tractor,30t");
    	vehicleType.setRequirementId(1);
    	
    	return vehicleType;
    }
}
