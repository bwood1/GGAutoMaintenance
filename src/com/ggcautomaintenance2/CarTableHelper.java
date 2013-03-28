package com.ggcautomaintenance2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_CAR = "car";
	public static final String COLUMN_CAR_NAME = "CarName";
	public static final String COLUMN_MAKE = "Make";
	public static final String COLUMN_MODEL = "Model";
	public static final String COLUMN_ODOMETER = "Odometer";

	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 2;
	
	//Database creation statement
	private static final String CREATE_CAR_TABLE = "CREATE TABLE " + TABLE_CAR +  "(" +
														COLUMN_CAR_NAME + " TEXT(20) not null, " +
														COLUMN_MAKE + " TEXT(15), " +
														COLUMN_MODEL + " TEXT(20), " +
														COLUMN_ODOMETER + " INTEGER, " +
														"PRIMARY KEY(CarName));";
	// Constructor
	public CarTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * onCreate method
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CAR_TABLE);
		
		//create initial values to insert into table
		ContentValues values = new ContentValues();
		String carName = "car1";
		values.put(CarTableHelper.COLUMN_CAR_NAME, carName);
		String carMake = "make1";
		values.put(CarTableHelper.COLUMN_MAKE, carMake);
		String carModel = "model1";
		values.put(CarTableHelper.COLUMN_MODEL, carModel);
		int odometer = 0;
		values.put(CarTableHelper.COLUMN_ODOMETER, odometer);
		
		// insert values into table
//		db.insert(this.TABLE_CAR, null, values);
	}
	
	/**
	 * onUpgrade method
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(CarTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
		onCreate(db);
	}
}
