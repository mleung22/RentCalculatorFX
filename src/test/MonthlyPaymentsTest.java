package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.MonthlyPayments;

public class MonthlyPaymentsTest {
	
	MonthlyPayments mp;
	
	@Before
	public void setup(){
		mp = new MonthlyPayments("5/5/2015", 950, 40, 50, 70);
	}
	
	@Test
	public void testUtilities() {
		
		double expectedOutput = 90;
		double actualOutput = mp.utilites();
		
		assertEquals(expectedOutput, actualOutput, .01);
		
	}
	
	@Test 
	public void testRentSplit(){
		
		double expectedOutput = 475;
		double actualOutput = mp.rentSplit();
		
		assertEquals(expectedOutput, actualOutput, .01);
	}
	
	@Test
	public void testDifference() {
		
		double expectedOutput = 10;
		double actualOutput = mp.difference();
		
		assertEquals(expectedOutput, actualOutput, .01);
	}
	
	@Test	
	public void testCalculateForP() {
		
		double expectedOutput = 465;
		double actualOutput = mp.calculateForP();
		
		assertEquals(expectedOutput, actualOutput, .01);
	}
	
	@Test	
	public void testCalculateForM() {
		
		double expectedOutput = 485;
		double actualOutput = mp.calculateForM();
		
		assertEquals(expectedOutput, actualOutput, .01);
	}

}
