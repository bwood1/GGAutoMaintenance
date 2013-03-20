package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MPGTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "mpg";
	public static final String COLUMN_FILL_NUMBER = "FillNumber";
	public static final String COLUMN_CAR_NAME = "CarName";
	public static final String COLUMN_ODOMETER = "Odometer";
	public static final String COLUMN_GALLONS = "Gallons";
	public static final String COLUMN_COST_PER_GALLON = "CostPerGallon";

	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 2;
	
	//Database creation statement
	private static final String CREATE_MPG_TABLE = "CREATE TABLE " + TABLE_COMMENTS + 
													"(fillnumber INTEGER PRIMARY KEY, " +
													"carname TEXT(20), " +
													"odometer INTEGER, " +
													"gallons INTEGER, " +
													"costpergallon CURRENCY);";

	public MPGTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MPG_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MPGTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}

}