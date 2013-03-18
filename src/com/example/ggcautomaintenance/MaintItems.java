package com.example.ggcautomaintenance;

public class MaintItems {
	private int maintId;
	private String maintDescription;
	private int mileageInterval;
	private int timeInterval;
	
	public MaintItems() {
		super();
	}
	
	public MaintItems(int maintID, String maintDescription, int mileageInterval, int timeInterval) {
		super();
		this.maintId = maintID;
		this.maintDescription = maintDescription;
		this.mileageInterval = mileageInterval;
		this.timeInterval = timeInterval;		
	}
	
	public int getMaintId() {
		return maintId;
	}
	
	public void setMaintId(int maintId) {
		this.maintId = maintId;
	}
	
	public String getMaintDescription() {
		return maintDescription;
	}
	
	public void setMaintDescription(String maintDescription) {
		this.maintDescription = maintDescription;
	}
	
	public int getMileageInterval() {
		return mileageInterval;
	}
	
	public void setMileageInterval(int mileageInterval) {
		this.mileageInterval = mileageInterval;
	}
	
	public int getTimeInterval() {
		return timeInterval;
	}
	
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	@Override
	public String toString() {
		return this.maintId + "." + this.maintDescription + "." + this.mileageInterval + "." + this.timeInterval;
	}
}
