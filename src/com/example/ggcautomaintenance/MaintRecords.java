package com.example.ggcautomaintenance;

public class MaintRecords {
	private int maintRecordId;
	private String maintCompleteDate;
	private String carName;
	private int maintId;
	private int odometer;
	private double cost;
	
	public int getMaintRecordId() {
		return maintRecordId;
	}
	
	public void setMaintRecordId(int maintRecordId) {
		this.maintRecordId = maintRecordId;
	}
	
	public String getMaintCompleteDate() {
		return maintCompleteDate;
	}
	
	public void setMaintCompleteDate(String maintCompleteDate) {
		this.maintCompleteDate = maintCompleteDate;
	}
	
	public String getCarName() {
		return carName;
	}
	
	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	public int getMaintId() {
		return maintId;
	}
	
	public void setMaintId(int maintId) {
		this.maintId = maintId;
	}
	
	public int getOdometer() {
		return odometer;
	}
	
	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
}
