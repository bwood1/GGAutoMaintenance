package com.ggcautomaintenance;

/**
 * @author Joey Armendariz
 * @version 1.0
 * This class returns the following:
 *    - Miles Per Gallon
 *    - Total Trip Miles
 *    - Help Display Popup
 */
public class MPGCalculator {
	
	public CarMaintDataSource dataSource;
	private static float totalMilesDriven;
	private static float numberOfGallons;
	private static int currentOdometer;	
	public static Odometer odometer;
	public static int odometerValue;
	
	/**
	 * Null Constructor MPGCalculator
	 */
	public MPGCalculator() {
		totalMilesDriven = 0;
		numberOfGallons = 0;
	}

	/**
	 * Constructor MPGCalculator
	 * @param totMilesDriven - the total number of miles driven
	 * @param numOfGallons - the total number of gallons to fill tank
	 */
	public MPGCalculator(float totMilesDriven, float numOfGallons, CarMaintDataSource dataSourceConstructor) {
		totalMilesDriven = totMilesDriven;
		numberOfGallons = numOfGallons;
		this.dataSource = dataSourceConstructor;
	}
	
	
	/**
	 * getMPG method returns Miles Per Gallon
	 * @return milesPerGallon - the miles per gallon
	 */
	public float getMPG() {
		float milesPerGallon = 0;
		milesPerGallon = (totalMilesDriven / numberOfGallons);
		
		return milesPerGallon;
	}
	
	/**
	 * Gets the last fillup mileage from the mpg table
	 * @return - the old mileage
	 */
	public int getFillupMileage() {
		return dataSource.getFillupMileage();
	}
	
	/**
	 * sets the last fillup odometer reading in the mpg table
	 */
	public void setFillupMileage()	{
		dataSource.setFillupMileage(dataSource.getCurrentMileage());
	}		

	/**
	 * Gets the current mileage from the MPG Table
	 * @return - current mileage
	 */
	public int getCurrentMileage() {
		return dataSource.getCurrentMileage();
	}

	/**
	 * Sets the database according to the odometer dial on 
	 * the home screen
	 */
	public void setCurrentMileage() {
		dataSource.setCurrentMileage(currentOdometer);
	}
	
	/**
	 * Returns the miles driven
	 * @return milesDriven
	 */
	public int getMilesDriven() {
		int milesDriven = getCurrentMileage() - getFillupMileage();		

		return milesDriven;
	}
}
