package com.example.ggcautomaintenance;

public class MaintItems {
	private int maintId;
	private String maintDescription;
	private int mileageInterval;
	private int timeInterval;
	
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

}
