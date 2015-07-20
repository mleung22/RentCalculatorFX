package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Database;
import application.MonthlyPayments;

public class DatabaseTest {

	Database db;
	MonthlyPayments mp;
	Statement myStmt;
	ResultSet myRs;

	@Before
	public void setup() throws FileNotFoundException, IOException, SQLException {
		db = new Database();
		mp = new MonthlyPayments("5/5/2015", 754, 24, 31, 71);
		db.insertInformation(mp);
	}

	@Test
	public void testInsertInformation() throws SQLException,
			FileNotFoundException, IOException {

		MonthlyPayments actualOutput = db.insertInformation(mp);

		assertEquals("Invalid Date", mp.getDate(), actualOutput.getDate());
		assertEquals("Invalid Rent Amount", mp.getRent(), actualOutput.getRent(), .01);
		assertEquals("Invalid Water Amount", mp.getWater(), actualOutput.getWater(), .01);
		assertEquals("Invalid Electricity Amount", mp.getElectricity(),
				actualOutput.getElectricity(), .01);
		assertEquals("Invalid Internet Amount", mp.getInternet(), actualOutput.getInternet(),
				.01);
	}

	@Test
	public void testGetDateFromDatabase() throws Exception {

		List actualOutput = db.getDateFromDatabase();
		String expectedOutput = mp.getDate();

		assertTrue(actualOutput.contains(expectedOutput));

	}

	@Test
	public void testGetPaymentsFromDatabaseWithDate() throws Exception {

		MonthlyPayments actualOutput = db.getPaymentsFromDatabaseWithDate(mp
				.getDate());
		MonthlyPayments expectedOutput = mp;

		assertEquals(expectedOutput.getDate(), actualOutput.getDate());
		assertEquals(expectedOutput.getRent(), actualOutput.getRent(), .01);
		assertEquals(expectedOutput.getWater(), actualOutput.getWater(), .01);
		assertEquals(expectedOutput.getElectricity(),
				actualOutput.getElectricity(), .01);
		assertEquals(expectedOutput.getInternet(), actualOutput.getInternet(),
				.01);
	}
	
	@Test
	public void testGetRentFromDatabase() throws Exception {

		List actualOutput = db.getRentFromDatabase();
		double expectedOutput = mp.getRent();

		assertTrue(actualOutput.contains(expectedOutput));

	}

	@Test
	public void testGetWaterFromDatabase() throws Exception {

		List actualOutput = db.getWaterFromDatabase();
		double expectedOutput = mp.getWater();

		assertTrue(actualOutput.contains(expectedOutput));

	}
	
	@Test
	public void testGetElectricityFromDatabase() throws Exception {

		List actualOutput = db.getElectricityFromDatabase();
		double expectedOutput = mp.getElectricity();

		assertTrue(actualOutput.contains(expectedOutput));
	}
	
	@Test
	public void testGetInternetFromDatabase() throws Exception {
		
		List actualOutput = db.getInternetFromDatabase();
		double expectedOutput = mp.getInternet();

		assertTrue(actualOutput.contains(expectedOutput));
	}
	
	@Test
	public void testDeleteDataFromDatabaseByDate() throws Exception{
		
		db.deleteDataFromDatabaseByDate(mp.getDate());
		
		assertFalse(db.getDateFromDatabase().contains(mp.getDate()));
		assertFalse(db.getRentFromDatabase().contains(mp.getRent()));
		assertFalse(db.getWaterFromDatabase().contains(mp.getWater()));
		assertFalse(db.getElectricityFromDatabase().contains(mp.getElectricity()));
		assertFalse(db.getInternetFromDatabase().contains(mp.getInternet()));
	}

	@After
	public void clean() throws FileNotFoundException, IOException, SQLException {
		db.deleteDataFromDatabaseByDate(mp.getDate());
	}
}
