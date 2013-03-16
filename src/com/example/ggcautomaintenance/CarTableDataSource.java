package com.example.ggcautomaintenance;

import java.util.ArrayList;
import java.util.List;

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
		return new Car();
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

		return cars;
	}

	//Get the odometer reading for the specified car
	public int getOdometer(String carName) {
		int odometer = 0;
		//TODO stuff to get the odometer reading
		return odometer;
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
}
