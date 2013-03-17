package com.example.ggcautomaintenance;

/**
 * @author Joey Armendariz
 * @version 1.0
 * This class returns the following:
 *    - Miles Per Gallon
 *    - Total Trip Miles
 *    - Help Display Popup
 */
public class MPGCalculator {
	
	private static float totalMilesDriven;
	private static float numberOfGallons;
	
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
	public MPGCalculator(float totMilesDriven, float numOfGallons) {
		totalMilesDriven = totMilesDriven;
		numberOfGallons = numOfGallons;
	}
	
	
	/**
	 * getMPG method returns Miles Per Gallon
	 * @return milesPerGallon - the miles per gallon
	 */
	public float getMPG() {
		float milesPerGallon = 0;
		milesPerGallon = totalMilesDriven / numberOfGallons;
		
		return milesPerGallon;
	}
	
	/**
	 * displayHelp method returns a popup message to the user
	 * @return message - popup message to user
	 */
	public void displayHelp() {
		
	}
	
	/**
	 * getTrip method returns the total trip taken since last update
	 * @return totTripMiles - the total miles of the trip.
	 */
	public float getTrip() {
		MPG mpg = new MPG();
		int newMileage = mpg.getCurrentOdometer();
		int oldMileage = mpg.getOldOdometer();
		
		float totTripMiles = newMileage - oldMileage;
		
		return totTripMiles;
	}
}
