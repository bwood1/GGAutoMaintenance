package com.example.ggcautomaintenance;

import android.util.Log;

public class MIID{

	public CarMaintDataSource dataSource;

	public int maintId;
	int currentOdometer = Main.getCurrentMileage();
	
	/**
	 * Constructor
	 * @param maintId
	 * @param dataSourceConstructor
	 */
	public MIID(int maintId, CarMaintDataSource dataSourceConstructor) {
		super();
		this.maintId = maintId;
		this.dataSource = dataSourceConstructor;
	}
	
	/**
	 * Sets the current odometer reading
	 */
	public void setCurrentOdometer() { 
		currentOdometer = dataSource.getMileage();
	}

	/**
	 * Returns next due date as string
	 * @return 
	 */
	public String getDateNextDue() {
		return dataSource.getNextMaintDueDate(maintId);
	}	
	
	/**
	 * Returns mileage till next maintenance requirement
	 * @return milesReturn
	 */
	public int getMilesTill () {
		int milesReturn;
		milesReturn = (dataSource.getMaintDueMileage(maintId) - currentOdometer);
		return milesReturn;
	}
	
	/**
	 * Returns date of last service
	 * @return dateLast
	 */
	public String getDateLastServ () {
		String dateLast;
		dateLast = dataSource.getMaintCompleteDate(maintId);
		return dateLast;	
	}
	
	/**
	 * Returns last mileage returned
	 * @return milesReturn
	 */
	public int getMilesLast () {
		int milesReturn;
		milesReturn = (dataSource.getOdometer(maintId));
		return milesReturn;
	}
	
	/**
	 * Returns miles since last service
	 * @return milesReturn
	 */
	public int getMilesSince(){
		int milesReturn;
		milesReturn = dataSource.getMileage() - (dataSource.getOdometer(maintId));
		return milesReturn;		
	}
}