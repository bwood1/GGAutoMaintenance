package com.ggcautomaintenance2;

public class MaintItems {
	private int maintId;
	private String maintDescription;
	private int mileageInterval;
	private int timeInterval;
	
	/**
	 * null constructor
	 */
	public MaintItems() {
		super();
	}
	
	/**
	 * Constructor
	 * @param maintID
	 * @param maintDescription
	 * @param mileageInterval
	 * @param timeInterval
	 */
	public MaintItems(int maintID, String maintDescription, int mileageInterval, int timeInterval) {
		super();
		this.maintId = maintID;
		this.maintDescription = maintDescription;
		this.mileageInterval = mileageInterval;
		this.timeInterval = timeInterval;		
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
	 */
	public void setMaintId(int maintId) {
		this.maintId = maintId;
	}
	
	/**
	 * Returns the maintenance description
	 * @return maintDescription
	 */
	public String getMaintDescription() {
		return maintDescription;
	}
	
	/**
	 * Sets the maintenance description
	 * @param maintDescription
	 */
	public void setMaintDescription(String maintDescription) {
		this.maintDescription = maintDescription;
	}
	
	/**
	 * Returns the mileage interval
	 * @return mileageInterval
	 */
	public int getMileageInterval() {
		return mileageInterval;
	}
	
	/**
	 * Sets the mileage interval
	 * @param mileageInterval
	 */
	public void setMileageInterval(int mileageInterval) {
		this.mileageInterval = mileageInterval;
	}
	
	/**
	 * Returns the time interval
	 * @return timeInterval
	 */
	public int getTimeInterval() {
		return timeInterval;
	}
	
	/**
	 * Sets the time interval
	 * @param timeInterval
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	/**
	 * Displays the mileage and time intervals
	 */
	@Override
	public String toString() {
		return this.maintId + "." + this.maintDescription + "." + this.mileageInterval + "." + this.timeInterval;
	}
}
