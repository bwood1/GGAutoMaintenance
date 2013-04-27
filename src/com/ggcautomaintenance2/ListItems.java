package com.ggcautomaintenance2;

public class ListItems {
	
	private int maintId;
	private String maintDescription;
	private int mileageNextDue;
	private String dateNextDue;
	
	// Null Constructor
	public ListItems() {
		super();
	}
	
	/**
	 * Constructor
	 * @param maintID
	 * @param maintDescription
	 * @param mileageNextDue
	 * @param dateNextDue
	 */
	public ListItems(int maintID, String maintDescription, int mileageNextDue, String dateNextDue) {
		super();
		this.maintId = maintID;
		this.maintDescription = maintDescription;
		this.mileageNextDue = mileageNextDue;
		this.dateNextDue = dateNextDue;
	}
	
	/**
	 * Returns the maintenance ID
	 * @return maintID
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
	 * Returns the mileage due
	 * @return mileageNextDue
	 */
	public int getMileageNextDue() {
		return mileageNextDue;
	}
	
	/**
	 * Sets the mileage due date
	 * @param mileageNextDue
	 */
	public void setMileageNextDue(int mileageNextDue) {
		this.mileageNextDue = mileageNextDue;
	}
	
	/**
	 * Returns the next due date
	 * @return dateNextDue
	 */
	public String getDateNextDue() {
		return dateNextDue;
	}
	
	/**
	 * Sets teh next due date
	 * @param dateNextDue
	 */
	public void setDateNextDue(String dateNextDue) {
		this.dateNextDue = dateNextDue;
	}
	
	/**
	 * Displays the maintenance items list object
	 */
	@Override
	public String toString() {
		return this.maintId + "." + this.maintDescription + "." + this.mileageNextDue + "." + this.dateNextDue;
	}
}