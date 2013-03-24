package com.example.ggcautomaintenance;

import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarMaintTableHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 5;

	// Static Variables for Maintenance Items Table
	public static final String TABLE_MAINT_ITEMS = "MaintenanceItems";
	public static final String MI_MAINT_ID = "MaintID";
	public static final String MI_MAINT_DESCRIPTION = "MaintDescription";
	public static final String MI_MILEAGE_INTERVAL = "MileageInterval";
	public static final String MI_TIME_INTERVAL = "TimeInterval";

	// Static Variable for Maintenance Records Table
	public static final String TABLE_MAINT_RECORDS = "MaintRecord";
	public static final String MR_MAINT_RECORD_ID = "MaintRecordID";
	public static final String MR_MAINT_COMPLETE_DATE = "MaintCompleteDate";
	public static final String MR_CAR_NAME = "CarName";
	public static final String MR_MAINT_ID = "MaintID";
	public static final String MR_ODOMETER = "Odometer";
	public static final String MR_COST = "Cost";
	public static final String MR_MAINT_DUE_DATE = "MaintDueDate";
	public static final String MR_MAINT_DUE_MILEAGE = "MaintDueMileage";

	// Static Variables for MPG Table
	public static final String TABLE_MPG = "MPG";
	public static final String MPG_FILL_NUMBER = "FillNumber";
	public static final String MPG_CAR_NAME = "CarName";
	public static final String MPG_ODOMETER = "Odometer";
	public static final String MPG_GALLONS = "Gallons";
	public static final String MPG_COST_PER_GALLON = "CostPerGallon";


	//Database creation statement
	private static final String CREATE_MAINT_ITEMS_TABLE = "CREATE TABLE  " + 
			TABLE_MAINT_ITEMS + "(" + 
			MI_MAINT_ID + " INTEGER PRIMARY KEY, " +
			MI_MAINT_DESCRIPTION + " TEXT(20), " + 
			MI_MILEAGE_INTERVAL + " INTEGER, " + 
			MI_TIME_INTERVAL + " INTEGER);";

	private static final String CREATE_MAINTRECORDS_TABLE = "CREATE TABLE " + TABLE_MAINT_RECORDS + "(" +
			MR_MAINT_RECORD_ID + " INTEGER PRIMARY KEY, " +
			MR_MAINT_COMPLETE_DATE + " TEXT, " +
			MR_CAR_NAME + " TEXT(20), " +
			MR_MAINT_ID + " INTEGER, " +
			MR_ODOMETER + " INTEGER, " +
			MR_COST + " CURRENCY, " +
			MR_MAINT_DUE_DATE + " TEXT, " + 
			MR_MAINT_DUE_MILEAGE + " INTEGER);";

	private static final String CREATE_MPG_TABLE = "CREATE TABLE " + TABLE_MPG + "(" + 
			MPG_FILL_NUMBER + " INTEGER PRIMARY KEY, " +
			MPG_CAR_NAME + " TEXT(20), " +
			MPG_ODOMETER +  " INTEGER, " +
			MPG_GALLONS + " INTEGER, " +
			MPG_COST_PER_GALLON + " CURRENCY);";

	private static final String CREATE_CAR_TABLE = "CREATE TABLE " + CarTableHelper.TABLE_CAR +  "(" +
			CarTableHelper.COLUMN_CAR_NAME + " TEXT(20) not null, " +
			CarTableHelper.COLUMN_MAKE + " TEXT(15), " +
			CarTableHelper.COLUMN_MODEL + " TEXT(20), " +
			CarTableHelper.COLUMN_ODOMETER + " INTEGER, " +
			"PRIMARY KEY(CarName));";

	public CarMaintTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MAINT_ITEMS_TABLE);
		db.execSQL(CREATE_MAINTRECORDS_TABLE);
		db.execSQL(CREATE_MPG_TABLE);
		//db.execSQL(CREATE_CAR_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(CarMaintTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destory all old data");
		db.execSQL("DROP TABLE IF EXISTS MaintenanceRecord");
		onCreate(db);
	}
}