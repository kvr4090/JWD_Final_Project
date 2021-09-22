package by.epamtc.kalimylin.dao.impl;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.bean.document.Requirement;
import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.VehicleType;
import by.epamtc.kalimylin.dao.connection.ConnectionPool;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.dao.util.ScriptRunner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static org.testng.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.testng.annotations.AfterSuite;

public class RequirementDAOimplTest {
	
	private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static final String DB_FILE = "src/test/resources/cargoGroup.sql";
	private static final RequirementDAOimpl reqDAOimpl = new RequirementDAOimpl();
	private static final VehicleDAOimpl vehicleDAOimpl = new VehicleDAOimpl();
	private Requirement requirement;
	private VehicleType type;
	private Vehicle vehicle;
	
	@BeforeClass
	public void beforeClass() {

		try {
			Connection connection = connectionPool.getConnection();
			ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(DB_FILE));
			scriptRunner.runScript(bufferedReader);
			connectionPool.releaseConnection(connection);
			bufferedReader.close();		
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			fail();
		}
	}
		
	@BeforeSuite
	public void beforeSuite() {

		type = fillVehicleType();
		vehicle = fillVehicle();    	
		requirement = fillRequirement();
	}

	@AfterSuite
	public void afterSuite() {
		
		requirement = null;
	}
	
	@Test
	public void clear() {
		
	}
	
	@Test(groups = "init")
	public void successfulAddRequirementTest() {
		
		try {
			boolean result = reqDAOimpl.addRequirement(requirement);
			vehicleDAOimpl.addVehicleType(type);
			vehicleDAOimpl.addVehicle(vehicle);
			assertEquals(result, true);
		} catch (DAOException e) {
			e.printStackTrace();					// FIXME
			fail();
		} 
	}
	
	@Test(expectedExceptions = DAOException.class, 
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddRequirementTest() throws DAOException {
		
		Requirement invalidReq = fillRequirement();
		invalidReq.setMaxLength((float) 100.123);
		
		try {
			reqDAOimpl.addRequirement(invalidReq);
		} finally {
			invalidReq = null;
		}		
	}
	
	@Test(dependsOnMethods = {"successfulAddRequirementTest"}, groups = "init")
  	public void successfulFindRequirementTest() {
		
    	try {
    		List<Requirement> list = reqDAOimpl.findRequirement();
    		requirement.setId(list.get(0).getId());
			int result = list.size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedFindRequirementByIdTest() {
		
		Requirement invalidReq = fillRequirement();
		invalidReq.setId(0);
		
		try {
			assertNull(reqDAOimpl.findRequirementById(invalidReq));
			invalidReq = null;
		} catch (DAOException e) {
			fail();
		}	
	}
	
	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindRequirementByIdTest() {
		
		try {
			assertNotNull(reqDAOimpl.findRequirementById(requirement));
		} catch (DAOException e) {
			fail();
		}	
	}

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindRequirementByVehicleTest() {

		try {
			assertNotNull(reqDAOimpl.findRequirementByVehicle(vehicle));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedFindRequirementByVehicleTest() {
		
		Vehicle invalidVehicle = fillVehicle();
		invalidVehicle.setTypeId(0);
		
		try {
			assertNull(reqDAOimpl.findRequirementByVehicle(invalidVehicle));
			invalidVehicle = null;
		} catch(DAOException e) {
			fail();
		}
	}
	
	public Requirement fillRequirement() {
		
		Requirement requirement = new Requirement();
		requirement.setMaxHeight((float) 2.75);
		requirement.setMaxLength((float) 13.5);
		requirement.setMaxWidth((float) 2.45);
		requirement.setPalletsQuantity(33);
		requirement.setVolume(90);
		requirement.setWeight(20);
		
		return requirement;
	}
	
	public Vehicle fillVehicle() {
    	
    	Vehicle vehicle = new Vehicle();
    	vehicle.setId(1);
		vehicle.setTypeId(1);
		vehicle.setBrand("Volvo");
    	vehicle.setColor("black");
    	vehicle.setDriveLicense("C");
    	vehicle.setIsAvailable(true);
    	vehicle.setLicensePlate("1234AA7");
    	vehicle.setModel("FH");
    	vehicle.setNote("new truck");
    	vehicle.setOdometer(5);
    	vehicle.setYearOfIssue(LocalDate.of(2021, 01, 01));
    	vehicle.setTrailerId(0);
    	
    	return vehicle;
    }
    
    public VehicleType fillVehicleType() {
    	
    	VehicleType vehicleType = new VehicleType();
    	vehicleType.setId(1);
    	vehicleType.setIsAvailable(true);
    	vehicleType.setType("refrigerator,10t");
    	vehicleType.setRequirementId(1);
    	
    	return vehicleType;
    }
}
