package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

	private static java.sql.Connection myConn;

	public Database() throws FileNotFoundException, IOException, SQLException {

		Properties props = new Properties();
		props.load(new FileInputStream("login"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);

	}

	public MonthlyPayments insertInformation(MonthlyPayments mp) throws SQLException {

		Statement myStmt = myConn.createStatement();

		String insertSql = "INSERT into mpdata "
				+ "(date, rent, water, electricity, internet)" + " values ('"
				+ mp.getDate() + "','" + mp.getRent() + "','" + mp.getWater()
				+ "','" + mp.getElectricity() + "','" + mp.getInternet() + "')";

		myStmt.executeUpdate(insertSql);
		
		return mp;

	}

	public MonthlyPayments convertDataToMP(ResultSet myRs) throws SQLException {

		String date = myRs.getString("Date");
		double rent = myRs.getDouble("Rent");
		double water = myRs.getDouble("Water");
		double electricity = myRs.getDouble("Electricity");
		double internet = myRs.getDouble("Internet");

		MonthlyPayments mp = new MonthlyPayments(date, rent, water,
				electricity, internet);
		return mp;
	}

	public List getDateFromDatabase() throws Exception {
		List list = new ArrayList<>();

		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from mpdata");

			while (myRs.next()) {
				MonthlyPayments mp = convertDataToMP(myRs);
				list.add(mp.getDate());
			}
			return list;
		} finally {
		}
	}

	public MonthlyPayments getPaymentsFromDatabaseWithDate(Object listItem)
			throws Exception {

		MonthlyPayments mp = new MonthlyPayments();
		Statement myStmt = myConn.createStatement();

		String query = "SELECT date, rent, water, electricity, internet "
				+ "FROM mpdata WHERE date='" + listItem + "'";
		ResultSet myRs = myStmt.executeQuery(query);

		while (myRs.next()) {
			mp = convertDataToMP(myRs);
		}

		return mp;
	}
	
	public List getRentFromDatabase() throws Exception {
		List list = new ArrayList<>();

		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from mpdata");

			while (myRs.next()) {
				MonthlyPayments mp = convertDataToMP(myRs);
				list.add(mp.getRent());
			}
			return list;
		} finally {
		}
	}

	public List getWaterFromDatabase() throws Exception {
		List list = new ArrayList<>();

		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from mpdata");

			while (myRs.next()) {
				MonthlyPayments mp = convertDataToMP(myRs);
				list.add(mp.getWater());
			}
			return list;
		} finally {
		}
	}

	public List getElectricityFromDatabase() throws Exception {
		List list = new ArrayList<>();

		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from mpdata");

			while (myRs.next()) {
				MonthlyPayments mp = convertDataToMP(myRs);
				list.add(mp.getElectricity());
			}
			return list;
		} finally {
		}
	}

	public List getInternetFromDatabase() throws Exception {
		List list = new ArrayList<>();

		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from mpdata");

			while (myRs.next()) {
				MonthlyPayments mp = convertDataToMP(myRs);
				list.add(mp.getInternet());
			}
			return list;
		} finally {
		}
	}

	public int deleteDataFromDatabaseByDate(Object listItem)
			throws SQLException {

		Statement myStmt = myConn.createStatement();

		String deleteQuery = "DELETE FROM mpdata WHERE date='" + listItem + "'";

		System.out.println("Deleted " + listItem + " entry");

		return myStmt.executeUpdate(deleteQuery);

	}

}
