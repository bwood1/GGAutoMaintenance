package com.ggcautomaintenance;

public class MPG {
	private int fillNumber;
	private String carName;
	private int oldOdometer; //mileage from last fill up
	private int currentOdometer; //mileage from current fill up
	private int gallons;
	private double costPerGallon;
	
	/**
	 * Returns fill number
	 * @return fillNumber
	 */
	public int getFillNumber() {
		return fillNumber;
	}
	
	/**
	 * Sets the fill number
	 * @param fillNumber
	 */
	public void setFillNumber(int fillNumber) {
		this.fillNumber = fillNumber;
	}
	
	/**
	 * Returns car name
	 * @return carName
	 */
	public String getCarName() {
		return carName;
	}
	
	/**
	 * Sets the car name
	 * @param carName
	 */
	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	/**
	 * Returns the old odometer reading
	 * @return oldOdometer
	 */
	public int getOldOdometer() {
		return oldOdometer;
	}
	
	/**
	 * Sets the old odometer reading
	 * @param odometer
	 */
	public void setOldOdometer(int odometer) {
		this.oldOdometer = odometer;
	}
	
	/**
	 * Returns the current odometer reading
	 * @return currentOdometer
	 */
	public int getCurrentOdometer() {
		return currentOdometer;
	}
	
	/**
	 * Sets the current odometer reading
	 * @param newOdometer
	 */
	public void setCurrentOdometer(int newOdometer) {
		this.currentOdometer = newOdometer;
	}
	
	/**
	 * Returns number of gallons
	 * @return gallons
	 */
	public int getGallons() {
		return gallons;
	}
	
	/**
	 * Sets the number of gallons
	 * @param gallons
	 */
	public void setGallons(int gallons) {
		this.gallons = gallons;
	}
	
	/**
	 * Returns the cost per gallon
	 * @return
	 */
	public double getCostPerGallon() {
		return costPerGallon;
	}
	
	/**
	 * Sets the cost per gallon
	 * @param costPerGallon
	 */
	public void setCostPerGallon(double costPerGallon) {
		this.costPerGallon = costPerGallon;
	}
}
