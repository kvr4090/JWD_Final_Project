package by.epamtc.kalimylin.dao.impl;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.bean.document.Order;
import by.epamtc.kalimylin.bean.document.Trip;
import by.epamtc.kalimylin.dao.exception.DAOException;

import org.testng.annotations.BeforeSuite;

import static org.testng.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterSuite;

public class OrderDAOimplTest {
	
	private static final OrderDAOimpl orderDAOimpl = new OrderDAOimpl();
	private Order firstTestOrder;
	private Order secondTestOrder;
	private Order thirdTestOrder;
	private List<Order> testListOrder;
	private Trip trip;
	
	@BeforeSuite
	public void beforeSuite() {
		
		trip = new Trip();
		trip.setId(1);
		
		firstTestOrder = fillOrder();
		firstTestOrder.setId(1);
		secondTestOrder = fillOrder();
		secondTestOrder.setId(2);
		thirdTestOrder = fillOrder();
		thirdTestOrder.setId(3);
		
		testListOrder = new ArrayList<>();
		testListOrder.add(secondTestOrder);
		testListOrder.add(thirdTestOrder);
	}

	@AfterSuite
	public void afterSuite() {
	}


	@Test(groups = "init")
	public void successfulAddOrderTest() {
		
		try {
			boolean resultFirst = orderDAOimpl.addOrder(firstTestOrder);
			boolean resultSecond = orderDAOimpl.addOrder(secondTestOrder);
			boolean resultThird = orderDAOimpl.addOrder(thirdTestOrder);
			assertEquals(resultFirst && resultSecond && resultThird, true);
		} catch (DAOException e) {
			fail();
		}  
	}
	
	@Test(expectedExceptions = DAOException.class, 
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedAddOrderTest() throws DAOException {
		
		Order invalidOrder = fillOrder();
		invalidOrder.setUserId(0);

		try {
			orderDAOimpl.addOrder(invalidOrder);
		} finally {
			invalidOrder = null; 
		}
	}

	@Test(dependsOnGroups = {"init", "successful"}, 
			dependsOnMethods = {"successfulUpdateOrderBeforeDeleteTripTest"})
	public void successfulDeleteListOrderTest() {
		
		try {
			boolean result = orderDAOimpl.deleteListOrder(testListOrder);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(expectedExceptions = DAOException.class, 
			dependsOnMethods = {"successfulDeleteListOrderTest"})
	public void failedDeleteListOrderTest() throws DAOException {
		
		Order invalidOrder = new Order();
		invalidOrder.setId(0);
		List<Order> invalidList = new ArrayList<>();
		invalidList.add(invalidOrder);
		
		try {
			orderDAOimpl.deleteListOrder(invalidList);
		} finally {
			invalidOrder = null;
			invalidList = null;
		}		
	}
	
	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindAvailableOrderTest() {
		
		try {
			int result = orderDAOimpl.findAvailableOrder().size();
			assertEquals(result, 1);
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindOrderByTripTest() {
		
		try {
			int result = orderDAOimpl.findOrderByTrip(trip).size();
			assertEquals(result, 2);
		} catch (DAOException e) {
			fail();
		}  
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedFindOrderByTripTest() {
		
		Trip fakeTrip = new Trip();
		fakeTrip.setId(0);
		
		try {
			int result = orderDAOimpl.findOrderByTrip(fakeTrip).size();
			assertEquals(result, 0);
		} catch (DAOException e) {
			fail();
		}  
	}
	

	@Test(dependsOnGroups = {"init"}, groups = "successful")
	public void successfulFindOrdersByIdTest() {
		
		try {
			int result = orderDAOimpl.findOrdersById(testListOrder).size();
			assertEquals(result, 2);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedFindOrdersByIdTest() {
		
		List<Order> fakeList = new ArrayList<>();
		
		try {
			int result = orderDAOimpl.findOrdersById(fakeList).size();
			assertEquals(result, 0);
			fakeList = null;
		} catch (DAOException e) {
			fail();
		}
	}

	@Test(groups = "init", dependsOnMethods = {"successfulAddOrderTest"})
	public void successfulUpdateListOrdersByTripIdTest() {
		
		try {
			boolean result =
					orderDAOimpl.updateListOrdersByTripId(testListOrder, trip);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test(expectedExceptions = DAOException.class,
			dependsOnGroups = {"init", "successful"}, groups = "failed")
	public void failedUpdateListOrdersByTripIdTest() throws DAOException {
		
		List<Order> fakeList = new ArrayList<>();
		fakeList.add(firstTestOrder);
		Trip fakeTrip = new Trip();
		fakeTrip.setId(0);
		
		try {
			orderDAOimpl.updateListOrdersByTripId(fakeList, fakeTrip);
		} finally {
			fakeList = null;
			fakeTrip = null;
		}
	}

	@Test(dependsOnGroups = {"init", "successful"}) 
	public void successfulUpdateOrderBeforeDeleteTripTest() {
		
		try {
			boolean result = orderDAOimpl.updateOrderBeforeDeleteTrip(trip);
			assertEquals(result, true);
		} catch (DAOException e) {
			fail();
		} 
	}
		
	public Order fillOrder() {
		
		Order order = new Order();
		
		order.setUserId(1);
		order.setShipperId(1);
		order.setRequirementId(1);
		order.setDate(LocalDate.now());
		order.setPrice(BigDecimal.valueOf(1000));
		order.setRouteStartPoint("Brest");
		order.setRouteEndPoint("Smolensk");
		order.setDistance(673);
		order.setNote("Test trip example");
		
		return order;		
	}
}
