package com.example.ggcautomaintenance;

public class MPG {
	private int fillNumber;
	private String carName;
	private int oldOdometer; //mileage from last fill up
	private int currentOdometer; //mileage from current fill up
	private int gallons;
	private double costPerGallon;
	
	public int getFillNumber() {
		return fillNumber;
	}
	
	public void setFillNumber(int fillNumber) {
		this.fillNumber = fillNumber;
	}
	
	public String getCarName() {
		return carName;
	}
	
	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	public int getOldOdometer() {
		return oldOdometer;
	}
	
	public void setOldOdometer(int odometer) {
		this.oldOdometer = odometer;
	}
	
	public int getCurrentOdometer() {
		return currentOdometer;
	}

	public void setCurrentOdometer(int newOdometer) {
		this.currentOdometer = newOdometer;
	}
	
	public int getGallons() {
		return gallons;
	}
	
	public void setGallons(int gallons) {
		this.gallons = gallons;
	}
	
	public double getCostPerGallon() {
		return costPerGallon;
	}
	
	public void setCostPerGallon(double costPerGallon) {
		this.costPerGallon = costPerGallon;
	}
}
