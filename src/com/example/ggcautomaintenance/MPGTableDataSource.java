package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MPGTableDataSource {
	
	//Database fields
			private SQLiteDatabase database;
			private MPGTableHelper mpgDbHelper;
			private String[] allColumns = {
								MPGTableHelper.COLUMN_FILL_NUMBER,
								MPGTableHelper.COLUMN_CAR_NAME,
								MPGTableHelper.COLUMN_ODOMETER,
								MPGTableHelper.COLUMN_GALLONS,
								MPGTableHelper.COLUMN_COST_PER_GALLON};
			
			//Constructor
			public MPGTableDataSource (Context context) {
				mpgDbHelper = new MPGTableHelper(context);
			}
			
			//Open database
			public void open() throws SQLException {
				database = mpgDbHelper.getWritableDatabase();
			}

			//Close database
			public void close() {
				mpgDbHelper.close();
			}
			
			//Get old MPG
			public int getOldMileage(int oldFillNum) {
				oldFillNum = 1;
				int prevMileage = 0;
				//TODO stuff to get the previous fill up mileage
				return prevMileage;
			}
			
			//Get current mileage
			public int getCurrentMileage() {
				int newMileage = 0;
				//TODO stuff to get the current fill up mileage from the car table
				return newMileage;
			}
			
			//add a new mpg to the table
			public MPG addMPG(int fillNumber, String carName, int oldOdomter, 
									int gallons, double costPerGallon) {
				//TODO stuff to add a new fill up to the database
				return new MPG();
			}
			
			//will update the old MPG with the new data
			public MPG updateMPG(int fillNumber, String carName, int oldOdometer,
										int gallons, double costPerGallon) {
				//TODO stuff to update the MPG entry in the database
				return new MPG();
			}

}
