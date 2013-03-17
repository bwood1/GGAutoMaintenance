package com.example.ggcautomaintenance;

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
	public void open() throws SQLException {
		database = carDbHelper.getWritableDatabase();
	}

	//Close database
	public void close() {
		carDbHelper.close();
	}

	//Create a new car
	public Car createCar(String carName, String carMake,
			String carModel, int odometer) {
		//TODO stuff to add a new car to the database
		ContentValues values = new ContentValues();
		values.put(CarTableHelper.COLUMN_CAR_NAME, carName);
		values.put(CarTableHelper.COLUMN_MAKE, carMake);
		values.put(CarTableHelper.COLUMN_MODEL, carModel);
		values.put(CarTableHelper.COLUMN_ODOMETER, odometer);

		Cursor cursor = database.query(CarTableHelper.TABLE_CAR, 
				allColumns,	null, null, null, null, null);

		cursor.moveToFirst();
		Car newCar = cursorToCar(cursor);
		cursor.close();
		return newCar;
	}

	//Delete a car (shouldn't need to use this but creating
	//stub anyways
	public void deleteCar(Car car) {
		String carName = car.getCarName();
		System.out.println("Car deleted with name: " + carName);
		database.delete(CarTableHelper.TABLE_CAR, 
				CarTableHelper.COLUMN_CAR_NAME + " = " + carName, 
				null);
	}

	//Get all cars from table
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

	//Get the odometer reading for the specified car
	public int getOdometer(String carName) {
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

	//updates the odometer
	public void setOdometer(String carName, int odometer) {
		// create content values for new odometer reading
		ContentValues values = new ContentValues();
		values.put(CarTableHelper.COLUMN_ODOMETER, odometer);

		//update database
		database.update(CarTableHelper.TABLE_CAR, 
				values, null, null);
	}

	//Not sure what this does yet but I think its important
	private Car cursorToCar(Cursor cursor) {
		Car car = new Car();
		car.setCarName(cursor.getString(0));
		car.setCarMake(cursor.getString(1));
		car.setCarModel(cursor.getString(2));
		car.setOdometer(cursor.getInt(4));
		return car;
	}

	private int cursorToInt(Cursor cursor) {
		Integer integer = new Integer(0);
		integer = cursor.getInt(0);
		return integer;
	}
}
