package by.epamtc.kalimylin.dao.impl;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.bean.executor.Vehicle;
import by.epamtc.kalimylin.bean.executor.user.AuthenticationData;
import by.epamtc.kalimylin.bean.executor.user.PersonalData;
import by.epamtc.kalimylin.bean.executor.user.Role;
import by.epamtc.kalimylin.bean.executor.user.User;
import by.epamtc.kalimylin.dao.exception.DAOException;
import by.epamtc.kalimylin.service.exception.CannotPerformOperationException;
import by.epamtc.kalimylin.service.util.PasswordProcessing;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static org.testng.Assert.*;

import java.time.LocalDate;

public class UserDAOimplTest {

	 private static final UserDAOimpl userDAOImpl = new UserDAOimpl();
	 private User user;
	 private AuthenticationData authData;
	 private PersonalData data;
	 private Vehicle vehicle;
	 
	@BeforeSuite
	public void initBeans() throws CannotPerformOperationException {
		
		user = fillUser();
		data = fillData();
		authData = fillAuthData();
		vehicle = fillVehicle();
	}
	
	@AfterSuite
	public void afterSuite() {
		
		user = null;
		authData = null;
		data = null;
		vehicle = null;
	}
	 
	@Test(groups = "init")
	public void successfulAddUserTest() {
		
		try {
			boolean result = userDAOImpl.addUser(user);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	  	
	@Test(expectedExceptions = DAOException.class, 
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddUserTest() throws DAOException {
		
	  	userDAOImpl.addUser(user); 		
	} 
	 
  	@Test(groups = "init", dependsOnMethods = {"successfulFindUserByEmailTest"})
  	public void successfulAddPersonalDataTest() {
  		
		try {
			data.setUserId(user.getId());
			boolean result = userDAOImpl.addPersonalData(data);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(expectedExceptions = DAOException.class,
  			dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedAddPersonalDataTest() throws DAOException {
  		
  		userDAOImpl.addPersonalData(data); 		
  	}
  
  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void findAllPersonalDataTest() {
  		
		try {
			int result = userDAOImpl.findAllPersonalData().size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void findAllUsersTest() {
  		
		try {
			int result = userDAOImpl.findAllUsers().size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		} 		
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulFindPersonalDataTest() {
  		
  		try {
			assertNotNull(userDAOImpl.findPersonalDataByUserId(data));
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedFindPersonalDataTest() {
  		
  		PersonalData failedData = new PersonalData();
  		failedData.setUserId(0);
  		
  		try {
			assertNull(userDAOImpl.findPersonalDataByUserId(failedData));
			failedData = null;
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void findSimilarLoginUserTest() {
  		
  		User similarUser = new User();
  		similarUser.setLogin(user.getLogin());
  		
		try {
			int result = userDAOImpl.findSimilarUser(similarUser).size();
			assertEquals(result, 1);
			similarUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void findSimilarEmailUserTest() {
  		
  		User similarUser = new User();
  		similarUser.setEmail(user.getEmail());
  		
		try {
			int result = userDAOImpl.findSimilarUser(similarUser).size();
			assertEquals(result, 1);
			similarUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void findSimilarPhoneUserTest() {
  		
  		User similarUser = new User();
  		similarUser.setPhone(user.getPhone());
  		
  		try {
			int result = userDAOImpl.findSimilarUser(similarUser).size();
			assertEquals(result, 1);
			similarUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void uniqueFindSimilarUserTest() {
  		
  		User uniqueUser = new User();
  		uniqueUser.setLogin("uniqueLogin");
  		uniqueUser.setPhone("375440001122");
  		uniqueUser.setEmail("uniqueEmail@unique.eu");

		try {
			int result = userDAOImpl.findSimilarUser(uniqueUser).size();
			assertEquals(result, 0);
			uniqueUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(groups = "init", dependsOnMethods = {"successfulAddUserTest"})
  	public void successfulFindUserByEmailTest() {
  		
  		try {
  			user.setId(userDAOImpl.findUserByEmail(user).getId());
			assertNotNull(userDAOImpl.findUserByEmail(user));
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedFindUserByEmailTest() {
  		
  		User uniqueUser = new User();
  		uniqueUser.setEmail("uniqueEmail@unique.eu");
  		
  		try {
			assertNull(userDAOImpl.findUserByEmail(uniqueUser));
			uniqueUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulFindUserByIdTest() {
  		
  		try {
			assertNotNull(userDAOImpl.findUserById(user));
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedFindUserByIdTest() {
  		
  		User uniqueUser = new User();
  		uniqueUser.setId(0);
  		
  		try {
			assertNull(userDAOImpl.findUserById(uniqueUser));
			uniqueUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulFindUserByLoginTest() {
  		
  		try {
			assertNotNull(userDAOImpl.findUserByLogin(authData));
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedFindUserByLoginTest() {
  		
  		AuthenticationData uniqueAuthData = new AuthenticationData();
  		uniqueAuthData.setLogin("uniqueLogin");
  		uniqueAuthData.setPassword("uniquePassword");
  		
  		try {
			assertNull(userDAOImpl.findUserByLogin(uniqueAuthData));
			uniqueAuthData = null;
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulFindUserByVehicleIdTest() {
  		
  		try {
			assertNotNull(userDAOImpl.findUserByVehicleId(vehicle));
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedFindUserByVehicleIdTest() {
  		
  		Vehicle uniqueVehicle = new Vehicle();
  		uniqueVehicle.setId(0);
  		
  		try {
			assertNull(userDAOImpl.findUserByVehicleId(uniqueVehicle));
			uniqueVehicle = null;
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulMatchesPersonalDataTest() {
 
		try {
			int result = userDAOImpl.matchesPersonalData(data);
			assertEquals(result, 0);
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedMatchesPersonalDataTest() {
  		
  		PersonalData uniqueData = new PersonalData();
  		uniqueData.setPassportIdentificationNumber("1234567V123ZX2");
  		uniqueData.setPassportNumber("QW1234560");

		try {
			int result = userDAOImpl.matchesPersonalData(uniqueData);
			assertEquals(result, 0);
			uniqueData = null;
		} catch (DAOException e) {
			fail();
		}
  	}
  
  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulUpdatePersonalDataTest() {

		try {
			boolean result = userDAOImpl.updatePersonalData(data);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedUpdatePersonalDataTest() {
  		
  		PersonalData uniqueData = fillData();
  		uniqueData.setUserId(0);

		try {
			boolean result = userDAOImpl.updatePersonalData(uniqueData);
	  		assertEquals(result, false);
	  		uniqueData = null;
		} catch (DAOException e) {
			fail();
		}
  	}

  	@Test(groups = "init", dependsOnMethods = {"successfulAddUserTest"})
  	public void successfulUpdateUserTest() {

		try {
			boolean result = userDAOImpl.updateUser(user);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedUpdateUserTest() {
  		
		try {
			User fakeUser = fillUser();
			fakeUser.setLogin("uniqueLogin");
	  		boolean result = userDAOImpl.updateUser(fakeUser);
	  		assertEquals(result, false);
	  		fakeUser = null;
		} catch (DAOException | CannotPerformOperationException e) {
			fail();
		}
  	}

  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulUpdateUserPasswordTest() {
  		
		try {
			boolean result = userDAOImpl.updateUserPassword(user);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedUpdateUserPasswordTest() {
  		
  		User uniqueUser = new User();
  		uniqueUser.setId(0);
  		
  		try {
  			boolean result = userDAOImpl.updateUserPassword(uniqueUser);
  			assertEquals(result, false);
  			uniqueUser = null;
		} catch (DAOException e) {
			fail();
		}
  	}	
  	
  	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
  	public void failedUpdateUserRoleStatusTest() {
  		  		
		try {
			User uniqueUser = fillUser();
	  		uniqueUser.setId(0);
			boolean result = userDAOImpl.updateUserRoleStatus(uniqueUser);
			assertEquals(result, false);
			uniqueUser = null;
		} catch (DAOException | CannotPerformOperationException e) {
			fail();
		}
  	}
  	
  	@Test(dependsOnGroups = {"init"}, groups = "successful")
  	public void successfulUpdateUserRoleStatusTest() {

		try {
			boolean result = userDAOImpl.updateUserRoleStatus(user);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
  	}
  
    public User fillUser() throws CannotPerformOperationException {

    	User user = new User();
    	user.setEmail("kvr40490@mail.ru");
		user.setLogin("Driver11");
		user.setCorrectHash(PasswordProcessing.createHash("Q1!$qq"));
		user.setPhone("375291112233");
		user.setRole(Role.DRIVER);
		user.setVehicleId(1);
		
		return user;
    }
    
    public PersonalData fillData() {
    	
    	PersonalData data = new PersonalData();
    	data.setBirthdate(LocalDate.of(1990, 01,01));
    	data.setDriveLicense("C");
    	data.setName("Igorrr");
    	data.setPassportAuthority("MINISTRY OF INTERNAL AFFAIRS");
    	data.setPassportDateOfExpiry(LocalDate.of(2025, 01, 01));
    	data.setPassportDateOfIssue(LocalDate.of(2020, 01, 01));
    	data.setPassportNumber("QW1234567");
    	data.setPassportIdentificationNumber("1234567V123ZX1");
    	data.setPost("driver");
    	data.setRecruitmentDate(LocalDate.of(2020, 01, 01));
    	data.setSurname("Smith");
    	
    	return data;
    }
    
    public AuthenticationData fillAuthData() {
    	
    	AuthenticationData authData = new AuthenticationData();
		authData.setLogin(user.getLogin());
		authData.setPassword(user.getCorrectHash());
		
		return authData;
    }
    
    public Vehicle fillVehicle() {
    	
    	Vehicle vehicle = new Vehicle();
    	vehicle.setId(user.getVehicleId());
    	
    	return vehicle;
    }
}
