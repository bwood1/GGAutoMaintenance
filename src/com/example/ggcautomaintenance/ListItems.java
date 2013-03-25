package com.example.ggcautomaintenance;

public class ListItems {
	
	private int maintId;
	private String maintDescription;
	private int mileageNextDue;
	private String dateNextDue;
	
	public ListItems() {
		super();
	}
	
	public ListItems(int maintID, String maintDescription, int mileageNextDue, String dateNextDue) {
		super();
		this.maintId = maintID;
		this.maintDescription = maintDescription;
		this.mileageNextDue = mileageNextDue;
		this.dateNextDue = dateNextDue;
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

	public int getMileageNextDue() {
		return mileageNextDue;
	}

	public void setMileageNextDue(int mileageNextDue) {
		this.mileageNextDue = mileageNextDue;
	}

	public String getDateNextDue() {
		return dateNextDue;
	}

	public void setDateNextDue(String dateNextDue) {
		this.dateNextDue = dateNextDue;
	}
	
	@Override
	public String toString() {
		return this.maintId + "." + this.maintDescription + "." + this.mileageNextDue + "." + this.dateNextDue;
	}
}
