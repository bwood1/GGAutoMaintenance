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
		currentOdometer = dataSource.getMileage();
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
		dateLast = dataSource.getMaintCompleteDate(maintId);
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
		milesReturn = dataSource.getMileage() - (dataSource.getOdometer(maintId));
		return milesReturn;		
	}

	public void setMilesSince(){

	}

}