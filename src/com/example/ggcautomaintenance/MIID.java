package com.example.ggcautomaintenance;

import android.util.Log;

public class MIID{
	
	public CarMaintDataSource dataSource;
	
	public int maintId;
	int currentOdometer = Main.getCurrentMileage();
	
	public MIID(int maintId, CarMaintDataSource dataSourceConstructor) {
		super();
	this.maintId = maintId;
	this.dataSource = dataSourceConstructor;
	}
	
	public void setCurrentOdometer() { 
		currentOdometer = dataSource.getOldMileage();
	}
	
	
	public String getDateNextDue() {
		return dataSource.getNextMaintDueDate(maintId);
	}
	
	public void setDateNextDue(){
		
	}
	
	public int getMilesTill () {
		int milesReturn;
		milesReturn = (dataSource.getMaintDueMileage(maintId) - currentOdometer);
		return milesReturn;
	}
	
	public void setMilesTill(){
		
	}
	
	public String getDateLastServ () {
		String dateLast;
		dateLast = dataSource.getNextMaintCompleteDate(maintId);
		return dateLast;	
	}
	
	public void setDateLastServ(){
		
	}
	
	public int getMilesLast () {
		int milesReturn;
		milesReturn = (dataSource.getOdometer(maintId));
		return milesReturn;
	}
	
	public void setMilesLast(){
		
	}
	
	public int getMilesSince(){
		int milesReturn;
		Log.d("Brandon made this", "" + dataSource.getOdometer(maintId));
		milesReturn = currentOdometer - (dataSource.getOdometer(maintId));
		return milesReturn;		
	}
	
	public void setMilesSince(){
		
	}

}