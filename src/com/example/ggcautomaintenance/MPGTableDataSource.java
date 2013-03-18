package com.example.ggcautomaintenance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	/**
	 * Adds a new fill up to the table
	 * @param fillNumber - always will be 1
	 * @param carName - name of car
	 * @param odometer - odometer reading
	 * @param gallons - number of gallons put in(optional)
	 * @param costPerGal - cost per gal (optional)
	 */
	public void addNewFill(int fillNumber, String carName, 
			int odometer, int gallons, double costPerGal) {
		//Create a content values to hole all the info
		ContentValues values = new ContentValues();
		values.put(MPGTableHelper.COLUMN_FILL_NUMBER, fillNumber);
		values.put(MPGTableHelper.COLUMN_CAR_NAME, carName);
		values.put(MPGTableHelper.COLUMN_ODOMETER, odometer);
		values.put(MPGTableHelper.COLUMN_GALLONS, gallons);
		values.put(MPGTableHelper.COLUMN_COST_PER_GALLON, costPerGal);
		
		String[] fillNum = new String[1];
		fillNum[0] = fillNum.toString();

		//replace the first entry of the table with new fill up
		database.update(MPGTableHelper.TABLE_COMMENTS, values,
				MPGTableHelper.COLUMN_FILL_NUMBER, fillNum);
		
//		//add fill to table
//		database.insert(MPGTableHelper.TABLE_COMMENTS, null, values);
	}

	//Get old MPG
	public int getOldMileage(int oldFillNum) {
		//TODO stuff to get the previous fill up mileage

		//Create a cursor to hold the data 
		//till we can convert to int
		Cursor cursor = database.rawQuery("SELECT odometer" +
				"FROM mpg" +
				"WHERE fillnumber=" + oldFillNum + ";", 
				null);

		cursor.moveToFirst();
		MPG mpg = new MPG();
		Integer integer = cursorToInt(cursor);
		mpg.setOldOdometer(integer);
		cursor.close();
		return mpg.getOldOdometer();
	}

	public void setCurrentMileaga(int odometer) {
		ContentValues values = new ContentValues();
		values.put(MPGTableHelper.COLUMN_ODOMETER, odometer);

		String[] whereArgs = new String[1];
		whereArgs[0] = "2";
		//update table
		database.update(MPGTableHelper.TABLE_COMMENTS, 
				values, MPGTableHelper.COLUMN_FILL_NUMBER ,
				whereArgs);
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

	//When you do a query it returns a cursor. 
	//must convert to Integer
	private int cursorToInt(Cursor cursor) {
		Integer integer = new Integer(0);
		integer = cursor.getInt(0);
		return integer;
	}

}
