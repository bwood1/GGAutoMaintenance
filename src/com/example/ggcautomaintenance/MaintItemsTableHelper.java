package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaintItemsTableHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_MAINT_ITEMS = "MaintenanceRecord";
	public static final String COLUMN_MAINT_ID = "MaintID";
	public static final String COLUMN_MAINT_DESCRIPTION = "MaintDescription";
	public static final String COLUMN_MILEAGE_INTERVAL = "MileageInterval";
	public static final String COLUMN_TIME_INTERVAL = "TimeInterval"; 
	
	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_MAINT_ITEMS_TABLE = "CREATE TABLE  " + 
											TABLE_MAINT_ITEMS + "(" + 
												COLUMN_MAINT_ID + "INTEGER PRIMARY KEY " +
													"AUTOINCREMENT, " + 
													COLUMN_MAINT_DESCRIPTION + "TEXT(20), " + 
													COLUMN_MILEAGE_INTERVAL + "INTEGER, " + 
													COLUMN_TIME_INTERVAL + "INTEGER);";
	
	public MaintItemsTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MAINT_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MaintItemsTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destory all old data");
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_MAINT_ITEMS_TABLE);
		onCreate(db);
	}

}
