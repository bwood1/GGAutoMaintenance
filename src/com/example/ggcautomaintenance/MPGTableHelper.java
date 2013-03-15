package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MPGTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "mpg";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_MPG_TABLE = "CREATE TABLE " + TABLE_COMMENTS + 
													"(fillnumber INTEGER PRIMARY KEY AUTOINCREMENT, " +
													"carname TEXT(20), " +
													"odometer INTEGER, " +
													"gallons INTEGER, " +
													"costpergallon CURRENCY, " +
													"FOREIGN KEY(odometer) REFRENCES car(odometer);";

	public MPGTableHelper(Context context) {
		super(context, CREATE_MPG_TABLE, null, DATABASE_VERSION);
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