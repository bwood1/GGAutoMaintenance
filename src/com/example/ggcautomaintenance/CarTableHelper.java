package com.example.ggcautomaintenance;

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
	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_CAR_TABLE = "CREATE TABLE " + TABLE_CAR +  "(" +
														COLUMN_CAR_NAME + " TEXT(20) not null, " +
														COLUMN_MAKE + " TEXT(15), " +
														COLUMN_MODEL + " TEXT(20), " +
														COLUMN_ODOMETER + " INTEGER, " +
														"PRIMARY KEY(CarName));";

	public CarTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CAR_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(CarTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
		onCreate(db);
	}
}
