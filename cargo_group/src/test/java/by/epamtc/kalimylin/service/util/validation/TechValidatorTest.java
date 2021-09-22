package by.epamtc.kalimylin.service.util.validation;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.DataProvider;

public class TechValidatorTest {

	@Test(dataProvider = "birthdate")
	public void isCorrectBirthdateTest(LocalDate date, boolean expectedResult) {
		boolean result = TechValidator.isCorrectBirthdate(date);
		assertEquals(result, expectedResult);
	}
	
	@DataProvider
    public Object[][] birthdate() {
      return new Object[][] {
    	   	  { LocalDate.of(2004, 01, 01), false },
    		  { LocalDate.of(2003, 01, 01), true },
    		  { LocalDate.now(), false },
    		  { LocalDate.of(2022, 01, 01), false }
      };
    }

  	@Test(dataProvider = "height")
  	public void isCorrectHeightTest(float value, boolean expectedResult) {
  		boolean result = TechValidator.isCorrectHeight(value);
    	assertEquals(result, expectedResult);
  	}
  	
  	@DataProvider
    public Object[][] height() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, true },
    		  { (float) 0.1, true },
    		  { (float) 4.9, true },
    		  { 5, false }
      };
    }
  	 	
    @Test(dataProvider = "id")
    public void isCorrectIdTest(int id, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectId(id);
    	assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] id() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, true },
    		  { 1, true },
    		  { 9999, true },
    		  { 10000, false }
      };
    }

    @Test(dataProvider = "lastYear")
    public void isCorrectLastYearTest(LocalDate date, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectLastYear(date);
		assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] lastYear() {
      return new Object[][] {
    	   	  { LocalDate.of(1959, 01, 01), false },
    		  { LocalDate.of(2021, 01, 01), true },
    		  { LocalDate.now(), false },
    		  { LocalDate.of(2022, 01, 01), false }
      };
    }

    @Test(dataProvider = "length")
    public void isCorrectLengthTest(float value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectLength(value);
    	assertEquals(result, expectedResult);
    }	
  	
    @DataProvider
    public Object[][] length() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, true },
    		  { (float) 0.1, true },
    		  { (float) 19.99, true },
    		  { 20, false }
      };
    }
    
    @Test(dataProvider = "odometer")
    public void isCorrectOdometerTest(int value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectOdometer(value);
    	assertEquals(result, expectedResult);   
    }
    
    @DataProvider
    public Object[][] odometer() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, true },
    		  { 1, true },
    		  { 9999999, true },
    		  { 10000000, false }
      };
    }
    	  
    @Test(dataProvider = "pallet")
    public void isCorrectPalletsQuantityTest(int value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectPalletsQuantity(value);
    	assertEquals(result, expectedResult); 
    }
    
    @DataProvider
    public Object[][] pallet() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, false },
    		  { 1, true },
    		  { 66, true },
    		  { 67, false }
      };
    }
    
    @Test(dataProvider = "price")
    public void isCorrectPriceTest(BigDecimal value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectPrice(value);
    	assertEquals(result, expectedResult); 	
    }
    
    @DataProvider
    public Object[][] price() {
      return new Object[][] {
    	   	  { BigDecimal.valueOf(-1), false },
    		  { BigDecimal.valueOf(1), false },
    		  { BigDecimal.valueOf(2), true },
    		  { BigDecimal.valueOf(199999), true },
    		  { BigDecimal.valueOf(200001), false }
      };
    }

    @Test(dataProvider = "distance")
    public void isCorrectRouteDistanceTest(int value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectRouteDistance(value);
    	assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] distance() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, false },
    		  { 1, true },
    		  { 14999, true },
    		  { 15000, false }
      };
    }

    @Test(dataProvider = "terminationDate")
    public void isCorrectTerminationDateTest(LocalDate date, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectTerminationDate(date);
    	assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] terminationDate() {
      return new Object[][] {
    	   	  { LocalDate.of(2021, 01, 01), true },
    		  { LocalDate.now(), true },
    		  { LocalDate.of(2022, 01, 01), false }
      };
    }

    @Test(dataProvider = "tripStartDate")
    public void isCorrectTripStartDateTest(LocalDate date, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectTripStartDate(date);
    	assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] tripStartDate() {
      return new Object[][] {
    	   	  { LocalDate.of(2021, 01, 01), false },
    		  { LocalDate.now(), true },
    		  { LocalDate.of(2022, 01, 01), true },
    		  { LocalDate.of(2031, 01, 01), false }
      };
    }

    @Test(dataProvider = "volume")
    public void isCorrectVolumeTest(float value, boolean expectedResult) {
  		boolean result = TechValidator.isCorrectVolume(value);
    	assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] volume() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, false },
    		  { (float) 0.1, true },
    		  { 120, true },
    		  { (float) 120.01, false }
      };
    }
  
    @Test(dataProvider = "weight")
    public void isCorrectWeightTest(float value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectWeight(value);
    	assertEquals(result, expectedResult);
    }
    
    @DataProvider
    public Object[][] weight() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, false },
    		  { (float) 0.1, true },
    		  { (float) 49.99, true },
    		  { 50, false }
      };
    }

    @Test(dataProvider = "width")
    public void isCorrectWidthTest(float value, boolean expectedResult) {
    	boolean result = TechValidator.isCorrectWidth(value);
    	assertEquals(result, expectedResult);
    }

    @DataProvider
    public Object[][] width() {
      return new Object[][] {
    	   	  { -1, false },
    		  { 0, false },
    		  { (float) 0.1, true },
    		  { (float) 4.9, true },
    		  { 5, false }
      };
    }   
}
