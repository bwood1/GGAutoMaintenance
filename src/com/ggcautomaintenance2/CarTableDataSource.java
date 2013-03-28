package com.ggcautomaintenance2;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CarTableDataSource {

	//Database fields
	private SQLiteDatabase database;
	private CarTableHelper carDbHelper;
	
	private String[] allColumns = {
			CarTableHelper.COLUMN_CAR_NAME,
			CarTableHelper.COLUMN_MAKE, 
			CarTableHelper.COLUMN_MODEL,
			CarTableHelper.COLUMN_ODOMETER};

	//Constructor
	public CarTableDataSource (Context context) {
		carDbHelper = new CarTableHelper(context);
	}

	//Open database
	public SQLiteDatabase open() throws SQLException {
		database = carDbHelper.getWritableDatabase();
		return database;
	}

	//Close database
	public void close() {
		carDbHelper.close();
	}

	//Create a new car
	public void addCarToDatabase(String carName, String carMake,
			String carModel, int odometer) {
		//create a ContentValues to hold the information for the new car
		ContentValues values = new ContentValues();
		values.put(CarTableHelper.COLUMN_CAR_NAME, carName);
		values.put(CarTableHelper.COLUMN_MAKE, carMake);
		values.put(CarTableHelper.COLUMN_MODEL, carModel);
		values.put(CarTableHelper.COLUMN_ODOMETER, odometer);

		//add car to table
		database.insert(CarTableHelper.TABLE_CAR, null, values);
	}

	//Delete the car table (shouldn't need to use this but creating
	//stub anyways for testing purposes)
	public void deleteCarTable(String tableName) {
//		/*this way sucks
//		String carName = car.getCarName();
//		System.out.println("Car deleted with name: " + carName);
//		database.delete(CarTableHelper.TABLE_CAR, 
//				CarTableHelper.COLUMN_CAR_NAME + " = " + carName, 
//				null);
//		 */
 
		//this is much better
		database.delete("car", null, null);
	}

	//Get all cars from table
	//(Dont think this will be needed either)
	public List<Car> getAllCars() {
		List<Car> cars = new ArrayList<Car>();
		//TODO stuff to get all cars from table

		//Have to create a cursor because SQLite is weird
		Cursor cursor = database.query(CarTableHelper.TABLE_CAR, 
				allColumns, null, null, null, null, null);
		//move to first row in cursor
		cursor.moveToFirst();
		//now go through all of the rows in the cursor and convert
		while(!cursor.isAfterLast()) {
			Car car = cursorToCar(cursor); //convert current row to Car
			cars.add(car); //add car
			cursor.moveToNext(); //move on
		}
		cursor.close(); //DONT FORGET TO CLOSE
		return cars; //and done
	}

	/**
	 * gets the odometer reading from the database
	 * @return the odomter reading
	 */
	public int getOdometer() {
		//TODO stuff to get the odometer reading
		String[] temp = new String[1];
		temp[0] = CarTableHelper.COLUMN_ODOMETER;

		//Have to create a cursor because SQLite is weird
		Cursor cursor = database.rawQuery("SELECT odometer " +
				"FROM car;",
				null);
	
		cursor.moveToFirst();	//move to the first row
		Car car = new Car(); 	//create a new car
		Integer integer = cursorToInt(cursor); 	//convert to int
		car.setOdometer(integer); 		//assign the car's odometer to an integer
		cursor.close(); 	//DONT FORGET TO CLOSE
		return car.getOdometer(); 	//FINALLY!!!
	}

	/**
	 * updates the odometer reading with the value passed in
	 * @param odometer - the new odometer reading
	 */
	public void setOdometer(int odometer) {
		// create content values for new odometer reading
		ContentValues values = new ContentValues();
		values.put(CarTableHelper.COLUMN_ODOMETER, odometer);
		
		//update database
		database.update(CarTableHelper.TABLE_CAR, 
				values, null, null);
	}

	//When you do a query it returns a cursor. 
	//must convert to Car
	private Car cursorToCar(Cursor cursor) {
		Car car = new Car();
		car.setCarName(cursor.getString(0));
		car.setCarMake(cursor.getString(1));
		car.setCarModel(cursor.getString(2));
		car.setOdometer(cursor.getInt(4));
		return car;
	}

	//When you do a query it returns a cursor. 
	//must convert to Integer
	private int cursorToInt(Cursor cursor) {
		Integer integer = new Integer(0);
		integer = cursor.getInt(0);
		return integer;
	}
}
