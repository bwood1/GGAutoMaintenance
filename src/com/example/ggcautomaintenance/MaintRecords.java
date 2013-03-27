package com.example.ggcautomaintenance;

public class MaintRecords {
	private int maintRecordId;
	private String maintCompleteDate;
	private String carName;
	private int maintId;
	private int odometer;
	private double cost;
	
	/**
	 * Returns the maintenance record ID
	 * @return maintRecordId
	 */
	public int getMaintRecordId() {
		return maintRecordId;
	}
	
	/**
	 * Sets the maintenance record ID
	 * @param maintRecordId
	 */
	public void setMaintRecordId(int maintRecordId) {
		this.maintRecordId = maintRecordId;
	}
	
	/**
	 * Returns the maintenance completion date
	 * @return maintCompleteDate
	 */
	public String getMaintCompleteDate() {
		return maintCompleteDate;
	}
	
	/**
	 * Sets teh maintenance completion date
	 * @param maintCompleteDate
	 */
	public void setMaintCompleteDate(String maintCompleteDate) {
		this.maintCompleteDate = maintCompleteDate;
	}
	
	/**
	 * Returns the car name
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
	 * Returns the maintenance ID
	 * @return maintId
	 */
	public int getMaintId() {
		return maintId;
	}
	
	/**
	 * Sets the maintenance ID
	 * @param maintId
	 */
	public void setMaintId(int maintId) {
		this.maintId = maintId;
	}
	
	/**
	 * Returns the odometer reading
	 * @return odometer
	 */
	public int getOdometer() {
		return odometer;
	}
	
	/**
	 * Sets the odometer reading
	 * @param odometer
	 */
	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}
	
	/**
	 * Returns cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Sets cost
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
