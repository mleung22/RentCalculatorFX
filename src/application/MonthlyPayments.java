package application;

public class MonthlyPayments {

	private String date;
	private double rent;
	private double water;
	private double electricity;
	private double internet;

	public MonthlyPayments(String date, double rent, double water,
			double electricity, double internet) {
		this.date = date;
		this.rent = rent;
		this.water = water;
		this.electricity = electricity;
		this.internet = internet;
	}
	
	public MonthlyPayments(){
		
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getElectricity() {
		return electricity;
	}

	public void setElectricity(double electricity) {
		this.electricity = electricity;
	}

	public double getInternet() {
		return internet;
	}

	public void setInternet(double internet) {
		this.internet = internet;
	}

	public double utilites() {

		double utilities;
		utilities = water + electricity;
		return utilities;
	}

	public double rentSplit() {

		double rentSplit;
		rentSplit = rent / 2;
		return rentSplit;
	}

	public double difference() {

		double diff;
		diff = (utilites() - internet) / 2;
		return diff;
	}

	public double calculateForP() {

		double totalRentAmount;
		totalRentAmount = rentSplit() - difference();
		return totalRentAmount;
	}

	public double calculateForM() {

		double totalRentAmount;
		totalRentAmount = rentSplit() + difference();
		return totalRentAmount;
	}

	public String toString() {
		return "MonthlyPayments [date=" + date + ", rent=" + rent + ", water="
				+ water + ", electricity=" + electricity + ", internet="
				+ internet + "]";
	}

}
